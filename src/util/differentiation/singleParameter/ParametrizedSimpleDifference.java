package util.differentiation.singleParameter;

import util.Function;

/**
 * Author: Fabian Fränz <f.fraenz@t-online.de>
 * Date: 17/05/14
 * Project: CAD
 * Version: 1.0
 * Description: TODO Add description.
 */
public class ParametrizedSimpleDifference implements ParametrizedDifferentiator {

	double h;

	public ParametrizedSimpleDifference(double h) {

		this.h = h;
	}

	@Override public double differentiate(Function fx, Function fy, double u) {

		double result;
		if (fx.isBounded() && u - h / 2 < fx.leftBound()) {
			result = (fy.evaluate(u + h) - fy.evaluate(u)) / (fx.evaluate(u + h) - fx.evaluate(u));
		}
		// five-point backward difference
		else if (fx.isBounded() && u + h / 2 > fx.rightBound()) {
			result = (fy.evaluate(u) - fy.evaluate(u - h)) / (fx.evaluate(u) - fx.evaluate(u - h));
		}
		// five-point centered difference
		else {
			result = (fy.evaluate(u + h / 2) - fy.evaluate(u - h / 2)) / (fx.evaluate(u + h / 2) - fx.evaluate(u - h / 2));
		}
		if (result < 0) {
			return Math.max(-100, result);
		} else {
			return Math.min(100, result);
		}

	}
}
