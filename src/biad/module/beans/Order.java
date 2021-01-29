package biad.module.beans;

import biad.module.agents.Student;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Order implements Serializable {
    private Map<Book,Integer> orderItems = new HashMap<>();
    private OrderType orderType;
    private Student customer;
    private boolean isProcessed ;
//    private OrderState orderState;

    public Order(OrderType orderType, Student customer) {
        this.orderType = orderType;
        this.customer = customer;
        this.isProcessed =false;
//        this.orderState = OrderState.Approved;
    }

    public Map<Book, Integer> getOrderItems() {
        return orderItems;
    }

    public OrderType getOrderType() {
        return orderType;
    }

    public Student getCustomer() {
        return customer;
    }
    public Set<Book> getBooks(){
        return this.orderItems.keySet();
    }
    //    public OrderState getOrderState() {
//        return orderState;
//    }

    public Order addOrderItem(Book book, Integer quantity){
        if (orderType == OrderType.Loan){
            quantity =1;
        }
        orderItems.put(book,quantity);
        return this;
    }

    public boolean isProcessed() {
        return isProcessed;
    }

    public void setProcessed(boolean processed) {
        isProcessed = processed;
    }
}
