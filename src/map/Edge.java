package map;

public class Edge {
	protected final int from;
	protected final int to;
	protected final double weight;
	
	public Edge(int from,int to,double weight){
		this.from=from;
		this.to=to;
		this.weight=weight;
	}
	
	public int from(){
		return from;
	}
	
	public int to(){
		return to;
	}
	
	public double weight(){
		return weight;
	}
	
	public PheromoneEdge toPheromonedEdge(double pheromone){
		return new PheromoneEdge(from,to,weight,pheromone);
	}
	
	@Override public String toString(){
		return String.format("%d --%.2f--> %d",from,weight,to);
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Edge edge=new Edge(1,4,2.3);
		System.out.println(edge);
	}

}
