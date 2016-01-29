package map;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Scanner;
import java.util.stream.Collectors;


public class PheromonedGraph {
	private static final double DEFAULT_PHEROMONE=1.0;
	private Map<Integer,List<PheromoneEdge>> edges;
	
	public PheromonedGraph(){
		edges=new HashMap<>();
	}
	
	public Collection<Integer> vertexes(){
		return edges.keySet();
	}
	
	public void initiate(File file){
		try{
			Scanner scanner=new Scanner(file);
			int size=scanner.nextInt();
			for(int i=0;i<size;i++){
				int from=scanner.nextInt();
				int to=scanner.nextInt();
				double weight=scanner.nextDouble();
				addPheromonedEdge(new PheromoneEdge(from,to,weight,DEFAULT_PHEROMONE));
			}
			scanner.close();
		}catch(FileNotFoundException e){
			e.printStackTrace();
		}catch(InputMismatchException e){
			e.printStackTrace();
		}
	}
	
	private void addPheromonedEdge(PheromoneEdge edge){
		if(!edges.containsKey(edge.from())){
			this.edges.put(edge.from(),new ArrayList<>());
		}
		this.edges.get(edge.from()).add(edge);
	}
	
	public PheromoneEdge getEdge(int from,int to){
		Optional<PheromoneEdge> target=edges.get(from).stream()
				.filter(edge->to==edge.to())
				.findFirst();
		if(!target.isPresent())throw new NoSuchElementException("invalid vertex");
		return target.get();
	}
	
	@Override public String toString(){
		String result="### pheromone edge(s) ###\n";
		for(List<PheromoneEdge> edges:this.edges.values()){
			for(PheromoneEdge edge:edges){
				result+=edge+"\n";
			}
		}
		result+="########## end ##########";
		return result;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		PheromonedGraph graph=new PheromonedGraph();
		graph.initiate(new File("doc/table.txt"));
		System.out.println(graph.vertexes().stream()
				.map(g->g.toString())
				.collect(Collectors.joining("/")));
	}

}
