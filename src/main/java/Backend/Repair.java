package Backend;

public class Repair {
    private int id;
    private Client client;
    private Service service;
    private Car car;
    private Institution institution;
    private String date;
    private String status;
    private String description;

    public Repair(int id, Client client, Service service, Car car, Institution institution, String date, String status, String description) {
        this.id = id;
        this.client = client;
        this.service = service;
        this.car = car;
        this.institution = institution;
        this.date = date;
        this.status = status;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Service getService() {
        return service;
    }

    public void setService(Service service) {
        this.service = service;
    }

    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
    }

    public Institution getInstitution() {
        return institution;
    }

    public void setInstitution(Institution institution) {
        this.institution = institution;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getServiceName(){
        return this.service.getName();
    }

    public String getCarData(){
        return this.car.getBrand() + " " + this.car.getModel();
    }

    public String getInstitutionName(){
        return this.institution.getName();
    }

    public String getRepairData(){
        return "Usługa: " + this.service.getName() + " Pojazd: " + this.car.getBrand() + " " + this.car.getModel() + " Data: " + this.date;
    }

    public String getClientData(){
        return this.client.getName() + " " + this.client.getLastName();
    }

    public String getRepairDataWithClient(){
        return this.client.getName() + " " + this.client.getLastName() + " Usługa: " + this.service.getName() + " Pojazd: " + this.car.getBrand() + " " + this.car.getModel() + " Data: " + this.date;
    }
}
