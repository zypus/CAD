package util.integration;

import util.Function;

/**
 * Author: Fabian Fränz <f.fraenz@t-online.de>
 * Date: 17/05/14
 * Project: CAD
 * Version: 1.0
 * Description: TODO Add description.
 */
public class ParametrizedMidpointRule
		implements ParametrizedIntegrator {

	@Override public double integrate(Function fx, Function fy, double a, double b, int steps) {

		double h = (b - a) / steps;
		double area = 0;
		for (int j = 1; j <= steps; j++) {
			area += fx.evaluate(a + (j - 0.5) * h) * (fy.evaluate(a + j * h)-fy.evaluate(a+(j-1) * h));
		}
		return area;
	}
}
