package Hmm;

import java.io.*;
import java.util.ArrayList;
import java.util.Set;

import Math.*;

public class Hmm2<State, Symbol> extends Hmm<State,Symbol> implements Serializable {
    private Matrix pi;

    /**
     * A constructor of {@link Hmm2} class which takes a {@link Set} of states, an array of observations (which also
     * consists of an array of states) and an array of instances (which also consists of an array of emitted symbols).
     * The constructor calls its super method to calculate the emission probabilities for those states.
     *
     * @param states A {@link Set} of states, consisting of all possible states for this problem.
     * @param observations An array of instances, where each instance consists of an array of states.
     * @param emittedSymbols An array of instances, where each instance consists of an array of symbols.
     */
    public Hmm2(Set<State> states, ArrayList<State>[] observations, ArrayList<Symbol>[] emittedSymbols) {
        super(states, observations, emittedSymbols);
    }

    /**
     * calculatePi calculates the prior probability matrix (initial probabilities for each state combinations)
     * from a set of observations. For each observation, the function extracts the first and second states in
     * that observation.  Normalizing the counts of the pair of states returns us the prior probabilities for each
     * pair of states.
     *
     * @param observations A set of observations used to calculate the prior probabilities.
     */
    protected void calculatePi(ArrayList<State>[] observations){
        pi = new Matrix(stateCount, stateCount);
        for (ArrayList<State> observation : observations) {
            int first = stateIndexes.get(observation.get(0));
            int second = stateIndexes.get(observation.get(1));
            pi.increment(first, second);
        }
        pi.columnWiseNormalize();
    }

    /**
     * calculateTransitionProbabilities calculates the transition probabilities matrix from each state to another state.
     * For each observation and for each transition in each observation, the function gets the states. Normalizing the
     * counts of the three of states returns us the transition probabilities.
     *
     * @param observations A set of observations used to calculate the transition probabilities.
     */
    protected void calculateTransitionProbabilities(ArrayList<State>[] observations){
        transitionProbabilities = new Matrix(stateCount * stateCount, stateCount);
        for (ArrayList<State> current : observations) {
            for (int j = 0; j < current.size() - 2; j++) {
                int from1 = stateIndexes.get(current.get(j));
                int from2 = stateIndexes.get(current.get(j + 1));
                int to = stateIndexes.get(current.get(j + 2));
                transitionProbabilities.increment(from1 * stateCount + from2, to);
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
            result.add(safeLog(transitionProbabilities.getValue(i * stateCount + column / stateCount, column % stateCount)));
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
        Symbol emission, emission1, emission2;
        ArrayList<State> result;
        Matrix gamma;
        Vector current, qs, previous;
        double observationLikelihood;
        Matrix phi;
        int sequenceLength = s.size();
        gamma = new Matrix(sequenceLength, stateCount * stateCount);
        phi = new Matrix(sequenceLength, stateCount * stateCount);
        qs = new Vector(sequenceLength, 0);
        result = new ArrayList<State>();
        /*Initialize*/
        emission1 = s.get(0);
        emission2 = s.get(1);
        for (i = 0; i < stateCount; i++){
            for (j = 0; j < stateCount; j++){
                observationLikelihood = states[i].getEmitProb(emission1) * states[j].getEmitProb(emission2);
                gamma.setValue(1, i * stateCount + j, safeLog(pi.getValue(i, j)) + safeLog(observationLikelihood));
            }
        }
        /*Iterate Dynamic Programming*/
        for (t = 2; t < sequenceLength; t++){
            emission = s.get(t);
            for (j = 0; j < stateCount * stateCount; j++){
                current = logOfColumn(j);
                try {
                    previous = gamma.getRow(t - 1).skipVector(stateCount, j / stateCount);
                    current.add(previous);
                } catch (VectorSizeMismatch vectorSizeMismatch) {
                }
                maxIndex = current.maxIndex();
                observationLikelihood = states[j % stateCount].getEmitProb(emission);
                gamma.setValue(t, j, current.getValue(maxIndex) + safeLog(observationLikelihood));
                phi.setValue(t, j, maxIndex * stateCount + j / stateCount);
            }
        }
        /*Backtrack pointers*/
        qs.setValue(sequenceLength - 1, gamma.getRow(sequenceLength - 1).maxIndex());
        result.add(0, states[((int) qs.getValue(sequenceLength - 1)) % stateCount].getState());
        for (i = sequenceLength - 2; i >= 1; i--){
            qs.setValue(i, phi.getValue(i + 1, (int) qs.getValue(i + 1)));
            result.add(0, states[((int) qs.getValue(i)) % stateCount].getState());
        }
        result.add(0, states[((int) qs.getValue(1)) / stateCount].getState());
        return result;
    }


}
