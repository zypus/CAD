package surface;

import org.scilab.forge.scirenderer.Canvas;
import org.scilab.forge.scirenderer.shapes.geometry.DefaultGeometry;
import util.Bound;
import util.Function;
import util.ParametricFunction;
import util.differentiation.singleParameter.Differentiator;
import util.differentiation.singleParameter.PointDifference;
import util.integration.multiParameter.MultiIntegrator;
import util.integration.multiParameter.MultiSimpsonsRule;

import java.util.ArrayList;
import java.util.List;

/**
 * Author: Fabian Fränz <f.fraenz@t-online.de>
 * Date: 11/06/14
 * Project: CAD
 * Version: 1.0
 * Description: TODO Add description.
 */
public class NURBSSurface extends Solid {

	List<List<HomogeneousPoint3d>> controls = new ArrayList<>();
	List<Double> uKnots = new ArrayList<>();
	List<Double> vKnots = new ArrayList<>();
	int uOrder = 3;
	int vOrder = 3;
	Triangles triangles;

	public NURBSSurface(List<List<HomogeneousPoint3d>> controls, List<Double> uKnots, List<Double> vKnots) {

		this.controls = controls;
		this.uKnots = uKnots;
		this.vKnots = vKnots;
	}

	public NURBSSurface(List<List<HomogeneousPoint3d>> controls, List<Double> uKnots, List<Double> vKnots, int order) {

		this(controls, uKnots, vKnots, order, order);
	}

	public NURBSSurface(List<List<HomogeneousPoint3d>> controls, List<Double> uKnots, List<Double> vKnots, int uOrder, int vOrder) {

		this(controls, uKnots, vKnots);
		this.uOrder = uOrder;
		this.vOrder = vOrder;
	}

	public Point3d s(double u, double v) {

		double k = controls.size();
		double l = controls.get(0).size();
		Point3d sum = new Point3d();
		for (int i = 0; i < k; i++) {
			for (int j = 0; j < l; j++) {
				double r = R(i, j, uOrder, vOrder, u, v);
				sum = sum.add(controls.get(i).get(j).scalarMult(r));
			}
		}


		return sum;
	}


	private double R(int i, int j, int n, int m, double u, double v) {

		double k = controls.size();
		double l = controls.get(0).size();
		double sum = 0;
		for (int p = 0; p < k; p++) {
			for (int q = 0; q < l; q++) {
				sum += N(p, n, u, uKnots) * N(q, m, v, vKnots) * w(p, q);
			}
		}
		return (N(i,n,u, uKnots) * N(j,m,v,vKnots) * w(i,j)) / sum;
	}

	private double N(int i, int n, double u, List<Double> knots) {

		if (n == 0) {
			if (u >= knots.get(i) && u < knots.get(i + 1)) {
				return 1;
			} else {
				return 0;
			}
		}
		return f(i, n, u, knots) * N(i, n - 1, u, knots) + g(i + 1, n, u, knots) * N(i + 1, n - 1, u, knots);
	}

	private double w(int i, int j) {

		return controls.get(i).get(j).w;
	}

	private double f(int i, int n, double u, List<Double> knots) {

		Double k = knots.get(i);
		double den = knots.get(i + n) - k;
		if (den == 0) {
			return u;
		}
		return (u - k) / den;
	}

	private double g(int i, int n, double u, List<Double> knots) {

		Double k = knots.get(i+n);

		double den = k - knots.get(i);
		if (den == 0) {
			return u;
		}
		return (k - u) / den;
	}

	public double getMinU() {

		return uKnots.get(0);
	}

	public double getMaxU() {

		return uKnots.get(uKnots.size() - 1);
	}

	public double getMinV() {

		return vKnots.get(0);
	}

	public double getMaxV() {

		return vKnots.get(vKnots.size() - 1);
	}

	@Override public List<DefaultGeometry> createGeometry(Canvas canvas) {

		List<DefaultGeometry> geometries = new ArrayList<>();
		geometries.add(triangulation(canvas, createTriangles()));

		return geometries;
	}

	public Triangles createTriangles() {

		if (changed) {

			List<Point3d> vertices = new ArrayList<>();
			List<Integer> indices = new ArrayList<>();

			for (int ui = 0; ui <= uSteps; ui++) {
				double u = (ui == uSteps) ? getMaxU()-0.00001 : getMinU() + 0.00001 + (getMaxU() - getMinU()) * (double) ui / (double) uSteps;
				for (int vi = 0; vi <= vSteps; vi++) {
					double v = (vi == vSteps) ? getMaxV()-0.00001 : getMinV() + 0.00001 + (getMaxV() - getMinV()) * (double) vi / (double) vSteps;
					Point3d point3d = s(u, v);
					vertices.add(point3d);
					if (ui != uSteps) {
						if (vi != 0) {
							indices.add(ui * (vSteps+1) + vi);
							indices.add(ui * (vSteps+1) + vi - 1);
							indices.add((ui + 1) * (vSteps+1) + vi - 1);
							indices.add(ui * (vSteps+1) + vi);
							indices.add((ui + 1) * (vSteps+1) + vi - 1);
							indices.add((ui + 1) * (vSteps+1) + vi);
						}
					}
				}
			}
			triangles = new Triangles(vertices, indices);
			changed = false;
		}

		return triangles;
	}

	@Override public double getArea() {

		return createTriangles().getArea();
	}

	@Override public double getVolume() {

		return createTriangles().getSignedVolume();
	}

	@Override public void addPoint(Point3d point3d) {

		changed = true;
		notifyObservers();
	}

	@Override public void removePoint(Point3d point3d) {

		changed = true;
		notifyObservers();
	}

	@Override public Point3d replacePoint(Point3d point3d, Point3d otherPoint3d) {

		otherPoint3d = new HomogeneousPoint3d(otherPoint3d);
		for (List<HomogeneousPoint3d> list : controls) {
			if (list.contains(point3d)) {
				list.set(list.indexOf(point3d), (HomogeneousPoint3d)otherPoint3d);
				changed = true;
				notifyObservers();
				return otherPoint3d;
			}
		}
		return null;
	}

	@Override public void setAllPoints(List<Point3d> points) {

		changed = true;
		notifyObservers();
	}

	@Override public List<Point3d> getAllPoints() {

		List<Point3d> points = new ArrayList<>();
		for (List<HomogeneousPoint3d> list : controls) {
			points.addAll(list);
		}
		return points;
	}

	public double getAreaUsingIntegration() {

		ParametricFunction crossProduct = new ParametricFunction() {
			@Override public double getValue(double u, double v) {

				final double h = 0.1;

				ParametricFunction diffXU = new ParametricFunction() {
					@Override public double getValue(double u, final double v) {

						Differentiator differentiator = new PointDifference(h);
						Function diff = new Function() {
							@Override public double evaluate(double x) {

								return s(x, v).getX();
							}

							@Override public boolean isBounded() {

								return true;
							}

							@Override public double leftBound() {

								return getMinU();
							}

							@Override public double rightBound() {

								return getMaxU();
							}
						};
						return differentiator.differentiate(diff, u);
					}
				};
				ParametricFunction diffYU = new ParametricFunction() {
					@Override public double getValue(double u, final double v) {

						Differentiator differentiator = new PointDifference(h);
						Function diff = new Function() {
							@Override public double evaluate(double x) {

								return s(x, v).getY();
							}

							@Override public boolean isBounded() {

								return true;
							}

							@Override public double leftBound() {

								return getMinU();
							}

							@Override public double rightBound() {

								return getMaxU();
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

								return s(x, v).getZ();
							}

							@Override public boolean isBounded() {

								return true;
							}

							@Override public double leftBound() {

								return getMinU();
							}

							@Override public double rightBound() {

								return getMaxU();
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

								return s(u, x).getX();
							}

							@Override public boolean isBounded() {

								return true;
							}

							@Override public double leftBound() {

								return getMinV();
							}

							@Override public double rightBound() {

								return getMaxV();
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

								return s(u, x).getY();
							}

							@Override public boolean isBounded() {

								return true;
							}

							@Override public double leftBound() {

								return getMinV();
							}

							@Override public double rightBound() {

								return getMaxV();
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

								return s(u, x).getZ();
							}

							@Override public boolean isBounded() {

								return true;
							}

							@Override public double leftBound() {

								return getMinV();
							}

							@Override public double rightBound() {

								return getMaxV();
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
		int us = (int) (getMaxU() - getMinU());
		if (us % 2 != 0) {
			us++;
		}
		int vs = (int) (getMaxV() - getMinV());
		if (vs % 2 != 0) {
			vs++;
		}

		return integrator.integrate(crossProduct, new Bound(getMinU(), getMaxU()), new Bound(getMinV(), getMaxV()), us, vs);
	}

	public List<List<HomogeneousPoint3d>> getControls() {

		return controls;
	}

	public List<Double> getuKnots() {

		return uKnots;
	}

	public List<Double> getvKnots() {

		return vKnots;
	}

	public int getuOrder() {

		return uOrder;
	}

	public int getvOrder() {

		return vOrder;
	}
}
