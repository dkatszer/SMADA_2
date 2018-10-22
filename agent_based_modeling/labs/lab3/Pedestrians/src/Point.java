import java.util.ArrayList;
import java.util.OptionalInt;

public class Point {

	public ArrayList<Point> neighbors;
	public static Integer []types ={0,1,2,3};
	public int type;
	public int staticField;
	public boolean isPedestrian;

	public Point() {
		type=0;
		staticField = 100000;
		neighbors= new ArrayList<Point>();
	}
	
	public void clear() {
		staticField = 100000;
		
	}

	public boolean calcStaticField() {
		OptionalInt min = neighbors.stream().mapToInt(neighbor -> neighbor.staticField).min();
		if(!min.isPresent()){
			return false;
		}
		int minNeighbourStaticField = min.getAsInt();
		if(staticField > minNeighbourStaticField + 1){
			staticField = minNeighbourStaticField + 1;
			return true;
		}
		return false;
	}
	
	public void move(){
	}

	public void addNeighbor(Point nei) {
		neighbors.add(nei);
	}
}