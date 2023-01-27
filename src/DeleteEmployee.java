import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Statement;

public class DeleteEmployee extends JDialog{
    private JPanel deleteEmployeePanel;
    private JButton cancelButton;
    private JTextField nameText;
    private JButton fireButton;

    public DeleteEmployee(JFrame parent){
        super(parent);
        String dep=new String("HR");
        setTitle("Delete Employee");
        setContentPane(deleteEmployeePanel);
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

        fireButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                fireEmployee();
            }
        });

        setVisible(true);
    }

    private void fireEmployee() {
        String name=nameText.getText();

        if(name.isEmpty()){
            JOptionPane.showMessageDialog(this,
                    "Thie Field is Mandatory",
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
        deleteEmployee(name);
        deleteAccount(name);
    }
    //public Employee employee;
    private void deleteEmployee(String name){
        Employee employee=null;
        final String DB_URL="jdbc:mysql://localhost/MyCompany?serverTimezone=UTC";
        final String USERNAME="root";
        final String PASSWORD="";
        try{
            Connection conn= DriverManager.getConnection(DB_URL,USERNAME,PASSWORD);
            Statement stmt=conn.createStatement();
            String sql="DELETE FROM employees WHERE name=?";
            PreparedStatement preparedStatement=conn.prepareStatement(sql);
            preparedStatement.setString(1,name);

            int deletedRows=preparedStatement.executeUpdate();
            if(deletedRows>0){
                JOptionPane.showMessageDialog(this,
                        "Successful Deletion",
                        "Succes",
                        JOptionPane.INFORMATION_MESSAGE);
                return;
            }
            stmt.close();
            conn.close();
            if(deletedRows==0){
                JOptionPane.showMessageDialog(this,
                        "This Employee does not exist",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        //return employee;
    }
    public void deleteAccount(String name){
        Employee employee=null;
        final String DB_URL="jdbc:mysql://localhost/MyCompany?serverTimezone=UTC";
        final String USERNAME="root";
        final String PASSWORD="";
        try{
            Connection conn= DriverManager.getConnection(DB_URL,USERNAME,PASSWORD);
            Statement stmt=conn.createStatement();
            String sql="DELETE FROM account WHERE name=?";
            PreparedStatement preparedStatement=conn.prepareStatement(sql);
            preparedStatement.setString(1,name);

            int deletedRows=preparedStatement.executeUpdate();
            stmt.close();
            conn.close();
        }catch(Exception e){
            e.printStackTrace();
        }
        //return employee;

    }
    public static void main(String args[]){
        DeleteEmployee deleteEmployee=new DeleteEmployee(null);
    }
}
