package biad.module.views;

import biad.module.agents.Librarian;
import biad.module.agents.Student;
import biad.module.beans.Department;
import biad.module.beans.Interest;
import biad.module.beans.InterestType;
import biad.module.beans.Subject;
import jade.wrapper.ContainerController;
import jade.wrapper.StaleProxyException;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    private List<Interest> interests = new ArrayList<>() ;

    public LibrarianAidGui(Librarian librarianAid) {
        super(librarianAid);
        initialize();
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
                    department,
                    interests,
                    this.librarian.getSubscribers()
            };

            this.librarian.register(subscriberArgs);

        } catch (StaleProxyException e) {
            showErrorAlert("Stale Proxy Exception");
            e.printStackTrace();
        }
    }
    private void addInterest(){
        if (interests == null) interests = new ArrayList<>();
        String interestName = interestField.getText();
        InterestType interestType = InterestType.valueOf(interestTypeComboBox.getSelectedItem().toString());
        Interest interest = new Interest(interestName,interestType);
        interests.add(interest);
        ((DefaultListModel)interestsList.getModel()).addElement(interest);

    }

    @Override
    protected void initialize() {
        frame = new JFrame();
        frame.setBounds(100, 100, 450, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(new BorderLayout(0, 0));

        JPanel footerPanel = new JPanel();
        frame.getContentPane().add(footerPanel, BorderLayout.SOUTH);

//        footerPanel.add(createConsumerBtn);

        departmentField.setModel(new DefaultComboBoxModel<>(Department.values()));
        interestTypeComboBox.setModel(new DefaultComboBoxModel(InterestType.values()));
        registerButton.addActionListener(e -> registerSubscriberHandler());
        addInterestButton.addActionListener(e -> addInterest());
        DefaultListModel model = new DefaultListModel();
        model.addAll(interests);
        interestsList.setModel(model);
        frame.getContentPane().add(studentRegistrationPanel, BorderLayout.CENTER);
    }

}
