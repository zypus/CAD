package intersection;


public class Segment {

	private int sb = 0;
	private Point leftPoint;
	private Point rightPoint;
	private float m;
	private float n;
	private boolean vertical;
	private Point start;
	private Point end;
	private boolean reversed = false;

	public boolean isVertical() {
		return vertical;
	}

	public Point getFirstPoint() {
		if (reversed) {
			return rightPoint;
		}
		else {
			return leftPoint;
		}
	}
	
	public Point getSecondPoint() {
		if (reversed) {
			return leftPoint;
		}
		else {
			return rightPoint;
		}
	}
	
	public Segment(Point firstPoint, Point secondPoint) {
		start = firstPoint;
		end = secondPoint;
		if (firstPoint.getX() < secondPoint.getX()) {
			leftPoint = firstPoint;
			rightPoint = secondPoint;
		} else if (firstPoint.getX() == secondPoint.getX()) {
			if (firstPoint.getY() >= secondPoint.getY()) {
				leftPoint = firstPoint;
				rightPoint = secondPoint;
			} else {
				leftPoint = secondPoint;
				rightPoint = firstPoint;
				reversed = true;
			}
		} else {
			leftPoint = secondPoint;
			rightPoint = firstPoint;
			reversed = true;
		}
		leftPoint.setOwner(this);
		rightPoint.setOwner(this);
		float denominator = rightPoint.getX() - leftPoint.getX();
		if (denominator != 0) {
			float nominator = rightPoint.getY() - leftPoint.getY();
			m = nominator / denominator;
			n = leftPoint.getY() - m * leftPoint.getX();
			vertical = false;
		} else {
			vertical = true;
		}
		leftPoint.setStart(true);
		rightPoint.setEnd(true);
	}

	public boolean isIntersecting(Segment segment) {
		return (getIntersection(segment) != null);
	}

	public boolean isIntersectingWithinBounds(Segment segment, float b, float e) {
		Point intersection = getIntersection(segment);
		return (intersection != null && intersection.getX() >= b && intersection.getX() <= e);
	}

	private static final float smallFloat = 0.01f;

	public Point getIntersection(Segment segment) {
		if (segment == null) {
			return null;
		}
		float x1 = leftPoint.getX();
		float y1 = leftPoint.getY();
		float x2 = rightPoint.getX();
		float y2 = rightPoint.getY();
		float x3 = segment.getLeftPoint()
							.getX();
		float y3 = segment.getLeftPoint()
							.getY();
		float x4 = segment.getRightPoint()
							.getX();
		float y4 = segment.getRightPoint()
							.getY();
		float numeratorX = (x1 * y2 - y1 * x2) * (x3 - x4) - (x1 - x2) * (x3 * y4 - y3 * x4);
		float numeratorY = (x1 * y2 - y1 * x2) * (y3 - y4) - (y1 - y2) * (x3 * y4 - y3 * x4);
		float denominator = (x1 - x2) * (y3 - y4) - (y1 - y2) * (x3 - x4);
		if (Math.abs(denominator) <= smallFloat) {
			return null;
		} else {
			Point intersection = new Point(numeratorX / denominator, numeratorY / denominator);
			if (contains(intersection) && segment.contains(intersection)) {
				return intersection;
			} else {
				return null;
			}
		}
	}

	public boolean isAtXBelow(float x, Segment segment) {
		if (x < leftPoint.getX() || x > rightPoint.getX()) {
			return true;
		}
		if (vertical && segment.isVertical()) {
			return false;
		}
		if (vertical) {
			if (leftPoint.getY() < segment.linearFunction(x)) {
				return true;
			} else {
				return false;
			}
		}
		if (segment.isVertical()) {
			if (segment.getRightPoint()
						.getY() > linearFunction(x)) {
				return true;
			} else {
				return false;
			}
		}
		return (linearFunction(x) < segment.linearFunction(x));
	}

	public boolean isSpanning(float b, float e) {
		return (leftPoint.getX() <= b && rightPoint.getX() >= e);
	}

	public Point getLeftPoint() {
		return leftPoint;
	}

	public Point getRightPoint() {
		return rightPoint;
	}

	public void setSB(Integer sb) {
		this.sb = sb;
	}

	public int getSB() {
		return sb;
	}

	public boolean isInner(float b, float e) {
		return (leftPoint.getX() > b && rightPoint.getX() < e);
	}

	public Point getStart(){
		return start;
	}
	
	public Point getEnd(){
		return end;
	}
	public float linearFunction(float x) {
		return m * x + n;
	}

	public boolean contains(Point point) {
		if (vertical) {
			return (Math.abs(point.getX() - leftPoint.getX()) <= 0.0001 && point.getY() > rightPoint.getY()+0.0001 && point.getY() < leftPoint.getY()-smallFloat);
		} else {
			System.out.println(point.getY() +" "+ linearFunction(point.getX()));
			System.out.println(leftPoint.getX() +" "+ point.getX() +" "+rightPoint.getX());
			if (point.getX() > leftPoint.getX()+0.0001 && point.getX() < rightPoint.getX()-0.0001) {
				System.out.println("Reached");
				return Math.abs(point.getY() - linearFunction(point.getX())) <= smallFloat;
			} else {
				return false;
			}
		}
	}

	public String toString() {
		return "{ " + leftPoint.toString() + " , " + rightPoint.toString() + " }";
	}
}
