package Backend;

public class Employee extends User{

    protected String position;
    protected String salary;

    public Employee(int id, String name, String lastName, String address, String position, String salary, String login, String password) {
        super(id, name, lastName, address, login, password);
        this.position = position;
        this.salary = salary;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getSalary() {
        return salary;
    }

    public void setSalary(String salary) {
        this.salary = salary;
    }

    public String getFullName(){
        return this.getName() + " " + this.getLastName();
    }
}
