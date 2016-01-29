package colony;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import map.Network;
import map.PheromoneEdge;

public class Ant {
	private int id;
	private int count;
	private int current;
	private double length;
	private final Network network;
	private final List<PheromoneEdge> route;
	private static int index=0;
	private Collection<Integer> permission;
	private static final Comparator<PheromoneEdge> comparator=new EdgeComparator();
	
	public Ant(Network network){
		if(network==null){
			throw new NullPointerException("null network");
		}
		id=index++;
		length=0.0;
		this.network=network;
		route=new ArrayList<>();
	}
	
	public void initiate(int count,int origin,Collection<Integer> permission){
		if(permission==null){
			throw new NullPointerException("null permission");
		}
		if(count<2||count>network.size()){
			throw new IllegalArgumentException("invalid count");
		}
		if(!network.contains(origin)){
			throw new IllegalArgumentException("invalid origin");
		}
		this.count=count;
		this.permission=permission;
		current=origin;
	}
	
	public boolean stop(){
		return count-1==route.size();
	}
	
	public void move(){
		PheromoneEdge edge=search();
		current=edge.to();
		length+=edge.weight();
		route.add(edge);
		permission.remove(current);
	}
	
	private PheromoneEdge search(){
		Map<PheromoneEdge,Double> utilities=new HashMap<>(permission.size());
		network.getEdges(current).stream()
				.filter(edge->permission.contains(edge.to()))
				.sorted(comparator)
				.limit(2)
				.forEach(edge->utilities.put(edge,edge.utility()));
		double sum=utilities.values().stream().mapToDouble(p->p).sum();
		double random=Math.random();
		double accumulation=0.0;
		PheromoneEdge output=null;
		for(Map.Entry<PheromoneEdge,Double> entry:utilities.entrySet()){
			accumulation+=entry.getValue();
			if(accumulation/sum>random){
				output=entry.getKey();
				break;
			}
		}
		return output;
	}
	
	private static class EdgeComparator implements Comparator<PheromoneEdge>{

		@Override
		public int compare(PheromoneEdge o1, PheromoneEdge o2) {
			// TODO Auto-generated method stub
			double delta=o1.weight()-o2.weight();
			return delta>0?1:(delta<0?-1:0);
		}
		
	}
	
	@Override public String toString(){
		String output=String.format("### ant%d state ###\n",id);
		output+=String.format("travel count: %d\n",count);
		output+=String.format("current: %d\n",current);
		output+=String.format("permit cities: %s\n",
				permission.stream().map(a->a.toString()).collect(Collectors.joining("/")));
		output+=String.format("route: %s\n",getRoute());
		output+=String.format("length: %.2f\n",length);
		output+="###### end ######";
		return output;
	}
	
	private String getRoute(){
		StringBuffer buffer=new StringBuffer();
		boolean isFirstElement=true;
		for(PheromoneEdge edge:route){
			if(isFirstElement){
				buffer.append(edge.from());
				isFirstElement=false;
			}
			buffer.append("->").append(edge.to());
		}
		return buffer.toString();
	}
	
	public void updatePheromone(){
		route.forEach(edge->edge.accumulate(1.0/length));
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
	}
}
