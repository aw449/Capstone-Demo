import java.awt.Point;
import java.awt.geom.Point2D;
public class AUV{
	
	Point2D currentLatLong;
	Point currentPos;
	double ratio; //Degree to cell size
	double startLong;
	double startLat;
	
	public AUV(double latitude, double longtitude, double ratio){
		this.currentLatLong = new Point2D.Double(latitude,longtitude);
		this.currentPos = new Point(0,0);
		this.ratio = ratio;
		this.startLong = longtitude;
		this.startLat = latitude;
	}
	
	public void setX(int x){
		if(x >= 0){
			this.currentPos.setLocation(x,this.currentPos.getY());
			this.currentLatLong.setLocation(this.startLat + x*ratio,this.currentLatLong.getY());
		}
		
	}
	
	public int getX(){
		return this.currentPos.x;
	}
	
	public void setY(int y){
		if(y >= 0){
			this.currentPos.setLocation(this.currentPos.getX(),y);
			this.currentLatLong.setLocation(this.currentLatLong.getX(),this.startLong + y*ratio);
		}
		
	}
	
	public int getY(){
		return this.currentPos.y;
	}
	
	public double getLat(){
		return this.currentLatLong.getX();
	}
	
	public double getLong(){
		return this.currentLatLong.getY();
	}
	
	
}