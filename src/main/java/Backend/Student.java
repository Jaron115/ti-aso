/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Backend;

import java.util.ArrayList;
import java.util.Date;

public class Student extends User {

    public Student(String zID, String name, String password, Date DOB, String gender) {
        super(zID, name, password, DOB, gender);
    }

    public String getType() {
        return "Student";
    }
}
