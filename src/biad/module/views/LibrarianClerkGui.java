package biad.module.views;

import biad.module.agents.Librarian;
import biad.module.beans.Book;
import biad.module.beans.Subject;

import javax.swing.*;
import java.awt.*;

public class LibrarianClerkGui extends LibrarianGui{


    private JTextField bookTitleField;
    private JTextField authorField;
    private JComboBox<Subject> subjectField;
    private JTextField unitPriceField;
    private JTextField totalQuantityField;
    private JTextField soldQuantityField;
    private JTextField borrowedQuantityField;

    public LibrarianClerkGui(Librarian librarianClerk) {
        super(librarianClerk);
    }

    private void addBookHandler() {
        System.out.println("Advertising the Product ...");
        String bookTitle = bookTitleField.getText();
        String author = authorField.getText();
        Subject subject = Subject.valueOf(subjectField.getSelectedItem().toString());
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
        frame.setBounds(100, 100, 450, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(new BorderLayout(0, 0));

        JPanel footerPanel = new JPanel();
        frame.getContentPane().add(footerPanel, BorderLayout.SOUTH);

        JButton advertiseBtn = new JButton("Add Book");
        advertiseBtn.addActionListener(e -> addBookHandler());
        footerPanel.add(advertiseBtn);

//        JButton createConsumerBtn = new JButton("Create consumer Agent");
//        createConsumerBtn.addActionListener(e -> createConsumerHandler());
//        footerPanel.add(createConsumerBtn);

        JPanel mainContentPanel = new JPanel();
        frame.getContentPane().add(mainContentPanel, BorderLayout.CENTER);
        mainContentPanel.setLayout(new GridLayout(1, 0, 0, 0));


        //region Book Form
        JPanel bookPanel = new JPanel();
        mainContentPanel.add(bookPanel);
        bookPanel.setLayout(new GridLayout(10, 0, 0, 0));

        //region Book Title
        JLabel bookTitleLabel = new JLabel("Book Title");
        bookTitleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        bookPanel.add(bookTitleLabel);

        bookTitleField = new JTextField();
        bookTitleField.setHorizontalAlignment(SwingConstants.CENTER);
        bookPanel.add(bookTitleField);
        bookTitleField.setColumns(10);
        //endregion

        //region Author
        JLabel authorLabel = new JLabel("Author");
        authorLabel.setHorizontalAlignment(SwingConstants.CENTER);
        bookPanel.add(authorLabel);

        authorField = new JTextField();
        authorField.setHorizontalAlignment(SwingConstants.CENTER);
        bookPanel.add(authorField);
        authorField.setColumns(10);
        //endregion

        //region Unit Price
        JLabel unitPriceLabel = new JLabel("Unit Price");
        unitPriceLabel.setHorizontalAlignment(SwingConstants.CENTER);
        bookPanel.add(unitPriceLabel);

        unitPriceField = new JTextField();
        unitPriceField.setHorizontalAlignment(SwingConstants.CENTER);
        bookPanel.add(unitPriceField);
        unitPriceField.setColumns(10);
        //endregion

        //region Total Quantity
        JLabel totalQuantityLabel = new JLabel("Total Quantity");
        totalQuantityLabel.setHorizontalAlignment(SwingConstants.CENTER);
        bookPanel.add(totalQuantityLabel);

        totalQuantityField = new JTextField();
        totalQuantityField.setHorizontalAlignment(SwingConstants.CENTER);
        bookPanel.add(totalQuantityField);
        totalQuantityField.setColumns(10);
        //endregion

        JLabel subjectLabel = new JLabel("Subject");
        authorLabel.setHorizontalAlignment(SwingConstants.CENTER);
        bookPanel.add(authorLabel);

        subjectField = new JComboBox<>();
        authorField.setHorizontalAlignment(SwingConstants.CENTER);
        bookPanel.add(authorField);
        authorField.setColumns(10);
        //endregion



        JPanel reportingPanel = new JPanel();
        mainContentPanel.add(reportingPanel);
        reportingPanel.setLayout(new GridLayout(6, 0, 0, 0));



        JLabel amountOfProfitLabel = new JLabel("Sold quantity");
        amountOfProfitLabel.setHorizontalAlignment(SwingConstants.CENTER);
        reportingPanel.add(amountOfProfitLabel);

        soldQuantityField = new JTextField();
        soldQuantityField.setHorizontalAlignment(SwingConstants.CENTER);
        soldQuantityField.setEditable(false);
        soldQuantityField.setText("0");
        reportingPanel.add(soldQuantityField);
        soldQuantityField.setColumns(10);

        JLabel borrowedQuantityLabel = new JLabel("Borrowed Quantity");
        borrowedQuantityLabel.setHorizontalAlignment(SwingConstants.CENTER);
        reportingPanel.add(borrowedQuantityLabel);

        borrowedQuantityField = new JTextField();
        borrowedQuantityField.setHorizontalAlignment(SwingConstants.CENTER);
        borrowedQuantityField.setEditable(false);
        borrowedQuantityField.setText("0");
        reportingPanel.add(borrowedQuantityField);
        borrowedQuantityField.setColumns(10);

        JPanel topPanel = new JPanel();
        frame.getContentPane().add(topPanel, BorderLayout.NORTH);

        JLabel label = new JLabel("Librarian Clerk GUI");
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setFont(new Font("Tahoma", Font.PLAIN, 24));
        topPanel.add(label);
    }
}
