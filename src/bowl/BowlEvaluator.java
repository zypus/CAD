package bowl;

import bowl.genetic.FitnessEvaluator;
import bowl.genetic.Individual;
import gui.Spline2D;
import splines.Spline;
import splines.SplineArea;
import splines.SplineLength;

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
		double area = new SplineArea().getValue(spline);
		double length = new SplineLength().getValue(spline);
		double ratio = area/length;
		Spline2D spline2d = new Spline2D(spline, ((BowlChromosome)individual.getChromosome()).getType());
		Rectangle.Double boundingBox = spline2d.getBoundingBox();
		double boundingBoxArea = boundingBox.width * boundingBox.height;
		individual.setFitness(ratio/boundingBoxArea);
	}
}
