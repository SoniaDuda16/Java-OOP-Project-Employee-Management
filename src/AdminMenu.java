import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AdminMenu extends JDialog {
    private JButton registerButton;
    private JButton employeesListButton;
    private JButton fireButton;
    private JButton salaryButton;
    private JButton cancelButton;
    private JPanel menuPanel;

    public AdminMenu(JFrame parent){
        super(parent);
        setTitle("Menu");
        setContentPane(menuPanel);
        setMinimumSize(new Dimension(500,400));
        setModal(true);
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                dispose();
                Login login=new Login(null);
            }
        });

        registerButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                dispose();
                RegisterEmployee registerEmployee=new RegisterEmployee(null);
            }
        });
        employeesListButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                dispose();
                EmployeeList employeeList=new EmployeeList(null);
            }
        });
        fireButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                dispose();
                DeleteEmployee deleteEmployee=new DeleteEmployee(null);
            }
        });

        salaryButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                dispose();
                ModifySalary modifySalary=new ModifySalary(null);
            }
        });

        setVisible(true);
    }
    public static void main(String args[]){
        AdminMenu adminMenu=new AdminMenu(null);
    }

}
