package biad.module.behaviours;

import biad.module.agents.Librarian;
import biad.module.beans.Order;
import biad.module.util.EncodingAndDecodingUtil;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

import java.io.IOException;


public class ReceiveSubscriberOrdersBehavior extends CyclicBehaviour {
	@Override
	public void action() {		
		ACLMessage message = receiveMessage(ACLMessage.REQUEST);		
		try {
			if (message != null) { 
				byte[] content= (byte[]) message.getContentObject();
				Object obj = EncodingAndDecodingUtil.decode(content);
				Order order = (Order) obj;

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
			/**
			 * Encoding in base 64 to avoid this error from jade platform
			 * 	===== E R R O R !!! =======
			 *
			 * Missing support for Base64 conversions
			 * Please refer to the documentation for details.
			 * =============================================
			 * */

			byte[] content = EncodingAndDecodingUtil.encode(processedOrder);
			reply.setContentObject(content);
			reply.setOntology("book-catalogue");
			reply.setLanguage("english");
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