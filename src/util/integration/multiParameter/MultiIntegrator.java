package util.integration.multiParameter;

import util.Bound;
import util.ParametricFunction;

/**
 * Author: Fabian Fränz <f.fraenz@t-online.de>
 * Date: 03/06/14
 * Project: CAD
 * Version: 1.0
 * Description: TODO Add description.
 */
public interface MultiIntegrator {

	double integrate(ParametricFunction f, Bound uBound, Bound vBound, int uSteps, int vSteps);
}
