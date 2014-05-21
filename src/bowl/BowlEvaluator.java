package bowl;

import bowl.genetic.FitnessEvaluator;
import bowl.genetic.Individual;
import gui.Spline2D;
import splines.Point;
import splines.Spline;
import splines.SplineProperty;

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

	private SplineProperty nominatorProperty;
	private SplineProperty denominatorProperty;

	public BowlEvaluator(SplineProperty nominatorProperty, SplineProperty denominatorProperty) {

		this.nominatorProperty = nominatorProperty;
		this.denominatorProperty = denominatorProperty;
	}

	@Override public void evaluate(Individual individual) {
		Spline spline = (Spline) individual.getPhenotype();
		if (isMonotone(spline)) {
			double nominator = Math.abs(nominatorProperty.getValue(spline));
			double denominator = Math.abs(denominatorProperty.getValue(spline));
			double ratio = nominator / denominator;
			Spline2D spline2d = new Spline2D(spline, ((BowlChromosome) individual.getChromosome()).getType());
			Rectangle.Double boundingBox = spline2d.getBoundingBox();
			if (boundingBox.width < 300 && boundingBox.height < 300 && ratio < 100 && ratio >= 0) {
				individual.setFitness(ratio);
				Bowl bowl = (Bowl) individual;
				bowl.setCustomInfo(
						nominatorProperty.getName() + ": " + (int)(nominator*100)/100.0 + "\n" + denominatorProperty.getName() + ": " + (int)(denominator*100)/100.0
						+ "\nRatio: "
						+ (int)(ratio*100)/100.0
				);
			} else {
				individual.setFitness(0);
				Bowl bowl = (Bowl) individual;
				bowl.setCustomInfo("To big.");
			}
		} else {
			individual.setFitness(0);
			Bowl bowl = (Bowl) individual;
			bowl.setCustomInfo("Not a bowl.");
		}
	}

	private boolean isMonotone(Spline spline) {

		double stepsize = 0.25;
		int midPoint = (spline.size() - 1) / 2;
		if (spline.size() <= 1) {
			return true;
		}
		Point point1 = spline.s(midPoint);
		Point point2 = spline.s(midPoint+stepsize);
		int sign1 = (int) Math.signum(point1.getY()-point2.getY());
		int sign2 = (int) Math.signum(point1.getX() - point2.getX());
		point1 = point2;
		int iterations = (int)(midPoint/stepsize);
		for (int i = 2 ; i <= iterations; i++) {
			double u = midPoint+i*stepsize;
			point2 = spline.s(u);
			int nextSign1 = (int) Math.signum(point1.getY() - point2.getY());
			if (nextSign1 != 0) {
				if (sign1 == 0) {
					sign1 = nextSign1;
				} else if (sign1 != nextSign1) {
					return false;
				}
			}
			int nextSign2 = (int) Math.signum(point1.getX() - point2.getX());
			if (nextSign2 != 0) {
				if (sign2 == 0) {
					sign2 = nextSign2;
				} else if (sign2 != nextSign2) {
					return false;
				}
			}
			if (point1.getX() == point2.getX() && point1.getY() == point2.getY()) {
				return false;
			}
			point1 = point2;
		}
		return true;
	}
}
