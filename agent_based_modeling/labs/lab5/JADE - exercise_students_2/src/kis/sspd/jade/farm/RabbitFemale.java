package kis.sspd.jade.farm;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.core.behaviours.OneShotBehaviour;
import jade.lang.acl.ACLMessage;

@SuppressWarnings("serial")
public class RabbitFemale extends Agent {
    protected int age = 0;
    static int lifetime = 10;
    static int adultAge = 2;
    private static final String BREEDER_NAME = "Breeder";

    protected void setup() {
        System.out.println("Female " + getLocalName() + " appeared on the farm.");
        addBehaviour(new Live());
        addBehaviour(new Listen());
    }


    private class Live extends CyclicBehaviour {
        public void action() {
            block(1000);
            if (age == adultAge) {
                System.out.println("Female " + getLocalName() + " becomes adult.");
            }
            age += 1;
            if (age > lifetime) {
                ACLMessage message = new ACLMessage(ACLMessage.INFORM);
                message.addReceiver(new AID(BREEDER_NAME, AID.ISLOCALNAME));
                message.setContent(getName());
                send(message);
            }
        }
    }

    private class Listen extends CyclicBehaviour {
        public void action() {
            //TODO
        }
    }

    @SuppressWarnings("unused")
    private class BreedNewRabbits extends OneShotBehaviour {
        public void action() {
            //TODO
        }
    }

    protected void takeDown() {
        System.out.println("Famale " + getLocalName() + " is dying.");
    }
}
