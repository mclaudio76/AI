package mclaudio76.astar.eightpuzzle;



import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import mclaudio76.astar.State;
import mclaudio76.astar.StateTransition;

public class EightPuzzleBoard extends State {
	
	private Tile[] tiles;
	private int squareDim  		= 0;
	private String stringState  = "";
	private State parent        = null;
	private double gCost, hCost = 0.0;
	
	private StateTransition transitionFromParent = null;
	
	/*private EightPuzzleBoard(int dimension) {
		this.squareDim  = dimension;
		int  seed		= (int) (Math.random() * (squareDim * squareDim));
		int  step		= squareDim;
		int  maxVal 	= (squareDim * squareDim) - 1;
		tiles			= new Tile[squareDim * squareDim];
		int index		= 0;
		for(int y = 0;  y< squareDim; y++) {
			for(int x = 0; x < squareDim; x++) {
				int actual = (seed + step) <= maxVal ? (seed +step) : (seed +step) -maxVal;
				Tile t = new Tile(x, y,  contains(actual) ? 0 : actual);
				tiles[index++] = t;
				seed = actual;
			}
		}
	}*/
	
	public EightPuzzleBoard(EightPuzzleBoard t) {
		this.squareDim = t.squareDim;
		this.tiles = new Tile[t.tiles.length];
		int index  = 0;
		for(Tile tile : t.tiles) {
			Tile newCreate = new Tile(tile.getX(), tile.getY(),tile.getValue());
			tiles[index++] = newCreate;
		}
	}
	
	
	
	public EightPuzzleBoard(Tile ... tiles) {
		squareDim  = (int) Math.sqrt((double)tiles.length);
		this.tiles = new Tile[tiles.length];
		System.arraycopy(tiles, 0, this.tiles, 0, tiles.length);
	}
	
	
	
	
	public double manhattanDistance(EightPuzzleBoard table) {
		double dist = 0.0;
		for(Tile alfa : tiles) {
			if(!alfa.isEmpty()) {
				Tile beta = table.getTileByVal(alfa.getValue());
				int distance = Math.abs(alfa.getX() - beta.getX()) + Math.abs(alfa.getY() - beta.getY());
				dist += distance;
			}
		}
		return dist;
	}
	
	
	
	@Override
	public State apply(StateTransition t) {
		Move move = (Move) t;
		EightPuzzleBoard newState = new EightPuzzleBoard(this);
		// Swaps values
	    Tile from   = newState.getTile(move.getFrom());
	    Tile to     = newState.getTile(move.getTo());
	    int value   = from.getValue();
	    from.setValue(to.getValue());
	    to.setValue(value);
	    return newState;
	}


	public String toString() {
 		StringBuffer sb = new StringBuffer();
 		int index = 0;
 		for(Tile t : tiles) {
			sb.append(t);
			if(++index == squareDim) {
				sb.append('\n');
				index = 0;
			}
 		}
 		sb.append('\n');
 		return sb.toString();
	}

	
	

	private Tile getTileAt(int x, int y) {
		int index = Arrays.asList(tiles).indexOf(new Tile(x,y));
		return index >= 0 ? tiles[index] : null;
	}
	
	
	private int[] toIntArray() {
		int[] data = new int[squareDim * squareDim -1];
		int index  = 0;
		for(int y = 0;  y< squareDim; y++) {
			for(int x = 0; x < squareDim; x++) {
				Tile t = getTileAt(x, y);
				if(!t.isEmpty()) {
					data[index++]  = t.getValue();
				}
			}
		}
		return data;
	}
	
	private Tile getTileByVal(int value) {
		for(Tile t : tiles) {
			if(t.getValue() == value) {
				return t;
			}
		}
		return null;
	}
	
	private Tile getTile(Tile search) {
		return getTileAt(search.getX(), search.getY());
	}
	
	
	private boolean contains(int v) {
		for(Tile t : tiles) {
			if(t != null && t.getValue() == v) {
				return true;
			}
		}
		return false;
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
			potential.add(new Move(up,blankTile));
		}
		if(down != null) {
			potential.add(new Move(down,blankTile));
		}
		if(left != null) {
			potential.add(new Move(left,blankTile));
		}
		if(right != null) {
			potential.add(new Move(right,blankTile));
		}
		return potential;
	}

	@Override
	public State copy() {
		return new EightPuzzleBoard(this);
	}
	
	public int countInversionWithRespect(EightPuzzleBoard anotherState) {
		int a1[] = toIntArray();
		int inversion = 0;
		for(int x = 0; x < a1.length-1; x++) {
			for(int y = x+1; y < a1.length; y++) {
				int v1 = a1[x];
				int v2 = a1[y];
				if(v1 > v2) {
					inversion++;
				}
			}
		}
		return inversion;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((internalState() == null) ? 0 : internalState().hashCode());
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
		EightPuzzleBoard other = (EightPuzzleBoard) obj;
		if (internalState() == null) {
			if (other.internalState() != null)
				return false;
		} else if (!internalState().equals(other.internalState()))
			return false;
		return true;
	}

	public String internalState() {
		String s = "";
		for(int y = 0;  y< squareDim; y++) {
			for(int x = 0; x < squareDim; x++) {
				Tile t = getTileAt(x, y);
				s  += t.getValue();
			}
		}
		return s;
	}

	
	public static EightPuzzleBoard generateRandomInitialState(EightPuzzleBoard initialConfiguration, int r) {
		EightPuzzleBoard start = new EightPuzzleBoard(initialConfiguration);
		int indexes   = 0;
		while(indexes++ < r) {
			List<StateTransition> states = start.potentialTransitions();
			double rand = Math.random();
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
				start = (EightPuzzleBoard) start.apply(target);
			}
		}
		return start;
	}
	
	
	
	
	@Override
	public State getParent() {
		return parent;
	}

	@Override
	public void setParent(State parent) {
		this.parent = parent;
	}

	@Override
	public StateTransition getTransitionFromParent() {
		return transitionFromParent;
	}

	@Override
	public void setTransitionFromParent(StateTransition st) {
		this.transitionFromParent = st;
	}

	@Override
	public double getGCost() {
		return gCost;
	}

	@Override
	public double getHCost() {
		return hCost;
	}

	@Override
	public void setGCost(double d) {
		this.gCost = d;		
	}

	@Override
	public void setHCost(double d) {
		this.hCost = d;
	}

	@Override
	public String innerCode() {
		return this.internalState(); 
	}
	
}
