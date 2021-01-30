package biad.module.behaviours;

import biad.module.agents.Student;
import biad.module.beans.Book;
import biad.module.beans.Order;
import biad.module.beans.OrderType;
import biad.module.util.EncodingAndDecodingUtil;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.lang.acl.UnreadableException;

import java.util.List;

public class ReceiveOrderedBooksBehavior extends CyclicBehaviour {
    @Override
    public void action() {
        ACLMessage message = receiveMessage(ACLMessage.AGREE);
        if (message != null) {
            System.out.println(myAgent+" Received an Agree message from "+message.getSender());
            receiveOrderedBooks(message);

        } else {
            block();
        }
    }
    private ACLMessage receiveMessage(int performative) {
        MessageTemplate template =
                MessageTemplate.and(MessageTemplate.MatchPerformative(performative),
                        MessageTemplate.MatchOntology("book-catalogue"));
        ACLMessage message = myAgent.receive(template);
        return message;
    }
    private void receiveOrderedBooks(ACLMessage message){
        try {
            /**
             * Decode and deserialize order
             * */
            byte[] content= (byte[]) message.getContentObject();
            Object obj = EncodingAndDecodingUtil.decode(content);
            Order order = (Order) obj;
            System.out.println(myAgent+"Received Order "+order);

            Student subscriber = (Student) myAgent;
            if (order.isProcessed()) {
                if (order.getOrderType() == OrderType.Purchase){
                    subscriber.getSubscription().getBoughtBooks().addAll(order.getBooks());
                }

                if (order.getOrderType() == OrderType.Loan){
                    subscriber.getSubscription().getBorrowedBooks().addAll(order.getBooks());
                }

            }
            return;

        } catch (UnreadableException e) {
            e.printStackTrace();
        }

    }
}
