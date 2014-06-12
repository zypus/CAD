package surface;

/**
 * Author: Fabian Fränz <f.fraenz@t-online.de>
 * Date: 10/06/14
 * Project: CAD
 * Version: 1.0
 * Description: TODO Add description.
 */
public class TriangulationAlgorithm {

	// Delaunay
//	public Triangles triangulate(List<Point3d> points) {
//
//	}

	private void legaliceEdge(Point3d pr, Point3d pi, Point3d pj, Triangles T) {

	}

	private boolean isLegal(Point3d pi, Point3d pj, Triangles T) {

		int i = T.getVertices().indexOf(pi);
		int j = T.getVertices().indexOf(pj);
		int l = -1;
		int k = -1;
		for (int t = 0; t < T.getIndices().size(); t++) {
			int found = -1;
			int index = T.getIndices().get(t);
			if (index == i) {
				if (t % 3 == 0) {
					if (T.getIndices().get(t+1) == j) {
						found = T.getIndices().get(t + 2);
					} else if (T.getIndices().get(t+2) == j) {
						found = T.getIndices().get(t + 1);
					}
				} else if (t % 2 == 0) {
					if (T.getIndices().get(t -1) == j) {
						found = T.getIndices().get(t - 2);
					} else if ( T.getIndices().get(t - 2) == j) {
						found = T.getIndices().get(t - 1);
					}
				} else {
					if (T.getIndices().get(t - 1) == j) {
						found = T.getIndices().get(t + 1);
					} else if (T.getIndices().get(t + 1) == j) {
						found = T.getIndices().get(t - 1);
					}
				}
				if (found != -1) {
					if (l == -1) {
						l = T.getIndices().get(t + 2);
					} else {
						k = T.getIndices().get(t + 2);
					}
				}

			}
		}


		return false;
	}

	private Point3d circle(Point3d p1, Point3d p2, Point3d p3) {

		Point3d mid1 = p1.add(p2.sub(p1));
		Point3d mid2 = p2.add(p3.sub(p2));
		Point3d n1 = p2.sub(p1).cross(new Point3d(0, 0, 1));
		Point3d n2 = p3.sub(p2).cross(new Point3d(0, 0, 1));


		return null;
	}

}
