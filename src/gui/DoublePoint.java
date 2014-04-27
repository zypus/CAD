package gui;

import splines.Point;

import java.awt.geom.Point2D;

/**
 * Author: Fabian Fränz <f.fraenz@t-online.de>
 * Date: 27/04/14
 * Project: CAD
 * Version: 1.0
 * Description: Class that wraps the Point2D.Double class and provides the interface of spline.Point.
 */
public class DoublePoint extends Point2D.Double implements Point {

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

	@Override public Point createPoint(double x, double y) {
		return new DoublePoint(x,y);
	}
}
