package biad.module.behaviours;

import biad.module.agents.Librarian;
import biad.module.agents.Student;
import biad.module.beans.Book;
import biad.module.beans.Subscription;
import jade.core.AID;
import jade.core.behaviours.OneShotBehaviour;
import jade.lang.acl.ACLMessage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class NotifySubscribersOfBookAvailabilityBehavior extends OneShotBehaviour {
    @Override
    public void action() {
        Librarian librarian = (Librarian) myAgent;
        List<Student> subscribers = librarian.getSubscribers();
        for (Student subscriber : subscribers) {
            Subscription subscription = subscriber.getSubscription();
            ArrayList<Book> booksOfInterest = librarian.getCatalogue()
                    .stream()
                    .filter(book -> (subscription.isInterestedInBook(book) && book.isAvailable()))
                    .collect(Collectors.toCollection(ArrayList::new));
            ACLMessage message = new ACLMessage(ACLMessage.INFORM);
            message.addReceiver(new AID(subscriber
                    .getName(), AID.ISLOCALNAME));
            try {
                message.setContentObject(booksOfInterest);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            message.setOntology("book-catalogue");
            myAgent.send(message);
            System.out.println("Sent INFORM message to inform subscriber of his books of interest");
        }


    }
}
