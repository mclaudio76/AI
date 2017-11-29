package mclaudio76.astar;

@FunctionalInterface
public interface Heuristic<T extends State> {
	 double evaluate(T currentState, T goal);
}
