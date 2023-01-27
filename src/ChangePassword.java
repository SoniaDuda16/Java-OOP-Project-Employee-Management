import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Statement;

public class ChangePassword extends JDialog {
    private JPanel changePasswordPanel;
    private JPasswordField passwordText;
    private JButton cancelButton;
    private JButton changePasswordButton;
    public String name;
    public String passwod;

    public ChangePassword(String name,String password) {
        setTitle("Change Password");
        setContentPane(changePasswordPanel);
        setMinimumSize(new Dimension(500, 400));
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(changePasswordButton);
        this.name = name;
        this.passwod=password;

        cancelButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                dispose();
                Home home=new Home(name,password);
            }
        });

        changePasswordButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
               changePassword();
            }
        });
        setVisible(true);
    }

    private void changePassword() {
        String newPassword=String.valueOf(passwordText.getPassword());

        if(newPassword.isEmpty() ){
            JOptionPane.showMessageDialog(this,
                    "This Field is Mandatory",
                    "Please try again",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }
        change(name,passwod,newPassword);
    }

    private void change(String name,String password, String newPassword){
        final String DB_URL="jdbc:mysql://localhost/MyCompany?serverTimezone=UTC";
        final String USERNAME="root";
        final String PASSWORD="";
        try{
            Connection conn= DriverManager.getConnection(DB_URL,USERNAME,PASSWORD);
            Statement stmt=conn.createStatement();
            String sql="UPDATE account SET password=? WHERE name=? and password=?";
            PreparedStatement preparedStatement=conn.prepareStatement(sql);
            preparedStatement.setString(1,newPassword);
            preparedStatement.setString(2,name);
            preparedStatement.setString(3,password);

            int modifiedRows=preparedStatement.executeUpdate();
            if(modifiedRows>0){
                JOptionPane.showMessageDialog(this,
                        "Successful Modification of the Password",
                        "Succes",
                        JOptionPane.INFORMATION_MESSAGE);
                return;
            }
            stmt.close();
            conn.close();
            if(modifiedRows==0){
                JOptionPane.showMessageDialog(this,
                        "Password Unchanged",
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
        Home home=new Home(null,null);
    }
}

