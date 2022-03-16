package entity;

import javax.persistence.Entity;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

/**
 * @author Adam BÃ¶rjesson
 */
@Entity
public class Student {

    @Id
    @GeneratedValue
    private int id;
    private String name;

    @ManyToOne
    private Education education;

    public Student() {
    }

    public Student(String name) {

        this.name = name;
    }

    public int getId() {

        return id;
    }

    public void setId(int id) {

        this.id = id;
    }

    public String getName() {

        return name;
    }

    public void setName(String name) {

        this.name = name;
    }

    public Education getEducation() {

        return education;
    }

    public void setEducation(Education education) {
        this.education = education;

    }

    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
