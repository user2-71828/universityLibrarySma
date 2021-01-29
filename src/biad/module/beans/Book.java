package biad.module.beans;

import java.io.Serializable;

public class Book implements Serializable {
    private String bookTitle;
    private String author;
    private Subject subject;
    private int totalQuantity;
    private double unitPrice ;
    private int soldQuantity;
    private int borrowedQuantity;



    public Book(String bookTitle, String author, Subject subject, int totalQuantity,double unitPrice) {
        this.bookTitle = bookTitle;
        this.author = author;
        this.subject = subject;
        this.totalQuantity = totalQuantity;
        this.soldQuantity = 0;
        this.borrowedQuantity = 0;
        this.unitPrice = unitPrice;
    }

    public String getBookTitle() {
        return bookTitle;
    }

    public void setBookTitle(String bookTitle) {
        this.bookTitle = bookTitle;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }

    public int getTotalQuantity() {
        return totalQuantity;
    }

    public void setTotalQuantity(int totalQuantity) {
        this.totalQuantity = totalQuantity;
    }

    public int getSoldQuantity() {
        return soldQuantity;
    }

    public void setSoldQuantity(int soldQuantity) {
        this.soldQuantity = soldQuantity;
    }

    public int getBorrowedQuantity() {
        return borrowedQuantity;
    }

    public void setBorrowedQuantity(int borrowedQuantity) {
        this.borrowedQuantity = borrowedQuantity;
    }

    public int getAvailableQuantity() {
        return totalQuantity - soldQuantity - borrowedQuantity;
    }


    public double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public boolean isAvailable(){
        return getAvailableQuantity()>0;
    }

    @Override
    public String toString() {
        return "Book{" +
                "bookName='" + bookTitle + '\'' +
                ", author='" + author + '\'' +
                ", subject='" + subject + '\'' +
                ", availableQuantity=" + getAvailableQuantity() +
                ", unitPrice=" + unitPrice +
                '}';
    }
}
