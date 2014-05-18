package bowl;

import bowl.genetic.Individual;
import gui.Spline2D;
import gui.SplineRenderer;
import gui.SplineType;
import gui.tools.Drawable;
import splines.Point;
import splines.Spline;

import java.awt.*;
import java.util.Random;

/**
 * Author: Fabian Fränz <f.fraenz@t-online.de>
 * Date: 13/05/14
 * Project: CAD
 * Version: 1.0
 * Description: TODO Add description.
 */
public class Bowl
		extends Individual
		implements Drawable {

	private static Random random = new Random(System.currentTimeMillis());

	private SplineType type;
	private String customInfo = "";

	public Bowl(SplineType type) {
		this.type = type;
		setChromosome(new BowlChromosome(random.nextInt(5)+1, type));
	}

	@Override public Object getPhenotype() {

		return getChromosome().expressGenotype();
	}

	@Override public Individual createIndividual() {

		return new Bowl(type);
	}

	@Override public Individual dubplicate() {

		Bowl dublicatedBowl = new Bowl(type);
		dublicatedBowl.setCustomInfo(customInfo);
		dublicatedBowl.setChromosome(getChromosome().duplicate());
		return dublicatedBowl;
	}

	@Override public void draw(Graphics2D g2) {

		SplineType type = ((BowlChromosome) getChromosome()).getType();
		Spline spline = (splines.Spline) getChromosome().expressGenotype();
		Spline2D bowl = new Spline2D(spline, type);
		SplineRenderer renderer = new SplineRenderer(g2);
		g2.setStroke(new BasicStroke(2));
		g2.setColor(Color.WHITE);
		Point midPoint = spline.s((spline.size()-1)/2);
		// render bowl
		renderer.renderSplineAtPosition(bowl.getSpline(), -midPoint.getX(), 10-midPoint.getY(), false);
	}

	public boolean isUpsideDown() {

		Spline spline = (splines.Spline) getChromosome().expressGenotype();
		Point p1 = spline.get((spline.size()-1)/2);
		Point p2 = spline.get(spline.size()-1);
		return p1.getY() > p2.getY();
	}

	public String getCustomInfo() {

		return customInfo;
	}

	public void setCustomInfo(String customInfo) {

		this.customInfo = customInfo;
	}
}
