package splines;

/**
 * Created by fabian on 19/04/14.
 */
public interface Point {
    double getX();
    double getY();
 	Point manipulate(double z);
	Point addValue(Point p);

	Point createPoint(double x,double y);

}
