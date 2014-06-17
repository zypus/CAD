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
public class NURBSPatchwork
		extends Solid {

	List<NURBSSurface> surfaces = new ArrayList<>();

	public NURBSPatchwork(List<NURBSSurface> surfaces) {

		this.surfaces = surfaces;
	}

	@Override public double getArea() {

		double area = 0;
		for (NURBSSurface surface : surfaces) {
			area += surface.getArea();
		}
		return area;
	}

	@Override public double getVolume() {

		double volume = 0;
		for (NURBSSurface surface : surfaces) {
			volume += surface.getVolume();
		}
		return volume;
	}

	public double getAreaUsingIntegration() {

		double area = 0;
		for (NURBSSurface surface : surfaces) {
			area += surface.getAreaUsingIntegration();
		}
		return area;
	}

	@Override public List<DefaultGeometry> createGeometry(Canvas canvas) {

		List<DefaultGeometry> geometries = new ArrayList<>();
		for (NURBSSurface surface : surfaces) {
			geometries.add(triangulation(canvas, surface.createTriangles()));
		}
		return geometries;
	}

	@Override public void setChanged() {

		super.setChanged();
		for (NURBSSurface surface : surfaces) {
			surface.setChanged();
		}

	}

	@Override public void addPoint(Point3d point3d) {

	}

	@Override public void removePoint(Point3d point3d) {

	}

	@Override public Point3d replacePoint(Point3d point3d, Point3d otherPoint3d) {

		for (NURBSSurface surface : surfaces) {
			Point3d point3d1 = surface.replacePoint(point3d, otherPoint3d);
			if (point3d1 != null) {
				notifyObservers();
				return point3d1;
			}
		}
		return null;
	}

	@Override public void setAllPoints(List<Point3d> points) {

	}

	@Override public List<Point3d> getAllPoints() {

		List<Point3d> point3ds = new ArrayList<>();
		for (NURBSSurface surface : surfaces) {
			point3ds.addAll(surface.getAllPoints());
		}
		return point3ds;
	}

	@Override public void setvSteps(int vSteps) {

		super.setvSteps(vSteps);
		for (NURBSSurface surface : surfaces) {
			surface.setvSteps(vSteps);
		}
	}

	@Override public void setuSteps(int uSteps) {

		super.setuSteps(uSteps);
		for (NURBSSurface surface : surfaces) {
			surface.setuSteps(uSteps);
		}
	}
}
