/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Backend;


import javax.management.Notification;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.sql.SQLException;



public class MainSystem {


    public void test() throws SQLException {
        DbConnection db = new DbConnection();

        ResultSet rs = db.Select("","Klient","");

        while(rs.next()){
            System.out.println(rs.getInt("id"));
            System.out.println(rs.getString("imie"));
        }

        db.closeConnection();

    }

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

    public static void setUserID(int userID) {
        MainSystem.userID = userID;
    }

    public static void setUserType(String userType) {
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


}
