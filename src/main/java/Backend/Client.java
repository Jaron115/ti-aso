package Backend;

public class Client extends User {

    protected String email;

    public Client(String id, String name, String lastName, String adres, String login, String password, String email) {
        super(id, name, lastName, adres, login, password);
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
