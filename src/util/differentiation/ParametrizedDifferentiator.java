package util.differentiation;

import util.Function;

/**
 * Author: Fabian Fränz <f.fraenz@t-online.de>
 * Date: 17/05/14
 * Project: CAD
 * Version: 1.0
 * Description: TODO Add description.
 */
public interface ParametrizedDifferentiator {

	double differentiate(Function fx, Function fy, double u);
}
