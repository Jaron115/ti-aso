package Backend;

public class Client extends User {

    private String email;

    Client(int id, String name, String lastName, String adres, String email, String login, String password ) {
        super(id, name, lastName, adres, login, password);
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getClientData(){
        return this.getName() + " " + this.getLastName();
    }
}
