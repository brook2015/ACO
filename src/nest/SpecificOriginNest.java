package nest;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import colony.Ant;
import map.Network;
import map.PheromoneGraph;

public class SpecificOriginNest implements Nest{
	private final Network network;
	private int origin;
	
	public SpecificOriginNest(int origin,Network network){
		if(network==null){
			throw new NullPointerException("null network");
		}
		this.network=network;
		setOrigin(origin);
	}
	
	public void setOrigin(int origin){
		if(!network.contains(origin)){
			throw new IllegalArgumentException("invalid origin");
		}
		this.origin=origin;
	}
	
	@Override
	public Collection<Ant> produceAntColony(int amount){
		Collection<Ant> ants=new ArrayList<>(amount);
		List<Integer> vertexes=new ArrayList<>(network.vertexes());
		vertexes.removeIf(vertex->origin==vertex.intValue());
		int[] groups=group(vertexes.size(),amount,1);
		for(int i=0;i<amount;i++){
			Ant ant=new Ant(network);
			ant.initiate(groups[i]+1,origin,vertexes);
			ants.add(ant);
		}
		return ants;
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Nest nest=new SpecificOriginNest(3,PheromoneGraph.getGraphDemo1());
		nest.produceAntColony(2).forEach(System.out::println);
	}
}
