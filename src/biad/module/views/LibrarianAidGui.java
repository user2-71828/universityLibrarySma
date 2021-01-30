package biad.module.views;

import biad.module.agents.Librarian;
import biad.module.beans.Department;
import biad.module.beans.Subject;
import jade.wrapper.StaleProxyException;

import javax.swing.*;
import java.awt.*;

public class LibrarianAidGui extends LibrarianGui {

    private JTextField studentIdField;
    private JComboBox<Department> departmentField;
    private JTextField studentNameField;
    private JButton registerButton;
    private JTextField interestField;
    private JComboBox interestTypeComboBox;
    private JButton addInterestButton;
    private JList interestsList;
    private JPanel studentRegistrationPanel;

    public LibrarianAidGui(Librarian librarianAid) {
        super(librarianAid);
    }

    private void registerSubscriberHandler() {
        System.out.println("Registering Student ...");
        try {
            Long studentId = Long.parseLong(studentIdField.getText());
            String studentName = studentNameField.getText();
            Department department = Department.valueOf(departmentField.getSelectedItem().toString());

            Object[] subscriberArgs = {
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
        frame = new JFrame();
        frame.setBounds(100, 100, 450, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(new BorderLayout(0, 0));

        JPanel footerPanel = new JPanel();
        frame.getContentPane().add(footerPanel, BorderLayout.SOUTH);

//        JButton advertiseBtn = new JButton("Advertise");
//        advertiseBtn.addActionListener(e -> advertiseProductHandler());
//        footerPanel.add(advertiseBtn);
//
//        JButton createConsumerBtn = new JButton("Create consumer Agent");
//        createConsumerBtn.addActionListener(e -> createConsumerHandler());
//        footerPanel.add(createConsumerBtn);

        frame.getContentPane().add(studentRegistrationPanel, BorderLayout.CENTER);
    }

}
