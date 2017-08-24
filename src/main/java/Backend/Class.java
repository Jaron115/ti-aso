/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Backend;

import java.util.ArrayList;

public class Class {

    //variables
    private ArrayList<Group> groupList
            = new ArrayList<Group>();
    private String classID;
    private String name;

    public Class(String classID,
            String name) {
        this.classID
                = classID;
        this.name
                = name;
    }

    public String getClassID() {
        return this.classID;
    }

    public String getName() {
        return this.name;
    }

    public ArrayList<Group> getGroups() {
        return this.groupList;
    }

    @Override
    public String toString() {
        return this.classID
                + ": "
                + this.name;
    }

    public void addGroup(Group group) {
        this.groupList.add(group);
    }
}
