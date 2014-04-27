package splines;

/**
 * Created by fabian on 27/04/14.
 */
public class LinearSpline
		extends Spline {

	@Override public Point s(double u) {

		Point leftPoint = get((int) Math.floor(u));
		Point rightPoint = get((int) Math.ceil(u));

		double percentage = u - Math.floor(u);
		double x = leftPoint.getX() + percentage * (rightPoint.getX() - leftPoint.getX());
		double y = leftPoint.getY() + percentage * (rightPoint.getY() - leftPoint.getY());

		return leftPoint.createPoint(x, y);
	}
}
