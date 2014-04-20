package splines;

/**
 * Created by fabian on 19/04/14.
 */
public class Interval {
    private final double leftBound;
    private final double rightBound;
    private final double upperBound;
    private final double lowerBound;

    public Interval(double leftBound, double rightBound, double upperBound, double lowerBound) {
        this.leftBound = leftBound;
        this.rightBound = rightBound;
        this.upperBound = upperBound;
        this.lowerBound = lowerBound;
    }

    public double getLeftBound() {
        return leftBound;
    }

    public double getRightBound() {
        return rightBound;
    }

    public double getUpperBound() {
        return upperBound;
    }

    public double getLowerBound() {
        return lowerBound;
    }
}
