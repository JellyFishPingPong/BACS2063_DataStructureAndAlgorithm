/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Control;

import ADT.*;
import Client.*;
import Entity.Programme;
import Entity.Student;
import Entity.TutorialGroup;
import Utility.Sortable;
import Utility.Filter;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 *
 * @author Oliver Tan Zu Zhuo
 */
public class ProgControl {

    public BinarySearchTreeInterface<Programme> progTree = new BinarySearchTree();
    public SetListInterface<TutorialGroup> tutGrpList = new ArraySetList<>(50);

    private List<Programme> programmes;
    private Sortable sorter;
    private Filter filter;
    private ProgDisplayUI progMain;
    private ProgAddUI addForm;
    private ProgEditUI editForm;
    private ProgReportUI reportUI;
    private int sortType;

    private Programme tempProg = null;
    private SetListInterface<TutorialGroup> tempGrps = new ArraySetList();
    private SetListInterface<Student> studentList = new ArraySetList<>(50);

    public ProgControl() {
        programmes = new ArrayList();
        filter = new Filter();
        sortType = Programme.SORT_BY_CODE;

        initData();
    }

    public static void main(String[] args) {
        ProgControl progControl = new ProgControl();
        progControl.run();
    }

    public void run() {
        progMain = new ProgDisplayUI(this);
        progMain.setVisible(true);
        progMain.setTableData(sort());
    }

    public void refreshList() {
        programmes = progTree.toList();
    }

    public void sortRequest(int sortType) {
        setSortType(sortType);
    }

    public List<Programme> sort() {
        sorter = new Programme();
        programmes = sorter.sort(programmes, sortType);

        return filtered();
    }

    public void createAddForm() {
        addForm = new ProgAddUI(this);
        addForm.setVisible(true);
        addForm.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        addForm.setLocationRelativeTo(progMain);

        for (WindowListener listener : addForm.getWindowListeners()) {
            addForm.removeWindowListener(listener);
        }

        addForm.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                resetUI();
            }
        });
    }

    public void createEditForm(Programme data) {
//        getTutorialGroup(data);
//
//        data.setTutGroups(tempGrps);
        tempProg = progTree.getEntry(data);
        progTree.remove(data);

        tempGrps = new ArraySetList((ArraySetList) tempProg.getTutGroups());

        editForm = new ProgEditUI(this);
        editForm.setVisible(true);
        editForm.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        editForm.setLocationRelativeTo(progMain);

        editForm.initFields(tempProg.getProgramCode(),
                tempProg.getProgramName(),
                String.valueOf(tempProg.getProgramDuration()),
                tempProg.getTutGroups());

        for (WindowListener listener : editForm.getWindowListeners()) {
            editForm.removeWindowListener(listener);
        }

        editForm.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                if (editForm.confirmDialog() == JOptionPane.YES_OPTION) {
                    var data = editForm.getData();
                    updateTutGrpList();
                    tempProg.setProgramCode(data.get(0));
                    tempProg.setProgramName(data.get(1));
                    tempProg.setProgramDuration(Integer.valueOf(data.get(2)));
                    progTree.add(tempProg);
                } else {
                    progTree.add(tempProg);
                }

                editForm.clearForm();
                editForm.dispose();
                resetUI();

            }
        });
    }

    public void addProgramme(String code, String name, String durationStr) {
        code = code.toUpperCase();
        int duration = Integer.valueOf(durationStr);

        if (progTree.add(new Programme(code, name, duration))) {
            addForm.displayMessage("Successfully added " + code + ".",
                    JOptionPane.INFORMATION_MESSAGE);
        } else {
            addForm.displayMessage("Failed to add " + code + ".",
                    JOptionPane.ERROR_MESSAGE);
        }

        addForm.clearTextFields();
    }

    public void updateFilter(int criteria) {

        if (criteria > 0) {
            filter.addFilter(criteria);
        } else {
            filter.removeFilter(Math.abs(criteria));
        }

        progMain.setTableData(filtered());
    }

    public List<Programme> filtered() {
        var tempList = new ArrayList(programmes);

        sorter = new Programme();
        return sorter.filter(tempList, filter);
    }

    public void search(String query) {
        sorter = new Programme();
        progMain.setTableData(sorter.search(filtered(), query));
    }

    public void setSortType(int sortType) {
        this.sortType = sortType;

        progMain.setTableData(sort());
    }

    private void resetUI() {
        filter.clear();
        sortType = Programme.SORT_BY_CODE;

        refreshList();

        tempProg = null;
        tempGrps.clear();

        progMain.resetUI();
        progMain.setTableData(sort());
    }

    public void editClicked(int selectedCode) {
        if (selectedCode == -1) {
            progMain.displayMessage("Select a row to modify",
                    JOptionPane.WARNING_MESSAGE);
        } else {
            var data = progMain.getSelectedData(selectedCode);

            createEditForm(new Programme(data[0],
                    data[1],
                    Integer.valueOf(data[2])));
        }
    }

//    private void getTutorialGroup(Programme data) {
//        for (int i = 1; i <= tutGrpList.size(); i++) {
//            var tutGrp = tutGrpList.getEntry(i);
//
//            if (tutGrp.getTutGrpProgramme().equals(data)) {
//                tempGrps.add(new TutorialGroup(tutGrp));
//            }
//        }
//    }
    public void saveData() {
        var data = editForm.getData();
        tempProg.setProgramCode(data.get(0));
        tempProg.setProgramName(data.get(1));
        tempProg.setProgramDuration(Integer.valueOf(data.get(2)));

        updateTutGrpList();
        tempGrps = new ArraySetList((ArraySetList) tempProg.getTutGroups());
    }

    public void deletePressed() {
        if (editForm.deleteDialog("Are you sure you want to delete this item?") == JOptionPane.YES_OPTION) {
            var list = tempProg.getTutGroups();

            for (int i = 1; i <= list.size(); i++) {
                if (tutGrpList.contains(list.getEntry(i))) {
                    tutGrpList.remove(list.getEntry(i));
                }
            }

            editForm.clearForm();
            resetUI();
            editForm.dispose();
        }
    }

    private void updateTutGrpList() {
        for (int i = 1; i <= tempGrps.size(); i++) {
            if (tutGrpList.contains(tempGrps.getEntry(i))) {
                tutGrpList.remove(tempGrps.getEntry(i));
            }
        }

        var list = tempProg.getTutGroups();
        for (int i = 1; i <= list.size(); i++) {
            tutGrpList.add(list.getEntry(i));
        }
    }

    public void addTutorialGroup() {
        tempProg.addTutorialGroup();
        editForm.setTableData(tempProg.getTutGroups());
    }

    public void removeTutorialGroup(int selectedRow) {
        if (selectedRow == -1) {
            return;
        }

        var tutGrpData = editForm.getSelectedData(selectedRow);

        var list = tempProg.getTutGroups();
        for (int i = 1; i <= list.size(); i++) {
            var grp = list.getEntry(i);

            if (grp.getTutGrpCode().equals(tutGrpData)) {
                if (grp.getTutGrpStudents().size() == 0) {
                    tempProg.removeTutorialGroup(grp);
                    editForm.setTableData(tempProg.getTutGroups());
                } else {
                    var choice = editForm.deleteDialog("Remove the tutorial group? \n(All students will have to be reassigned)");

                    if (choice == JOptionPane.YES_OPTION) {
                        tempProg.removeTutorialGroup(grp);
                        editForm.setTableData(tempProg.getTutGroups());
                    }

                    break;
                }
            }
        }

        updateTutGrpList();
        tempGrps = new ArraySetList((ArraySetList) tempProg.getTutGroups());
    }

    public void initData() {
        progTree.add(new Programme("FIA", "Foundation in Arts", 1));
        progTree.add(new Programme("FIS", "Foundation in Science", 1));
        progTree.add(new Programme("FIB", "Foundation in Business", 1));
        progTree.add(new Programme("RAN", "Degree in Analytical Chemistry ", 4));
        progTree.add(new Programme("RBS", "Degree in Bioscience with Chemistry", 4));
        progTree.add(new Programme("RFN", "Degree in Food Science", 3));
        progTree.add(new Programme("RMI", "Degree in Microelectronics (Embedded Technology)", 4));
        progTree.add(new Programme("RPI", "Degree in Applied Physics", 3));
        progTree.add(new Programme("RMM", "Degree in Management Mathematics with Computing", 3));
        progTree.add(new Programme("DAC", "Diploma in Accounting", 2));
        progTree.add(new Programme("DCA", "Diploma in Corporate Administration", 2));
        progTree.add(new Programme("DEC", "Diploma in Economics", 2));
        progTree.add(new Programme("DHR", "Diploma in Human Resource Management", 2));
        progTree.add(new Programme("DRM", "Diploma in Retail Management", 2));
        progTree.add(new Programme("DEE", "Diploma in Electrical and Electronics", 2));
        progTree.add(new Programme("DGD", "Diploma in Graphic Design", 2));
        progTree.add(new Programme("RGD", "Degree in Graphic Design", 3));
        progTree.add(new Programme("REE", "Degree in Electrical and Electronics", 3));
//        programmes.add(new Programme("", "", 2));

        var w = new TutorialGroup(new Programme("FIA", "Foundation in Arts", 1), 1);
        var x = new TutorialGroup(new Programme("FIA", "Foundation in Arts", 1), 2);
        var y = new TutorialGroup(new Programme("FIA", "Foundation in Arts", 1), 3);
        var z = new TutorialGroup(new Programme("FIA", "Foundation in Arts", 1), 4);

        tutGrpList.add(w);
        tutGrpList.add(x);
        tutGrpList.add(y);
        tutGrpList.add(z);

        SetListInterface<TutorialGroup> tempList = new ArraySetList();

        tempList.add(w);
        tempList.add(x);
        tempList.add(y);
        tempList.add(z);

        progTree.getEntry(new Programme("FIA", "Foundation in Arts", 1)).setTutGroups(tempList);
//        tutGrpList.add(new TutorialGroup(new Programme("FIA", "Foundation in Arts", 1), 2));
//        tutGrpList.add(new TutorialGroup(new Programme("FIA", "Foundation in Arts", 1), 3));
//        tutGrpList.add(new TutorialGroup(new Programme("FIA", "Foundation in Arts", 1), 4));

        var a = new TutorialGroup(new Programme("REE", "Degree in Electrical and Electronics", 3), 1);
        var b = new TutorialGroup(new Programme("REE", "Degree in Electrical and Electronics", 3), 2);
        var c = new TutorialGroup(new Programme("REE", "Degree in Electrical and Electronics", 3), 3);

        tutGrpList.add(a);
        tutGrpList.add(b);
        tutGrpList.add(c);

        SetListInterface<TutorialGroup> tempList2 = new ArraySetList();

        tempList2.add(a);
        tempList2.add(b);
        tempList2.add(c);

        progTree.getEntry(new Programme("REE", "Degree in Electrical and Electronics", 3)).setTutGroups(tempList2);

//        tutGrpList.add(new TutorialGroup(new Programme("REE", "Degree in Electrical and Electronics", 3), 1));
//        tutGrpList.add(new TutorialGroup(new Programme("REE", "Degree in Electrical and Electronics", 3), 2));
//        tutGrpList.add(new TutorialGroup(new Programme("REE", "Degree in Electrical and Electronics", 3), 3));
        studentList.add(new Student("2317660", "Michael Buble", new Programme("FIA", "Foundation in Arts", 1), tutGrpList.getEntry(1)));
        studentList.add(new Student("2308411", "John Major", new Programme("FIA", "Foundation in Arts", 1), tutGrpList.getEntry(1)));
        studentList.add(new Student("2318461", "Stephen Sanz", new Programme("FIA", "Foundation in Arts", 1), tutGrpList.getEntry(1)));
        studentList.add(new Student("2303886", "Tommy Hank", new Programme("FIA", "Foundation in Arts", 1), tutGrpList.getEntry(1)));

        tutGrpList.getEntry(1).setTutGrpStudents(new ArraySetList((ArraySetList) studentList));

        refreshList();
    }

    public void generateReport() {
        System.out.println("report");
        reportUI = new ProgReportUI(this);
        reportUI.setVisible(true);
        reportUI.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        reportUI.setLocationRelativeTo(progMain);

        sorter = new Programme();
        var foundList = sorter.search(programmes, "Foundation");
        reportUI.setFoundationData(foundList);

        var dipList = sorter.search(programmes, "Diploma");
        reportUI.setDiplomaData(dipList);

        var degList = sorter.search(programmes, "Degree");
        reportUI.setDegreeData(degList);
        
        for (WindowListener listener : reportUI.getWindowListeners()) {
            reportUI.removeWindowListener(listener);
        }

        reportUI.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                resetUI();
            }
        });
    }

}
