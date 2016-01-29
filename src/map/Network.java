package map;

import java.util.List;
import java.util.Set;

public interface Network {
	int size();
	boolean contains(int id);
	PheromoneEdge getEdge(int from,int to);
	List<PheromoneEdge> getEdges(int from);
	Set<Integer> vertexes();
	void volatilize();
}
