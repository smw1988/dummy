package leetcode.p10;

import java.util.List;
import java.util.LinkedList;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.Set;
import java.util.HashSet;

public class Solution {

    public static void main(String[] args) {
        Solution s = new Solution();
        boolean result = s.isMatch("a", ".*..a*");
        System.out.println(result);
    }

    public boolean isMatch(String s, String p) {
        if (p.isEmpty()) {
            return s.isEmpty();
        }
        
        State startingState = buildStateGraph(p);

        //collectIdAndPrintStateGraph(startingState);
        
        return match(startingState, s);
    }

    private void collectIdAndPrintStateGraph(State startState) {
        Set<State> stateSet = new HashSet<>();
        
        LinkedList<State> stack = new LinkedList<>();
        stack.add(startState);

        int id = 0;
        while (!stack.isEmpty()) {
            State s = stack.pop();
            if (!stateSet.contains(s)) {
                s.id = ++id;
                stateSet.add(s);

                for (Set<State> ss : s.nextStates.values()) {
                    for (State state : ss) {
                        stack.push(state);
                    }
                }
            }
        }

        for (State s : stateSet) {
            System.out.println("State: " + s.id + " " + s.acceptable);
            for (char c : s.nextStates.keySet()) {
                for (State state : s.nextStates.get(c)) {
                    System.out.println("    " + c + " -> " + state.id);
                }
            }
        }
    }

    // non-deterministic finite automaton
    private State buildStateGraph(String pattern) {
        State startState = new State();
        State lastState = startState;
        
        for (int i = 0; i < pattern.length(); ++i) {
            char c = pattern.charAt(i);
            if (i + 1 < pattern.length() && pattern.charAt(i + 1) == '*') {
                lastState = buildStatesFor0orMultipleChar(c, lastState);
                ++i;
            } else {
                State newState = new State();
                lastState.addNextState(c, newState);
                lastState = newState;
            }
        }
        
        lastState.acceptable = true;
        return startState;
    }
    
    private State buildStatesFor0orMultipleChar(char c, State start) {
        State state1 = new State();
        State state2 = new State();
        State state3 = new State();
        
        // E stands for empty char (empty step in the graph)
        start.addNextState('E', state1);
        start.addNextState('E', state3);
        state1.addNextState(c, state2);
        state2.addNextState('E', state3);
        state2.addNextState('E', state1);
        
        return state3;
    }
    
    private boolean match(State state, String input) {
        Set<State> stateSet = new HashSet<>();
        stateSet.add(state);
        stateSet = close(stateSet);
        
        for (int i = 0; i < input.length(); ++i) {
            char c = input.charAt(i);
            
            Set<State> transitionedStateSet = move(stateSet, c);
            if (transitionedStateSet.isEmpty()) {
                return false;
            }
            
            stateSet = close(transitionedStateSet);
        }
        
        for (State s : stateSet) {
            if (s.acceptable) {
                return true;
            }
        }
        
        return false;
    }
    
    private Set<State> move(Set<State> stateSet, char c) {
        Set<State> newStateSet = new HashSet<>();
        
        for (State s : stateSet) {
            Set<State> nextStates = s.tryMove(c);
            newStateSet.addAll(nextStates);
        }
        
        return newStateSet;
    }
    
    private Set<State> close(Set<State> stateSet) {
        Set<State> resultSet = new HashSet<>(stateSet);
        
        LinkedList<State> stack = new LinkedList<>(stateSet);
        while (!stack.isEmpty()) {
            State s = stack.pop();
            Set<State> s2eStateSet = s.tryMove('E');
            
            for (State s2eState : s2eStateSet) {
                if (!resultSet.contains(s2eState)) {
                    resultSet.add(s2eState);
                    stack.push(s2eState);
                }
            }
        }
        
        return resultSet;
    }
    
    static class State {
        int id;
        boolean acceptable = false;
        Map<Character, Set<State>> nextStates = new HashMap<>();
        
        void addNextState(char c, State s) {
            Set<State> stateSet = nextStates.get(c);
            if (stateSet == null) {
                stateSet = new HashSet<State>();
                stateSet.add(s);
                nextStates.put(c, stateSet);
            } else {
                stateSet.add(s);
            }
        }
        
        Set<State> tryMove(char c) {
            Set<State> states = new HashSet<>();
            if (nextStates.containsKey(c)) {
                states.addAll(nextStates.get(c));
            }
            if (c != 'E') {
                if (nextStates.containsKey('.')) {
                    states.addAll(nextStates.get('.'));
                }
            }
            return states;
        }
    }
}
