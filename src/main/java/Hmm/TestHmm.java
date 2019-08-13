package Hmm;

import java.util.ArrayList;
import java.util.HashSet;

public class TestHmm {

    public static void hmm1(){
        int observationCount = 5;
        ArrayList<String>[] observations;
        ArrayList<Integer>[] emittedSymbols;
        HashSet<String> states = new HashSet<String>();
        ArrayList<Integer> observed = new ArrayList<Integer>();
        observed.add(1);
        observed.add(1);
        observed.add(1);
        observed.add(1);
        observed.add(1);
        observed.add(1);
        states.add("HOT");
        states.add("COLD");
        observations = new ArrayList[observationCount];
        emittedSymbols = new ArrayList[observationCount];
        for (int i = 0; i < observationCount; i++){
            observations[i] = new ArrayList<String>();
            emittedSymbols[i] = new ArrayList<Integer>();
        }
        observations[0].add("HOT");
        observations[0].add("HOT");
        observations[0].add("HOT");
        emittedSymbols[0].add(3);
        emittedSymbols[0].add(2);
        emittedSymbols[0].add(3);
        observations[1].add("HOT");
        observations[1].add("COLD");
        observations[1].add("COLD");
        observations[1].add("COLD");
        emittedSymbols[1].add(2);
        emittedSymbols[1].add(2);
        emittedSymbols[1].add(1);
        emittedSymbols[1].add(1);
        observations[2].add("HOT");
        observations[2].add("COLD");
        observations[2].add("HOT");
        observations[2].add("COLD");
        emittedSymbols[2].add(3);
        emittedSymbols[2].add(1);
        emittedSymbols[2].add(2);
        emittedSymbols[2].add(1);
        observations[3].add("COLD");
        observations[3].add("COLD");
        observations[3].add("COLD");
        observations[3].add("HOT");
        observations[3].add("HOT");
        emittedSymbols[3].add(3);
        emittedSymbols[3].add(1);
        emittedSymbols[3].add(2);
        emittedSymbols[3].add(2);
        emittedSymbols[3].add(3);
        observations[4].add("COLD");
        observations[4].add("HOT");
        observations[4].add("HOT");
        observations[4].add("COLD");
        observations[4].add("COLD");
        emittedSymbols[4].add(1);
        emittedSymbols[4].add(2);
        emittedSymbols[4].add(3);
        emittedSymbols[4].add(2);
        emittedSymbols[4].add(1);
        Hmm<String, Integer> hmm = new Hmm2<String, Integer>(states, observations, emittedSymbols);
        ArrayList<String> observedStates = hmm.viterbi(observed);
    }

    public static void main(String[] args){
        hmm1();
    }
}
