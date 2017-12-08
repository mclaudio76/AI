package mclaudio76.astar.eighttilespuzzle;

import java.util.List;

import mclaudio76.astar.AStarSolver;
import mclaudio76.astar.Heuristic;
import mclaudio76.astar.StateTransition;

public class EightTilesPuzzleSolver {
	public static void main(String[] args) {
		/*Heuristic<EightPuzzleBoard> h = (EightPuzzleBoard a, EightPuzzleBoard b) -> a.manhattanDistance(b);
		EightPuzzleBoard goal  = new EightPuzzleBoard(new Tile(0,0,0), new Tile(1,0,1), new Tile(2,0,2), 
				new Tile(0,1,3), new Tile(1,1,4), new Tile(2,1,5),
				new Tile(0,2,6), new Tile(1,2,7), new Tile(2,2,8));
		for(int x = 0; x < 10; x++) {
			EightPuzzleBoard start		   = EightPuzzleBoard.generateRandomInitialState(goal,100);
			AStarSolver<EightPuzzleBoard> solver 			   = new AStarSolver<EightPuzzleBoard>(start, goal, h);
			long startTime			   = System.currentTimeMillis();
			List<StateTransition> solution = solver.solve();
			long endTime			   = System.currentTimeMillis();
			System.out.println("Solution found in "+solution.size()+" steps in "+(endTime-startTime)+" msec ");
		} */
		
		EightTilesPuzzleBoard goal  = new EightTilesPuzzleBoard(1,2,3,4,5,6,7,8,0);
		Heuristic<EightTilesPuzzleBoard> h = (EightTilesPuzzleBoard a, EightTilesPuzzleBoard b) -> a.manhattanDistance(b);
		
		for(int x = 0; x < 10; x++) {
			EightTilesPuzzleBoard start = EightTilesPuzzleBoard.generateRandomInitialState(goal, 100);
			AStarSolver<EightTilesPuzzleBoard> solver 			   = new AStarSolver<EightTilesPuzzleBoard>(start, goal, h);
			long startTime			   = System.currentTimeMillis();
			List<StateTransition> solution = solver.solve();
			long endTime			   = System.currentTimeMillis();
			System.out.println("Solution found in "+solution.size()+" steps in "+(endTime-startTime)+" msec ");
		}
		
	}
}
