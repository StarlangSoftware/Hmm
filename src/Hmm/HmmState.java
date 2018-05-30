package Hmm;

import java.io.Serializable;
import java.util.HashMap;

public class HmmState<State, Symbol> implements Serializable {
    protected HashMap<Symbol, Double> emissionProbabilities;
    protected State state;

    public HmmState(State state, HashMap<Symbol, Double> emissionProbabilities){
        this.state = state;
        this.emissionProbabilities = emissionProbabilities;
    }

    public State getState(){
        return state;
    }

    public double getEmitProb(Symbol symbol){
        if (emissionProbabilities.containsKey(symbol)){
            return emissionProbabilities.get(symbol);
        } else {
            return 0.0;
        }
    }

}
