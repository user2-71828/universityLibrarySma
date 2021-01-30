package biad.module.agents;

import biad.module.beans.Book;
import biad.module.beans.Department;
import biad.module.beans.Interest;
import biad.module.beans.Subscription;
import biad.module.behaviours.ReceiveNotificationAndOrderBooksBehavior;
import biad.module.behaviours.ReceiveOrderedBooksBehavior;
import jade.core.Agent;
import jade.core.behaviours.OneShotBehaviour;

import java.util.ArrayList;
import java.util.List;

public class Student extends Agent {
    private Long studentId;
    private String studentName;
    private Department department;
    private Subscription subscription ;

    @Override
    protected void setup() {
        Object[] args = getArguments();
        studentId = (Long) args[0];
        studentName = (String) args[1];
        department = (Department) args[2];
        List<Interest> interests = (List<Interest>) args[3];
        List<Student> subscribers = (List<Student>) args[4];
        subscribers.add(this);
        Subscription subscription = new Subscription(this);
        subscription.addInterests(interests);
        addBehaviour(new OneShotBehaviour() {
            @Override
            public void action() { System.out.println("Student "+studentName+" Created!!");
            }
        });
        addBehaviour(new ReceiveNotificationAndOrderBooksBehavior());
        addBehaviour(new ReceiveOrderedBooksBehavior());

    }

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
