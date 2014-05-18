package splines;

import util.Function;
import util.differentiation.*;
import util.integration.Integrator;
import util.integration.ParametrizedIntegrator;
import util.integration.ParametrizedSimpsonsRule;
import util.integration.SimpsonsRule;

/**
 * Author: Fabian Fränz <f.fraenz@t-online.de>
 * Date: 16/05/14
 * Project: CAD
 * Version: 1.0
 * Description: TODO Add description.
 */
public class SplineSurfaceOfRevolution implements SplineProperty {

	public static void main(String[] args) {

		final Function f = new Function() {
			double r = 5;

			@Override public double evaluate(double y) {

				return Math.sqrt(Math.pow(r, 2) - Math.pow(y, 2));
			}

			@Override public boolean isBounded() {

				return true;
			}

			@Override public double leftBound() {

				return -5;
			}

			@Override public double rightBound() {

				return 5;
			}
		};
		int steps = 50;
		Integrator integrator = new SimpsonsRule();
		Function integral = new Function() {

			Differentiator diff = new PointDifference(0.1);

			@Override public double evaluate(double y) {

				return f.evaluate(y) * Math.sqrt(1 + Math.pow(diff.differentiate(f, y), 2));
			}
		};
		System.out.println(2 * Math.PI * integrator.integrate(integral, -5, 5, steps));
	}

	@Override public double getValue(Spline spline) {

		int steps = spline.size()*20;
		final SplineFunctionY fy = new SplineFunctionY(spline);
		final SplineFunctionX fx = new SplineFunctionX(spline);
		ParametrizedIntegrator integrator = new ParametrizedSimpsonsRule();
		Function integral = new Function() {

			ParametrizedDifferentiator differentiator = new ParametrizedSimpleDifference(0.2);

			@Override public double evaluate(double u) {

				return fy.evaluate(u)*Math.sqrt(1 + Math.pow( differentiator.differentiate(fy,fx,u), 2));
			}
		};
		return 2 * Math.PI * integrator.integrate(integral, fy,(spline.size() - 1) / 2, spline.size() - 1, steps);
	}

	@Override public String getName() {

		return "Surface of Revolution";
	}
}
