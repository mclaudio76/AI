package mclaudio76.astar.eightpuzzle;

import mclaudio76.astar.Move;

public class TileMove extends Move {
	private Tile from;
	private Tile to;
	
	public TileMove(Tile from, Tile to) {
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
