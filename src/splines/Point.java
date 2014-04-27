package splines;

/**
 * Created by fabian on 19/04/14.
 */
public interface Point {
    double getX();
    double getY();
    void setX(double x);
    void setY(double y);
	Point createPoint(double x,double y);
}
