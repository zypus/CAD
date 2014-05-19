package splines;

import gui.DoublePoint;
import util.Function;
import util.differentiation.ParametrizedDifferentiator;
import util.differentiation.ParametrizedSimpleDifference;
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


		Spline spline = new LinearSpline();
		spline.add(new DoublePoint(-100, 100));
		spline.add(new DoublePoint(0, 0));
		spline.add(new DoublePoint(100, 100));

		System.out.println("Linear");
		System.out.println("Result: "+new SplineSolidOfRevolution().getValue(spline));
		System.out.println("Actual: "+1.0 / 3.0 * Math.PI * 100 * Math.pow(100, 2));

		Spline spline2 = new BezierSpline();
		spline2.add(new DoublePoint(-100, 100));
		spline2.add(new DoublePoint(0, 0));
		spline2.add(new DoublePoint(100, 100));

		System.out.println("Bezier");
		System.out.println("Result: "+new SplineSolidOfRevolution().getValue(spline2));
		System.out.println("Actual: "+1.0 / 3.0 * Math.PI * 50 * Math.pow(100, 2));
	}

	@Override public double getValue(Spline spline) {

		int steps = spline.size() * 20;
		final SplineFunctionX fx = new SplineFunctionX(spline);
		final SplineFunctionY fy = new SplineFunctionY(spline);
		ParametrizedIntegrator integrator = new ParametrizedSimpsonsRule();
		Function integral = new Function() {

			ParametrizedDifferentiator differentiator = new ParametrizedSimpleDifference(0.2);

			@Override public double evaluate(double u) {

				return Math.pow(fx.evaluate(u), 2)*differentiator.differentiate(fx,fy,u);
			}
		};
		return Math.PI * integrator.integrate(integral, fy, (spline.size()-1) / 2.0, spline.size()-1, steps);
	}

	@Override public String getName() {

		return "Solid of Revolution";
	}
}
