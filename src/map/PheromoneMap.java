package map;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

public class PheromoneMap implements Network{

	private static final double DEFAULT_PHEROMONE=1.0;
	private static final double VOLATILIZE_RATIO=0.2;
	private final Map<Integer,List<PheromoneEdge>> edges;
	
	public PheromoneMap(){
		edges=new HashMap<>();
	}
	
	private void addPheromoneEdge(PheromoneEdge edge){
		if(edge==null){
			throw new NullPointerException("null edge");
		}
		if(!edges.containsKey(edge.from())){
			edges.put(edge.from(),new ArrayList<>());
		}
		edges.get(edge.from()).add(edge);
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
		return edges.keySet();
	}
	
	public boolean contains(int index){
		return edges.containsKey(index);
	}
	
	public int size(){
		return edges.size();
	}
	
	public PheromoneEdge getEdge(int from,int to){
		PheromoneEdge output=null;
		List<PheromoneEdge> candidate=edges.get(from);
		for(PheromoneEdge c:candidate){
			if(to==c.to()){
				output=c;
				break;
			}
		}
		return output;
	}
	
	public List<PheromoneEdge> getEdges(int from){
		return edges.get(from);
	}
	
	public static PheromoneGraph toPheromoneGraphDemo(){
		PheromoneGraph graph=new PheromoneGraph();
		graph.initiate(new File("doc/table.txt"));
		return graph;
	}
	
	public void volatilize(){
		for(List<PheromoneEdge> list:edges.values()){
			list.forEach(l->l.volatilize(VOLATILIZE_RATIO));
		}
	}
	
	@Override public String toString(){
		StringBuffer buffer=new StringBuffer("### pheromone edge(s) ###\n");
		for(List<PheromoneEdge> list:edges.values()){
			list.forEach(l->buffer.append(l).append("\n"));
		}
		buffer.append("########## end ##########");
		return buffer.toString();
	}
	
	public static PheromoneMap getMapDemo1(){
		PheromoneMap map=new PheromoneMap();
		map.initiate(new File("doc/table.txt"));
		return map;
	}
	
	public static PheromoneMap getMapDemo2(){
		PheromoneMap map=new PheromoneMap();
		map.initiate(new File("doc/graph.txt"));
		return map;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Network network=PheromoneMap.getMapDemo1();
		System.out.println(network);
	}

}
