package surface;

import org.scilab.forge.scirenderer.Canvas;
import org.scilab.forge.scirenderer.shapes.geometry.DefaultGeometry;

import java.util.List;

/**
 * Author: Fabian Fränz <f.fraenz@t-online.de>
 * Date: 03/06/14
 * Project: CAD
 * Version: 1.0
 * Description: TODO Add description.
 */
public class Triangulation
		extends Solid {

	@Override public double getArea() {

		return 0;
	}

	@Override public double getVolume() {

		return 0;
	}

	@Override public List<DefaultGeometry> createGeometry(Canvas canvas) {

		return null;
	}

	@Override public void addPoint(Point3d point3d) {

	}

	@Override public void removePoint(Point3d point3d) {

	}

	@Override public Point3d replacePoint(Point3d point3d, Point3d otherPoint3d) {

		return null;
	}

	@Override public void setAllPoints(List<Point3d> points) {

	}

	@Override public List<Point3d> getAllPoints() {

		return null;
	}
}
