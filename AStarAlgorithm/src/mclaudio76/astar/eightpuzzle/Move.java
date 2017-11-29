package mclaudio76.astar.eightpuzzle;

import mclaudio76.astar.StateTransition;

public class Move extends StateTransition {
	private Tile from;
	private Tile to;
	
	public Move(Tile from, Tile to) {
		this.from = from;
		this.to   = to;
	}
	
	public String toString() {
		return "Move tile  "+from+ " to tile "+to;
	}

	public Tile getFrom() {
		return from;
	}

	public Tile getTo() {
		return to;
	}

	@Override
	public double getCost() {
		return 1.0;
	}
 	
	
	
}
