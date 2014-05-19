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
public class SplineSurfaceOfRevolution implements SplineProperty {

	public static void main(String[] args) {

		Spline spline = new BezierSpline();
		spline.add(new DoublePoint(-100, 100));
		spline.add(new DoublePoint(0, 0));
		spline.add(new DoublePoint(100, 100));

		System.out.println(new SplineSurfaceOfRevolution().getValue(spline));
		System.out.println(Math.PI*100*Math.sqrt(Math.pow(50, 2)+ Math.pow(100, 2)));
	}

	@Override public double getValue(Spline spline) {

		int steps = spline.size()*20;
		final SplineFunctionX fx = new SplineFunctionX(spline);
		final SplineFunctionY fy = new SplineFunctionY(spline);
		ParametrizedIntegrator integrator = new ParametrizedSimpsonsRule();
		Function integral = new Function() {

			ParametrizedDifferentiator differentiator = new ParametrizedSimpleDifference(0.2);

			@Override public double evaluate(double u) {

				return fx.evaluate(u)*Math.sqrt(Math.pow(differentiator.differentiate(fx, fy, u), 2) + Math.pow( differentiator.differentiate(fy,fx,u), 2));
			}
		};
		return 2 * Math.PI * integrator.integrate(integral, fy,(spline.size() - 1) / 2.0, spline.size() - 1, steps);
	}

	@Override public String getName() {

		return "Surface of Revolution";
	}
}
