package intersection;


public class Intersection {

	private Segment firstSegment;
	private Segment secondSegment;

	public Intersection(Segment first, Segment second) {
		firstSegment = first;
		secondSegment = second;
	}

	public Segment getFirstSegment() {
		return firstSegment;
	}

	public void setFirstSegment(Segment firstSegment) {
		this.firstSegment = firstSegment;
	}

	public Segment getSecondSegment() {
		return secondSegment;
	}

	public void setSecondSegment(Segment secondSegment) {
		this.secondSegment = secondSegment;
	}

	public Point getIntersectionPoint() {
		return firstSegment.getIntersection(secondSegment);
	}

	public String toString() {
		return "{ " + firstSegment.toString() + " , " + secondSegment.toString() + " }";
	}

	@Override
	public boolean equals(Object obj) {
		Intersection inter = (Intersection) obj;
		return ((firstSegment == inter.getFirstSegment() && secondSegment == inter.getSecondSegment()) || (secondSegment == inter.getFirstSegment() && firstSegment == inter.getSecondSegment()));
	}
}
