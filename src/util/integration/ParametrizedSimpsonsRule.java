package util.integration;

import util.Function;

/**
 * Author: Fabian Fränz <f.fraenz@t-online.de>
 * Date: 17/05/14
 * Project: CAD
 * Version: 1.0
 * Description: TODO Add description.
 */
public class ParametrizedSimpsonsRule
		implements ParametrizedIntegrator {

	@Override public double integrate(Function fx, Function fy, double a, double b, int steps) {

		ParametrizedIntegrator midpointRule = new ParametrizedMidpointRule();
		ParametrizedIntegrator trapezoidRule = new ParametrizedTrapezoidRule();
		return 0.5 * (midpointRule.integrate(fx, fy, a, b, steps) + trapezoidRule.integrate(fx, fy, a, b, steps));
	}
}
