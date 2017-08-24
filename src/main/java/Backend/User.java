/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Backend;

import java.util.ArrayList;
import java.util.Date;

//superclass fo teacher, student
public class User {

    protected String zID;
    protected String name;
    protected String password;
    protected Date DOB;
    protected String gender;

    public User(String zID, String name, String password, Date DOB, String gender) {
        this.zID = zID;
        this.name = name;
        this.password = password;
        this.DOB = DOB;
        this.gender = gender;
    }

    public String getID() {
        return this.zID;
    }

    public String getName() {
        return this.name;
    }

    public String getPassword() {
        return this.password;
    }

    public Date getDOB() {
        return this.DOB;
    }

    public String getGender() {
        return this.gender;
    }

    public String getType() {
        return "";
    }

    //can only be called by Teacher
    public ArrayList<Class> getClasses() {
        return new ArrayList<>();
    }

    public void addClass(Class class1) {
        //leave blank
    }

    public void addClassToTeacher(String classID, String name) {
        //leave blank
    }

    @Override
    public String toString() {
        return this.zID + ": " + this.name;
    }
}
