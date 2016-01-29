package colony;

import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import map.Network;
import map.PheromoneEdge;
import map.PheromoneMap;

public class Ant {
	private int id;
	private static int index=0;
	private int count;
	private int current;
	private Network network;
	private Collection<Integer> permission;
	private final Set<PheromoneEdge> route;
	
	public Ant(Network network){
		if(network==null){
			throw new NullPointerException("null network");
		}
		id=index++;
		this.network=network;
		route=new LinkedHashSet<>();
	}
	
	public Ant initiate(int count,int origin,Collection<Integer> permission){
		if(count<2||count>network.size()){
			throw new IllegalArgumentException("invalid count");
		}
		if(!network.contains(origin)){
			throw new IllegalArgumentException("invalid origin");
		}
		this.count=count;
		this.permission=permission;
		current=origin;
		return this;
	}
	
	public boolean stop(){
		return count==route.size();
	}
	
	public void move(){
		PheromoneEdge edge=search();
		current=edge.to();
		permission.remove(current);
		route.add(edge);
	}
	
	private PheromoneEdge search(){
		Map<PheromoneEdge,Double> probabilities=new HashMap<>(permission.size());
		List<PheromoneEdge> edges=network.getEdges(current).stream()
				.filter(edge->permission.contains(edge.to()))
				.collect(Collectors.toList());
		edges.forEach(edge->probabilities.put(edge,edge.utility()));
		double sum=probabilities.values().stream().mapToDouble(p->p).sum();
		double random=Math.random();
		double accumulation=0.0;
		PheromoneEdge target=null;
		for(Map.Entry<PheromoneEdge,Double> entry:probabilities.entrySet()){
			accumulation+=entry.getValue();
			if(accumulation/sum>random){
				target=entry.getKey();
				break;
			}
		}
		return target;
	}
	
	@Override public String toString(){
		String output=String.format("### ant%d state ###\n",id);
		output+=String.format("travel count: %d\n",count);
		output+=String.format("current: %d\n",current);
		output+=String.format("permit cities: %s\n",
				permission.stream().map(a->a.toString()).collect(Collectors.joining("/")));
		output+=String.format("route: %s\n",
				route.stream().map(r->r.toString()).collect(Collectors.joining("/")));
		output+="###### end ######";
		return output;
	}
	
	public void updatePheromone(){
		double length=route.stream().mapToDouble(edge->edge.weight()).sum();
		route.forEach(edge->edge.accumulate(delta(length)));
	}
	
	private double delta(double input){
		return 1.0/input;
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Network network=PheromoneMap.getMapDemo1();
		Ant ant=new Ant(network).initiate(1,1,null);
		System.out.println(ant);
		while(!ant.stop()){
			ant.move();
			System.out.println(ant);
		}
		ant.updatePheromone();
		System.out.println(network);
	}
}
