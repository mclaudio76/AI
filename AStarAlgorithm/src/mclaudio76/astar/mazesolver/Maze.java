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
	
	private static final char START  = 'S';
	private static final char GOAL	 = 'G';
	private static final char WALL	 = '#';
	private static final char POS	 = '*';
	private static final char BLACK  = '\u25A0';
	
	private Point startPoint = null;
	private Point goalPoint  = null;
	private Point position   = null;
	private int   xDimension = 0;
	private int   yDimension = 0;
	
	public Maze(Maze another) {
		this.startPoint  = new Point(another.startPoint);
		this.goalPoint 	 = new Point(another.goalPoint);
		this.position	 = new Point(another.position);
		this.xDimension	 = another.xDimension;
		this.yDimension  = another.yDimension;
		this.board 		 = another.board;//new char[xDimension][yDimension];
	}
	
	public Maze(String fileName) throws Exception {
		String line			  = null;
		ArrayList<String> lines = new ArrayList<>();
		try (BufferedReader reader = new BufferedReader(new FileReader(new File(fileName)))) {;
			do {
				line = reader.readLine();
				if(line != null) {
					xDimension = line.toCharArray().length;
					lines.add(line);
				}
			}while(line != null);
			yDimension = lines.size();
			board = new char[xDimension][yDimension];
			int index			  = 0;
			for(String s: lines) {
				char array[] = s.toCharArray();
				for(int x = 0; x < xDimension; x++) {
					board[x][index] = array[x];
				}
				index++;
			}
			for(int x = 0; x < xDimension; x++) {
				for(int y = 0; y < yDimension; y++) {
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
	
	public void cleanMoves() {
		for(int x = 0; x < xDimension; x++) {
			for(int y = 0; y < yDimension; y++) {
				this.board[x][y] = this.board[x][y] == POS ? ' ' : this.board[x][y];
			}
		}
	}
	
	public void print() {
		for(int y = 0; y < yDimension; y++) {
			for(int x = 0; x < xDimension; x++) {
				char val = board[x][y];
				System.out.print(val == WALL ? BLACK : val);
			}
			System.out.println();
		}
	}
	
	
	@Override
	public State nextState(StateTransition t) {
		Maze newMaze	 = new Maze(this);
		Move move	 	 = (Move) t;
		newMaze.position = new Point(move.getTo());
		char output = board[move.getTo().getX()][move.getTo().getY()];
		newMaze.board[move.getTo().getX()][move.getTo().getY()] = output == GOAL || output == START ? output : POS;
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
