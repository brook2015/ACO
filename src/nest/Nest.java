package nest;

import java.util.Collection;
import java.util.Random;

import colony.Ant;

public interface Nest {
	Collection<Ant> produceAntColony(int amount);
	
	default int[] group(int count,int group,int minimum){
		if(count<minimum*group||count<1||group<1){
			throw new IllegalArgumentException("invalid count and group");
		}
		int[] output=new int[group];
		if(count==minimum*group){
			for(int i=0;i<group;i++){
				output[i]=minimum;
			}
		}else{
			int g=group,value;
			Random randomInt=new Random();
			for(int i=0;i<group-1;i++){
				value=randomInt.nextInt(count-minimum*g)+minimum;
				output[i]=value;
				count-=value;
				g--;
			}
			output[group-1]=count;
		}
		return output;
	}
}
