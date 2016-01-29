package nest;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;

import colony.Ant;
import map.Network;
import map.PheromoneGraph;

public class SingleOriginNest implements Nest{
	private static final Random random=new Random();
	private final Network network;
	
	public SingleOriginNest(Network network){
		if(network==null){
			throw new NullPointerException("null network");
		}
		this.network=network;
	}
	
	@Override
	public Collection<Ant> produceAntColony(int amount){
		Collection<Ant> ants=new ArrayList<>(amount);
		List<Integer> vertexes=new ArrayList<>(network.vertexes());
		int index=random.nextInt(vertexes.size());
		int origin=vertexes.get(index);
		vertexes.remove(index);
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
		Nest nest=new SingleOriginNest(PheromoneGraph.getGraphDemo1());
		nest.produceAntColony(2).forEach(System.out::println);
	}
}
