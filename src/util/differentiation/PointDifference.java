package util.differentiation;

import util.Function;

/**
 * Author: Fabian Fränz <f.fraenz@t-online.de>
 * Date: 16/05/14
 * Project: CAD
 * Version: 1.0
 * Description: TODO Add description.
 */
public class PointDifference implements Differentiator {

	double h;

	public PointDifference(double h) {

		this.h = h;
	}

	@Override public double differentiate(Function f, double x) {

		double dx;
		// five-point forward difference
		if (f.isBounded() && x-2*h < f.leftBound()) {
			dx = (-25 * f.evaluate(x) + 48 * f.evaluate(x + h) - 36 * f.evaluate(x + 2 * h) + 16 * f.evaluate(x + 3 * h)
					 - 3 * f.evaluate(x + 4 * h)) / (12 * h);
		}
		// five-point backward difference
		else if (f.isBounded() && x + 2 * h > f.rightBound()) {
			dx = (-25 * f.evaluate(x) + 48 * f.evaluate(x - h) - 36 * f.evaluate(x - 2 * h) + 16 * f.evaluate(x - 3 * h)
				  - 3 * f.evaluate(x - 4 * h)) / (12 * -h);
		}
		// five-point centered difference
		else {
			dx = (-f.evaluate(x + 2 * h) + 8 * f.evaluate(x + h) - 8 * f.evaluate(x - h) + f.evaluate(x - 2 * h)) / (12 * h);
		}
		return dx;
	}
}
