package Hmm;

import Math.*;

import java.io.*;
import java.util.ArrayList;
import java.util.Set;

public class Hmm1<State, Symbol> extends Hmm<State,Symbol> implements Serializable{
    private Vector pi;

    /**
     * A constructor of {@link Hmm1} class which takes a {@link Set} of states, an array of observations (which also
     * consists of an array of states) and an array of instances (which also consists of an array of emitted symbols).
     * The constructor calls its super method to calculate the emission probabilities for those states.
     *
     * @param states A {@link Set} of states, consisting of all possible states for this problem.
     * @param observations An array of instances, where each instance consists of an array of states.
     * @param emittedSymbols An array of instances, where each instance consists of an array of symbols.
     */
    public Hmm1(Set<State> states, ArrayList<State>[] observations, ArrayList<Symbol>[] emittedSymbols) {
        super(states, observations, emittedSymbols);
    }

    /**
     * calculatePi calculates the prior probability vector (initial probabilities for each state) from a set of
     * observations. For each observation, the function extracts the first state in that observation. Normalizing the
     * counts of the states returns us the prior probabilities for each state.
     *
     * @param observations A set of observations used to calculate the prior probabilities.
     */
    protected void calculatePi(ArrayList<State>[] observations){
        pi = new Vector(stateCount, 0.0);
        for (ArrayList<State> observation : observations) {
            int index = stateIndexes.get(observation.get(0));
            pi.addValue(index, 1.0);
        }
        pi.l1Normalize();
    }

    /**
     * calculateTransitionProbabilities calculates the transition probabilities matrix from each state to another state.
     * For each observation and for each transition in each observation, the function gets the states. Normalizing the
     * counts of the pair of states returns us the transition probabilities.
     *
     * @param observations A set of observations used to calculate the transition probabilities.
     */
    protected void calculateTransitionProbabilities(ArrayList<State>[] observations){
        transitionProbabilities = new Matrix(stateCount, stateCount);
        for (ArrayList<State> current : observations) {
            for (int j = 0; j < current.size() - 1; j++) {
                int from = stateIndexes.get(current.get(j));
                int to = stateIndexes.get(current.get(j + 1));
                transitionProbabilities.increment(from, to);
            }
        }
        transitionProbabilities.columnWiseNormalize();
    }

    /**
     * logOfColumn calculates the logarithm of each value in a specific column in the transition probability matrix.
     *
     * @param column Column index of the transition probability matrix.
     * @return A vector consisting of the logarithm of each value in the column in the transition probability matrix.
     */
    private Vector logOfColumn(int column){
        Vector result = new Vector(0, 0);
        int i;
        for (i = 0; i < stateCount; i++){
            result.add(safeLog(transitionProbabilities.getValue(i, column)));
        }
        return result;
    }

    /**
     * viterbi calculates the most probable state sequence for a set of observed symbols.
     *
     * @param s A set of observed symbols.
     * @return The most probable state sequence as an {@link ArrayList}.
     */
    public ArrayList<State> viterbi(ArrayList<Symbol> s){
        int i, j, t, maxIndex;
        Symbol emission;
        ArrayList<State> result;
        Matrix gamma;
        Vector tempArray, qs;
        double observationLikelihood;
        Matrix phi;
        int sequenceLength = s.size();
        gamma = new Matrix(sequenceLength, stateCount);
        phi = new Matrix(sequenceLength, stateCount);
        qs = new Vector(sequenceLength, 0);
        result = new ArrayList<>();
        /*Initialize*/
        emission = s.get(0);
        for (i = 0; i < stateCount; i++){
            observationLikelihood = states[i].getEmitProb(emission);
            gamma.setValue(0, i, safeLog(pi.getValue(i)) + safeLog(observationLikelihood));
        }
        /*Iterate Dynamic Programming*/
        for (t = 1; t < sequenceLength; t++){
            emission = s.get(t);
            for (j = 0; j < stateCount; j++){
                tempArray = logOfColumn(j);
                try {
                    tempArray.add(gamma.getRow(t - 1));
                } catch (VectorSizeMismatch ignored) {
                }
                maxIndex = tempArray.maxIndex();
                observationLikelihood = states[j].getEmitProb(emission);
                gamma.setValue(t, j, tempArray.getValue(maxIndex) + safeLog(observationLikelihood));
                phi.setValue(t, j, maxIndex);
            }
        }
        /*Backtrack pointers*/
        qs.setValue(sequenceLength - 1, gamma.getRow(sequenceLength - 1).maxIndex());
        result.add(0, states[(int) qs.getValue(sequenceLength - 1)].getState());
        for (i = sequenceLength - 2; i >= 0; i--){
            qs.setValue(i, phi.getValue(i + 1, (int) qs.getValue(i + 1)));
            result.add(0, states[(int) qs.getValue(i)].getState());
        }
        return result;
    }

}
