package gui;

import splines.Point;
import splines.Spline;

import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;

/**
 * Created by fabian on 19/04/14.
 */
public class SplineRenderer {

	public static final int STEPS = 10;
	private static final double KNOT_SIZE = 10;
	public static final int HANDLE_SIZE = 1;
	public static final int HANDLE_ALPHA = 175;
	private final java.awt.Graphics2D context;

    protected SplineRenderer(Graphics2D context) {
        this.context = context;
    }

    public void renderSplineAtPosition(Spline spline, double x, double y, boolean displayControlPoints) {
        context.translate(x,y);

		Point leftPoint = spline.get(0);
		if (displayControlPoints) {
			Ellipse2D.Double knot =
					new Ellipse2D.Double(leftPoint.getX() - KNOT_SIZE / 2,
										 leftPoint.getY() - KNOT_SIZE / 2,
										 KNOT_SIZE,
										 KNOT_SIZE);
			context.fill(knot);
		}
		if (spline.size() == 1) {
			Line2D.Double lineSegment = new Line2D.Double(new Point2D.Double(leftPoint.getX(), leftPoint.getY()),
														  new Point2D.Double(leftPoint.getX(),
																			 leftPoint.getY()));
			context.draw(lineSegment);
		} else {
			int totalSteps = (spline.size() - 1) * STEPS;
			for (int s = 1; s <= totalSteps; s++) {
				double u = (double) s / (double) STEPS;

				Point rightPoint = spline.s(u);
				Line2D.Double lineSegment = new Line2D.Double(new Point2D.Double(leftPoint.getX(), leftPoint.getY()),
															  new Point2D.Double(rightPoint.getX(), rightPoint.getY()));
				context.draw(lineSegment);
				int flooredU = (int) Math.floor(u);
				boolean isControlPoint = (u == flooredU);
				if (isControlPoint && displayControlPoints) {
					Point controlPoint = spline.get(flooredU);
					Line2D.Double handle = new Line2D.Double(new Point2D.Double(spline.get(flooredU - 1).getX(),
																  spline.get(flooredU - 1).getY()),
											   new Point2D.Double(controlPoint.getX(), controlPoint.getY())
					);
					Ellipse2D.Double knot =
							new Ellipse2D.Double(controlPoint.getX() - KNOT_SIZE / 2,
												 controlPoint.getY() - KNOT_SIZE / 2,
												 KNOT_SIZE,
												 KNOT_SIZE);
					Stroke stroke = context.getStroke();
					Color color = context.getColor();
					context.setStroke(new BasicStroke(HANDLE_SIZE));
					context.setColor(new Color(color.getRed(), color.getGreen(), color.getBlue(), HANDLE_ALPHA));
					context.draw(handle);
					context.setStroke(stroke);
					context.setColor(color);
					context.fill(knot);
				}
				leftPoint = rightPoint;
			}
		}

		context.translate(-x,-y);
    }
}
