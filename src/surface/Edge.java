package surface;

/**
 * Author: Fabian Fränz <f.fraenz@t-online.de>
 * Date: 12/06/14
 * Project: CAD
 * Version: 1.0
 * Description: TODO Add description.
 */
public class Edge {

	Point3d start;
	Point3d end;

	public Edge(Point3d start, Point3d end) {

		this.start = start;
		this.end = end;
	}

	public Point3d getStart() {

		return start;
	}

	public Point3d getEnd() {

		return end;
	}
}
