package mclaudio76.astar;

import java.util.ArrayList;
import java.util.List;


public class Solution {
	 private List<StateTransition> tList = new ArrayList<>();
	 private State internalState	= null;
	 
	 
	 public Solution(State s) {
		 internalState = s.copy();
	 }
	 
	 public Solution(Solution s, StateTransition next) {
		 internalState = s.getState().apply(next);
		 tList.addAll(s.tList);
		 tList.add(next);
	}
	
	public State getState() {
		return internalState.copy();
	}
	
	 
	public List<StateTransition> transitionList() {
		return new ArrayList<>(tList);
	}
	
	
	public String toString() {
		StringBuffer sb = new StringBuffer();
		for(StateTransition s : transitionList()) {
			sb.append(s).append("\n");
		}
		return sb.toString();
	}

	@Override
	public int hashCode() {
		return this.internalState.hashCode();
	}



	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Solution other = (Solution) obj;
		return this.internalState.equals(other.internalState);
	}

	
	
}
