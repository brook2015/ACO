package map;

public class Vertex {
	private int id;
	private double longitude;
	private double latitude;
	private static int index=1;
	
	public Vertex(double longitude,double latitude){
		this.id=index++;
		this.longitude=longitude;
		this.latitude=latitude;
	}
	
	public int id(){
		return id;
	}
	
	public double longitude(){
		return longitude;
	}
	
	public double latitude(){
		return latitude;
	}
	
	@Override public String toString(){
		return String.format("%d %.4f %.4f",id,longitude,latitude);
	}
	
	public static double distance(Vertex from,Vertex to){
		double d1=from.latitude()-to.latitude();
		double d2=from.longitude()-to.longitude();
		return Math.sqrt(d1*d1+d2*d2);
	}
	
	public static Vertex getRandomVertex(double limit){
		double longitude=Math.random()*limit;
		double latitude=Math.random()*limit;
		return new Vertex(longitude,latitude);
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Vertex vertex=new Vertex(125.6,83.9);
		System.out.println(vertex);
		System.out.println(Vertex.getRandomVertex(200.0));
	}
}
