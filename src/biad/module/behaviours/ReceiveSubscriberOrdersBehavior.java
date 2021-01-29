package biad.module.behaviours;

import biad.module.agents.Librarian;
import biad.module.beans.Book;
import biad.module.beans.Order;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


public class ReceiveSubscriberOrdersBehavior extends CyclicBehaviour {
	@Override
	public void action() {		
		ACLMessage message = receiveMessage(ACLMessage.REQUEST);		
		try {
			if (message != null) { 
				Order order = (Order)message.getContentObject();
				System.out.println("Receiving order :" + order.toString());
				Librarian librarian = (Librarian) myAgent;
				String customerName = message.getSender().getName();
				String cause ="";
				if (librarian.validateOder(order, customerName,cause)){
					librarian.checkout(order);
					if (order.isProcessed()){
						sendDesiredBooks(message,order,ACLMessage.AGREE);
					}

				}else {
					sendDenialOfRequestToSubscriber(message,cause,ACLMessage.REFUSE);
				}

			} else {
				block();
			}			
		} catch (Exception e) {
			e.printStackTrace();
		}		
	}
	
	private ACLMessage receiveMessage(int performative) {
		MessageTemplate template = 
				MessageTemplate.and(MessageTemplate.MatchPerformative(performative), 
						MessageTemplate.MatchOntology("book-catalogue"));
		ACLMessage message = myAgent.receive(template);
		return message;
	}
	private void sendDesiredBooks(ACLMessage message, Order processedOrder, int performative) {
		ACLMessage reply = message.createReply();
		reply.setPerformative(performative);
		try {
			reply.setContentObject(processedOrder);
			reply.setOntology("book-catalogue");
		} catch (IOException e) {
			e.printStackTrace();
		}
		myAgent.send(reply);
	}
	private void sendDenialOfRequestToSubscriber(ACLMessage message,String cause, int performative){
		ACLMessage reply = message.createReply();
		reply.setPerformative(performative);
		try {
			reply.setContentObject(cause);
			reply.setOntology("library-rules");
		} catch (IOException e) {
			e.printStackTrace();
		}
		myAgent.send(reply);
	}
	
}