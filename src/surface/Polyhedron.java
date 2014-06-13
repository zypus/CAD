package surface;

import org.scilab.forge.scirenderer.Canvas;
import org.scilab.forge.scirenderer.shapes.geometry.DefaultGeometry;

import java.util.ArrayList;
import java.util.List;

/**
 * Author: Fabian Fränz <f.fraenz@t-online.de>
 * Date: 03/06/14
 * Project: CAD
 * Version: 1.0
 * Description: TODO Add description.
 */
public class Polyhedron
		extends Solid {

	private List<Point3d> points;
	private Triangles triangles;
	private DefaultGeometry geometry;
	private boolean changed = true;

	private static final int ELEMENT_SIZE = 4;

	@Override public List<DefaultGeometry> createGeometry(Canvas canvas) {

		if (changed) {
			triangles = new ChansAlgorithm().computeConvexHull(points);
			changed = false;
		}
		List<DefaultGeometry> geometries = new ArrayList<>();
		geometries.add(triangulation(canvas, triangles));

		return geometries;
	}

	@Override public double getArea() {

		return 0;
	}

	@Override public double getVolume() {

		return 0;
	}

	@Override public void addPoint(Point3d point3d) {

		points.add(point3d);
		changed = true;
		notifyObservers();
	}

	@Override public void removePoint(Point3d point3d) {

		points.remove(point3d);
		changed = true;
		notifyObservers();
	}

	@Override public void replacePoint(Point3d point3d, Point3d otherPoint3d) {

		points.set(points.indexOf(point3d), otherPoint3d);
		changed = true;
		notifyObservers();
	}

	@Override public void setAllPoints(List<Point3d> points) {

		this.points = points;
		changed = true;
		notifyObservers();
	}

	@Override public List<Point3d> getAllPoints() {

		return points;
	}
}
