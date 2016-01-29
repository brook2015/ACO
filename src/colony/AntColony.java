package colony;

import java.util.Collection;
import java.util.stream.Collectors;

import map.Network;
import map.PheromoneGraph;
import nest.Nest;
import nest.SpecificOriginNest;

public class AntColony {
	private final Network network;
	private Collection<Ant> ants;
	private int amount;
	
	public AntColony(int amount,Network network){
		this.amount=amount;
		this.network=network;
	}
	
	public void initiate(Nest nest){
		ants=nest.produceAntColony(amount);
	}
	
	public void iterate(){
		while(true){
			Collection<Ant> target=ants.stream()
					.filter(ant->!ant.stop())
					.collect(Collectors.toList());
			if(target.isEmpty())break;
			target.forEach(Ant::move);
		}
		network.volatilize();
		ants.forEach(Ant::updatePheromone);
	}
	
	public void print(){
		ants.forEach(System.out::println);
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Network network=PheromoneGraph.getGraphDemo2();
		//Network network=PheromoneMap.getMapDemo2();
		AntColony colony=new AntColony(1,network);
		int iterate=50;
		Nest nest=new SpecificOriginNest(1,network);
		for(int i=0;i<iterate;i++){
			colony.initiate(nest);
			colony.iterate();
		}
		colony.print();
		//System.out.println(network);
	}
}
