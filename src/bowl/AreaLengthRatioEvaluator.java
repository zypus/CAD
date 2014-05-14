package bowl;

import bowl.genetic.FitnessEvaluator;
import bowl.genetic.Individual;
import splines.Spline;
import splines.SplineArea;
import splines.SplineLength;

/**
 * Author: Fabian Fränz <f.fraenz@t-online.de>
 * Date: 13/05/14
 * Project: CAD
 * Version: 1.0
 * Description: TODO Add description.
 */
public class AreaLengthRatioEvaluator implements FitnessEvaluator {

	@Override public void evalutate(Individual individual) {
		Spline spline = (Spline)individual.getPhenotype();
		double area = new SplineArea().getValue(spline);
		double length = new SplineLength().getValue(spline);
		double ratio = area/length;
		individual.setFitness(ratio);
	}
}
