package Hmm;

import java.util.ArrayList;
import java.util.HashSet;

import static org.junit.Assert.*;

public class Hmm1Test {

    @org.junit.Test
    public void testViterbi() {
        int observationCount = 5;
        ArrayList<String>[] observations;
        ArrayList<Integer>[] emittedSymbols;
        HashSet<String> states = new HashSet<>();
        states.add("HOT");
        states.add("COLD");
        observations = new ArrayList[observationCount];
        emittedSymbols = new ArrayList[observationCount];
        for (int i = 0; i < observationCount; i++){
            observations[i] = new ArrayList<>();
            emittedSymbols[i] = new ArrayList<>();
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
        Hmm<String, Integer> hmm1 = new Hmm1<>(states, observations, emittedSymbols);
        ArrayList<Integer> observed = new ArrayList<>();
        observed.add(1);
        observed.add(1);
        observed.add(1);
        observed.add(1);
        observed.add(1);
        observed.add(1);
        ArrayList<String> observedStates = hmm1.viterbi(observed);
        assertEquals("COLD", observedStates.get(0));
        assertEquals("COLD", observedStates.get(1));
        assertEquals("COLD", observedStates.get(2));
        assertEquals("COLD", observedStates.get(3));
        assertEquals("COLD", observedStates.get(4));
        assertEquals("COLD", observedStates.get(5));
        observed = new ArrayList<>();
        observed.add(3);
        observed.add(3);
        observed.add(3);
        observed.add(3);
        observed.add(3);
        observed.add(3);
        observedStates = hmm1.viterbi(observed);
        assertEquals("HOT", observedStates.get(0));
        assertEquals("HOT", observedStates.get(1));
        assertEquals("HOT", observedStates.get(2));
        assertEquals("HOT", observedStates.get(3));
        assertEquals("HOT", observedStates.get(4));
        assertEquals("HOT", observedStates.get(5));
    }
}