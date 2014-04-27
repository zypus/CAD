package splines;

/**
 * Created by fabian on 19/04/14.
 */
public interface Point {
    double getX();
    double getY();
 	// Point manipulate(double z) { 
	// return new Point(x*z,y*z);
	// }
	// Point addValue(Point p) {
	// return new Point(x+p.getX(),y+p.getY());	
	// }
	
	Point createPoint(double x,double y);

}
