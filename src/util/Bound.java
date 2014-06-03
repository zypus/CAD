package util;

/**
* Author: Fabian Fränz <f.fraenz@t-online.de>
* Date: 03/06/14
* Project: CAD
* Version: 1.0
* Description: TODO Add description.
*/
public class Bound {

	double lower;
	double upper;

	public Bound(double lower, double upper) {

		this.lower = lower;
		this.upper = upper;
	}

	public double getLower() {

		return lower;
	}

	public double getUpper() {

		return upper;
	}
}
