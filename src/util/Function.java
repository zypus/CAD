package util;

/**
 * Author: Fabian Fränz <f.fraenz@t-online.de>
 * Date: 16/05/14
 * Project: CAD
 * Version: 1.0
 * Description: TODO Add description.
 */
public abstract class Function {
	public abstract double evaluate(double x);

	public boolean isBounded() {
		return false;
	}
	public double leftBound() {
		return 0;
	}
	public double rightBound() {
		return 0;
	}
}
