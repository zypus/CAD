package gui;

import splines.Point;
import splines.Spline;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.Stroke;
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

    public SplineRenderer(Graphics2D context) {
        this.context = context;
    }

    public void renderSplineAtPosition(Spline2D spline2d, double x, double y, boolean displayControlPoints) {
        context.translate(x,y);

		Spline spline = spline2d.getSpline();



		if (spline2d.isFilled()) {

			Color fillColor = new Color(spline2d.getColor().getRed(), spline2d.getColor().getGreen(), spline2d.getColor().getBlue(), 80);

			context.setColor(fillColor);

			java.util.List<Point> points = spline2d.getInterpolatedPoints();
			if (spline.isClosed()) {
				Polygon polygon = new Polygon();
				for (Point point : points) {
					polygon.addPoint((int)point.getX(), (int)point.getY());
				}
				context.fill(polygon);
			} else {
				Polygon polygon = new Polygon();
				for (Point point : points) {
					polygon.addPoint((int) point.getX(), (int) point.getY());
				}
				Rectangle.Double rect = spline2d.getBoundingBox();
				polygon.addPoint((int)(rect.getMinX()+rect.getWidth()),(int)rect.getMinY());
				polygon.addPoint((int)(rect.getMinX()),(int)rect.getMinY());
				context.fill(polygon);
			}
		}

		context.setColor(spline2d.getColor());

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
