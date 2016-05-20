package atlas.model;

import java.util.*;
import atlas.utils.Pair;

public class Trail {
    
	private static int TRAILSIZE = 365*60*60*24;
	
	private Deque<Pair<Double, Double>> points;
	private int length;
	
	public Trail(int length){
		this.points = new ArrayDeque<>();
		this.length = Trail.TRAILSIZE;
	}
	
	public void addPoint(double x, double y){
		this.points.addFirst(new Pair<Double, Double>(x,y));
		if(this.points.size() - 1 == length){
			this.points.removeLast();
		}
	}
	
	public Iterator<Pair<Double, Double>> getPoints(){
		return this.points.iterator();
	}
	
	public int getLength(){
		return this.length;
	}
	
	public String toString(){
		return points.toString();
	}
	
	//Testing performance
	public static void main(String s[]){
		int points = 10;
		Trail t = new Trail(points);
		
		for(int i = 0; i < points; i++){
			t.addPoint((double)i, (double)i*2);
		}
		
		for(int i = 11; i < 16; i++){
			t.addPoint((double)i, (double)i*2);
		}
		
	}
	
	
}