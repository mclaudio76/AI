package mclaudio76.astar;

import java.util.List;

public abstract class State {

	
	public abstract State nextState(StateTransition t);
	public abstract List<StateTransition> potentialTransitions(); 
	
	
	public abstract State getOrigin();
	public abstract void  setOrigin(State origin);
	
	public abstract StateTransition getTransitionFromOrigin();
	public abstract void setTransitionFromOrigin(StateTransition st);
	
	public abstract double getGCost();
	public abstract double getHCost();
	public abstract void setGCost(double d);
	public abstract void setHCost(double d);
	
	//public abstract String innerCode();
	
	
	public abstract boolean equals(Object o);
	public abstract int hashCode();
	public abstract State copy();
	
	
}
