package gui.tools.draw;

import gui.DoublePoint;
import gui.Spline2D;
import gui.SplineType;
import gui.tools.Tool;
import gui.tools.ToolConstants;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;

/**
 * Author: Fabian Fränz <f.fraenz@t-online.de>
 * Date: 07/05/14
 * Project: CAD
 * Version: 1.0
 * Description:
 */
public class SplineDrawer extends Tool {

	protected static final Color[] COLORS = { Color.WHITE, Color.BLUE, Color.MAGENTA, Color.ORANGE, Color.PINK, Color.YELLOW };
	protected static int colorCounter = 0;

	protected Spline2D currentSpline = null;
	protected boolean splineIsFinished = true;
	protected SplineType currentSplineType;
	protected Shape splineStartIndicator = null;

	private SplineDrawer() {

	}

	public SplineDrawer(SplineType type) {
		super();
		currentSplineType = type;
	}

	@Override public void mousePressed(MouseEvent e) {

		super.mousePressed(e);
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
					started();
				}
				if (show && !first) {
					currentSpline.getSpline().add(new DoublePoint(currentSpline.getSpline().get(0)));
					currentSpline.setClosed(true);
					currentSpline = null;
				} else {
					currentSpline.getSpline().add(new DoublePoint(e.getPoint()));
				}
				updated();
			} else if (e.getButton() == MouseEvent.BUTTON3) {
				splineIsFinished = true;
				finished();
			}
		}
	}

	protected boolean showIndicator(MouseEvent e) {

		if (currentSpline != null && !currentSpline.getSpline().isEmpty() && Math.abs(e.getX() - currentSpline.getSpline().get(0).getX()) < ToolConstants.SELECT_SENSITIVITY
			&& Math.abs(e.getY() - currentSpline.getSpline().get(0).getY()) < ToolConstants.SELECT_SENSITIVITY) {
			if (splineStartIndicator == null) {
				splineStartIndicator =
						new Ellipse2D.Double(currentSpline.getSpline().get(0).getX() - ToolConstants.INDICATOR_SIZE / 2,
											 currentSpline.getSpline().get(0).getY() - ToolConstants.INDICATOR_SIZE / 2,
											 ToolConstants.INDICATOR_SIZE,
											 ToolConstants.INDICATOR_SIZE);
			}
			return true;
		} else if (splineStartIndicator != null) {
			splineStartIndicator = null;
		}
		return false;
	}

	@Override public void mouseReleased(MouseEvent e) {

		super.mouseReleased(e);
		if (isHandlingEvents()) {
			if (showIndicator(e) && currentSpline != null && currentSpline.getSpline().size() > 1) {
				currentSpline.setClosed(true);
				splineIsFinished = true;
				finished();
			}
		}
	}

	@Override public void mouseDragged(MouseEvent e) {

		super.mouseDragged(e);
		if (isHandlingEvents()) {

		}
	}

	@Override public void mouseMoved(MouseEvent e) {

		super.mouseMoved(e);
		if (isHandlingEvents()) {
			showIndicator(e);
		}
	}

	@Override public void draw(Graphics2D g2) {

		super.draw(g2);
		if (shouldDraw()) {
			if (splineStartIndicator != null) {
				g2.setStroke(ToolConstants.INDICATOR_STROKE);
				g2.setColor(currentSpline.getColor());
				g2.draw(splineStartIndicator);
			}
		}
	}

	public Spline2D getCurrentSpline() {

		return currentSpline;
	}

	public void finish() {

		splineIsFinished = true;
		finished();
	}
}
