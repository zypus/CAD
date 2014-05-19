package util.integration;

import util.Function;

/**
 * Author: Fabian Fränz <f.fraenz@t-online.de>
 * Date: 16/05/14
 * Project: CAD
 * Version: 1.0
 * Description: TODO Add description.
 */
public class SimpsonsRule implements Integrator{

	@Override public double integrate(Function f, double a, double b, int steps) {

		Integrator midpointRule = new MidpointRule();
		Integrator trapezoidRule = new TrapezoidRule();
		return 0.5*(midpointRule.integrate(f,a,b,steps) + trapezoidRule.integrate(f,a,b,steps));
	}
}
