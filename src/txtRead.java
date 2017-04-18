import java.io.*;
import java.util.Scanner;

public class txtRead{
	public static double[][] readFile(String pathname,int size_x, int size_y) throws FileNotFoundException{
		Scanner scan = new Scanner(new File(pathname));
		double[][] simWorld = new double[size_x][size_y]; 
		try{
			String line = "";
			int rowNumber = 0;
			while(scan.hasNextLine()) {
			  line = scan.nextLine();
			  String[] elements = line.split(",");
			  int elementCount = 0;
			  for(String element : elements) {
			    double elementValue = Double.parseDouble(element);
			    simWorld[rowNumber][elementCount] = elementValue;
			    elementCount++;
			  } 
			    rowNumber++;
			}
		}
		catch(Exception e){
			System.err.println("Error");
		}
		scan.close();
		return simWorld;
	}
	
}