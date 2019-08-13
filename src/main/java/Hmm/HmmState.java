package Hmm;

import java.io.Serializable;
import java.util.HashMap;

public class HmmState<State, Symbol> implements Serializable {
    protected HashMap<Symbol, Double> emissionProbabilities;
    protected State state;

    /**
     * A constructor of {@link HmmState} class which takes a {@link State} and emission probabilities as inputs and
     * initializes corresponding class variable with these inputs.
     *
     * @param state Data for this state.
     * @param emissionProbabilities Emission probabilities for this state
     */
    public HmmState(State state, HashMap<Symbol, Double> emissionProbabilities){
        this.state = state;
        this.emissionProbabilities = emissionProbabilities;
    }

    /**
     * Accessor method for the state variable.
     *
     * @return state variable.
     */
    public State getState(){
        return state;
    }

    /**
     * getEmitProb method returns the emission probability for a specific symbol.
     *
     * @param symbol Symbol for which the emission probability will be get.
     * @return Emission probability for a specific symbol.
     */
    public double getEmitProb(Symbol symbol){
        if (emissionProbabilities.containsKey(symbol)){
            return emissionProbabilities.get(symbol);
        } else {
            return 0.0;
        }
    }

}
