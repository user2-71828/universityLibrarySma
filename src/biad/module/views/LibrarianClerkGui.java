package biad.module.views;

import biad.module.agents.Librarian;
import biad.module.beans.Book;
import biad.module.beans.Subject;
import jade.wrapper.ContainerController;

import javax.swing.*;
import java.awt.*;

public class LibrarianClerkGui extends LibrarianGui{


    private JTextField bookTitleField;
    private JComboBox subjectComboBox;
    private JButton addBookButton;
    private JTextField authorField;
    private JTextField unitPriceField;
    private JTextField totalQuantityField;
    private JTextField soldQuantityField;
    private JTextField borrowedQuantityField;

    private JPanel bookForm;

    public LibrarianClerkGui(Librarian librarianClerk) {
        super(librarianClerk);
        initialize();
    }

    private void addBookHandler() {
        System.out.println("Notifying Subscribers  ...");
        String bookTitle = bookTitleField.getText();
        String author = authorField.getText();
        Subject subject = Subject.valueOf(subjectComboBox.getSelectedItem().toString());
        try {
            Double unitPrice = Double.parseDouble(unitPriceField.getText());
            Integer totalQuantity = Integer.parseInt(totalQuantityField.getText());
            Book product = new Book(bookTitle, author, subject,totalQuantity,unitPrice);
            this.librarian.addBook(product);
        } catch (NumberFormatException e) {
            showErrorAlert("The price must be a number !");
        }

    }

    @Override
    protected void initialize() {
        frame = new JFrame();
        frame.setBounds(100, 100, 600, 500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(new BorderLayout(0, 0));

        JPanel footerPanel = new JPanel();
        frame.getContentPane().add(footerPanel, BorderLayout.SOUTH);

        addBookButton.addActionListener(e -> addBookHandler());
        subjectComboBox.setModel(new DefaultComboBoxModel(Subject.values()));
        frame.getContentPane().add(bookForm, BorderLayout.CENTER);


    }
}
