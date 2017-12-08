package mclaudio76.astar;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class AStarSolver<S extends State> {
	
	private S initialState 					= null;
	private S goal		   					= null;
	private S currentSolution 		    	= null;
	private boolean done 			 		= false;
	private Heuristic<S> heuristic 			= null;
	
	private List<S>  closedList		 		= new ArrayList<>();
	private List<S>  openList		 		= new ArrayList<>(); 
	
	public AStarSolver(S initialState, S goal, Heuristic<S> heuristic) {
		this.initialState = (S) initialState;
		this.goal		  = (S) goal;
		this.heuristic	  = heuristic;
	}
	
	public List<StateTransition> solve() {
		List<StateTransition> steps = new ArrayList<>();
		State solution = innerSolve();
		if(solution == null) {
			return null;
		}
		// Find root
		State current =  solution;
		while(current != null) {
			steps.add(current.getTransitionFromOrigin());
			current = current.getOrigin();
		}
		Collections.reverse(steps);
		return steps;
	}
	
	private State innerSolve() {
		openList.add(initialState);
		currentSolution = initialState;
		while(!done) {
			currentSolution = findBestSolution();
			if(currentSolution == null) {
				return null;
			}
			if(currentSolution.goalAchieved(goal)) {
				return currentSolution;
			}
			else {
				openList.remove(currentSolution);
				closedList.add(currentSolution);
				for(StateTransition nextStep : currentSolution.potentialTransitions()) {
					S newState = (S) currentSolution.nextState(nextStep);
					double cost = currentSolution.getGCost() + nextStep.getCost();
					if(openList.contains(newState)) {
						double gCost = openList.get(openList.indexOf(newState)).getGCost();
						if(cost < gCost) {
							openList.remove(newState);
						}
					}
					if(closedList.contains(newState)) {
						double gCost = closedList.get(closedList.indexOf(newState)).getGCost();
						if(cost < gCost) {
							closedList.remove(newState);
						}
					}
					if(!openList.contains(newState) && !closedList.contains(newState)) {
						newState.setOrigin(currentSolution);
						newState.setTransitionFromOrigin(nextStep);
						newState.setGCost(cost);
						newState.setHCost(heuristic.evaluate(newState, goal));
						openList.add(newState);
					}
				}
			}
		}
		return null;
	}
	
	
	
	private S findBestSolution() {
		S best 	         = null;
		Collections.sort(openList, new Comparator<S>() {
			@Override
			public int compare(S o1, S o2) {
				Double c1 = o1.getGCost() + o1.getHCost();
				Double c2 = o2.getGCost() + o2.getHCost();
				return c1.compareTo(c2);
			}
		});
		if(openList.isEmpty()) {
			return null;
		}
		best = openList.get(0);
		openList.remove(0);
		return best;
	}

	
	
	
	
	
}
