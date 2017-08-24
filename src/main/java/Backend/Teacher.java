/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Backend;

import java.util.ArrayList;
import java.util.Date;

public class Teacher extends User {

    private ArrayList<Class> classList = new ArrayList<Class>();    //array of classes

    public Teacher(String zID, String name, String password, Date DOB, String gender) {
        super(zID, name, password, DOB, gender);
    }

    public ArrayList<Class> getClasses() {
        return this.classList;
    }

    public void addClass(Class class1) {
        this.classList.add(class1);
    }

    public String getType() {
        return "Teacher";
    }

}
