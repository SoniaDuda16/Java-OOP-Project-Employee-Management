import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class EmployeeList extends JDialog{
    private JPanel employeeListPanel;
    private JTextArea employeesText;
    private JButton cancelButton;

    public EmployeeList(JFrame parent){
        super(parent);
        setTitle("All Employees");
        setContentPane(employeeListPanel);
        setMinimumSize(new Dimension(600,500));
        setModal(true);
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        final String DB_URL="jdbc:mysql://localhost/MyCompany?serverTimezone=UTC";
        final String USERNAME="root";
        final String PASSWORD="";

        try{
            Connection conn= DriverManager.getConnection(DB_URL,USERNAME,PASSWORD);
            Statement stmt=conn.createStatement();
            String sql="SELECT name,department,age,experience,salary FROM employees";
            PreparedStatement preparedStatement=conn.prepareStatement(sql);

            int counter=1;
            ResultSet resultSet=preparedStatement.executeQuery();
            while(resultSet.next()){
                String counterString=Integer.toString(counter);
                employeesText.append(counterString);
                employeesText.append(".) Name:");
                employeesText.append(resultSet.getString("name"));
                employeesText.append("   Department:");
                employeesText.append(resultSet.getString("department"));
                employeesText.append("   Age:");
                employeesText.append(resultSet.getString("age"));
                employeesText.append("   Experience Years:");
                employeesText.append(resultSet.getString("experience"));
                employeesText.append("   Salary:");
                employeesText.append(resultSet.getString("salary"));
                employeesText.append("\n");
                counter++;
            }
            stmt.close();
            conn.close();
        }
        catch(Exception e){
            e.printStackTrace();
        }

        setLocationRelativeTo(null);

        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                AdminMenu adminMenu=new AdminMenu(null);
            }
        });

        setVisible(true);
    }
    public static void main(String args[]){
        EmployeeList employeeList=new EmployeeList(null);
    }
}

