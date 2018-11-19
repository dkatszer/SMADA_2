package kis.sspd.jade.farm;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.core.behaviours.OneShotBehaviour;
import jade.lang.acl.ACLMessage;

@SuppressWarnings("serial")
public class Wolf extends Agent {
	protected int age = 0;
	static int adultAge = 2;
	private static final String BREEDER_NAME = "Breeder";

	protected void setup(){
		System.out.println("Wolf " + getLocalName() + " is getting closer to the farm.");
		addBehaviour(new Live());
		addBehaviour(new GetHungry());
		addBehaviour(new EatBunny());
	}
	
	
	private class Live extends CyclicBehaviour {
		public void action() {
			block(1000);
			if (age == adultAge) {
				System.out.println("Wolf " + getLocalName() + " becomes adult.");
			}
			age += 1;
		}
	}
	
	private class GetHungry extends CyclicBehaviour {
		public void action() {
			ACLMessage message = new ACLMessage(ACLMessage.REQUEST);
			message.addReceiver(new AID(BREEDER_NAME, AID.ISLOCALNAME));
			message.setContent("randomRabbitName");
			send(message);

			ACLMessage receiveMsg = myAgent.blockingReceive();
			if(receiveMsg.getPerformative() != ACLMessage.UNKNOWN) {
				String rabbitName = receiveMsg.getContent();

				ACLMessage messageToRabbit = new ACLMessage(ACLMessage.INFORM);
				message.addReceiver(new AID(rabbitName, AID.ISLOCALNAME));
				message.setContent(getName());
				send(messageToRabbit);
			}
		}
	}
	
	@SuppressWarnings("unused")
	private class GetOld extends OneShotBehaviour {
		public void action() {
			//TODO
		}
	}
	
	private class EatBunny extends OneShotBehaviour {
		public void action() {
			ACLMessage fromRabbit = myAgent.blockingReceive(1000);
			if(fromRabbit != null){
				System.out.println("I have eaten rabbit " + fromRabbit.getContent());
			}
		}
	}
	
	protected void takeDown(){
		System.out.println("Wolf " + getLocalName() + " is daying.");
	}	
}
