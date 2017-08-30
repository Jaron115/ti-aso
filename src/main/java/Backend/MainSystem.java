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
import java.util.ArrayList;
import java.sql.SQLException;
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
    DELETE CLIENT REPAIR
     */

    private void clientRepairDeleteByCar(int carId) throws SQLException {
        DbConnection db = new DbConnection();

        db.Delete(
                "Naprawa",
                "id_pojazdu = '" + carId + "' AND id_klienta = '" + getUserID() + "'"
        );

        db.closeConnection();
    }

}
