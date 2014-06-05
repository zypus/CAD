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
}
