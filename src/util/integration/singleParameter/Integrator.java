package util.integration.singleParameter;

import util.Function;

/**
 * Author: Fabian Fränz <f.fraenz@t-online.de>
 * Date: 16/05/14
 * Project: CAD
 * Version: 1.0
 * Description: TODO Add description.
 */
public interface Integrator {

	double integrate(Function f, double a, double b, int steps);
}
