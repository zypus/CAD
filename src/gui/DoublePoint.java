package gui;

import gui.tools.select.Selectable;
import splines.Point;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

/**
 * Author: Fabian Fränz <f.fraenz@t-online.de>
 * Date: 27/04/14
 * Project: CAD
 * Version: 1.0
 * Description: Class that wraps the Point2D.Double class and provides the interface of spline.Point.
 */
public class DoublePoint extends Point2D.Double implements Point, Selectable {

	SelectionType selectionStatus = SelectionType.UNSELECTED;

	public DoublePoint(double x, double y) {
		super(x, y);
	}

	public DoublePoint(Point2D point) {
		super(point.getX(),point.getY());
	}

	public DoublePoint(java.awt.Point point) {
		super(point.getX(), point.getY());
	}

	public DoublePoint(Point point) {

		super(point.getX(), point.getY());
	}

	public double distance(Point p2) {

		return super.distance(p2.getX(), p2.getY());
	}

	@Override public Point manipulate(double z) {
		return new DoublePoint(getX()*z,getY()*z);
	}

	@Override public Point addValue(Point p) {
		return new DoublePoint(getX()+p.getX(),getY()+p.getY());
	}

	@Override public Point createPoint(double x, double y) {
		return new DoublePoint(x,y);
	}

	@Override public Point sub(Point p) {

		return new DoublePoint(getX()-p.getX(),getY()-p.getY());
	}

	@Override public List<Point> getSelectablePoints() {

		List<Point> points = new ArrayList<>();
		points.add(this);
		return points;
	}

	@Override public boolean onlySelectableOnPoints() {

		return true;
	}

	@Override public void setSelectionStatus(SelectionType selected) {
		selectionStatus = selected;
	}

	@Override public SelectionType getSelectionStatus() {

		return selectionStatus;
	}
}
