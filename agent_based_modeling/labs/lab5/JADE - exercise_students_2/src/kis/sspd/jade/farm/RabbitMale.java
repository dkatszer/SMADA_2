package kis.sspd.jade.farm;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.core.behaviours.OneShotBehaviour;
import jade.lang.acl.ACLMessage;


@SuppressWarnings("serial")
public class RabbitMale extends Agent {
	protected int age = 0;
	static int lifetime = 10;
	static int adultAge = 2;
	private static final String BREEDER_NAME = "Breeder";

	protected void setup(){
		System.out.println("Male " + getLocalName() + " appeared on the farm.");
		addBehaviour(new Live());
		addBehaviour(new Behave());
	}

	private class Live extends CyclicBehaviour {
		public void action() {
			block(1000);
			if (age == adultAge) {
				System.out.println("Male " + getLocalName() + " becomes adult.");
			}
			age += 1;
			if (age == lifetime) {
				ACLMessage message = new ACLMessage(ACLMessage.INFORM);
				message.addReceiver(new AID(BREEDER_NAME, AID.ISLOCALNAME));
				message.setContent(getName());
				send(message);
			}
		}
	}
	
	private class Behave extends CyclicBehaviour {
		public void action() {
			ACLMessage fromWolf = myAgent.blockingReceive(1000);
			if (fromWolf != null) {
				ACLMessage message = new ACLMessage(ACLMessage.INFORM);
				message.addReceiver(new AID(BREEDER_NAME, AID.ISLOCALNAME));
				message.setContent(getName());
				send(message);

				ACLMessage reply = fromWolf.createReply();
				reply.setPerformative(ACLMessage.INFORM);
				reply.setContent(getName());
				send(reply);
			}
		}
	}
	
	@SuppressWarnings("unused")
	private class GetOlder extends OneShotBehaviour {
		public void action() {
			//TODO
		}
	}
	
	@SuppressWarnings("unused")
	private class TryToHaveChildren extends OneShotBehaviour {
		public void action() {
			//TODO
		}
	}
	
	@SuppressWarnings("unused")
	private class Court extends OneShotBehaviour {
		public void action() {
			//TODO
		}
	}
	
	protected void takeDown(){
		System.out.println("Male " + getLocalName() + " is dying.");
	}
}
