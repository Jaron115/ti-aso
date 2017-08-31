/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Backend;


import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;


public class MainSystem {


    /*
    STATIC VARIABLE
     */
    private static int userID = 0;
    private static String userType = null;

    public static int getUserID() {
        return userID;
    }

    public static String getUserType() {
        return userType;
    }

    private static void setUserID(int userID) {
        MainSystem.userID = userID;
    }

    private static void setUserType(String userType) {
        MainSystem.userType = userType;
    }

    /*
    CLIENT LOGIN
     */
    public boolean clientLogin(String login, String password) throws SQLException {
        boolean loginStatus = false;

        DbConnection db = new DbConnection();

        ResultSet rs = db.Select("count(*) AS clientCount, id", "Klient", "login = '" + login + "' AND haslo = '" + password + "'");

        while(rs.next()){
            if(rs.getInt("clientCount") > 0){
                setUserID(rs.getInt("id"));
                setUserType("client");

                loginStatus = true;
            }
        }

        db.closeConnection();

        return loginStatus;
    }

    /*
    EMPLOYEE LOGIN
     */
    public boolean employeeLogin(String login, String password) throws SQLException {
        boolean loginStatus = false;

        DbConnection db = new DbConnection();

        ResultSet rs = db.Select("count(*) AS employeeCount, id", "Pracownik", "login = '" + login + "' AND haslo = '" + password + "'");

        while(rs.next()){
            if(rs.getInt("employeeCount") > 0){
                setUserID(rs.getInt("id"));
                setUserType("employee");

                loginStatus = true;
            }
        }

        db.closeConnection();

        return loginStatus;
    }

    /*
    USER LOGOUT
     */
    public void userLogout(){
        setUserID(0);
        setUserType("");
    }

    /*
    CLIENT REGISTER
     */
    public void registerClient(String name, String lastName, String address, String email, String login, String password) throws SQLException {

        DbConnection db = new DbConnection();

        db.Insert(
                "Klient",
                "imie, nazwisko, adres, email, login, haslo",
                "'" + name + "', '" + lastName + "', '" + address + "', '" + email + "', '" + login + "', '" + password + "'"
        );

//        db.Insert(
//                "Pracownik",
//                "imie, nazwisko, adres, stanowisko, wynagrodzenie, login, haslo",
//                "'" + name + "', '" + lastName + "', '" + address + "', 'mechanik', '3000', '" + login + "', '" + password + "'"
//        );

        db.closeConnection();
    }

    /*
    CHECK IF ACCOUNT EXIST
     */
    public boolean isAccountExist(String login) throws SQLException {
        boolean isAccountExist = false;
        int accountCount = 0;

        DbConnection db = new DbConnection();

        ResultSet rs = db.Select("count(*) AS existAccount","Klient","login = '" + login + "'");

        while(rs.next()){
            accountCount = rs.getInt("existAccount");
        }

        db.closeConnection();

        if(accountCount > 0){
            isAccountExist = true;
        }

        return isAccountExist;
    }

    /*
    MD5 PASSWORD
     */
    public String md5Password(String password) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        MessageDigest m = MessageDigest.getInstance("MD5");
        m.update(password.getBytes("UTF8"));
        byte s[] = m.digest();
        StringBuilder result = new StringBuilder();

        for (byte value : s) {
            result.append(Integer.toHexString((0x000000ff & value) | 0xffffff00).substring(6));
        }

        return result.toString();
    }

    /*
    GET DATA SERVICES FROM DATABASE
     */

    public List<Service> getServiceDataFromDatabase() throws SQLException {
        DbConnection db = new DbConnection();
        List<Service> serviceList = new ArrayList<>();
        ResultSet rs = db.Select("*", "Usluga", "");

        while(rs.next()){
            serviceList.add(
                    new Service(
                            rs.getInt("id"),
                            rs.getString("nazwa"),
                            rs.getString("opis"),
                            rs.getString("cena")
                    )
            );
        }

        db.closeConnection();

        return serviceList;
    }

      /*
    GET DATA INSTITUTION FROM DATABASE
     */

    public List<Institution> getInstitutionDataFromDatabase() throws SQLException {
        DbConnection db = new DbConnection();
        List<Institution> institutionList = new ArrayList<>();
        ResultSet rs = db.Select("*", "Placowka", "");

        while(rs.next()){
            institutionList.add(
                    new Institution(
                            rs.getInt("id"),
                            rs.getString("nazwa"),
                            rs.getString("opis"),
                            rs.getString("adres")
                    )
            );
        }

        db.closeConnection();

        return institutionList;
    }

     /*
    GET CLIENT CARS DATA FROM DATABASE
     */

    public List<Car> getCarDataFromDatabase() throws SQLException {
        DbConnection db = new DbConnection();
        List<Car> carList = new ArrayList<>();
        ResultSet rs = db.Select("*", "Pojazd p INNER JOIN Klient k ON p.id_klienta = k.id", "id_klienta = '" + getUserID() + "'");

        while(rs.next()){
            carList.add(
                    new Car(
                            rs.getInt("p.id"),
                            rs.getString("p.marka"),
                            rs.getString("p.model"),
                            rs.getString("p.rok_produkcji"),
                            rs.getString("p.kolor"),
                            new Client(
                                    rs.getInt("k.id"),
                                    rs.getString("k.imie"),
                                    rs.getString("k.nazwisko"),
                                    rs.getString("k.adres"),
                                    rs.getString("k.email"),
                                    rs.getString("k.login"),
                                    rs.getString("k.haslo")
                            )
                    )
            );
        }

        db.closeConnection();

        return carList;
    }

    /*
    ADD CAR TO CLIENT
     */

    public void clientCar(String brand, String model, String year, String color) throws SQLException {
        DbConnection db = new DbConnection();

        db.Insert(
                "Pojazd",
                "marka, model, rok_produkcji, kolor, id_klienta",
                "'" + brand + "', '" + model + "', '" + year + "', '" + color + "', '" + getUserID() + "'"
        );


        db.closeConnection();
    }

    /*
    ADD INSTITUTION
     */

    public void institutionAdd(String name, String address, String description) throws SQLException {
        DbConnection db = new DbConnection();

        db.Insert(
                "Placowka",
                "nazwa, opis, adres",
                "'" + name + "', '" + address + "', '" + description + "'"
        );


        db.closeConnection();
    }

    /*
    UPDATE INSTITUTION
     */

    public void institutionUpdate(int id, String name, String address, String description) throws SQLException {
        DbConnection db = new DbConnection();

        db.Update(
                "Placowka",
                "nazwa = '" + name + "', opis = '" + description + "', adres = '" + address + "'",
                "id = '" + id + "'"
        );

        db.closeConnection();
    }

    /*
    DELETE INSTITUTION
     */

    public void institutionDelete(int id) throws SQLException {
        DbConnection db = new DbConnection();

        db.Delete(
                "Placowka",
                "id = '" + id + "'"
        );

        db.closeConnection();
    }

    /*
    ADD SERVICE
     */

    public void serviceAdd(String name, String price, String description) throws SQLException {
        DbConnection db = new DbConnection();

        db.Insert(
                "Usluga",
                "nazwa, opis, cena",
                "'" + name + "', '" + price + "', '" + description + "'"
        );


        db.closeConnection();
    }

    /*
    UPDATE SERVICE
     */

    public void serviceUpdate(int id, String name, String price, String description) throws SQLException {
        DbConnection db = new DbConnection();

        db.Update(
                "Usluga",
                "nazwa = '" + name + "', opis = '" + description + "', cena = '" + price + "'",
                "id = '" + id + "'"
        );

        db.closeConnection();
    }

    /*
    DELETE SERVICE
     */

    public void serviceDelete(int id) throws SQLException {
        DbConnection db = new DbConnection();

        db.Delete(
                "Usluga",
                "id = '" + id + "'"
        );

        db.closeConnection();
    }

    /*
    UPDATE CLIENT CAR
     */

    public void clientCarUpdate(int id, String brand, String model, String year, String color) throws SQLException {
        DbConnection db = new DbConnection();

        db.Update(
                "Pojazd",
                "marka = '" + brand + "', model = '" + model + "', rok_produkcji = '" + year + "', kolor = '" + color + "'",
                "id = '" + id + "' AND id_klienta = '" + getUserID() + "'"
        );

        db.closeConnection();
    }

    /*
    DELETE CLIENT CAR
     */

    public void clientCarDelete(int id) throws SQLException {

        clientRepairDeleteByCar(id);

        DbConnection db = new DbConnection();

        db.Delete(
                "Pojazd",
                "id = '" + id + "' AND id_klienta = '" + getUserID() + "'"
        );

        db.closeConnection();
    }


    /*
    GET SINGLE CLIENT DATA
     */

    public Client getSingleClientData() throws SQLException {
        DbConnection db = new DbConnection();
        Client clientData = null;

        ResultSet rs = db.Select("*", "Klient", "id = '" + getUserID() + "'");

        while(rs.next()){
            clientData = new Client(
                rs.getInt("id"),
                rs.getString("imie"),
                rs.getString("nazwisko"),
                rs.getString("adres"),
                rs.getString("email"),
                rs.getString("login"),
                rs.getString("haslo")
            );
        }

        db.closeConnection();

        return clientData;
    }

     /*
    GET CLIENT DATA
     */

    public List<Client> getClientData() throws SQLException {
        DbConnection db = new DbConnection();
        List<Client> clientList = new ArrayList<>();

        ResultSet rs = db.Select("*", "Klient", "");

        while(rs.next()){

            clientList.add( new Client(
                    rs.getInt("id"),
                    rs.getString("imie"),
                    rs.getString("nazwisko"),
                    rs.getString("adres"),
                    rs.getString("email"),
                    rs.getString("login"),
                    rs.getString("haslo")
            ));
        }

        db.closeConnection();

        return clientList;
    }

    /*
    DELETE CLIENT DATA
     */
    public void deleteClientData(int id) throws SQLException {
        clientRepairDeleteByClientId(id);
        clientCarDeleteByClientId(id);

        DbConnection db = new DbConnection();

        db.Delete(
                "Klient",
                "id = '" + id + "'"
        );

        db.closeConnection();
    }

         /*
    GET CAR DATA
     */

    public List<Car> getCarsData() throws SQLException {
        DbConnection db = new DbConnection();
        List<Car> carList = new ArrayList<>();

        ResultSet rs = db.Select("*", "Pojazd p INNER JOIN Klient k ON p.id_klienta = k.id", "");

        while(rs.next()){

            carList.add( new Car(
                    rs.getInt("p.id"),
                    rs.getString("p.marka"),
                    rs.getString("p.model"),
                    rs.getString("p.rok_produkcji"),
                    rs.getString("p.kolor"),
                    new Client(
                            rs.getInt("k.id"),
                            rs.getString("k.imie"),
                            rs.getString("k.nazwisko"),
                            rs.getString("k.adres"),
                            rs.getString("k.email"),
                            rs.getString("k.login"),
                            rs.getString("k.haslo")
                    )
            ));
        }

        db.closeConnection();

        return carList;
    }

    /*
    DELETE CAR DATA
     */

    public void deleteCarData(int id) throws SQLException {
        clientRepairDeleteByCar(id);

        DbConnection db = new DbConnection();

        db.Delete(
                "Pojazd",
                "id = '" + id + "'"
        );

        db.closeConnection();
    }

    /*
    UPDATE CURRENT CLIENT DATA
     */

    public void updateCurrentClientData(String name, String lastName, String address, String email, String login, String password) throws SQLException {
        DbConnection db = new DbConnection();

        db.Update(
                "Klient",
                "imie = '" + name + "', nazwisko = '" + lastName + "', adres = '" + address + "', email = '" + email + "', login = '" + login + "', haslo = '" + password + "'",
                "id = '" + getUserID() + "'"
        );

        db.closeConnection();
    }

    /*
    DELETE CURRENT CLIENT
     */

    public void deleteCurrentClientData() throws SQLException {
        clientRepairDeleteByCurrentClient();

        currentClientCarDelete();

        DbConnection db = new DbConnection();

        db.Delete(
                "Klient",
                "id = '" + getUserID() + "'"
        );

        db.closeConnection();

        setUserType("");
        setUserID(0);
    }

    /*
    GET CURRENT CLIENT REPAIR DATA
     */

    public List<Repair> getRepairDataFromDatabase() throws SQLException {
        DbConnection db = new DbConnection();
        List<Repair> repairList = new ArrayList<>();
        ResultSet rs = db.Select("*", "Naprawa n INNER JOIN Klient k ON n.id_klienta = k.id INNER JOIN Usluga u ON n.id_uslugi = u.id INNER JOIN Pojazd p ON n.id_pojazdu = p.id INNER JOIN Placowka pl ON n.id_placowki = pl.id", "n.id_klienta = '" + getUserID() + "'");

        while(rs.next()){
            repairList.add(
                    new Repair(
                            rs.getInt("n.id"),
                            new Client(
                                    rs.getInt("k.id"),
                                    rs.getString("k.imie"),
                                    rs.getString("k.nazwisko"),
                                    rs.getString("k.adres"),
                                    rs.getString("k.email"),
                                    rs.getString("k.login"),
                                    rs.getString("k.haslo")
                                    ),
                            new Service(
                                    rs.getInt("u.id"),
                                    rs.getString("u.nazwa"),
                                    rs.getString("u.opis"),
                                    rs.getString("u.cena")
                            ),
                            new Car(
                                    rs.getInt("p.id"),
                                    rs.getString("p.marka"),
                                    rs.getString("p.model"),
                                    rs.getString("p.rok_produkcji"),
                                    rs.getString("p.kolor"),
                                    new Client(
                                            rs.getInt("k.id"),
                                            rs.getString("k.imie"),
                                            rs.getString("k.nazwisko"),
                                            rs.getString("k.adres"),
                                            rs.getString("k.email"),
                                            rs.getString("k.login"),
                                            rs.getString("k.haslo")
                                    )
                            ),
                            new Institution(
                                    rs.getInt("pl.id"),
                                    rs.getString("pl.nazwa"),
                                    rs.getString("pl.opis"),
                                    rs.getString("pl.adres")
                            ),
                            rs.getString("n.data_naprawy"),
                            rs.getString("n.status"),
                            rs.getString("n.opis_naprawy")
                    )
            );
        }

        db.closeConnection();

        return repairList;
    }

    /*
    GET DELETABLE REPAIR DATA
     */

    public List<Repair> getDeletableRepairDataFromDatabase() throws SQLException {
        DbConnection db = new DbConnection();
        List<Repair> repairList = new ArrayList<>();
        ResultSet rs = db.Select("*", "Naprawa n INNER JOIN Klient k ON n.id_klienta = k.id INNER JOIN Usluga u ON n.id_uslugi = u.id INNER JOIN Pojazd p ON n.id_pojazdu = p.id INNER JOIN Placowka pl ON n.id_placowki = pl.id", "n.id_klienta = '" + getUserID() + "' AND n.status = 'Oczekiwanie'");

        while(rs.next()){
            repairList.add(
                    new Repair(
                            rs.getInt("n.id"),
                            new Client(
                                    rs.getInt("k.id"),
                                    rs.getString("k.imie"),
                                    rs.getString("k.nazwisko"),
                                    rs.getString("k.adres"),
                                    rs.getString("k.email"),
                                    rs.getString("k.login"),
                                    rs.getString("k.haslo")
                            ),
                            new Service(
                                    rs.getInt("u.id"),
                                    rs.getString("u.nazwa"),
                                    rs.getString("u.opis"),
                                    rs.getString("u.cena")
                            ),
                            new Car(
                                    rs.getInt("p.id"),
                                    rs.getString("p.marka"),
                                    rs.getString("p.model"),
                                    rs.getString("p.rok_produkcji"),
                                    rs.getString("p.kolor"),
                                    new Client(
                                            rs.getInt("k.id"),
                                            rs.getString("k.imie"),
                                            rs.getString("k.nazwisko"),
                                            rs.getString("k.adres"),
                                            rs.getString("k.email"),
                                            rs.getString("k.login"),
                                            rs.getString("k.haslo")
                                    )
                            ),
                            new Institution(
                                    rs.getInt("pl.id"),
                                    rs.getString("pl.nazwa"),
                                    rs.getString("pl.opis"),
                                    rs.getString("pl.adres")
                            ),
                            rs.getString("n.data_naprawy"),
                            rs.getString("n.status"),
                            rs.getString("n.opis_naprawy")
                    )
            );
        }

        db.closeConnection();

        return repairList;
    }

    /*
    DELETE CLIENT REPAIR
     */

    public void clientRepairDelete(int id) throws SQLException {
        DbConnection db = new DbConnection();

        db.Delete(
                "Naprawa",
                "id = '" + id + "' AND id_klienta = '" + getUserID() + "'"
        );

        db.closeConnection();
    }

    /*
    ADD CLIENT REPAIR
     */

    public void clientRepair(int id_uslugi, int id_pojazdu, int id_placowki) throws SQLException {
        String date = new SimpleDateFormat("dd-MM-yyyy").format(new Date());

        DbConnection db = new DbConnection();

        db.Insert(
                "Naprawa",
                "id_klienta, id_uslugi, id_pojazdu, id_placowki, data_naprawy, status, opis_naprawy",
                "'" + getUserID() + "', '" + id_uslugi + "', '" + id_pojazdu + "', '" + id_placowki + "', '" + date + "', 'Oczekiwanie', ''"
        );


        db.closeConnection();
    }

     /*
    GET CLIENT REPAIR DATA
     */

    public List<Repair> getRepairDataFromDatabaseAll() throws SQLException {
        DbConnection db = new DbConnection();
        List<Repair> repairList = new ArrayList<>();
        ResultSet rs = db.Select("*", "Naprawa n INNER JOIN Klient k ON n.id_klienta = k.id INNER JOIN Usluga u ON n.id_uslugi = u.id INNER JOIN Pojazd p ON n.id_pojazdu = p.id INNER JOIN Placowka pl ON n.id_placowki = pl.id", "");

        while(rs.next()){
            repairList.add(
                    new Repair(
                            rs.getInt("n.id"),
                            new Client(
                                    rs.getInt("k.id"),
                                    rs.getString("k.imie"),
                                    rs.getString("k.nazwisko"),
                                    rs.getString("k.adres"),
                                    rs.getString("k.email"),
                                    rs.getString("k.login"),
                                    rs.getString("k.haslo")
                            ),
                            new Service(
                                    rs.getInt("u.id"),
                                    rs.getString("u.nazwa"),
                                    rs.getString("u.opis"),
                                    rs.getString("u.cena")
                            ),
                            new Car(
                                    rs.getInt("p.id"),
                                    rs.getString("p.marka"),
                                    rs.getString("p.model"),
                                    rs.getString("p.rok_produkcji"),
                                    rs.getString("p.kolor"),
                                    new Client(
                                            rs.getInt("k.id"),
                                            rs.getString("k.imie"),
                                            rs.getString("k.nazwisko"),
                                            rs.getString("k.adres"),
                                            rs.getString("k.email"),
                                            rs.getString("k.login"),
                                            rs.getString("k.haslo")
                                    )
                            ),
                            new Institution(
                                    rs.getInt("pl.id"),
                                    rs.getString("pl.nazwa"),
                                    rs.getString("pl.opis"),
                                    rs.getString("pl.adres")
                            ),
                            rs.getString("n.data_naprawy"),
                            rs.getString("n.status"),
                            rs.getString("n.opis_naprawy")
                    )
            );
        }

        db.closeConnection();

        return repairList;
    }

    /*
    UPDATE REPAIR
     */

    public void repairUpdate(int id, String status) throws SQLException {
        DbConnection db = new DbConnection();

        db.Update(
                "Naprawa",
                "status = '" + status + "'",
                "id = '" + id + "'"
        );

        db.closeConnection();
    }

    /*
    DELETE REPAIR
     */

    public void repairDelete(int id) throws SQLException {
        DbConnection db = new DbConnection();

        db.Delete(
                "Naprawa",
                "id = '" + id + "'"
        );

        db.closeConnection();
    }


     /*
    DELETE CLIENT CAR
     */

    private void currentClientCarDelete() throws SQLException {

        DbConnection db = new DbConnection();

        db.Delete(
                "Pojazd",
                "id_klienta = '" + getUserID() + "'"
        );

        db.closeConnection();
    }

    private void clientCarDeleteByClientId(int id) throws SQLException {
        DbConnection db = new DbConnection();

        db.Delete(
                "Pojazd",
                "id_klienta = '" + id + "'"
        );

        db.closeConnection();
    }


    /*
    DELETE CLIENT REPAIR
     */

    private void clientRepairDeleteByCar(int carId) throws SQLException {
        DbConnection db = new DbConnection();

        db.Delete(
                "Naprawa",
                "id_pojazdu = '" + carId + "'"
        );

        db.closeConnection();
    }


    private void clientRepairDeleteByCurrentClient() throws SQLException {
        DbConnection db = new DbConnection();

        db.Delete(
                "Naprawa",
                "id_klienta = '" + getUserID() + "'"
        );

        db.closeConnection();
    }

    private void clientRepairDeleteByClientId(int id) throws SQLException {
        DbConnection db = new DbConnection();

        db.Delete(
                "Naprawa",
                "id_klienta = '" + id + "'"
        );

        db.closeConnection();
    }
}
