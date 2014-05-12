package gui.tools.draw;

import gui.DoublePoint;
import gui.Spline2D;
import gui.SplineType;
import splines.Spline;

import java.awt.event.MouseEvent;
import java.awt.geom.Point2D;

/**
 * Author: Fabian Fränz <f.fraenz@t-online.de>
 * Date: 07/05/14
 * Project: CAD
 * Version: 1.0
 * Description:
 */
public class BezierSplineDrawer extends SplineDrawer {

	public BezierSplineDrawer() {

		super(SplineType.BEZIER);
	}

	@Override public void mousePressed(MouseEvent e) {

		if (isHandlingEvents()) {
			if (e.getButton() == MouseEvent.BUTTON1) {
				Point2D p;
				boolean show = showIndicator(e);
				boolean first = false;
				if (splineIsFinished) {
					currentSpline = new Spline2D(currentSplineType.createInstance(), currentSplineType);
					currentSpline.setColor(COLORS[colorCounter]);
					colorCounter = (colorCounter + 1) % COLORS.length;
					first = true;
					splineIsFinished = false;
				}
				if (show && !first) {
					addPoint(new DoublePoint(currentSpline.getSpline().get(0)));
					currentSpline.setClosed(true);
					currentSpline = null;
				} else if (first) {
					currentSpline.getSpline().add(new DoublePoint(e.getPoint()));
					started();
			    } else {
					addPoint(new DoublePoint(e.getPoint()));
				}
				updated();
			} else if (e.getButton() == MouseEvent.BUTTON3) {
				splineIsFinished = true;
				finished();
			}
		}
	}

	private void addPoint(DoublePoint rightPoint) {

		Spline spline = currentSpline.getSpline();
		DoublePoint leftPoint = (DoublePoint)spline.get(spline.size()-1);
		double difX = rightPoint.getX()-leftPoint.getX();
		double difY = rightPoint.getY()-leftPoint.getY();
		DoublePoint firstControlPoint = new DoublePoint(leftPoint.getX()+0.33*difX, leftPoint.getY()+0.33*difY);
		DoublePoint secondControlPoint = new DoublePoint(leftPoint.getX()+0.66*difX, leftPoint.getY()+0.66*difY);
		spline.add(firstControlPoint);
		spline.add(secondControlPoint);
		spline.add(rightPoint);
	}

	public Spline2D getCurrentSpline() {

		return currentSpline;
	}

}
