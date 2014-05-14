package bowl;

import bowl.genetic.Individual;
import gui.Spline2D;
import gui.SplineRenderer;
import gui.SplineType;
import gui.tools.Drawable;

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

	private SplineType type;

	public Bowl(SplineType type) {
		this.type = type;
		setChromosome(new BowlChromosome(1, type));
	}

	@Override public Object getPhenotype() {

		return getChromosome().expressGenotype();
	}

	@Override public Individual createIndividual() {

		return new Bowl(type);
	}

	@Override public void draw(Graphics2D g2) {

		SplineType type = ((BowlChromosome) getChromosome()).getType();
		Spline2D spline2d = new Spline2D((splines.Spline) getChromosome().expressGenotype(), type);
		SplineRenderer renderer = new SplineRenderer(g2);
		g2.setStroke(new BasicStroke(2));
		g2.setColor(Color.WHITE);
		renderer.renderSplineAtPosition(spline2d.getSpline(),0,0,false);
	}
}
