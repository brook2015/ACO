package map;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collection;

public class Vertexes {
	private final Collection<Vertex> vertexes;
	
	public Vertexes(){
		vertexes=new ArrayList<>(100);
	}
	
	public Vertexes(Collection<Vertex> vertexes){
		if(vertexes==null){
			throw new NullPointerException("null vertexes");
		}
		this.vertexes=new ArrayList<>(vertexes);
	}
	
	public void addVertex(Vertex vertex){
		if(vertex==null){
			throw new NullPointerException("null vertex");
		}
		vertexes.add(vertex);
	}
	
	@Override public String toString(){
		StringBuffer buffer=new StringBuffer("id longitude latitude\n");
		for(Vertex vertex:vertexes){
			buffer.append(vertex.toString()).append("\n");
		}
		return buffer.toString();
	}
	
	public String toGraph(){
		StringBuffer buffer=new StringBuffer();
		for(Vertex from:vertexes){
			for(Vertex to:vertexes){
				if(from==to)continue;
				String line=String.format("%d %d %.4f",from.id(),to.id(),Vertex.distance(from,to));
				buffer.append(line).append("\n");
			}
		}
		return buffer.toString();
	}
	
	public static void toFile(String path,String input){
		try {
			FileOutputStream stream=new FileOutputStream(new File(path));
			stream.write(input.getBytes("utf-8"));
			stream.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int amount=200;
		Vertexes vertexes=new Vertexes();
		for(int i=0;i<amount;i++){
			vertexes.addVertex(Vertex.getRandomVertex(200));
		}
		Vertexes.toFile("doc/vertexes.txt",vertexes.toString());
		Vertexes.toFile("doc/graph.txt",vertexes.toGraph());
		System.out.println("ok");
	}
}
