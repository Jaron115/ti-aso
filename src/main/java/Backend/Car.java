package Backend;

public class Car {

    private int id;
    private String brand;
    private String model;
    private String year;
    private String color;
    private Client client;

    public Car(int id, String brand, String model, String year, String color, Client client) {
        this.id = id;
        this.brand = brand;
        this.model = model;
        this.year = year;
        this.color = color;
        this.client = client;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public String carData(){
        return "Samochód: " + this.brand + " " + this.model + " Rok: " + this.year + " Kolor: " + this.color;
    }
}
