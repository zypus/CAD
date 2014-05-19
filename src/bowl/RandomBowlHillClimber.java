package bowl;

import gui.SplineType;
import splines.Spline;
import splines.SplineProperty;

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
	private final int MAX_TRIES = 100;
	private int trial = 0;

	@Override public void setup(SplineProperty numerator, SplineProperty denominator, SplineType type) {

		super.setup(numerator, denominator, type);
		bestSpline = super.getCurrentBestSpline();
	}

	@Override public boolean advance() {

		boolean advance = super.advance();

		Spline spline = super.getCurrentBestSpline();
		double num = numerator.getValue(bestSpline);
		double den = denominator.getValue(bestSpline);
		double ratio = num / den * ((isMonotone(bestSpline)) ? 1 : 0);
		double num1 = numerator.getValue(spline);
		double den2 = denominator.getValue(spline);
		double ratio2 = num1 / den2 * ((isMonotone(spline)) ? 1 : 0);

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
		bowl.setCustomInfo(
				numerator.getName() + ": " + (int)(num/100) + "\n" + denominator.getName() + ": " + (int)(den/100)
				+ "\nRatio: "
				+ (int)(ratio*100)/100.0
		);
		bowls.add(bowl);
		return bowls;
	}
}
