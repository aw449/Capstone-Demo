import java.util.ArrayList;
import java.util.Date;
import java.awt.Point;
import java.awt.geom.Point2D;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Set;


public class mainLoop{
	AUV blueRov;
	World worldOrder;
	double[][] simWorld;
	List<Point> wayPoints;
	wayPointGen wpGenerator;
	
	int size_x;
	int size_y;
	double startLat;
	double startLong;
	double ratio;
	double tempThreshold = 0;
	boolean sparseTraverse = true;
	boolean finishedFlag = false;
	
	private Point nextPoint(List<Point> wPoints){
		int x = blueRov.getX();
		int y = blueRov.getY();
		Point p = wPoints.get(0);
		int diffx = (int) (p.getX() - x);
		int diffy = (int) (p.getY() - y);
		System.out.println(diffx + "," + diffy);
		if(diffx == 0 && diffy == 0){
			wPoints.remove(0);
			if(!wPoints.isEmpty()){
				p = wPoints.get(0);
				diffx = (int) (p.getX() - x);
				diffy = (int) (p.getY() - y);
			}
			else{
				System.out.println("Empty");
			}
		}
		
		if(Math.abs(diffx) < Math.abs(diffy) && (diffy != 0)){
	
			if(diffy < 0){
				y--;
			}
			else{
				y++;
			}
			blueRov.setY(y);
		
		}
		else if(diffx != 0){
			if(diffx < 0){
				x--;
			}
			else{
				x++;
			}
			blueRov.setX(x);
		}
		
		Point newPoint = new Point(x,y);
		
		return newPoint;
	}
	
	
	public mainLoop(String pathname, double startLatitude, double startLongtitude, double ratio, int size_x, int size_y,int step_size,int threshold){
		this.size_x = size_x;
		this.size_y = size_y;
		this.tempThreshold = threshold;
		this.ratio = ratio;
		this.startLat = startLatitude;
		this.startLong = startLongtitude;
		
		blueRov = new AUV(startLatitude, startLongtitude,ratio);
		wpGenerator = new wayPointGen(size_x,size_y);
		wayPoints = wpGenerator.sparseWayPointGen(step_size);
		worldOrder = new World(size_x,size_y);
		simWorld = new double[size_x][size_y];
		
		
		try {
			simWorld = txtRead.readFile(pathname, size_x, size_y);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
	public dataPoint simulatedTick(){

		Date date = new Date(); //grabs current time stamp
		dataPoint newData = new dataPoint(date,0,0,0);
		if(wayPoints.isEmpty()){
			sparseTraverse = false;
		}
		
		if(sparseTraverse){		
			Point p = nextPoint(wayPoints); //next coordinate to travel to ie. x+1,y or x,y+1
			double value = simWorld[(int) p.getX()][(int) p.getY()];
			newData = new dataPoint(date,p.getX(),p.getY(),value);
			worldOrder.update(newData,startLat,startLong,ratio);
			
			if(value >= this.tempThreshold){
				worldOrder.pointsOfInterest.add(p);
			}
		}
		
		else if(!worldOrder.pointsOfInterest.isEmpty()){
			//System.out.println("Hello");
			//Calls trim every tick because user can add points at any time
			Set<Point> tempSet = trim.trimmer(worldOrder.pointsOfInterest,2);
			worldOrder.pointsOfInterest.clear();
			worldOrder.pointsOfInterest.addAll(tempSet);
			//on an empty waypoint list, grab nearest neighbor
			if(wayPoints.isEmpty()){
				Point o = nearestNeighbor.nearest(blueRov.currentPos, worldOrder.pointsOfInterest);
				List<Point> temp = wpGenerator.denseWayPointGen(o, 3);
				wayPoints.addAll(temp);
				worldOrder.pointsOfInterest.remove(o);
			}
			
			Point p = nextPoint(wayPoints);
			double value = simWorld[(int) p.getX()][(int) p.getY()];
			newData = new dataPoint(date,p.getX(),p.getY(),value);
			worldOrder.update(newData,startLat,startLong,ratio);
			
		}
		else{
			finishedFlag = true;
		}
		
		return newData;
	}
	
	public boolean isFinished(){
		return this.finishedFlag;
	}
	
	
	public List<Point2D> getEstimatedPath(){
		//Covert from cells to gps coor.
		List<Point2D> result = new ArrayList<Point2D>();
		for (Point p : wayPoints){
			result.add(new Point2D.Double(p.getX() * ratio + this.startLong,p.getY()* ratio + this.startLat));
		}
		
		return result;
	}
	
	
	public void reset(String pathname, double startLatitude, double startLongtitude, double ratio, int size_x, int size_y,int step_size,int threshold){
		this.size_x = size_x;
		this.size_y = size_y;
		this.tempThreshold = threshold;
		this.ratio = ratio;
		this.startLat = startLatitude;
		this.startLong = startLongtitude;
		
		blueRov = new AUV(startLatitude, startLongtitude,ratio);
		wpGenerator = new wayPointGen(size_x,size_y);
		wayPoints = wpGenerator.sparseWayPointGen(step_size);
		worldOrder = new World(size_x,size_y);
		simWorld = new double[size_x][size_y];
		this.sparseTraverse = true;
		this.finishedFlag = false;
		
		try {
			simWorld = txtRead.readFile(pathname, size_x, size_y);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}