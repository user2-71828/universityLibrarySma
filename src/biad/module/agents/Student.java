package biad.module.agents;

import biad.module.beans.Book;
import biad.module.beans.Department;
import biad.module.beans.Subscription;
import jade.core.Agent;

import java.util.ArrayList;
import java.util.List;

public class Student extends Agent {
    private Long studentId;
    private String studentName;
    private Department department;
    private Subscription subscription ;

    public Subscription getSubscription() {
        return subscription;
    }

    public void setSubscription(Subscription subscription) {
        this.subscription = subscription;
    }



    @Override
    public String toString() {
        return "Student{" +
                "studentId=" + studentId +
                ", studentName='" + studentName + '\'' +
                ", department=" + department +
                '}';
    }
}
