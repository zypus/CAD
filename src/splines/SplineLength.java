package splines;

/**
 * Created by fabian on 19/04/14.
 */
public class SplineLength implements SplineProperty {
	private int STEPS = 50;
	@Override
	public double getValue(Spline spline) {
		int totalSteps = (spline.size() - 1) * STEPS;
		double length = 0;
		Point leftPoint = spline.get(0);

		for (int s = 1; s <= totalSteps; s++) {
			double u = (double) s / (double) STEPS;

			Point rightPoint = spline.s(u);
			double aSqr = Math.pow(Math.abs(rightPoint.getX() - leftPoint.getX()),2);
			double bSqr = Math.pow(Math.abs(rightPoint.getY() - leftPoint.getY()),2);
			length += Math.sqrt(aSqr + bSqr);

			leftPoint = rightPoint;
		}
		return length;
	}

	@Override
	public String getName() {
		return "Length";
	}
}
