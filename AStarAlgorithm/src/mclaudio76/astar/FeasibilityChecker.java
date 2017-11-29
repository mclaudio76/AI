package mclaudio76.astar;

@FunctionalInterface
public interface FeasibilityChecker<T extends State> {
	public boolean isFeasible(T initialState, T goal);
}
