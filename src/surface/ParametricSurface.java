package surface;

import util.Bound;
import util.Function;
import util.ParametricFunction;
import util.differentiation.singleParameter.Differentiator;
import util.differentiation.singleParameter.PointDifference;
import util.integration.multiParameter.MultiIntegrator;
import util.integration.multiParameter.MultiSimpsonsRule;

/**
 * Author: Fabian Fränz <f.fraenz@t-online.de>
 * Date: 03/06/14
 * Project: CAD
 * Version: 1.0
 * Description: TODO Add description.
 */
public class ParametricSurface {

	private ParametricFunction xFunction;
	private ParametricFunction yFunction;
	private ParametricFunction zFunction;
	private Bound uBound;
	private Bound vBound;

	public ParametricSurface(ParametricFunction xFunction, ParametricFunction yFunction, ParametricFunction zFunction,
			Bound uBound, Bound vBound) {

		this.xFunction = xFunction;
		this.yFunction = yFunction;
		this.zFunction = zFunction;
		this.uBound = uBound;
		this.vBound = vBound;
	}

	public Point3d s(double u, double v) {
		double x = xFunction.getValue(u,v);
		double y = yFunction.getValue(u,v);
		double z = zFunction.getValue(u,v);

		return new Point3d(x, y, z);
	}

	public Bound getUBound() {

		return uBound;
	}

	public Bound getVBound() {

		return vBound;
	}

	public double getArea() {

		ParametricFunction crossProduct = new ParametricFunction() {
			@Override public double getValue(double u, double v) {

				final double h = 0.01;

				ParametricFunction diffXU = new ParametricFunction() {
					@Override public double getValue(double u, final double v) {

						Differentiator differentiator = new PointDifference(h);
						Function diff = new Function() {
							@Override public double evaluate(double x) {

								return xFunction.getValue(x, v);
							}

							@Override public boolean isBounded() {

								return true;
							}

							@Override public double leftBound() {

								return uBound.getLower();
							}

							@Override public double rightBound() {

								return uBound.getUpper();
							}
						};
						return differentiator.differentiate(diff,u);
					}
				};
				ParametricFunction diffYU = new ParametricFunction() {
					@Override public double getValue(double u, final double v) {

						Differentiator differentiator = new PointDifference(h);
						Function diff = new Function() {
							@Override public double evaluate(double x) {

								return yFunction.getValue(x, v);
							}

							@Override public boolean isBounded() {

								return true;
							}

							@Override public double leftBound() {

								return uBound.getLower();
							}

							@Override public double rightBound() {

								return uBound.getUpper();
							}
						};
						return differentiator.differentiate(diff, u);
					}
				};
				ParametricFunction diffZU = new ParametricFunction() {
					@Override public double getValue(double u, final double v) {

						Differentiator differentiator = new PointDifference(h);
						Function diff = new Function() {
							@Override public double evaluate(double x) {

								return zFunction.getValue(x, v);
							}

							@Override public boolean isBounded() {

								return true;
							}

							@Override public double leftBound() {

								return uBound.getLower();
							}

							@Override public double rightBound() {

								return uBound.getUpper();
							}
						};
						return differentiator.differentiate(diff, u);
					}
				};
				ParametricFunction diffXV = new ParametricFunction() {
					@Override public double getValue(final double u, double v) {

						Differentiator differentiator = new PointDifference(h);
						Function diff = new Function() {
							@Override public double evaluate(double x) {

								return xFunction.getValue(u, x);
							}

							@Override public boolean isBounded() {

								return true;
							}

							@Override public double leftBound() {

								return vBound.getLower();
							}

							@Override public double rightBound() {

								return vBound.getUpper();
							}
						};
						return differentiator.differentiate(diff, v);
					}
				};
				ParametricFunction diffYV = new ParametricFunction() {
					@Override public double getValue(final double u, double v) {

						Differentiator differentiator = new PointDifference(h);
						Function diff = new Function() {
							@Override public double evaluate(double x) {

								return yFunction.getValue(u, x);
							}

							@Override public boolean isBounded() {

								return true;
							}

							@Override public double leftBound() {

								return vBound.getLower();
							}

							@Override public double rightBound() {

								return vBound.getUpper();
							}
						};
						return differentiator.differentiate(diff, v);
					}
				};
				ParametricFunction diffZV = new ParametricFunction() {
					@Override public double getValue(final double u, double v) {

						Differentiator differentiator = new PointDifference(h);
						Function diff = new Function() {
							@Override public double evaluate(double x) {

								return zFunction.getValue(u, x);
							}

							@Override public boolean isBounded() {

								return true;
							}

							@Override public double leftBound() {

								return vBound.getLower();
							}

							@Override public double rightBound() {

								return vBound.getUpper();
							}
						};
						return differentiator.differentiate(diff, v);
					}
				};

				double cross1 = diffYU.getValue(u, v) * diffZV.getValue(u, v) - diffZU.getValue(u, v) * diffYV.getValue(u, v);
				double cross2 = diffZU.getValue(u, v) * diffXV.getValue(u, v) - diffXU.getValue(u, v) * diffZV.getValue(u, v);
				double cross3 = diffXU.getValue(u, v) * diffYV.getValue(u, v) - diffYU.getValue(u, v) * diffXV.getValue(u, v);

				return Math.sqrt(Math.pow(cross1, 2) + Math.pow(cross2, 2) + Math.pow(cross3, 2));
			}
		};
		MultiIntegrator integrator = new MultiSimpsonsRule();
		int uSteps = (int)(uBound.getUpper()-uBound.getLower())*10;
		if (uSteps % 2 != 0) {
			uSteps++;
		}
		int vSteps = (int) (vBound.getUpper() - vBound.getLower())*10;
		if (vSteps % 2 != 0) {
			vSteps++;
		}

		return integrator.integrate(crossProduct, uBound, vBound, uSteps, vSteps);
	}

}
