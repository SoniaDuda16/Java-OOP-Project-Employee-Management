import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Statement;

public class ModifySalary extends JDialog {
    private JTextField nameText;
    private JTextField salaryText;
    private JButton modifyButton;
    private JButton cancelButton;
    private JPanel modifyPanel;

    public ModifySalary(JFrame parent){
        super(parent);
        String dep=new String("HR");
        setTitle("Modify Salary");
        setContentPane(modifyPanel);
        setMinimumSize(new Dimension(500,400));
        setModal(true);
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                AdminMenu adminMenu=new AdminMenu(null);
            }
        });

        modifyButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                modifySalary();
            }
        });

        setVisible(true);
    }

    private void modifySalary() {
        String name=nameText.getText();
        String salary=salaryText.getText();

        if(name.isEmpty() || salary.isEmpty()){
            JOptionPane.showMessageDialog(this,
                    "All Fields are Mandatory",
                    "Please try again",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        boolean validation=name.matches("[0-9]+");
        if(validation==true){
            JOptionPane.showMessageDialog(this,
                    "Invalid Name",
                    "Please try Again",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        validation=salary.matches("[0-9]+");
        if(validation==false){
            JOptionPane.showMessageDialog(this,
                    "Invalid Salary",
                    "Please try Again",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }
        int salaryInt=Integer.parseInt(salary);

        if(salaryInt<1500){
            JOptionPane.showMessageDialog(this,
                    "This Salary is too small",
                    "Please try Again",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }
        modify(name,salaryInt);
    }
    private void modify(String name,int salary){
        final String DB_URL="jdbc:mysql://localhost/MyCompany?serverTimezone=UTC";
        final String USERNAME="root";
        final String PASSWORD="";
        try{
            Connection conn= DriverManager.getConnection(DB_URL,USERNAME,PASSWORD);
            Statement stmt=conn.createStatement();
            String sql="UPDATE employees SET salary=? WHERE name=?";
            PreparedStatement preparedStatement=conn.prepareStatement(sql);
            preparedStatement.setInt(1,salary);
            preparedStatement.setString(2,name);

            int modifiedRows=preparedStatement.executeUpdate();
            if(modifiedRows>0){
                JOptionPane.showMessageDialog(this,
                        "Successful Modification",
                        "Succes",
                        JOptionPane.INFORMATION_MESSAGE);
                return;
            }
            stmt.close();
            conn.close();
            if(modifiedRows==0){
                JOptionPane.showMessageDialog(this,
                        "This Employee does not exist",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }
            stmt.close();
            conn.close();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    public static void main(String args[]){
        ModifySalary modifySalary=new ModifySalary(null);
    }
}
