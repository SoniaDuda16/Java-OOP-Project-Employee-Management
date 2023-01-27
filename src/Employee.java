public class Employee {
    public String name;
    public String department;
    public int age;
    public int experienceYears;
    public int salary;

    public String password;

    public Employee() {
    }

    public String getName() {
        return name;
    }

    public String getDepartment() {
        return department;
    }

    public int getAge() {
        return age;
    }

    public int getSalary() {
        return salary;
    }

    public int getExperienceYears() {
        return experienceYears;
    }

    public String getPassword() {
        return password;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setExperienceYears(int experienceYears) {
        this.experienceYears = experienceYears;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int findSalary(){
        return 0;
    }
}
