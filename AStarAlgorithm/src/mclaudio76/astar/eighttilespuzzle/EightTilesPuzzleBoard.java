package mclaudio76.astar.eighttilespuzzle;

import java.util.ArrayList;
import java.util.List;
import java.util.function.IntConsumer;
import java.util.stream.IntStream;

import mclaudio76.astar.State;
import mclaudio76.astar.StateTransition;

public class EightTilesPuzzleBoard  extends State {
	private int[] innerData		= null;
	private String stringState  = "";
	private int   boardSize		= 0;
	
	public EightTilesPuzzleBoard(EightTilesPuzzleBoard t) {
		 this(t.innerData);
	}
	
	public EightTilesPuzzleBoard(int ... values) {
		this.innerData = new  int[values.length];
		this.boardSize = (int) Math.sqrt(values.length);
		stringState	   = "";
		System.arraycopy(values, 0, this.innerData, 0, innerData.length);
		calculateStringState();
	}
	
	private void calculateStringState() {
		stringState  = "";
		IntStream.of(innerData).forEach(new IntConsumer() {
			@Override
			public void accept(int value) {
				stringState += value;
			}
		});
	}
	
	public double manhattanDistance(EightTilesPuzzleBoard table) {
		double dist = 0.0;
		for(int index = 0; index < innerData.length; index++) {
			 Tile alfa = getTileByIndex(index);
			 Tile beta = table.getTileByVal(alfa.getValue());
			 if(!alfa.isEmpty()) {
				 int distance = Math.abs(alfa.getX() - beta.getX()) + Math.abs(alfa.getY() - beta.getY());
				 dist += distance; 
			 }
		}
		return dist;
	}
	
	@Override
	public State nextState(StateTransition t) {
		TileMove move = (TileMove) t;
		EightTilesPuzzleBoard newState = new EightTilesPuzzleBoard(this);
		// Swaps values
	    int valueFrom   = newState.getValueAt(move.getFrom());
	    int valueTo     = newState.getValueAt(move.getTo());
	    newState.setValueAt(move.getTo(),   valueFrom);
	    newState.setValueAt(move.getFrom(), valueTo);
	    newState.calculateStringState();
	    return newState;
	}

	public String toString() {
 		StringBuffer sb = new StringBuffer();
 		for(int column = 0; column < this.boardSize; column++) {
 			for(int row = 0; row < this.boardSize; row++) {
 				sb.append(getValueAt(row,column)+ " ");
 			}
 			sb.append('\n');
 		}
 		sb.append('\n');
 		return sb.toString();
	}

	private Tile getTileByIndex(int index) {
		int x = index % boardSize;
		int y = index / boardSize;
		int v = getValueAt(x,y);
		return new Tile(x,y,v);
	}
	
	private int getValueAt(int x , int y) {
		return innerData[x + y * boardSize];
	}
	
	private int getValueAt(Tile t) {
		return getValueAt(t.getX(), t.getY());
	}
	
	private void setValueAt(Tile t, int value) {
		setValueAt(t.getX(), t.getY(), value);
	}
	
	private void setValueAt(int x, int y, int value) {
		innerData[x + y * boardSize] = value;
	}
	
	private Tile getTileAt(int x, int y) {
		if (x < 0 || y < 0 || x >= boardSize || y >= boardSize) {
			return null;
		}
		return new Tile(x,y, getValueAt(x,y));
	}
	
	private Tile getTileByVal(int value) {
		int index = 0;
		for(int x : innerData) {
			if(x == value) {
				return new Tile(index % boardSize, index / boardSize);
			}
			++index;
		}
		return null;
	}

	@Override
	public List<StateTransition> potentialTransitions() {
		List<StateTransition> potential = new ArrayList<StateTransition>();
		Tile blankTile = getTileByVal(0);
		Tile up  	= getTileAt(blankTile.getX(),   blankTile.getY()-1);
		Tile down  	= getTileAt(blankTile.getX(),   blankTile.getY()+1);
		Tile left   = getTileAt(blankTile.getX()-1, blankTile.getY());
		Tile right  = getTileAt(blankTile.getX()+1, blankTile.getY());
		
		if(up != null) {
			potential.add(new TileMove(up,blankTile));
		}
		if(down != null) {
			potential.add(new TileMove(down,blankTile));
		}
		if(left != null) {
			potential.add(new TileMove(left,blankTile));
		}
		if(right != null) {
			potential.add(new TileMove(right,blankTile));
		}
		return potential;
	}
	
	public static EightTilesPuzzleBoard generateRandomInitialState(EightTilesPuzzleBoard initialConfiguration, int r) {
		EightTilesPuzzleBoard start = new EightTilesPuzzleBoard(initialConfiguration);
		int indexes   = 0;
		while(indexes++ < r) {
			List<StateTransition> states = start.potentialTransitions();
			double rand 				 = Math.random();
			StateTransition target = null;
			if(states.size() == 2) {
				target  =  rand < 0.5 ? states.get(0) : states.get(1);
			}
			if(states.size() == 3) {
				if(rand < 0.33) {
					target = states.get(0);
				}
				if(rand > 0.33 && rand < 0.66) {
					target = states.get(1);
				}
				if(rand > 0.66) {
					target = states.get(2);
				}
			}
			if(states.size() == 4) {
				if(rand < 0.25) {
					target = states.get(0);
				}
				if(rand > 0.25 && rand < 0.50) {
					target = states.get(1);
				}
				if(rand > 0.50 && rand < 0.75) {
					target = states.get(2);
				}
				if(rand > 0.75) {
					target = states.get(3);
				}
			}
			if(target != null) {
				start = (EightTilesPuzzleBoard) start.nextState(target);
			}
		}
		return start;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((stringState == null) ? 0 : stringState.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		EightTilesPuzzleBoard other = (EightTilesPuzzleBoard) obj;
		if (stringState == null) {
			if (other.stringState != null)
				return false;
		} else if (!stringState.equals(other.stringState))
			return false;
		return true;
	}

	@Override
	public boolean goalAchieved(State goalState) {
		return this.equals(goalState);
	}

}