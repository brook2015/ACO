package colony;

import java.util.Collection;
import java.util.stream.Collectors;

import map.Network;
import map.PheromoneGraph;
import nest.Nest;
import nest.UniqueOriginNest;

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
		print();
	}
	
	public void iterate(){
		while(true){
			Collection<Ant> target=ants.stream()
					.filter(ant->!ant.stop())
					.collect(Collectors.toList());
			if(target.isEmpty())break;
			target.forEach(Ant::move);
			print();
		}
		network.volatilize();
		ants.forEach(Ant::updatePheromone);
	}
	
	public void print(){
		ants.forEach(System.out::println);
	}
	
	public void show(){
		System.out.println(network);
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Network network=PheromoneGraph.getGraphDemo();
		AntColony colony=new AntColony(1,network);
		int iterate=50;
		for(int i=0;i<iterate;i++){
			colony.initiate(new UniqueOriginNest(network));
			colony.iterate();
			colony.show();
		}
	}
}
