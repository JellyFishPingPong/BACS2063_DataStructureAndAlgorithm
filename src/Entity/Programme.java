/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Entity;

import ADT.*;
import Utility.*;
import java.util.Objects;

/**
 *
 * @author OLIVER TAN ZU ZHUO
 */
public class Programme implements Sortable<Programme>, Comparable<Programme> {

    //Sorter constants
    public final static int SORT_BY_CODE = 0;
    public final static int SORT_BY_NAME = 1;
    public final static int SORT_BY_DURATION = 2;
    public final static int REVERSE = 3;

    //Filter constants
    public final static int EX_FOUNDATION = 1;
    public final static int EX_DIPLOMA = 2;
    public final static int EX_DEGREE = 3;

    private String programCode;
    private String programName;
    private int programDuration;
    private SetListInterface<TutorialGroup> tutGroups;

    public SetListInterface<TutorialGroup> getTutGroups() {
        return tutGroups;
    }

    public void setTutGroups(SetListInterface<TutorialGroup> tutGroups) {
        this.tutGroups = tutGroups;
    }

    public Programme(String programmeCode, String programmeName, int programmeDuration) {
        this(programmeCode, programmeName, programmeDuration, new ArraySetList());
    }

    public Programme(String programCode, String programName, int programDuration, SetListInterface<TutorialGroup> tutGroups) {
        this.programCode = programCode.toUpperCase();
        this.programName = programName;
        this.programDuration = programDuration;
        this.tutGroups = tutGroups;
    }

    public Programme() {
        this("", "", 0);
    }

    public String getProgramCode() {
        return programCode;
    }

    public void setProgramCode(String programmeCode) {
        this.programCode = programmeCode.toUpperCase();
    }

    public String getProgramName() {
        return programName;
    }

    public void setProgramName(String programmeName) {
        this.programName = programmeName;
    }

    public int getProgramDuration() {
        return programDuration;
    }

    public void setProgramDuration(int programDuration) {
        this.programDuration = programDuration;
    }

    public void addTutorialGroup() {
        var lastGrp = tutGroups.getEntry(tutGroups.size());
        int newGrpNumber;
        
        if (lastGrp == null) {
            newGrpNumber = 1;
        } else {
            newGrpNumber = Integer.valueOf(lastGrp.getTutGrpCode().substring(3)) + 1;
        }        
        
        tutGroups.add(new TutorialGroup(this, newGrpNumber));
    }

    public void removeTutorialGroup(TutorialGroup tutGrp) {
        tutGroups.remove(tutGrp);
    }

    @Override
    public String toString() {
        return this.programCode + "|" + this.programName + "|" + this.programDuration + "|" + this.tutGroups;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Programme programme = (Programme) o;
        return programDuration == programme.programDuration
                && Objects.equals(programCode, programme.programCode)
                && Objects.equals(programName, programme.programName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(programCode, programName, programDuration);
    }

    @Override
    public List<Programme> sort(List<Programme> objectList, int sortType) {

        switch (sortType) {
            case SORT_BY_CODE ->
                Sorter.sort(objectList, (Programme o1, Programme o2) -> o1.getProgramCode().compareTo(o2.getProgramCode()));
            case SORT_BY_NAME ->
                Sorter.sort(objectList, (Programme o1, Programme o2) -> o1.getProgramName().compareTo(o2.getProgramName()));
            case SORT_BY_DURATION ->
                Sorter.sort(objectList, (Programme o1, Programme o2) -> Integer.compare(o1.getProgramDuration(), o2.getProgramDuration()));
            case REVERSE ->
                Sorter.reverse(objectList);
        }

        return objectList;
    }

    @Override
    public int compareTo(Programme o) {
        return this.getProgramCode().compareTo(o.getProgramCode());
    }

    @Override
    public List<Programme> filter(List<Programme> objectList, Filter filter) {
        List<Programme> tempList = new ArrayList(objectList);

        if (!filter.hasFilter()) {
            return objectList;
        }

        if (filter.filters(EX_FOUNDATION)) {
            removeProg(tempList, 'F');
        }

        if (filter.filters(EX_DIPLOMA)) {
            removeProg(tempList, 'D');
        }

        if (filter.filters(EX_DEGREE)) {
            removeProg(tempList, 'R');
        }

        return tempList;
    }

    public void removeProg(List<Programme> pList, char progCode) {
        for (int i = 0; i < pList.size(); i++) {
            if (pList.get(i).getProgramCode().charAt(0) == progCode) {
                pList.remove(i);
                i--;
            }
        }
    }

    @Override
    public List<Programme> search(List<Programme> objectList, String query) {
        List<Programme> searchResult = new ArrayList();
        for (int i = 0; i < objectList.size(); i++) {
            var p = objectList.get(i);

            if (p.getProgramCode().toLowerCase().contains(query.toLowerCase()) || p.getProgramName().toLowerCase().contains(query.toLowerCase())) {
                searchResult.add(p);
            }
        }

        return searchResult;
    }
}
