/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Entity;

import java.util.Comparator;
import ADT.List;
import Utility.Filter;
import Utility.Sortable;
import Utility.Sorter;

/**
 *
 * @author LEE VESE
 */
public class Student implements Sortable<Student> {

    //sorting criteria
    public final static int SORT_BY_ID = 1;
    public final static int SORT_BY_NAME = 2;

    //data fields
    private String studentId;
    private String studentName;
    private Programme studentProgramme;
    private TutorialGroup studentTutGrp;

    //constructor
    public Student() {
        this.studentId = "";
        this.studentName = "";
        this.studentProgramme = null;
        this.studentTutGrp = null;
    }

    public Student(String studentId, String studentName, Programme studentProgramme) {
        this.studentId = studentId;
        this.studentName = studentName;
        this.studentProgramme = studentProgramme;
        this.studentTutGrp = null;
    }

    public Student(String studentId, String studentName, Programme studentProgramme, TutorialGroup studentTutGrp) {
        this.studentId = studentId;
        this.studentName = studentName;
        this.studentProgramme = studentProgramme;
        this.studentTutGrp = studentTutGrp;
    }

    //getter & setter
    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public Programme getStudentProgramme() {
        return studentProgramme;
    }

    public void setStudentProgramme(Programme studentProgramme) {
        this.studentProgramme = studentProgramme;
    }

    public TutorialGroup getStudentTutGrp() {
        return studentTutGrp;
    }

    public void setStudentTutGrp(TutorialGroup studentTutGrp) {
        this.studentTutGrp = studentTutGrp;
    }

    @Override
    public String toString() {
        String tutGrpString;

        if (this.studentTutGrp == null) {
            tutGrpString = "";
        } else {
            tutGrpString = this.studentTutGrp.getTutGrpCode();
        }
        return String.format("%-20s %-20s %-20s %-20s",
                this.studentId,
                this.studentName,
                this.studentProgramme.getProgramCode(),
                tutGrpString);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Student student = (Student) o;
        return this.studentId.equals(student.studentId)
                && this.studentName.equals(student.studentName)
                && this.studentProgramme.equals(student.studentProgramme)
                && this.studentTutGrp.equals(student.studentTutGrp);
    }

    @Override
    public List<Student> sort(List<Student> objectList, int sortType) {
        if (sortType == SORT_BY_ID) {
            Sorter.sort(objectList, new Comparator<Student>() {
                @Override
                public int compare(Student o1, Student o2) {
                    return o1.getStudentId().compareTo(o2.getStudentId());
                }
            });
        } else if (sortType == SORT_BY_NAME) {
            Sorter.sort(objectList, new Comparator<Student>() {
                @Override
                public int compare(Student o1, Student o2) {
                    return o1.getStudentName().compareTo(o2.getStudentName());
                }
            });
        }

        return objectList;
    }

    @Override
    public List<Student> filter(List<Student> objectList, Filter filter) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public List<Student> search(List<Student> objectList, String query) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
