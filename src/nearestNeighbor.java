import java.awt.Point;
import java.util.Set;
public class nearestNeighbor{
	public nearestNeighbor(){
		
	}
	
	public static Point nearest(Point currentPos, Set<Point> pointsOfInterest){
		double min_dis = Double.MAX_VALUE;
		Point closest_point = null;
		for(Point p : pointsOfInterest){
			if(min_dis > currentPos.distance(p)){
				closest_point = p;
				min_dis = currentPos.distance(p);
			}
		}
		return closest_point;
	}
	
	
}