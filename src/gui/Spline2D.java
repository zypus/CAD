package gui;

import splines.Point;
import splines.Spline;
import splines.SplineObserver;

import java.awt.*;

/**
 * Author: Fabian Fränz <f.fraenz@t-online.de>
 * Date: 27/04/14
 * Project: CAD
 * Version: 1.0
 * Description: Class that encapsulates a spline and properties which are used for rendering the spline.
 */
public class Spline2D implements SplineObserver {
	private Spline spline;
	private Color color;
	private double thickness;
	private SplineType type;
	private java.util.List<Point> interpolatedPoints = null;
	private int interpolationDensity = 10;
	private boolean closed = false;
	private boolean changed = true;

	public Spline2D(Spline spline, SplineType type) {

		this.spline = spline;
		this.type = type;
	}

	public Spline getSpline() {

		return spline;
	}

	public Color getColor() {

		return color;
	}

	public void setColor(Color color) {

		this.color = color;
	}

	public double getThickness() {

		return thickness;
	}

	public void setThickness(double thickness) {

		this.thickness = thickness;
	}

	public SplineType getType() {

		return type;
	}

	public void setClosed(boolean closed) {

		boolean isReallyClosed = ( spline.get(0).getX() == spline.get(spline.size() - 1).getX() && spline.get(0).getY() == spline.get(spline.size() - 1).getY() );
		this.closed = closed && isReallyClosed;
	}

	public boolean isClosed() {

		return closed;
	}

	public java.util.List<Point> getInterpolatedPoints() {

		if (interpolatedPoints == null || changed) {
			int totalSteps = (spline.size() - 1) * interpolationDensity;
			interpolatedPoints.clear();
			for (int s = 0; s <= totalSteps; s++) {
				double u = (double) s / (double) interpolationDensity;
				Point interpolatedPoint = spline.s(u);
				interpolatedPoints.add(interpolatedPoint);
			}
			changed = false;
		}
		return interpolatedPoints;
	}

	public int getInterpolationDensity() {

		return interpolationDensity;
	}

	public void setInterpolationDensity(int interpolationDensity) {

		if (interpolationDensity != this.interpolationDensity) {
			changed = true;
		}
		this.interpolationDensity = interpolationDensity;
	}

	@Override public void observedSplineChanged() {
		changed = true;
	}
}
