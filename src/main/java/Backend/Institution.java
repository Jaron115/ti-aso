package Backend;

public class Institution {

    private int Id;
    private String name;
    private String description;
    private String address;

    Institution(int id, String name, String description, String address) {
        Id = id;
        this.name = name;
        this.description = description;
        this.address = address;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
