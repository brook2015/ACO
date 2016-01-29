package map;

import java.util.*;

public class Route {
	private int origin;
	private int currentCity;
	private PheromonedGraph graph;
	private Collection<PheromoneEdge> edges=new ArrayList<>();
	
	public Route(PheromonedGraph graph){
		this.graph=graph;
	}
	
	public void initiate(int origin){
		this.origin=origin;
		currentCity=origin;
		edges.clear();
	}
	
	public int origin(){
		return origin;
	}
	
	public int currentCity(){
		return currentCity;
	}
	
	public int cityCount(){
		return edges.size();
	}
	
	public void record(int nextCity){
		edges.add(graph.getEdge(currentCity,nextCity));
		currentCity=nextCity;
	}
	
	public void updatePheromone(){
		double length=edges.stream().mapToDouble(edge->edge.weight()).sum();
		for(PheromoneEdge edge:edges){
			edge.volatilize(0.2);
			edge.accumulate(1.0/length);
		}
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
