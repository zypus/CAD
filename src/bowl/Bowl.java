package bowl;

import bowl.genetic.Individual;
import gui.Spline2D;
import gui.SplineRenderer;
import gui.SplineType;
import gui.tools.Drawable;
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
		Spline spline = (splines.Spline) getChromosome().expressGenotype();
		Spline2D firstHalf = new Spline2D(spline, type);
		Spline2D secondHalf = new Spline2D(otherHalf(spline), type);
		SplineRenderer renderer = new SplineRenderer(g2);
		g2.setStroke(new BasicStroke(2));
		g2.setColor(Color.WHITE);
		renderer.renderSplineAtPosition(firstHalf.getSpline(),0,0,false);
		renderer.renderSplineAtPosition(secondHalf.getSpline(),0,0,false);
	}

	private Spline otherHalf(Spline halfBowl) {
		Spline otherHalf = type.createInstance();
		for (int i = halfBowl.size()-1; i >= 0; i--) {
			Point point = halfBowl.get(i);
			Point mirroredPoint = point.createPoint(-point.getX(), point.getY());
			otherHalf.add(mirroredPoint);
		}
		return otherHalf;
	}
}
