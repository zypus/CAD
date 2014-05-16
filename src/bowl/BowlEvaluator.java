package bowl;

import bowl.genetic.FitnessEvaluator;
import bowl.genetic.Individual;
import gui.Spline2D;
import splines.*;
import splines.Point;

import java.awt.*;

/**
 * Author: Fabian Fränz <f.fraenz@t-online.de>
 * Date: 13/05/14
 * Project: CAD
 * Version: 1.0
 * Description: TODO Add description.
 */
public class BowlEvaluator
		implements FitnessEvaluator {

	@Override public void evalutate(Individual individual) {
		Spline spline = (Spline)individual.getPhenotype();
		if (isMonotone(spline)) {
			double area = new SplineArea().getValue(spline);
			double length = new SplineLength().getValue(spline);
			double ratio = area / length;
			Spline2D spline2d = new Spline2D(spline, ((BowlChromosome) individual.getChromosome()).getType());
			Rectangle.Double boundingBox = spline2d.getBoundingBox();
			double boundingBoxArea = boundingBox.width * boundingBox.height;
			individual.setFitness(ratio / boundingBoxArea);
		} else {
			individual.setFitness(0);
		}
	}

	private boolean isMonotone(Spline spline) {

		double stepsize = 0.05;
		if (spline.size() <= 1) {
			return true;
		}
		Point point1 = spline.s(0);
		Point point2 = spline.s(stepsize);
		int sign = (int)Math.signum(point1.getY()-point2.getY());
		point1 = point2;
		int iterations = (int)((spline.size() - 1)/stepsize);
		for (int i = 2 ; i <= iterations; i++) {
			double u = i*stepsize;
			point2 = spline.s(u);
			int nextSign = (int) Math.signum(point1.getY() - point2.getY());
			if (nextSign != 0) {
				if (sign == 0) {
					sign = nextSign;
				} else if (sign != nextSign) {
					return false;
				}
			}
		}
		return true;
	}
}
