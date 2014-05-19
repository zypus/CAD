package gui;

import gui.tools.select.Selectable;
import splines.Point;
import splines.Spline;
import splines.SplineObserver;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Author: Fabian Fränz <f.fraenz@t-online.de>
 * Date: 27/04/14
 * Project: CAD
 * Version: 1.0
 * Description: Class that encapsulates a spline and properties which are used for rendering the spline.
 */
public class Spline2D implements SplineObserver, Selectable {
	private Spline spline;
	private Color color;
	private double thickness;
	private SplineType type;
	private java.util.List<Point> interpolatedPoints = null;
	private int interpolationDensity = 10;
	private boolean closed = false;
	private boolean changed = true;
	SelectionType selectionStatus = SelectionType.UNSELECTED;

	public Spline2D(Spline spline, SplineType type) {

		this.spline = spline;
		this.spline.addObserver(this);
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
			interpolatedPoints = new ArrayList<>();
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

	@Override public List<Point> getSelectablePoints() {

		return getInterpolatedPoints();
	}

	@Override public boolean onlySelectableOnPoints() {

		return false;
	}

	@Override public void setSelectionStatus(SelectionType selected) {
		selectionStatus = selected;
	}

	@Override public SelectionType getSelectionStatus() {

		return selectionStatus;
	}

	public Rectangle.Double getBoundingBox() {

		double minX = 999999;
		double minY = 999999;
		double maxX = 0;
		double maxY = 0;
		for (int i = 0; i < getInterpolatedPoints().size(); i++) {
			Point p = getInterpolatedPoints().get(i);
			if (p.getX() < minX) {
				minX = p.getX();
			}if (p.getY() < minY) {
				minY = p.getY();

			}if (p.getX() > maxX) {
				maxX = p.getX();
			}if (p.getY() > maxY) {
				maxY = p.getY();
			}
		}
		return new Rectangle.Double(minX, minY, maxX - minX, maxY - minY);
	}
}
