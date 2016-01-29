package nest;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;

import colony.Ant;
import map.Network;
import map.PheromoneGraph;

public class UniqueOriginNest implements Nest{
	private static final Random random=new Random();
	private final Network network;
	
	public UniqueOriginNest(Network network){
		if(network==null){
			throw new NullPointerException("null network");
		}
		this.network=network;
	}
	
	@Override
	public Collection<Ant> produceAntColony(int amount) {
		// TODO Auto-generated method stub
		Collection<Ant> ants=new ArrayList<>(amount);
		List<Integer> vertexes=new ArrayList<>(network.vertexes());
		int[] origins=getUniqueOrigin(vertexes,amount);
		int[] groups=group(vertexes.size(),amount,1);
		for(int i=0;i<amount;i++){
			Ant ant=new Ant(network);
			ant.initiate(groups[i]+1,origins[i],vertexes);
			ants.add(ant);
		}
		return ants;
	}
	
	private static int[] getUniqueOrigin(List<Integer> origins,int amount){
		if(amount>origins.size()){
			throw new IllegalArgumentException("invalid arguments: origins, amount");
		}
		int[] output=new int[amount];
		for(int i=0;i<amount;i++){
			int index=random.nextInt(origins.size());
			output[i]=origins.get(index);
			origins.remove(index);
		}
		return output;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Nest nest=new UniqueOriginNest(PheromoneGraph.getGraphDemo1());
		nest.produceAntColony(2).forEach(System.out::println);
	}
}
