/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Backend;

import java.util.ArrayList;
import java.util.Date;

public class MainSystem {

    /*
    *   STATIC VARIABLES
     */
    private static ArrayList<User> userList = new ArrayList<>();
    private static User currentUser;

    /*
    *   GETTER METHODS
     */
    public ArrayList<User> getUserList() {
        return this.userList;
    }

    public User getCurrentUser() {
        return this.currentUser;
    }

    public ArrayList<Teacher> getTeachers() { //from user list
        ArrayList<Teacher> teacherList = new ArrayList<>();
        for (User teacher : userList) {
            if (teacher instanceof Teacher) {
                teacherList.add((Teacher) teacher);
            }
        }
        return teacherList;
    }

    public ArrayList<Student> getStudents() { //from user list
        ArrayList<Student> studentList = new ArrayList<>();
        for (User student : userList) {
            if (student instanceof Student) {
                studentList.add((Student) student);
            }
        }
        return studentList;
    }

    public ArrayList<Class> getAllClasses() {
        ArrayList<Class> classList = new ArrayList<>();
        for (Teacher teacher : getTeachers()) {
            for (Class class1 : teacher.getClasses()) {
                classList.add(class1);
            }
        }
        return classList;
    }

    /*
    *   LOGIN
    */
    public Boolean Login(String zID, String password) {
        for (User user : userList) {
            if (user.zID.equals(zID) && user.password.equals(password)) {
                this.currentUser = user;
                return true;    //move to next screen
            }
        }
        return false;   //show notification
    }

    public void Logout() {
        this.currentUser = null;
    }

    /*
    *   SIGNUP
     */
    public void signUp(String zID, String name, String password, String type, Date DOB, String gender) {
        //type based on comboBox
        if (type.equals("Student")) {
            Student newStudent = new Student(zID, name, password, DOB, gender);
            userList.add(newStudent);
        } else { //know its a teacher
            Teacher newTeacher = new Teacher(zID, name, password, DOB, gender);
            userList.add(newTeacher);
        }
    }

    /*
    *   STUDENT VIEW
     */
    //returns student that has a particular ID
    public Student getStudentFromID(String studentID) {
        for (Student student : getStudents()) {
            if (student.getID().equals(studentID)) {
                return student;
            }
        }
        return null;
    }

    //for student view of groups
    public ArrayList<Group> getGroupsStudentBelongsTo() {   //use current user
        // return currentUser.getGroups();
        ArrayList<Group> groupList = new ArrayList<>();
        for (Teacher teacher : getTeachers()) {
            for (Class class1 : getTeacherClasses(teacher)) {
                for (Group group : class1.getGroups()) {
                    for (Student student : group.getStudents()) {
                        if (student.equals(this.currentUser)) {
                            groupList.add(group);
                        }
                    }
                }
            }
        }
        return groupList;
    }

    //returns all classes that the current student logged in belongs to
    public ArrayList<Class> getClassesStudentBelongsTo() {
        ArrayList<Class> classList = new ArrayList<>();
        for (Teacher teacher : getTeachers()) {
            for (Class class1 : getTeacherClasses(teacher)) {
                for (Group group : class1.getGroups()) {
                    for (Student student : group.getStudents()) {
                        if (student.equals(this.currentUser)) {
                            classList.add(class1);
                        }
                    }
                }
            }
        }
        return classList;
    }

    //adds the currently logged in student to a group
    public void addStudentToGroup(String groupID, String classID) {
        if (getClassFromID(classID) != null) {    //not equal to null
            Class class1 = getClassFromID(classID);
            if (classID.equals(class1.getClassID())) {
                boolean hasFoundGroup = false;
                for (Group group : class1.getGroups()) {
                    if (groupID.equals(group.getGroupID())) {
                        hasFoundGroup = true;
                        group.addStudent((Student) this.currentUser);
                    }
                }
                //IF GROUP DOESN'T EXIST THEN CREATE A NEW GROUP (which includes the user creating it)
                if (!hasFoundGroup) {
                    //System.out.println("new group");
                    Group newGroup = new Group(groupID);
                    //newGroup.addStudent((Student) this.currentUser); -doesnt work so use recursion instead
                    class1.addGroup(new Group(groupID));
                    addStudentToGroup(groupID, classID);
                }
            }
        }
    }

    //get groups from class
    public ArrayList<Group> getGroupsFromClass(String classID) {
        return this.getClassFromID(classID).getGroups();
    }

    //get group from ID
    public Group getGroupFromID(String groupID, String classID) {
        //Group group1;
        for (Group group : getGroupsFromClass(classID)) {
            if (groupID.equals(group.getGroupID())) {
                return group;
            }
        }
        return null;
    }

    //using student that is currently logged in, add a rating of another student
    public void addRatingToGroup(Group group, Rating rating) {
        group.addRating(rating);
    }

    //add rating from the currently logged in student 
    public void addRatingFromCurrentStudent(String classID, String groupID, String studentRatedID, int score, String comment) {
        Rating rating = new Rating(this.getStudentFromID(studentRatedID), (Student) this.getCurrentUser(), score, comment);
        addRatingToGroup(this.getGroupFromID(groupID, classID), rating);
    }

    /*
     * STUENT VIEW
     */
    //get the classes from the teacher currently logged in
    public ArrayList<Class> getCurrentTeacherClasses() {
        return getTeacherClasses((Teacher) this.currentUser);
    }

    //adds a class to the teacher
    public void addClassToTeacher(String classID, String name) {
        this.currentUser.addClass(new Class(classID, name));
    }

    //gets an array of classes that a teacher has
    public ArrayList<Class> getTeacherClasses(Teacher teacher) {
        return teacher.getClasses();
    }

    //get class from classID
    public Class getClassFromID(String classID) {
        for (Teacher teacher : this.getTeachers()) {
            for (Class class1 : teacher.getClasses()) {
                if (class1.getClassID().equals(classID)) {
                    return class1;
                }
            }
        }
        return null;
    }

    //get groups from a particular class that a student belongs to 
    public ArrayList<Group> getGroupsFromClassListAndStudent(ArrayList<Class> classList, String ClassID) {
        ArrayList<Group> groupList = new ArrayList<>();
        for (Class class1 : classList) {
            if (class1.getClassID().equals(ClassID)) {
                for (Backend.Group group : class1.getGroups()) {
                    for (Backend.Student student : group.getStudents()) {
                        if (student.equals(this.getCurrentUser())) {
                            groupList.add(group);
                        }
                    }
                }
            }
        }
        return groupList;
    }
}
