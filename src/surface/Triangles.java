package surface;

import java.util.List;

/**
 * Author: Fabian Fränz <f.fraenz@t-online.de>
 * Date: 05/06/14
 * Project: CAD
 * Version: 1.0
 * Description: TODO Add description.
 */
public class Triangles {

	private List<Point3d> vertices;
	private List<Integer> indices;

	public Triangles(List<Point3d> vertices, List<Integer> indices) {

		this.vertices = vertices;
		this.indices = indices;
	}

	public List<Point3d> getVertices() {

		return vertices;
	}

	public List<Integer> getIndices() {

		return indices;
	}

	private double areaOfTriangle(Point3d v1, Point3d v2, Point3d v3) {

		Point3d v12 = v2.sub(v1);
		Point3d v13 = v3.sub(v1);
		Point3d cross = v12.cross(v13);

		return 0.5 * cross.norm();
	}

	public double getArea() {

		double area = 0;
		for (int i = 0; i < indices.size(); i += 3) {
			area += areaOfTriangle(vertices.get(indices.get(i)), vertices.get(indices.get(i+1)), vertices.get(indices.get(i+2)));
		}
		return area;
	}

	private double singedVolumeOfTriangle(Point3d v1, Point3d v2, Point3d v3) {

		return v1.dot(v2.cross(v3)) / 6.0;
	}

	public double getSignedVolume() {

		double volume = 0;
		for (int i = 0; i < indices.size(); i+=3) {
			volume += singedVolumeOfTriangle(vertices.get(indices.get(i)), vertices.get(indices.get(i+1)), vertices.get(indices.get(i+2)));
		}
		return volume;
	}


}
