package map;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Scanner;
import java.util.Set;
import java.util.stream.Collectors;

public class PheromoneGraph implements Network{
	private static final double DEFAULT_PHEROMONE=1.0;
	private static final double VOLATILIZE_RATIO=0.2;
	private final Collection<PheromoneEdge> edges;
	private final Set<Integer> vertexes;
	
	public PheromoneGraph(){
		edges=new ArrayList<>();
		vertexes=new HashSet<Integer>();
	}
	
	private void addPheromoneEdge(PheromoneEdge edge){
		if(edge==null){
			throw new NullPointerException("null edge");
		}
		edges.add(edge);
		vertexes.add(edge.from());
	}
	
	public void initiate(File file){
		try{
			Scanner scanner=new Scanner(file);
			int size=scanner.nextInt();
			for(int i=0;i<size;i++){
				int from=scanner.nextInt();
				int to=scanner.nextInt();
				double weight=scanner.nextDouble();
				addPheromoneEdge(new PheromoneEdge(from,to,weight,DEFAULT_PHEROMONE));
			}
			scanner.close();
		}catch(FileNotFoundException e){
			e.printStackTrace();
		}
	}
	
	public final Set<Integer> vertexes(){
		return vertexes;
	}
	
	public boolean contains(int index){
		return vertexes.contains(index);
	}
	
	public int size(){
		return vertexes.size();
	}
	
	public PheromoneEdge getEdge(int from,int to){
		Optional<PheromoneEdge> target=edges.stream()
				.filter(edge->from==edge.from()&&to==edge.to()).findFirst();
		if(!target.isPresent()){
			throw new NoSuchElementException("no such edge");
		}
		return target.get();
	}
	
	public void volatilize(){
		edges.forEach(edge->edge.volatilize(VOLATILIZE_RATIO));
	}
	
	@Override public String toString(){
		String output="### pheromone edge(s) ###\n";
		for(PheromoneEdge edge:edges){
			output+=edge+"\n";
		}
		output+="########## end ##########";
		return output;
	}
	
	public static PheromoneGraph getGraphDemo(){
		PheromoneGraph graph=new PheromoneGraph();
		graph.initiate(new File("doc/table.txt"));
		return graph;
	}
	
	@Override
	public List<PheromoneEdge> getEdges(int from) {
		// TODO Auto-generated method stub
		return edges.stream().filter(edge->from==edge.from()).collect(Collectors.toList());
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Network network=PheromoneGraph.getGraphDemo();
		System.out.println(network);
	}
}
