package mclaudio76.astar;

import java.util.List;

public abstract class State {

	public abstract State nextState(StateTransition t);
	public abstract List<StateTransition> potentialTransitions(); 
	protected double gCost, hCost 				   = 0.0;
	protected StateTransition transitionFromParent = null;
	private State parent        				   = null;
	
	public abstract boolean equals(Object o);
	public abstract int hashCode();
	
	public abstract boolean goalAchieved(State goalState);
	
	
	public State getOrigin() {
		return parent;
	}
	
	public void setOrigin(State parent) {
		this.parent = parent;
	}

	public StateTransition getTransitionFromOrigin() {
		return transitionFromParent;
	}
	
	public void setTransitionFromOrigin(StateTransition st) {
		this.transitionFromParent = st;
	}
	
	public double getGCost() {
		return gCost;
	}

	
	public double getHCost() {
		return hCost;
	}

	
	public void setGCost(double d) {
		this.gCost = d;		
	}

	
	public void setHCost(double d) {
		this.hCost = d;
	}

}
