package mclaudio76.astar.mazesolver;

import java.util.List;

import mclaudio76.astar.AStarSolver;
import mclaudio76.astar.Heuristic;
import mclaudio76.astar.StateTransition;

public class MazeSolver {

	public static void main(String ... args) throws Exception {
		Maze maze = new Maze("D://maze.txt");
		Maze goal = new Maze(maze);
		Heuristic<Maze> h = (Maze a, Maze b) -> a.manhattanDistance(b);
		AStarSolver<Maze> solver = new AStarSolver<Maze>(maze, goal, h);
		List<StateTransition> solution = solver.solve();
		if(solution != null) {
			System.out.println("Maze solved");
		}
		//maze.print();
	}
}
