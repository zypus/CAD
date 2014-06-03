package util.integration.singleParameter;

import util.Function;

/**
 * Author: Fabian Fränz <f.fraenz@t-online.de>
 * Date: 16/05/14
 * Project: CAD
 * Version: 1.0
 * Description: TODO Add description.
 */
public class TrapezoidRule implements Integrator {

	@Override public double integrate(Function f, double a, double b, int steps) {

		double h = (b-a)/steps;
		double area = 0.5*(f.evaluate(a)+f.evaluate(b));
		for (int i = 1; i < steps ; i++) {
			area+= f.evaluate(a+i*h);
		}
		area *= h;
		return area;
	}
}
