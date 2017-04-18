import java.io.*;
import java.awt.Point;
import java.awt.geom.Point2D;
import java.util.List;
import java.util.Set;
import java.util.ArrayList;
import java.util.HashSet;


public class tester{
	
	 public static void sparseGenTest(){
		   wayPointGen test = new wayPointGen(100,99);
	        List<Point> p = test.sparseWayPointGen(10);
	        for(int i = 0; i < p.size(); i++){
	        	System.out.println(p.get(i).getX() + "," + p.get(i).getY());
	        }
	 }
	 
	 public static void denseGenTest(){
		   	wayPointGen test = new wayPointGen(100,100);
	        List<Point> p = test.denseWayPointGen(new Point(0,50), 3);
		   	
	        for(int i = 0; i < p.size(); i++){
	        	System.out.println(p.get(i).getX() + "," + p.get(i).getY());
	        }
	 }
	 
	 public static void nearestNeighborTest(){
		 Set<Point> s = new HashSet<Point>();
		 s.add(new Point(1,1));
		 s.add(new Point(1,3));
		 Point p = nearestNeighbor.nearest(new Point(0,0), s);
     	 System.out.println(p.getX() + "," + p.getY());

	 }
	 
	 public static void trimmerTest(){
		 Set<Point> s = new HashSet<Point>();
		 s.add(new Point(1,1));
		 s.add(new Point(2,2));
		 s.add(new Point(3,3));
		 s.add(new Point(4,4));
		 s.add(new Point(10,12));
		 s.add(new Point(13,14));
		 s.add(new Point(22,1));
		 s.add(new Point(1,30));
		 s.add(new Point(54,54));
		 s.add(new Point(600,1));
		 Set<Point> k = trim.trimmer(s,2);
		 for(Point p : k){
			 System.out.println(p.toString());
		 }
	 }
	 
	 public static void worldTest(){
		 World w = new World(100,100);
		 w.update(0,0,10);
		 for(int i =0 ; i<w.size_x; i++){
			 for(int j = 0; j<w.size_y; j++){
				 if(w.dataMap[i][j] > 0){
					 System.out.println(w.dataMap[i][j]);
				 }
			 }
		 }
	 }
	 
	public static void nextPointTest(){
		
		mainLoop test = new mainLoop("./src/simWorld.txt",80,80,.0009,100,100,10,50);
		List<Point> wPoints = new ArrayList<Point>();
		while(!wPoints.isEmpty()){
		Point p = test.nextPoint(wPoints);
		System.out.println(p.getX() + "," + p.getY());
		}
	}
	
	public static void getEstimatedTest(){
		
		mainLoop test = new mainLoop("./src/simWorld.txt",0,0,1,100,100,10,50);
		List<Point2D> sPoints = test.getEstimatedPath();
		for(Point2D a: sPoints){
			System.out.println(a.getX() + "," + a.getY());
		}
	}
	
	public static void simulatedTicktest(){
		
		mainLoop test = new mainLoop("./src/simWorld.txt",0,0,1,100,100,10,50);
		dataPoint d = null;
		
		while(!test.isFinished()){
			 d = test.simulatedTick();
			 System.out.println(d.Lat  + "," + d.Lng  + "," + d.Temp  + "," + d.Time );
		}
		
		
	}
	
	 public static void main(String[] args) {
		 	//nearestNeighborTest();
		 //	Point t = new Point(2,2);
		 //	trimmerTest();
		 // denseGenTest();
		 // worldTest();
		 //txtReadtestr();
		 //nextPointTest();
		 //getEstimatedTest();
		  simulatedTicktest();
	 }

}