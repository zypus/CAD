package util.differentiation.singleParameter;

import util.Function;

/**
 * Author: Fabian Fränz <f.fraenz@t-online.de>
 * Date: 19/05/14
 * Project: CAD
 * Version: 1.0
 * Description: TODO Add description.
 */
public class SimpleDifference implements Differentiator {

	double h;

	public SimpleDifference(double h) {

		this.h = h;
	}

	@Override public double differentiate(Function f, double x) {

		double result;
		if (f.isBounded() && x - h / 2 < f.leftBound()) {
			result = (f.evaluate(x + h) - f.evaluate(x)) / h;
		}
		// five-point backward difference
		else if (f.isBounded() && x + h / 2 > f.rightBound()) {
			result = (f.evaluate(x) - f.evaluate(x - h)) / h;
		}
		// five-point centered difference
		else {
			result = (f.evaluate(x + h / 2) - f.evaluate(x - h / 2)) / h;
		}
		if (result < 0) {
			return Math.max(-100, result);
		} else {
			return Math.min(100, result);
		}
	}
}
