package splines;

/**
 * Created by fabian on 19/04/14.
 */
public class Interval {
    private final double leftBound;
    private final double rightBound;

    public Interval(double leftBound, double rightBound) {
        this.leftBound = leftBound;
        this.rightBound = rightBound;
    }

    public double getLeftBound() {
        return leftBound;
    }

    public double getRightBound() {
        return rightBound;
    }
}
