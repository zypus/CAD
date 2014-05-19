package util.integration;

import util.Function;

/**
 * Author: Fabian Fränz <f.fraenz@t-online.de>
 * Date: 16/05/14
 * Project: CAD
 * Version: 1.0
 * Description: TODO Add description.
 */
public class MidpointRule implements Integrator {

	@Override public double integrate(Function f, double a, double b, int steps) {

		double h = (b-a)/steps;
		double area = 0;
		for (int j = 1; j <= steps; j++) {
			area += f.evaluate(a + (j - 0.5) * h);
		}
		area *= h;
		return area;
	}
}
