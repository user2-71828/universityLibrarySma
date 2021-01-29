package biad.module.views;

import biad.module.agents.Librarian;
import biad.module.beans.Department;
import biad.module.beans.Subject;
import jade.wrapper.StaleProxyException;

import javax.swing.*;

public class LibrarianAidGui extends LibrarianGui {

    private JTextField studentNameField;
    private JTextField studentIdField;
    private JComboBox<Subject> departmentField;

    public LibrarianAidGui(Librarian librarianAid) {
        super(librarianAid);
    }

    private void registerSubscriberHandler() {
        System.out.println("Registering Student ...");
        try {
            Long studentId = Long.parseLong(studentIdField.getText());
            String studentName = studentNameField.getText();
            Department department = Department.valueOf(departmentField.getSelectedItem().toString());

            Object [] subscriberArgs = {
                    studentId,
                    studentName,
                    department
            };
            this.librarian.register(subscriberArgs);

        } catch (StaleProxyException e) {
            showErrorAlert("Stale Proxy Exception");
            e.printStackTrace();
        }
    }

    @Override
    protected void initialize() {

    }
}
