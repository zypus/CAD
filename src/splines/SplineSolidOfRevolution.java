package splines;

import gui.DoublePoint;
import util.Function;
import util.integration.ParametrizedIntegrator;
import util.integration.ParametrizedSimpsonsRule;

/**
 * Author: Fabian Fränz <f.fraenz@t-online.de>
 * Date: 16/05/14
 * Project: CAD
 * Version: 1.0
 * Description: TODO Add description.
 */
public class SplineSolidOfRevolution implements SplineProperty {

	public static void main(String[] args) {

		int steps = 40;
		Spline spline = new LinearSpline();
		spline.add(new DoublePoint(-200,100));
		spline.add(new DoublePoint(-100,0));
		spline.add(new DoublePoint(0,0));
		spline.add(new DoublePoint(100,0));
		spline.add(new DoublePoint(200,100));
		final SplineFunctionY fy = new SplineFunctionY(spline);
		final SplineFunctionX fx = new SplineFunctionX(spline);

		ParametrizedIntegrator integrator = new ParametrizedSimpsonsRule();
		Function integral = new Function() {

			@Override public double evaluate(double y) {

				return Math.pow(fy.evaluate(y), 2);
			}
		};
		System.out.println(Math.PI * integrator.integrate(integral, fx, (spline.size() - 1) / 2, spline.size() - 1, steps));
	}

	@Override public double getValue(Spline spline) {

		int steps = spline.size() * 20;
		final SplineFunctionY fy = new SplineFunctionY(spline);
		final SplineFunctionX fx = new SplineFunctionX(spline);
		ParametrizedIntegrator integrator = new ParametrizedSimpsonsRule();
		Function integral = new Function() {

			@Override public double evaluate(double y) {

				return Math.pow(fy.evaluate(y), 2);
			}
		};
		return Math.PI * integrator.integrate(integral, fx, (spline.size()-1)/2, spline.size()-1, steps);
	}

	@Override public String getName() {

		return "Solid of Revolution";
	}
}
