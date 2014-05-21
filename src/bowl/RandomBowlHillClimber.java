package bowl;

import gui.Spline2D;
import gui.SplineType;
import splines.Point;
import splines.Spline;
import splines.SplineProperty;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;

/**
 * Author: Fabian Fränz <f.fraenz@t-online.de>
 * Date: 19/05/14
 * Project: CAD
 * Version: 1.0
 * Description: TODO Add description.
 */
public class RandomBowlHillClimber extends BowlHillClimber {

	private Spline bestSpline;
	private final int MAX_TRIES = 1000;
	private int trial = 0;

	@Override public void setup(SplineProperty numerator, SplineProperty denominator, SplineType type) {

		super.setup(numerator, denominator, type);
		bestSpline = super.getCurrentBestSpline();
	}

	@Override public boolean advance() {

		boolean advance = super.advance();

		Spline spline = super.getCurrentBestSpline();
		Spline2D spline2d = new Spline2D(spline, type);
		Rectangle.Double rectangle = spline2d.getBoundingBox();
		Spline2D spline2d2 = new Spline2D(bestSpline, type);
		Rectangle.Double rectangle2 = spline2d2.getBoundingBox();
		double num = numerator.getValue(bestSpline);
		double den = denominator.getValue(bestSpline);
		double ratio = num / den * ((isMonotone(bestSpline)) ? 1 : 0);
		ratio /= rectangle2.width * rectangle2.height / 1000;
		double num1 = numerator.getValue(spline);
		double den2 = denominator.getValue(spline);
		double ratio2 = num1 / den2 * ((isMonotone(spline)) ? 1 : 0);
		ratio2 /= rectangle.width * rectangle.height / 1000;

		if (ratio2 > ratio) {
			bestSpline = spline;
		}

		if (!advance) {
			trial++;
			super.setup(numerator, denominator, type);
		}

		return advance || trial < MAX_TRIES;
	}

	@Override public List<Bowl> getResult() {

		List<Bowl> bowls = new ArrayList<>();
		HillBowl bowl = new HillBowl(type, bestSpline);
		double num = numerator.getValue(bestSpline);
		double den = denominator.getValue(bestSpline);
		double ratio = num/den * ((isMonotone(bestSpline)) ? 1 : 0);
		Spline2D spline2d = new Spline2D(bestSpline, type);
		Rectangle.Double rectangle = spline2d.getBoundingBox();
		ratio /= rectangle.width * rectangle.height / 1000;
		bowl.setCustomInfo(
				numerator.getName() + ": " + (int)(num*100)/100.0 + "\n" + denominator.getName() + ": " + (int)(den*100)/100.0
				+ "\nRatio: "
				+ (int)(ratio*100)/100.0
		);
		bowls.add(bowl);
		return bowls;
	}

	@Override public String getInfo() {

		return "Try: "+trial+" / "+MAX_TRIES+"\n";
	}

	@Override protected boolean isMonotone(Spline spline) {

		double stepsize = 0.25;
		int midPoint = (spline.size()-1)/2;
		if (spline.size() <= 1) {
			return true;
		}
		Point point1 = spline.s(midPoint);
		Point point2 = spline.s(midPoint + stepsize);
		int sign1 = (int) Math.signum(point1.getY() - point2.getY());
		int sign2 = (int) Math.signum(point1.getX() - point2.getX());
		point1 = point2;
		int iterations = (int) (midPoint / stepsize);
		for (int i = 2; i <= iterations; i++) {
			double u = midPoint + i * stepsize;
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
			point1 = point2;
		}
		return true;
	}
}
