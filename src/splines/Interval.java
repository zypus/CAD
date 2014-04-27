package splines;

/**
 * Created by fabian on 19/04/14.
 */
public class Interval {
    private final double leftBound;
    private final double rightBound;
    private final double lowerBound;
    private final double upperBound;

    public Interval(double leftBound, double rightBound, double lowerBound, double upperBound) {
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

	public boolean contains(Point point) {
		return point.getX() >= leftBound && point.getX() <= rightBound && point.getY() >= lowerBound && point.getY() <= upperBound;
	}

	public boolean contains(Point point, double accuracy) {

		return point.getX() >= leftBound-accuracy && point.getX() <= rightBound+ accuracy && point.getY() >= lowerBound- accuracy && point.getY() <= upperBound+ accuracy;
	}

	@Override public String toString() {

		return "Interval{" +
			   "leftBound=" + leftBound +
			   ", rightBound=" + rightBound +
			   ", lowerBound=" + lowerBound +
			   ", upperBound=" + upperBound +
			   '}';
	}

    public double getXSpan() {
    	return rightBound - leftBound;
    }

    public double getYSpan() {
    	return upperBound - lowerBound;
    }

}
