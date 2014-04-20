package intersection;


import java.util.ArrayList;
import java.util.List;

public class Point {

	private float x;
	private float y;
	private Segment owner;
	boolean start;
	boolean end;
	List<Segment> intersections = new ArrayList<Segment>();

	public boolean isStart() {
		return start;
	}

	public void setStart(boolean start) {
		this.start = start;
	}

	public boolean isEnd() {
		return end;
	}

	public void setEnd(boolean end) {
		this.end = end;
	}

	public List<Segment> getIntersections() {
		return intersections;
	}

	public Segment getIntersections(int i) {
		return intersections.get(i);
	}

	public void setIntersections(List<Segment> intersections) {
		this.intersections = intersections;
	}

	public Point(float x, float y) {
		this.x = x;
		this.y = y;
		this.owner = null;
	}

	public Point(double x, double y) {
		this.x = (float) x;
		this.y = (float) y;
		this.owner = null;
	}
	
	public Point(Point p) {
		this.x = p.getX();
		this.y = p.getY();
		this.owner = null;
	}
	
	public float getX() {
		return x;
	}

	public float getY() {
		return y;
	}

	public Segment getOwner() {
		return owner;
	}

	public void setOwner(Segment owner) {
		this.owner = owner;
	}

	public String toString() {
		return "(" + x + "," + y + ")";
	}

	public Point swap() {
		float temp = x;
		x = y;
		y = temp;
		return this;
	}

	@Override
	public boolean equals(Object obj) {
		Point p = (Point) obj;
		return (x == p.getX() && y == p.getY() && owner == p.getOwner());
	}

}
