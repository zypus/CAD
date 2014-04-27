package splines;

/**
 * Created by fabian on 27/04/14.
 */
public class LinearSpline extends Spline {

	@Override public Point s(double u) {

		Point leftPoint = controlPoints.get((int)Math.floor(u));
		Point rightPoint = controlPoints.get((int)Math.ceil(u));

		double m = (rightPoint.getY()-leftPoint.getY())/(rightPoint.getX()-leftPoint.getX());
		double b = rightPoint.getY()-rightPoint.getX()*m;

		double x = leftPoint.getX() + (u-Math.floor(u)) * (rightPoint.getX()-leftPoint.getX());
		double y = m*x+b;

		return leftPoint.createPoint(x,y);
	}
}
