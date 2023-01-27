import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class Login extends JDialog{
    private JTextField nameText;
    private JPasswordField passwordText;
    private JButton logInButton;
    private JButton cancelButton;
    private JPanel loginPanel;

    public Login(JFrame parent){
        super(parent);
        setTitle("Login");
        setContentPane(loginPanel);
        setMinimumSize(new Dimension(500,400));
        setModal(true);
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        logInButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name=nameText.getText();
                String password=String.valueOf(passwordText.getPassword());

                check(name,password);
                account=login(name,password);

                if(account!=null){
                    if(account.name.equals("amanov")&& account.password.equals("mergen")){
                        dispose();
                        AdminMenu adminMenu=new AdminMenu(null);
                    }
                    else {
                        dispose();
                        Home home=new Home(account.name,account.password);
                    }
                }
                else{
                    error();
                }
            }
        });
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        setVisible(true);
    }

    void check(String name,String password){
        if(name.isEmpty()||password.isEmpty()){
            JOptionPane.showMessageDialog(this,
                    "All Fields are Mandatory",
                    "Please try Again",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }
    }

    void error(){
        JOptionPane.showMessageDialog(this,
                "Incorrect Name or Password",
                "Please try Again",
                JOptionPane.ERROR_MESSAGE);
        return;
    }
    public Account account;

    private Account login(String name,String password){
        Account account=null;

        final String DB_URL="jdbc:mysql://localhost/MyCompany?serverTimezone=UTC";
        final String USERNAME="root";
        final String PASSWORD="";

        try{
            Connection conn= DriverManager.getConnection(DB_URL,USERNAME,PASSWORD);
            Statement stmt=conn.createStatement();
            String sql="SELECT * FROM account WHERE name=? AND password=?";
            PreparedStatement preparedStatement=conn.prepareStatement(sql);
            preparedStatement.setString(1,name);
            preparedStatement.setString(2,password);

            ResultSet resultSet=preparedStatement.executeQuery();
            if(resultSet.next()){
                account=new Account();
                account.name=resultSet.getString("name");
                account.password=resultSet.getString("password");
            }
            stmt.close();
            conn.close();
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return account;
    }

    public static void main(String[] args) {
        Login login=new Login(null);
    }
}
