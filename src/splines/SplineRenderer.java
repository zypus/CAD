package splines;

import java.awt.*;

/**
 * Created by fabian on 19/04/14.
 */
public abstract class SplineRenderer {
    private final java.awt.Graphics2D context;

    protected SplineRenderer(Graphics2D context) {
        this.context = context;
    }

    public void renderSplineAtPosition(Spline spline, double x, double y) {
        spline.size();
    }
}
