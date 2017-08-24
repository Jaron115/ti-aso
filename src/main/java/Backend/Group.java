/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Backend;

import java.util.ArrayList;

public class Group {

    private String groupID;
    //ArrayList<Student
    private ArrayList<Rating> ratingList = new ArrayList<>();   //array of ratings
    private ArrayList<Student> studentList = new ArrayList<>(); //array of students

    public Group(String groupID) {
        this.groupID = groupID;
        //this.class1 = class1;
    }

    public String getGroupID() {
        return groupID;
    }

    public Group getGroup() {
        return this;
    }

    public ArrayList<Student> getStudents() {
        return this.studentList;
    }

    public void addStudent(Student student) {
        this.studentList.add(student);
    }

    public ArrayList<Rating> getRatings() {
        return this.ratingList;
    }

    public void addRating(Rating rating) {
        this.ratingList.add(rating);
    }
}
