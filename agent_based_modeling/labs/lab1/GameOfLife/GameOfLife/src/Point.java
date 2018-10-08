import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Point {
    private ArrayList<Point> neighbors;
    private int currentState;
    private int nextState;
    private int numStates = 6;
    private Rule rule = Rule.CORAL;

    public Point() {
        currentState = 0;
        nextState = 0;
        neighbors = new ArrayList<>();
    }

    public void clicked() {
        currentState = (++currentState) % numStates;
    }

    public int getState() {
        return currentState;
    }

    public void setState(int s) {
        currentState = s;
    }

    public void calculateNewState() {
//        int activeNeighbors = countActiveNeighbors();
//
//        StateStatus nextStateStatus;
//        if(isAlive()){
//            nextStateStatus = rule.nextStateForAliveState(activeNeighbors);
//        }else{
//            nextStateStatus = rule.nextStateForDeadState(activeNeighbors);
//        }
//        if(nextStateStatus==StateStatus.DEAD){
//            nextState = 0;
//        }else{
//            nextState = 1;
//        }

        if (currentState > 0) {
            nextState = currentState - 1;
        } else {
            if (!neighbors.isEmpty() && neighbors.get(0).currentState > 0) {
                nextState = 6;
            }
        }
    }

    public void changeState() {
        currentState = nextState;
    }

    public void addNeighbor(Point nei) {
        neighbors.add(nei);
    }

    private int countActiveNeighbors() {
        int counter = 0;
        for (Point neighbor : neighbors) {
            if (neighbor.isAlive()) {
                counter++;
            }
        }
        return counter;
    }

    private boolean isAlive() {
        return currentState > 0;
    }

    private enum Rule {
        STANDARD(List.of(2, 3), 3),
        CITIES(List.of(2, 3, 4, 5), List.of(4, 5, 6, 7, 8)),
        CORAL(List.of(4, 5, 6, 7, 8), 3);

        private final List<Integer> stayAlive;
        private final List<Integer> becomesAlive;

        Rule(List<Integer> stayAlive, List<Integer> becomesAlive) {
            this.stayAlive = stayAlive;
            this.becomesAlive = becomesAlive;
        }

        Rule(List<Integer> stayAlive, int becomesAlive) {
            this.stayAlive = stayAlive;
            this.becomesAlive = List.of(becomesAlive);
        }

        public StateStatus nextStateForAliveState(int activeNeighbours) {
            if (stayAlive.contains(activeNeighbours))
                return StateStatus.ALIVE;
            return StateStatus.DEAD;
        }

        public StateStatus nextStateForDeadState(int activeNeighbours) {
            if (becomesAlive.contains(activeNeighbours))
                return StateStatus.ALIVE;
            return StateStatus.DEAD;
        }
    }

    public void drop() {
        int rand = new Random().nextInt(100);
        if (rand <= 5) {
            nextState = 6;
        }
    }

    private enum StateStatus {
        ALIVE,
        DEAD
    }
}
