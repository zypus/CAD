package splines;

import util.Function;

/**
 * Author: Fabian Fränz <f.fraenz@t-online.de>
 * Date: 16/05/14
 * Project: CAD
 * Version: 1.0
 * Description: TODO Add description.
 */
public class SplineFunctionY extends Function {

	private Spline spline;

	public SplineFunctionY(Spline spline) {

		this.spline = spline;
	}

	@Override public double evaluate(double u) {

		return spline.s(u).getY();
	}

	@Override public boolean isBounded() {

		return true;
	}

	@Override public double leftBound() {

		return 0;
	}

	@Override public double rightBound() {

		return spline.size()-1;
	}
}
