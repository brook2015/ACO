package map;

public class PheromoneEdge extends Edge{
	private double pheromone;
	
	public PheromoneEdge(int from,int to,double weight,double pheromone){
		super(from,to,weight);
		this.pheromone=pheromone;
	}
	
	public double pheromone(){
		return pheromone;
	}
	
	/*public void setPheromone(double pheromone){
		this.pheromone=pheromone;
	}*/
	
	public void accumulate(double delta){
		pheromone+=delta;
	}
	
	public void volatilize(double ratio){
		pheromone*=1-ratio;
	}
	
	public double utility(){
		return Math.pow(pheromone,2.0)*Math.pow(1.0/weight,2.0);
	}
	
	@Override public String toString(){
		return String.format("%d --(%.2f / %.2f)--> %d",from,weight,pheromone,to);
	}
	

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
