/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Entity;

import ADT.ArraySetList;
import ADT.List;
import ADT.SetListInterface;
import Utility.Filter;
import Utility.Sortable;
import Utility.Sorter;
import java.util.Comparator;
import java.util.Objects;

/**
 * @author LEE VESE
 */
public class TutorialGroup implements Sortable<TutorialGroup> {

    //sorter constants
    private static final int SORT_BY_CODE = 1;
    private static final int SORT_BY_TOTAL_STUDENTS = 2;

    //data fields
    private Programme tutGrpProgramme;
    private String tutGrpCode;
    private SetListInterface<Student> tutGrpStudents;

    //constructor
    public TutorialGroup(Programme tutGrpProgramme, int tutGrpNum) {
        this.tutGrpProgramme = tutGrpProgramme;
        this.tutGrpCode = tutGrpProgramme.getProgramCode() + tutGrpNum;
        this.tutGrpStudents = new ArraySetList<>();
    }

    public TutorialGroup(Programme tutGrpProgramme, int tutGrpNum, SetListInterface<Student> studentList) {
        this.tutGrpProgramme = tutGrpProgramme;
        this.tutGrpCode = tutGrpProgramme.getProgramCode() + tutGrpNum;
        this.tutGrpStudents = studentList;
    }

    public TutorialGroup() {
        this.tutGrpProgramme = null;
        this.tutGrpCode = "";
        this.tutGrpStudents = new ArraySetList<>();
    }
    
    public TutorialGroup(TutorialGroup tGrp) {
        this.tutGrpProgramme = tGrp.tutGrpProgramme;
        this.tutGrpCode = tGrp.getTutGrpCode();
        this.tutGrpStudents = tGrp.getTutGrpStudents();
    }

    public Programme getTutGrpProgramme() {
        return tutGrpProgramme;
    }

    public void setTutGrpProgramme(Programme tutGrpProgramme) {
        this.tutGrpProgramme = tutGrpProgramme;
    }

    public String getTutGrpCode() {
        return tutGrpCode;
    }

    public void setTutGrpCode(String tutGrpCode) {
        this.tutGrpCode = tutGrpCode;
    }

    public SetListInterface<Student> getTutGrpStudents() {
        return tutGrpStudents;
    }

    public void setTutGrpStudents(SetListInterface<Student> tutGrpStudents) {
        this.tutGrpStudents = tutGrpStudents;
    }
    

//    @Override //temp toString
//    public String toString() {
//        return String.format(
//                "Programme: %s | Code: %s \n",
//                this.tutGrpProgramme,
//                this.tutGrpCode
//        );
//    }
    @Override
    public String toString() {
        return String.format(
                """
                Programme: %s 
                Code:      %s 
                Number of students: %d
                %45s
                ------------------------------------------------------------------------------
                %-20s %-20s %-20s %-20s
                """
                + this.tutGrpStudents.toString()
                + "\n\n",
                this.tutGrpProgramme.getProgramCode(),
                this.tutGrpCode,
                this.tutGrpStudents.getTotalEntries(),
                "Students List",
                "StudentID",
                "StudentName",
                "Programme",
                "Tutorial Group"
        );
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        TutorialGroup tutGrp = (TutorialGroup) o;
        return tutGrpProgramme.equals(tutGrp.tutGrpProgramme)
                && Objects.equals(tutGrpCode, tutGrp.tutGrpCode)
                && Objects.equals(tutGrpStudents, tutGrp.tutGrpStudents);
    }

    @Override
    public List<TutorialGroup> sort(List<TutorialGroup> objectList, int sortType) {
        if (sortType == SORT_BY_CODE) {
            Sorter.sort(objectList, new Comparator<TutorialGroup>() {
                @Override
                public int compare(TutorialGroup o1, TutorialGroup o2) {
                    return o1.getTutGrpCode().compareTo(o2.getTutGrpCode());
                }
            });
        } else if (sortType == SORT_BY_TOTAL_STUDENTS) {
            Sorter.sort(objectList, new Comparator<TutorialGroup>() {
                @Override
                public int compare(TutorialGroup o1, TutorialGroup o2) {
                    Integer total1 = o1.tutGrpStudents.getTotalEntries();
                    Integer total2 = o2.tutGrpStudents.getTotalEntries();
                    return total1.compareTo(total2);
                }
            });
        }

        return objectList;
    }

    @Override
    public List<TutorialGroup> filter(List<TutorialGroup> objectList, Filter filter) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public List<TutorialGroup> search(List<TutorialGroup> objectList, String query) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
