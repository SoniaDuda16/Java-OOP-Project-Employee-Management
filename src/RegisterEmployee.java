import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Statement;

public class RegisterEmployee extends JDialog {
    private JButton cancelButton;
    private JTextField NameText;
    private JTextField ageText;
    private JTextField experienceText;
    private JButton registerButton;
    private JLabel employeePanel;
    private JComboBox Departmentcombo;
    private JLabel label;
    private JPanel a;

    public RegisterEmployee(JFrame parent){
        super(parent);
        String dep=new String("HR");
        Departmentcombo.addItem(dep);
        dep=new String("Marketing");
        Departmentcombo.addItem(dep);
        dep=new String("Engineering");
        Departmentcombo.addItem(dep);
        setTitle("Add Employee");
        setContentPane(a);
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

        registerButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                registerEmployee();
            }
        });

        setVisible(true);
    }

    private void registerEmployee() {
        String name=NameText.getText();
        String department=Departmentcombo.getSelectedItem().toString();
        String age=ageText.getText();
        String experience=experienceText.getText();

        if(name.isEmpty()|| department.isEmpty() ||age.isEmpty() || experience.isEmpty()){
            JOptionPane.showMessageDialog(this,
                    "Complete all Fields",
                    "Please try Again",
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

        validation=age.matches("[0-9]+");
        if(validation==false ){
            JOptionPane.showMessageDialog(this,
                    "Invalid Age",
                    "Please try Again",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }
        validation=experience.matches("[0-9]+");
        if(validation==false ){
            JOptionPane.showMessageDialog(this,
                    "Invalid Experience",
                    "Please try Again",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        int experienceInt=Integer.parseInt(experience);
        int ageInt=Integer.parseInt(age);

        int salary=0;
        if(department.equals("HR")){
            HREmployee hrEmployee=new HREmployee();
            salary=hrEmployee.findSalary();
        }
        else if(department.equals("Marketing")){
            MarketingEmployee marketingEmployee=new MarketingEmployee();
            salary=marketingEmployee.findSalary(experienceInt);
        }
        else if(department.equals("Engineering")){
            EngineerEmployee engineerEmployee=new EngineerEmployee();
            salary=engineerEmployee.findSalary(experienceInt);
        }

        employee=addEmployee(name,department,ageInt,experienceInt,salary);
        if(employee==null){
            JOptionPane.showMessageDialog(this,
                    "Registration Failed",
                    "Try again",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    public Employee employee;
    private Employee addEmployee(String name,String department,int age,int experience,int salary){
        Employee employee=null;
        final String DB_URL="jdbc:mysql://localhost/MyCompany?serverTimezone=UTC";
        final String USERNAME="root";
        final String PASSWORD="";
        try{
            Connection conn= DriverManager.getConnection(DB_URL,USERNAME,PASSWORD);
            employee=new Employee();
            Statement stmt=conn.createStatement();
            String sql="INSERT INTO employees(name,department,age,experience,salary)"+
                    "VALUES(?,?,?,?,?)";
            PreparedStatement preparedStatement=conn.prepareStatement(sql);
            preparedStatement.setString(1,name);
            preparedStatement.setString(2,department);
            preparedStatement.setInt(3,age);
            preparedStatement.setInt(4,experience);
            preparedStatement.setInt(5,salary);


            int addedRows=preparedStatement.executeUpdate();
            if(addedRows>0){
                employee=new Employee();
                employee.name=name;
                employee.department=department;
                employee.age=age;
                employee.experienceYears=experience;
                employee.salary=salary;
                JOptionPane.showMessageDialog(this,
                        "Employee Registered Successfully",
                        "Succes",
                        JOptionPane.INFORMATION_MESSAGE);
            }
            stmt.close();
            conn.close();
        }catch(Exception e){
            e.printStackTrace();
        }


        try{
            Connection conn2= DriverManager.getConnection(DB_URL,USERNAME,PASSWORD);
            employee=new Employee();
            Statement stmt2=conn2.createStatement();
            String sql="INSERT INTO account(name,password)"+
                    "VALUES(?,?)";
            PreparedStatement preparedStatement=conn2.prepareStatement(sql);
            preparedStatement.setString(1,name);
            preparedStatement.setString(2,"123");

            int addedRows2=preparedStatement.executeUpdate();
            if(addedRows2>0){
                employee=new Employee();
                employee.name=name;
                employee.department=department;
                employee.age=age;
                employee.experienceYears=experience;
                employee.salary=salary;
            }
            stmt2.close();
            conn2.close();
        }catch(Exception e){
            e.printStackTrace();
        }
        return employee;
    }

    public static void main(String args[]){
        RegisterEmployee registerEmployee=new RegisterEmployee(null);
    }
}
