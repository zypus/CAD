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
public class MultiSimpsonsRule implements MultiIntegrator {

	@Override public double integrate(ParametricFunction f, Bound uBound, Bound vBound, int uSteps, int vSteps) {

		double k = (uBound.getUpper() - uBound.getLower()) / uSteps;
		double h = (vBound.getUpper() - vBound.getLower()) / vSteps;

		double sum = 0;
		for (int u = 0; u <= uSteps; u++) {
			for (int v = 0; v <= vSteps; v++) {
				double wu = (u == 0 || u == uSteps) ? 1 : (u % 2 == 0) ? 2 : 4;
				double wv = (v == 0 || v == uSteps) ? 1 : (v % 2 == 0) ? 2 : 4;
				double w = wu*wv;
				sum += w * f.getValue(uBound.getLower() + u * k, vBound.getLower() + v * h);
			}
		}

		return k * h / 9 * sum;
	}
}
