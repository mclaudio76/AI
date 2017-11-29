package mclaudio76.astar;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import mclaudio76.astar.eightpuzzle.PuzzleTable;
import mclaudio76.astar.eightpuzzle.Tile;

public class Solver<S extends State> {
	
	private State initialState 					= null;
	private State goal		   					= null;
	private State currentSolution 		    	= null;
	private boolean done 			 			= false;
	private Heuristic<State> h		 			= null;
	
	private List<State>  closedList		 = new ArrayList<>();
	private List<State>  openList		 = new ArrayList<>(); 
	
	public Solver(State initialState, State goal, Heuristic<State> h) {
		this.initialState = initialState.copy();
		this.goal		  = goal.copy();
		this.h		 	  = h;
	}
	
	public List<StateTransition> solve() {
		List<StateTransition> steps = new ArrayList<>();
		State solution = innerSolve();
		if(solution == null) {
			return null;
		}
		// Find root
		State current =  solution;
		while(current != null) {
			steps.add(current.getTransitionFromParent());
			current = current.getParent();
		}
		Collections.reverse(steps);
		return steps;
	}
	
	private State innerSolve() {
		openList.add(initialState);
		currentSolution = initialState;
		while(!done) {
			currentSolution = findBestSolution();
			if(currentSolution == null) {
				return null;
			}
			if(currentSolution.equals(goal)) {
				return currentSolution;
			}
			else {
				openList.remove(currentSolution);
				closedList.add(currentSolution);
				for(StateTransition nextStep : currentSolution.potentialTransitions()) {
					State newState = currentSolution.apply(nextStep);
					double cost = currentSolution.getGCost() + nextStep.getCost();
					if(openList.contains(newState)) {
						double gCost = openList.get(openList.indexOf(newState)).getGCost();
						if(cost < gCost) {
							openList.remove(newState);
						}
					}
					if(closedList.contains(newState)) {
						double gCost = closedList.get(closedList.indexOf(newState)).getGCost();
						if(cost < gCost) {
							closedList.remove(newState);
						}
					}
					if(!openList.contains(newState) && !closedList.contains(newState)) {
						newState.setParent(currentSolution);
						newState.setTransitionFromParent(nextStep);
						newState.setGCost(cost);
						newState.setHCost(h.evaluate(newState, goal));
						openList.add(newState);
					}
				}
			}
		}
		return null;
	}
	
	
	
	private State findBestSolution() {
		State best 	         = null;
		Collections.sort(openList, new Comparator<State>() {
			@Override
			public int compare(State o1, State o2) {
				Double c1 = o1.getGCost() + o1.getHCost();
				Double c2 = o2.getGCost() + o2.getHCost();
				return c1.compareTo(c2);
			}
		});
		if(openList.isEmpty()) {
			return null;
		}
		best = openList.get(0);
		openList.remove(0);
		return best;
	}

	
	
	
	
	public static void main(String[] args) {
		/*PuzzleTable start = new PuzzleTable(new Tile(0,0,7), new Tile(1,0,2), new Tile(2,0,4), 
				new Tile(0,1,5), new Tile(1,1,0), new Tile(2,1,6),
				new Tile(0,2,8), new Tile(1,2,3), new Tile(2,2,1));*/
		
		Heuristic<PuzzleTable> h = (PuzzleTable a, PuzzleTable b) -> a.manhattanDistance(b);
		FeasibilityChecker<PuzzleTable> f = (PuzzleTable a, PuzzleTable b) -> a.countInversionWithRespect(b) % 2 == 0;
		PuzzleTable goal  = new PuzzleTable(new Tile(0,0,0), new Tile(1,0,1), new Tile(2,0,2), 
				new Tile(0,1,3), new Tile(1,1,4), new Tile(2,1,5),
				new Tile(0,2,6), new Tile(1,2,7), new Tile(2,2,8));

		
		for(int x = 0; x < 10; x++) {
			PuzzleTable start		   = PuzzleTable.generateRandom(goal,100);
			Solver solver 			   = new Solver(start, goal, h);
			long startTime			   = System.currentTimeMillis();
			List<StateTransition> solution = solver.solve();
			long endTime			   = System.currentTimeMillis();
			System.out.println("Solution found in "+solution.size()+" steps in "+(endTime-startTime)+" msec ");
		
		}
		
		
		
	}
}
