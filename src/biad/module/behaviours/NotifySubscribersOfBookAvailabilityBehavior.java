package biad.module.behaviours;

import biad.module.agents.Librarian;
import biad.module.agents.Student;
import biad.module.beans.Book;
import biad.module.beans.Subscription;
import biad.module.util.EncodingAndDecodingUtil;
import jade.core.AID;
import jade.core.behaviours.OneShotBehaviour;
import jade.domain.FIPANames;
import jade.lang.acl.ACLMessage;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;

public class NotifySubscribersOfBookAvailabilityBehavior extends OneShotBehaviour {
    @Override
    public void action() {
        Librarian librarian = (Librarian) myAgent;
        List<Student> subscribers = librarian.getSubscribers();
        for (Student subscriber : subscribers) {
            System.out.println("Notifying subscriber "+ subscriber.getName()+" !");
            Subscription subscription = subscriber.getSubscription();
            ArrayList<Book> booksOfInterest = librarian.getCatalogue()
                    .stream()
                    .filter(book -> (subscription.isInterestedInBook(book) && book.isAvailable()))
                    .collect(Collectors.toCollection(ArrayList::new));
            ACLMessage message = new ACLMessage(ACLMessage.INFORM);
            message.addReceiver(subscriber.getAID());
            try {
                var content = EncodingAndDecodingUtil.encode(booksOfInterest);
                message.setContentObject(content);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            message.setOntology("book-catalogue");
            try {

//                message.setDefaultEnvelope();
//                message.getEnvelope().setAclRepresentation(FIPANames.ACLCodec.BITEFFICIENT);
                myAgent.send(message);
                System.out.println("Sent INFORM message to inform subscriber of his books of interest");
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("Failed to send message to subscriber "+ subscriber.getName());
            }

        }


    }

}
