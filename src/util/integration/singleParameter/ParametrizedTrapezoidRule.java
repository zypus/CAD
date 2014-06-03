package util.integration.singleParameter;

import util.Function;

/**
 * Author: Fabian Fränz <f.fraenz@t-online.de>
 * Date: 17/05/14
 * Project: CAD
 * Version: 1.0
 * Description: TODO Add description.
 */
public class ParametrizedTrapezoidRule
		implements ParametrizedIntegrator {

	@Override public double integrate(Function fx, Function fy, double a, double b, int steps) {

		double h = (b - a) / steps;
		double area = 0.5 * (fx.evaluate(a) + fx.evaluate(b)) * (fy.evaluate(a+h)-fy.evaluate(a));
		for (int i = 1; i < steps; i++) {
			area += fx.evaluate(a + i * h) * (fy.evaluate(a + (i + 1) * h) - fy.evaluate(a + i * h));
		}
		return area;
	}
}
