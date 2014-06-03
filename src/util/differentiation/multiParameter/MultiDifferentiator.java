package util.differentiation.multiParameter;

import util.ParametricFunction;

/**
 * Author: Fabian Fränz <f.fraenz@t-online.de>
 * Date: 03/06/14
 * Project: CAD
 * Version: 1.0
 * Description: TODO Add description.
 */
public interface MultiDifferentiator {

	double differentiate(ParametricFunction f, double u, double v, double h);

}
