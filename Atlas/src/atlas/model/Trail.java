package atlas.model;

import java.util.*;

import atlas.utils.Pair;

public class Trail {
    
	private static int TRAILSIZE = 60*60*6;
	/*Determines the storing frequency*/
	private static int STORE_PER_UPDATE = 100;
	
	private Deque<Pair<Double, Double>> points;
	private int length;
	private int timesCalled = 0;
	
	/**
	 * Default constructor, it construct a trail made of n points.
	 */
	public Trail(){
	    this(TRAILSIZE);
	}
	
	public Trail(int length){
		this.points = new ArrayDeque<>();
		this.length = length;
	}
	
	public void addPoint(double x, double y){
	    if(this.shouldAdd()){
	        this.points.addFirst(new Pair<Double, Double>(x,y));
	        if(this.points.size() - 1 == length){
	            this.points.removeLast();
	        }
	    }
	    this.timesCalled++;
	}
	
	private boolean shouldAdd(){
	    if(this.timesCalled >= this.length){
	        this.timesCalled = 0;
	    }
	    return (timesCalled % Trail.STORE_PER_UPDATE) == 0 ? true : false; 
	}
	
	public Collection<Pair<Double, Double>> getPoints(){
		return this.points; // defensive copy
	}
	
	public long getLength(){
		return this.length;
	}
	
	public String toString(){
		return points.toString();
	}
	
	//Testing performance
	public static void main(String s[]){
		int points = TRAILSIZE;
		Trail t = new Trail(points);
		
		for(int i = 0; i < points; i++){
			t.addPoint((double)i, (double)i*2);
		}
		
		for(int i = 11; i < 16; i++){
			t.addPoint((double)i, (double)i*2);
		}
		
		double unit = 0.1;
		
		long time = System.nanoTime();
//        List<Pair<Integer, Integer>> l = t.getPoints().stream()
//                .map(p -> new Pair<>((int) (double) (p.getX() * unit), (int) (double) (p.getY() * unit)))
//                .collect(Collectors.toList());
        time = System.nanoTime() - time; //~70ms
        System.out.println("time 1 (ms) = " + (double)time/(1000*1000) );
		
		time = System.nanoTime();
//		//oppure
		List<Pair<Integer,Integer>> l2 = new ArrayList<>(Trail.TRAILSIZE);
		for(Pair<Double,Double> p : t.getPoints()){
		    l2.add(new Pair<>((int)(double)(p.getX()*unit),(int)(double)(p.getY()*unit)));
		}
		time = System.nanoTime() - time; //~3ms
		
		System.out.println("time 2 (ms) = " + (double)time/(1000*1000) );
		
		
	}
	
	
}