/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Backend;

public class Rating {

    public Student studentRated;    //student that was rated
    public Student studentRating;   //student that is rating
    public int score;   //out of 10
    public String comment;      //comment to justify score

    public Rating(Student studentRated, Student studentRating, int score, String comment) {
        this.studentRated = studentRated;
        this.studentRating = studentRating;
        this.score = score;
        this.comment = comment;
    }

    //getters
    public Student getStudentRated() {
        return this.studentRated;
    }

    public Student getStudentRating() {
        return this.studentRating;
    }

    public int getScore() {
        return this.score;
    }

    public String getComment() {
        return this.comment;
    }

    @Override
    public String toString() {
        return this.studentRating + " rated " + this.studentRated + " " + this.score + "/10.";

    }
}
