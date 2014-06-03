package util.differentiation.singleParameter;

import util.Function;

/**
 * Author: Fabian Fränz <f.fraenz@t-online.de>
 * Date: 17/05/14
 * Project: CAD
 * Version: 1.0
 * Description: TODO Add description.
 */
public class ParametrizedPointDifference implements  ParametrizedDifferentiator {

	double h;

	public ParametrizedPointDifference(double h) {

		this.h = h;
	}

	@Override public double differentiate(Function fx, Function fy, double u) {

		double dx;
		// five-point forward difference
		if (fx.isBounded() && u - 2 * h < fx.leftBound()) {
			double h1 = fy.evaluate(u + h) - fy.evaluate(u);
			double h2 = fy.evaluate(u + 2 * h) - fy.evaluate(u + h);
			double h3 = fy.evaluate(u + 3 * h) - fy.evaluate(u + 2 * h);
			double h4 = fy.evaluate(u + 4 * h) - fy.evaluate(u + 3 * h);
			double H1 = h1+h2+h3;
			double H2 = h1+h2+h3+h4;

			dx = -((2*h1+h2)*H1*H2+h1*(h1+h2)*(2*H2-h4))/(h1*(h1+h2)*H1*H2)*fx.evaluate(u) + ((h1+h2)*H1*H2)/(h1*h2*(h1+h3)*(H2-h1))*fx.evaluate(u+h) - (h1*H1*H2)/((h1+h2)*h2*h3*(h3+h4))*fx.evaluate(u+2*h) + (h1*(h1+h2)*H2)/(H1*(h2+h3)*h3*h4)*fx.evaluate(u + 3 * h) - (h1*(h1+h2)*H1)/(H2*(H2-h1)*(h3+h4)*h4)*fx.evaluate(u+4*u);
		}
		// five-point backward difference
		else if (fx.isBounded() && u + 2 * h > fx.rightBound()) {
			double h1 = fy.evaluate(u - 3 * h) - fy.evaluate(u - 4 * h);
			double h2 = fy.evaluate(u - 2 * h) - fy.evaluate(u - 3 * h);
			double h3 = fy.evaluate(u - 1 * h) - fy.evaluate(u - 2 * h);
			double h4 = fy.evaluate(u) - fy.evaluate(u - h);
			double H1 = h1 + h2 + h3;
			double H2 = h1 + h2 + h3 + h4;
			dx = ((H2-h1)*(h3+h4)*h4)/(h1*(h1+h2)*H1*H2)*fx.evaluate(u - 4 * u) - (H2*(h3+h4)*h4)/(h1*h2*(h1+h3)*(H2-h1))*fx.evaluate(u - 3 * h) + (H2*(H2-h1)*h4)/((h1+h2)*h2*h3*(h3+h4))/((h1+h2)*h2*h3*(h3+h4))*fx.evaluate(u - 2 * h) - (H2*(H2-h1)*(h3+h4))/(H1*(h2+h3)*h3*h4)*fx.evaluate(u - h) + ((2*H2-h1)*(h3+h4)*h4+H2*(H2-h1)*(h3+2*h4))/(H2*(H2-h1)*(h3+h4)*h4)*fx.evaluate(u);
		}
		// five-point centered difference
		else {
			double h1 = fy.evaluate(u - h) - fy.evaluate(u - 2 * h);
			double h2 = fy.evaluate(u) - fy.evaluate(u - h);
			double h3 = fy.evaluate(u + h) - fy.evaluate(u);
			double h4 = fy.evaluate(u + 2 * h) - fy.evaluate(u + h);
			double H1 = h1 + h2 + h3;
			double H2 = h1 + h2 + h3 + h4;
			dx = (h2*h3*(h3+h4))/(h1*(h1+h2)*H1*H2)*fx.evaluate(u - 2 * h) - ((h1+h2)*h3*(h3+h4))/(h1*h2*(h1+h3)*(H2-h1))*fx.evaluate(u - h) + ((h1+2*h2)*h3*(h3+h4)-(h1+h2)*h2*(2*h3+h4))/((h1+h2)*h2*h3*(h3+h4))*fx.evaluate(u) + ((h1+h2)*h2*(h3+h4))/(H1*(h2+h3)*h3*h4)*fx.evaluate(u + h) - (h1*(h1+h2)*H1)/(H2*(H2-h1)*(h3+h4)*h4)*fx.evaluate(u + 2 * h);
		}
		return dx;
	}
}
