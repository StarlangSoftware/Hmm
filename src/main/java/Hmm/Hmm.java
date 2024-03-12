package Hmm;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

import DataStructure.CounterHashMap;
import Math.*;

public abstract class Hmm<State, Symbol> implements Serializable {
    protected Matrix transitionProbabilities;
    protected HashMap<State, Integer> stateIndexes;
    protected HmmState<State, Symbol>[] states;
    protected int stateCount;
    protected abstract void calculatePi(ArrayList<State>[] observations);
    protected abstract void calculateTransitionProbabilities(ArrayList<State>[] observations);
    public abstract ArrayList<State> viterbi(ArrayList<Symbol> s);

    /**
     * A constructor of {@link Hmm} class which takes a {@link Set} of states, an array of observations (which also
     * consists of an array of states) and an array of instances (which also consists of an array of emitted symbols).
     * The constructor initializes the state array with the set of states and uses observations and emitted symbols
     * to calculate the emission probabilities for those states.
     *
     * @param states A {@link Set} of states, consisting of all possible states for this problem.
     * @param observations An array of instances, where each instance consists of an array of states.
     * @param emittedSymbols An array of instances, where each instance consists of an array of symbols.
     */
    public Hmm(Set<State> states, ArrayList<State>[] observations, ArrayList<Symbol>[] emittedSymbols){
        int i = 0;
        stateCount = states.size();
        this.states = new HmmState[stateCount];
        stateIndexes = new HashMap<>();
        for (State state : states){
            stateIndexes.put(state, i);
            i++;
        }
        calculatePi(observations);
        i = 0;
        for (State state : states){
            HashMap<Symbol, Double> emissionProbabilities = calculateEmissionProbabilities(state, observations, emittedSymbols);
            this.states[i] = new HmmState(state, emissionProbabilities);
            i++;
        }
        calculateTransitionProbabilities(observations);
    }

    /**
     * calculateEmissionProbabilities calculates the emission probabilities for a specific state. The method takes the state,
     * an array of observations (which also consists of an array of states) and an array of instances (which also consists
     * of an array of emitted symbols).
     *
     * @param state The state for which emission probabilities will be calculated.
     * @param observations An array of instances, where each instance consists of an array of states.
     * @param emittedSymbols An array of instances, where each instance consists of an array of symbols.
     * @return A {@link HashMap} Emission probabilities for a single state. Contains a probability for each symbol emitted.
     */
    protected HashMap<Symbol, Double> calculateEmissionProbabilities(State state, ArrayList<State>[] observations, ArrayList<Symbol>[] emittedSymbols){
        CounterHashMap<Symbol> counts = new CounterHashMap<>();
        HashMap<Symbol, Double> emissionProbabilities = new HashMap<>();
        State currentState;
        Symbol currentSymbol;
        for (int i = 0; i < observations.length; i++){
            for (int j = 0; j < observations[i].size(); j++){
                currentState = observations[i].get(j);
                currentSymbol = emittedSymbols[i].get(j);
                if (currentState.equals(state)){
                    counts.put(currentSymbol);
                }
            }
        }
        double sum = counts.sumOfCounts();
        for (Symbol symbol : counts.keySet()){
            emissionProbabilities.put(symbol, counts.get(symbol) / sum);
        }
        return emissionProbabilities;
    }

    /**
     * safeLog calculates the logarithm of a number. If the number is less than 0, the logarithm is not defined, therefore
     * the function returns -Infinity.
     *
     * @param x Input number
     * @return the logarithm of x. If x less than 0 return -infinity.
     */
    protected double safeLog(double x){
        if (x <= 0){
            return -Integer.MAX_VALUE;
        }
        else{
            return Math.log(x);
        }
    }

    /**
     * save method saves the Hmm in object form.
     *
     * @param fileName File name to store the object file.
     */
    public void save(String fileName){
        FileOutputStream outFile;
        ObjectOutputStream outObject;
        try {
            outFile = new FileOutputStream(fileName);
            outObject = new ObjectOutputStream (outFile);
            outObject.writeObject(this);
        } catch (IOException ignored) {
        }
    }

}
