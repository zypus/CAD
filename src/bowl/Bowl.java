package bowl;

import bowl.genetic.Individual;
import gui.Spline2D;
import gui.SplineType;
import gui.tools.Drawable;
import gui.tools.draw.SplineDrawer;

import java.awt.*;

/**
 * Author: Fabian Fränz <f.fraenz@t-online.de>
 * Date: 13/05/14
 * Project: CAD
 * Version: 1.0
 * Description: TODO Add description.
 */
public class Bowl
		extends Individual implements Drawable {

	@Override public Object getPhenotype() {

		return getChromosome().expressGenotype();
	}

	@Override public Individual createIndividual() {

		return new Bowl();
	}

	@Override public void draw(Graphics2D g2) {

		SplineType type = ((BowlChromosome) getChromosome()).getType();
		Spline2D spline2d = new Spline2D((splines.Spline) getChromosome().expressGenotype(), type);
		SplineDrawer drawer = new SplineDrawer(type);
	}
}
