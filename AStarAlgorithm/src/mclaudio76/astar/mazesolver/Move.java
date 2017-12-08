package mclaudio76.astar.mazesolver;

import mclaudio76.astar.StateTransition;

public class Move extends StateTransition{
	private Point to;
	
	public Move(Point to) {
		this.to   = to;
	}
	
	public String toString() {
		return "Move to "+to;
	}
	
	public Point getTo() {
		return to;
	}
	
	@Override
	public double getCost() {
		return +1;
	}

}
