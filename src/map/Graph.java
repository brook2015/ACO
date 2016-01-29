package map;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.stream.Collectors;

public class Graph {
	private List<Integer> vertexes;
	private List<Edge> edges;
	
	public Graph(){
		vertexes=new ArrayList<>();
		edges=new ArrayList<>();
	}
	
	public int size(){
		return edges.size();
	}
	
	private void addEdge(Edge edge){
		if(edge==null)throw new NullPointerException("null edge");
		edges.add(edge);
	}
	
	private void addVertex(Integer vertex){
		if(!vertexes.contains(vertex)){
			vertexes.add(vertex);
		}
	}
	
	public Collection<Integer> getVertexes(){
		return vertexes;
	}
	
	public void initiate(File file){
		try{
			Scanner scanner=new Scanner(file);
			int size=scanner.nextInt();
			for(int i=0;i<size;i++){
				int from=scanner.nextInt();
				int to=scanner.nextInt();
				double weight=scanner.nextDouble();
				addEdge(new Edge(from,to,weight));
				addVertex(from);
			}
			scanner.close();
		}catch(FileNotFoundException e){
			e.printStackTrace();
		}catch(InputMismatchException e){
			e.printStackTrace();
		}
	}
	
	public PheromonedGraph toPheromonedGraph(Collection<Integer> vertexes){
		if(!vertexes.stream().allMatch(vertex->this.vertexes.contains(vertex))){
			throw new IllegalArgumentException("invalid vertexes");
		}
		PheromonedGraph graph=new PheromonedGraph();
		//double defaultPheromone=1.0f;
		for(int from:vertexes){
			for(int to:vertexes){
				if(from==to)continue;
				//double distance=shortestDistance(from,to);
				//graph.addPheromonedEdge(new PheromonedEdge(from,to,distance,defaultPheromone));
			}
		}
		return graph;
	}
	
	public static PheromonedGraph toPheromonedGraphDemo(){
		Graph graph=new Graph();
		graph.initiate(new File("doc/table.txt"));
		return graph.toPheromonedGraph(graph.getVertexes());
	}
	
	@Override public String toString(){
		String result="###### edge(s) ######\n";
		result+=String.format("vertex(es): %s\n",vertexes2String());
		for(Edge edge:edges)result+=edge+"\n";
		result+="######## end ########";
		return result;
	}
	
	public String vertexes2String(){
		return vertexes.stream()
				.map(vertex->vertex.toString())
				.collect(Collectors.joining("/"));
	}
	
	private double shortestDistance(int from,int to){
		return Math.random()*10;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Graph graph=new Graph();
		graph.initiate(new File("doc/table.txt"));
		//System.out.println(graph);
		System.out.println(Graph.toPheromonedGraphDemo());
	}

}
