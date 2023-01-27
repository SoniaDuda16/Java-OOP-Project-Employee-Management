public class EngineerEmployee extends Employee{
    //overload
    public int findSalary(int experience){
        if(experience>5){
            return 7000;
        }
        else{
            return 5000;
        }
    }
}
