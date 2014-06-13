package surface;

import org.scilab.forge.scirenderer.Canvas;
import org.scilab.forge.scirenderer.shapes.geometry.DefaultGeometry;

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

	List<List<Point3d>> controls = new ArrayList<>();
	List<Double> uKnots = new ArrayList<>();
	List<Double> vKnots = new ArrayList<>();
	int uOrder = 3;
	int vOrder = 3;
	boolean changed = true;
	Triangles triangles;

	public NURBSSurface(List<List<Point3d>> controls, List<Double> uKnots, List<Double> vKnots) {

		this.controls = controls;
		this.uKnots = uKnots;
		this.vKnots = vKnots;
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

		return 1;
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
			int uSteps = 50;
			int vSteps = 50;

			List<Point3d> vertices = new ArrayList<>();
			List<Integer> indices = new ArrayList<>();

			for (int ui = 0; ui <= uSteps; ui++) {
				double u = getMinU() + (getMaxU() - getMinU()) * (double) ui / (double) uSteps;
				for (int vi = 0; vi <= vSteps; vi++) {
					double v = getMinV() + (getMaxV() - getMinU()) * (double) vi / (double) vSteps;
					Point3d point3d = s(u, v);
					vertices.add(point3d);
					if (ui != uSteps) {
						if (vi != 0) {
							indices.add(ui * vSteps + vi);
							indices.add((ui + 1) * vSteps + vi - 1);
							indices.add(ui * vSteps + vi - 1);
							indices.add(ui * vSteps + vi);
							indices.add((ui + 1) * vSteps + vi);
							indices.add((ui + 1) * vSteps + vi - 1);
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

	@Override public void replacePoint(Point3d point3d, Point3d otherPoint3d) {

		System.out.println("Replacing");
		for (List<Point3d> list : controls) {
			if (list.contains(point3d)) {
				list.set(list.indexOf(point3d), otherPoint3d);
				changed = true;
				notifyObservers();
				break;
			}
		}

	}

	@Override public void setAllPoints(List<Point3d> points) {

		changed = true;
		notifyObservers();
	}

	@Override public List<Point3d> getAllPoints() {

		List<Point3d> points = new ArrayList<>();
		for (List<Point3d> list : controls) {
			points.addAll(list);
		}
		return points;
	}
}
