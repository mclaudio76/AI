package mclaudio76.astar;

import java.util.List;

public abstract class State {

	
	public abstract State apply(StateTransition t);
	public abstract List<StateTransition> potentialTransitions(); 
	
	
	public abstract State getParent();
	public abstract void  setParent(State parent);
	
	public abstract StateTransition getTransitionFromParent();
	public abstract void setTransitionFromParent(StateTransition st);
	
	public abstract double getGCost();
	public abstract double getHCost();
	public abstract void setGCost(double d);
	public abstract void setHCost(double d);
	
	public abstract String innerCode();
	
	
	public abstract boolean equals(Object o);
	public abstract int hashCode();
	public abstract State copy();
	
	
}
