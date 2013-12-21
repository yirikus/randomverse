package cz.terrmith.randomverse.core.state;

import java.util.HashMap;
import java.util.Map;

/**
 * State machine with
 */
public class StateMachine {
    private Map<String,State> states = new HashMap<String, State>();

    //reference to current state
    private State currentState;


    public void update() {

    }

    /**
     * Adds a state to available state colleciton
     * @param state
     */
    public void addState(State state) {
        if (state.getName() == null) {
            throw new IllegalArgumentException("State must have a name.");
        }

        states.put(state.getName(), state);
    }

    /**
     * sets state with given name as current state
     * @param stateName
     */
    public void setCurrentState(String stateName){
        State nextState = states.get(stateName);
        if (nextState == null) {
            throw new IllegalArgumentException("State machine does not know state with name '" + stateName + "'");
        }
        nextState.activate(currentState);
        if (currentState != null) {
            currentState.deactivate(nextState);
        }

        currentState = nextState;
    }


    public State getCurrentState() {
        return currentState;
    }
}
