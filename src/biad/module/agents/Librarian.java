package biad.module.agents;

import biad.module.beans.*;
import biad.module.behaviours.NotifySubscribersOfBookAvailabilityBehavior;
import biad.module.behaviours.ReceiveSubscriberOrdersBehavior;
import biad.module.views.LibrarianAidGui;
import biad.module.views.LibrarianClerkGui;
import biad.module.views.LibrarianGui;
import jade.core.Agent;
import jade.core.behaviours.OneShotBehaviour;
import jade.wrapper.StaleProxyException;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Librarian extends Agent {
    private LibrarianGui window;

    private List<Student> subscribers;

    private List<Book> catalogue;

    private List<Order> orders = new ArrayList<>();

    private LibrarianType librarianType;

    @Override
    protected void setup() {

        Object[] args = getArguments();
        subscribers = (List<Student>) args[0];
        catalogue = (List<Book>) args[1];
        librarianType = (LibrarianType) args[2];
        addBehaviours();
        showGUI();
    }

    private void addBehaviours() {
        addBehaviour(new OneShotBehaviour() {
            @Override
            public void action() {
                System.out.println(librarianType+" Agent Created !!");
            }
        });

        addBehaviour(new ReceiveSubscriberOrdersBehavior());
    }

    private void showGUI() {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    if (getLibrarianType() == LibrarianType.Aid) {
                        window = new LibrarianAidGui(Librarian.this);
                    } else {
                        window = new LibrarianClerkGui(Librarian.this);
                    }

                    window.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public List<Student> getSubscribers() {
        return subscribers;
    }

    public void setSubscribers(List<Student> subscribers) {
        this.subscribers = subscribers;
    }

    public List<Book> getCatalogue() {
        return catalogue;
    }

    public void setCatalogue(List<Book> catalogue) {
        this.catalogue = catalogue;
    }

    public LibrarianType getLibrarianType() {
        return librarianType;
    }

    public void setLibrarianType(LibrarianType librarianType) {
        this.librarianType = librarianType;
    }

    public void register(Object[] subscriberArgs) throws StaleProxyException {

        var container = this.getContainerController();
        String subscriberName = (String) subscriberArgs[1];
        var subscriber = container.createNewAgent(subscriberName,Student.class.getName(),subscriberArgs);
        subscriber.start();
        var state = subscriber.getState();
//        Subscription subscription = new Subscription();
//        subscribers.add(student);
    }

    public void addBook(Book book) {
        catalogue.add(book);
        addBehaviour(new NotifySubscribersOfBookAvailabilityBehavior());
    }

    public boolean validateOder(Order order, String customerName, String cause) {
        var customer = order.getCustomer();
        var subscription = customer.getSubscription();
        if (order.getOrderType() == OrderType.Loan) {
            if (subscription.getBorrowedBooks().size() >= 3) {
                cause = " Reached the maximum number of allowed loans ";
                System.out.println(customer + cause);

                return false;
            }
            if (subscription.getBorrowedBooks().size() + order.getOrderItems().size() >= 3) {
                cause = " Reached the maximum number of allowed loans "
                        + "\nAlready borrowed: " + subscription.getBorrowedBooks()
                        + "\nTo borrow: " + order.getBooks();
                System.out.println(customer + cause);
                return false;
            }
        }
        return true;
//        window.showAlert(customer.getName()+" wants "+order.getOrderItems());
//        updateSellingReport();
    }

    public void checkout(Order order) {
        if (order.getOrderType() == OrderType.Loan) {
            order.getBooks().forEach(book -> {
                LendBook(book);
            });
        }
        if (order.getOrderType() == OrderType.Purchase) {
            order.getBooks().forEach(book -> {
                SellBook(book);
            });
        }
        order.setProcessed(true);
        orders.add(order);
    }

    public void LendBook(Book book) {
        book.setBorrowedQuantity(book.getBorrowedQuantity() + 1);
    }

    public void SellBook(Book book) {
        book.setSoldQuantity(book.getSoldQuantity() + 1);
    }
}
