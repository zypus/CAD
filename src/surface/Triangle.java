package surface;

import java.util.ArrayList;
import java.util.List;

/**
 * Author: Fabian Fränz <f.fraenz@t-online.de>
 * Date: 10/06/14
 * Project: CAD
 * Version: 1.0
 * Description: TODO Add description.
 */
public class Triangle {

	List<Point3d> vertices;

	public Triangle(Point3d v1, Point3d v2, Point3d v3) {

		vertices = new ArrayList<>();
		vertices.add(v1);
		vertices.add(v2);
		vertices.add(v3);
	}

	public boolean contains(Point3d point3d, int ignoreAxis) {

		Point3d p1 = new Point3d();
		Point3d p2 = new Point3d();
		Point3d p3 = new Point3d();
		Point3d p = new Point3d();

		int pos = 0;

		for (int i = 0; i < 3; i++) {
			if (i != ignoreAxis) {
				p1.set(pos, vertices.get(0).get(i));
				p2.set(pos, vertices.get(1).get(i));
				p3.set(pos, vertices.get(2).get(i));
				p.set(pos, point3d.get(i));
			}
		}

		double alpha = ((p2.getY() - p3.getY()) * (p.getX() - p3.getX()) + (p3.getX() - p2.getX()) * (p.getY() - p3.getY())) /
					   ((p2.getY() - p3.getY()) * (p1.getX() - p3.getX()) + (p3.getX() - p2.getX()) * (p1.getY() - p3.getY()));
		double beta = ((p3.getY() - p1.getY()) * (p.getX() - p3.getX()) + (p1.getX() - p3.getX()) * (p.getY() - p3.getY())) /
					  ((p2.getY() - p3.getY()) * (p1.getX() - p3.getX()) + (p3.getX() - p2.getX()) * (p1.getY() - p3.getY()));
		double gamma = 1.0f - alpha - beta;

		return alpha > 0 && beta > 0 && gamma > 0;
	}

}
