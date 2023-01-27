public class MarketingEmployee extends Employee {
    //overload
    public int findSalary(int experience){
        int salary;
        if(experience>3){
            salary=(int) (Math.random() * (4000 -3000)) + 3000;
        }
        else{
            salary=(int) (Math.random() * (3500 -2000)) + 2000;
        }
        return salary;
    }
}