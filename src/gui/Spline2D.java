package gui;

import splines.Spline;

import java.awt.*;

/**
 * Author: Fabian Fränz <f.fraenz@t-online.de>
 * Date: 27/04/14
 * Project: CAD
 * Version: 1.0
 * Description: Class that encapsulates a spline and properties which are used for rendering the spline.
 */
public class Spline2D {
	private Spline spline;
	private Color color;
	private double thickness;
	private SplineType type;

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

	public boolean isClosed() {

		return spline.get(0).getX() == spline.get(spline.size()-1).getX() && spline.get(0).getY() == spline.get(spline.size() - 1).getY();
	}
}
