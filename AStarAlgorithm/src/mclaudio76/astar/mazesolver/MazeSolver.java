package mclaudio76.astar.mazesolver;

import java.util.List;

import mclaudio76.astar.AStarSolver;
import mclaudio76.astar.Heuristic;
import mclaudio76.astar.StateTransition;

public class MazeSolver {

	public static void main(String ... args) throws Exception {
		Maze maze = new Maze("D://maze2.txt");
		Heuristic<Maze> h = (Maze a, Maze b) -> a.manhattanDistance(b);
		AStarSolver<Maze> solver = new AStarSolver<Maze>(maze, new Maze(maze), h);
		List<StateTransition> solution = solver.solve();
		maze.cleanMoves();
		if(solution != null) {
			 for(StateTransition s : solution) {
				 maze = (Maze) maze.nextState(s);
			 }
		}
		maze.print();
	}
}
