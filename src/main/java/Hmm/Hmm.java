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

    public Hmm(Set<State> states, ArrayList<State>[] observations, ArrayList<Symbol>[] emittedSymbols){
        int i = 0;
        stateCount = states.size();
        this.states = new HmmState[stateCount];
        stateIndexes = new HashMap<State, Integer>();
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

    protected double safeLog(double x){
        if (x <= 0){
            return -Integer.MAX_VALUE;
        }
        else{
            return Math.log(x);
        }
    }

    public void save(String fileName){
        FileOutputStream outFile;
        ObjectOutputStream outObject;
        try {
            outFile = new FileOutputStream(fileName);
            outObject = new ObjectOutputStream (outFile);
            outObject.writeObject(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
