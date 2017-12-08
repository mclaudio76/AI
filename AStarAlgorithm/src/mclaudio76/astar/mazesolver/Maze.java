package mclaudio76.astar.mazesolver;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import mclaudio76.astar.State;
import mclaudio76.astar.StateTransition;


public class Maze extends State{

	private char[][] board;
	
	private char START 	 = 'S';
	private char GOAL	 = 'G';
	private char WALL	 = '#';
	private char BLACK   = '\u25A0';
	
	private Point startPoint = null;
	private Point goalPoint  = null;
	private Point position   = null;
	
	public Maze(Maze another) {
		this.startPoint  = new Point(another.startPoint);
		this.goalPoint 	 = new Point(another.goalPoint);
		this.position	 = new Point(another.position);
		this.board		 = new char[another.board.length][];
		int index		 = 0;
		for(char[] c : another.board) {
			this.board[index] = new char[c.length];
			System.arraycopy(c, 0, this.board[index],0,c.length);
			++index;
		}
	}
	
	public Maze(String fileName) throws Exception {
		String line			  = null;
		ArrayList<String> lines = new ArrayList<>();
		try (BufferedReader reader = new BufferedReader(new FileReader(new File(fileName)))) {;
			do {
				line = reader.readLine();
				if(line != null) {
					lines.add(line);
				}
			}while(line != null);
			board = new char[lines.size()][];
			int index			  = 0;
			for(String s: lines) {
				board[index++] = s.toCharArray();
			}
			for(int x = 0; x < board.length; x++) {
				for(int y = 0; y < board[x].length; y++) {
					if(board[x][y] == START) {
						this.startPoint = new Point(x,y);
					}
					if(board[x][y] == GOAL) {
						this.goalPoint = new Point(x,y);
					}
				}
			}
			position = new Point(startPoint);
		}
	}
	
	
	public double manhattanDistance(Maze table) {
		int distance = Math.abs(goalPoint.getX() - position.getX()) + Math.abs(goalPoint.getY() - position.getY());
		double dist = distance;
		return dist;
	}
	
	
	public void print() {
		for(char[] line : board) {
			for(char c : line) {
				System.out.print(c == WALL ? BLACK : c);
			}
			System.out.println();
		}
	}
	
	
	@Override
	public State nextState(StateTransition t) {
		Maze newMaze	 = new Maze(this);
		Move move	 	 = (Move) t;
		newMaze.position = new Point(move.getTo());
		System.out.println(newMaze.position);
		return newMaze;
	}

	@Override
	public List<StateTransition> potentialTransitions() {
		ArrayList<StateTransition> moves = new ArrayList<>();
		addTransitionIfLegal(moves, position.getX(),   position.getY()+1);
		addTransitionIfLegal(moves, position.getX(),   position.getY()-1);
		addTransitionIfLegal(moves, position.getX()+1, position.getY());
		addTransitionIfLegal(moves, position.getX()-1, position.getY());
		return moves;
	}

	private void addTransitionIfLegal(List<StateTransition> lst, int x, int y) {
		try {
			char c = board[x][y];
			if(c != WALL) {
				lst.add(new Move(new Point(x,y)));
			}
		}
		catch(Exception e) {

		}
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((position == null) ? 0 : position.hashCode());
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
		Maze other = (Maze) obj;
		if (position == null) {
			if (other.position != null)
				return false;
		} else if (!position.equals(other.position))
			return false;
		return true;
	}

	@Override
	public boolean goalAchieved(State goalState) {
		return position.equals(goalPoint);
	}
	

}
