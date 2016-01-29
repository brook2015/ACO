package nest;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;

import colony.Ant;
import map.Network;
import map.PheromoneGraph;

public class RandomOriginNest implements Nest{
	private static final Random random=new Random();
	private final Network network;
	
	public RandomOriginNest(Network network){
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
		List<Integer> origins=getRandomOrigin(vertexes,amount);
		vertexes.removeAll(origins);
		int[] groups=group(vertexes.size(),amount,1);
		for(int i=0;i<amount;i++){
			ants.add(new Ant(network).initiate(groups[i]+1,origins.get(i).intValue(),vertexes));
		}
		return ants;
	}
	
	private static List<Integer> getRandomOrigin(List<Integer> origins,int amount){
		if(amount>origins.size()){
			throw new IllegalArgumentException("invalid arguments: origins, amount");
		}
		List<Integer> output=new ArrayList<>(amount);
		for(int i=0;i<amount;i++){
			int index=random.nextInt(origins.size());
			output.add(origins.get(index));
		}
		return output;
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Nest nest=new RandomOriginNest(PheromoneGraph.getGraphDemo());
		nest.produceAntColony(2).forEach(System.out::println);
	}
}
