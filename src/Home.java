import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class Home extends JDialog {
    private JPanel homePanel;
    private JButton logoutButton;
    private JButton changePasswordButton;
    private JLabel nameLabel;

    public String name;
    public String passwod;

    public Home(String name,String password) {
        setTitle("Home");
        setContentPane(homePanel);
        setMinimumSize(new Dimension(500, 400));
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(changePasswordButton);
        this.name = name;
        this.passwod=password;
        nameLabel.setText(name);

        logoutButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                dispose();
                Login login=new Login(null);
            }
        });

        changePasswordButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                dispose();
                ChangePassword changePassword=new ChangePassword(name,password);
            }
        });
        setVisible(true);
    }
    public static void main(String args[]){
        Home home=new Home(null,null);
    }
}