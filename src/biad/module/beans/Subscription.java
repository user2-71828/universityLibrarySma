package biad.module.beans;

import biad.module.agents.Student;

import java.util.ArrayList;
import java.util.List;

public class Subscription {
    private Student subscriber;
    private List<Interest> interests = new ArrayList<>();
    private List<Book> borrowedBooks = new ArrayList<>();
    private List<Book> boughtBooks = new ArrayList<>();

    public Subscription(Student subscriber) {
        this.subscriber = subscriber;
        subscriber.setSubscription(this);
    }

    public Subscription(Student subscriber, List<Interest> interests) {
        this.subscriber = subscriber;
        this.interests = interests;
    }
    public void addInterest(String objectOfInterest, InterestType interestType){
        interests.add(new Interest(objectOfInterest,interestType));
    }

    public boolean isInterestedInBook(Book book) {
       return interests.stream().anyMatch(interest -> interest.isObjectOfInterest(book));
    }

    public List<Book> getBorrowedBooks() {
        return borrowedBooks;
    }

    public void setBorrowedBooks(List<Book> borrowedBooks) {
        this.borrowedBooks = borrowedBooks;
    }

    public List<Book> getBoughtBooks() {
        return boughtBooks;
    }

    public void setBoughtBooks(List<Book> boughtBooks) {
        this.boughtBooks = boughtBooks;
    }
}
