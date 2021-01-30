package biad.module.behaviours;

import biad.module.agents.Student;
import biad.module.beans.Book;
import biad.module.beans.Order;
import biad.module.beans.OrderType;
import biad.module.util.EncodingAndDecodingUtil;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

import java.io.IOException;
import java.util.List;
import java.util.Random;

public class ReceiveNotificationAndOrderBooksBehavior extends CyclicBehaviour {

    @Override
    public void action() {
        ACLMessage message = receiveMessage(ACLMessage.INFORM);
        if (message != null) {
            System.out.println("Received an INFORM message");
            Order order = generateOrderForBooksOfInterest(message);
            if (order != null) sendOrder(message, order, ACLMessage.REQUEST);
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

    private Order generateOrderForBooksOfInterest(ACLMessage message) {
        try {
            var content = message.getContentObject();
            Object obj = EncodingAndDecodingUtil.decode((byte[]) content);
            List<Book> booksOfInterest = (List<Book>) obj;
                    System.out.println("message content (books of interest): " + booksOfInterest);
            Student subscriber = (Student) myAgent;

            var order = new Order(OrderType.Loan,subscriber);

            for (int i = 0; i <booksOfInterest.size() ; i++) {
                var randomBook = getRandomBook(booksOfInterest);
                if (order.addOrderItem(randomBook,1) == null) break;
            }
            System.out.println("Subscriber(Student) " + subscriber.getName() + " wants "+ order.getOrderItems());
            return order ;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private void sendOrder(ACLMessage message, Order order, int performative) {
        ACLMessage reply = message.createReply();
        reply.setPerformative(performative);
        try {
            byte [] content = EncodingAndDecodingUtil.encode(order);
            reply.setContentObject(content);
        } catch (IOException e) {
            e.printStackTrace();
        }
        myAgent.send(reply);
    }

    public Book getRandomBook(List<Book> books){
        Random rn = new Random();
        var index =rn.nextInt(books.size());
        return books.get(index);
    }

}
