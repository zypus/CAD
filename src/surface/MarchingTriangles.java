package surface;

import java.util.ArrayList;
import java.util.List;

/**
 * Author: Fabian Fränz <f.fraenz@t-online.de>
 * Date: 12/06/14
 * Project: CAD
 * Version: 1.0
 * Description: TODO Add description.
 */
public class MarchingTriangles {

	public Triangles march(FieldFunction f, Triangles initialTriangle) {

		List<Edge> edgeList = new ArrayList<>();
		for (int i = 0; i < initialTriangle.getIndices().size(); i += 3) {
			edgeList.add(new Edge(initialTriangle.getVertices().get(i), initialTriangle.getVertices().get(i+1)));
			edgeList.add(new Edge(initialTriangle.getVertices().get(i+1), initialTriangle.getVertices().get(i+2)));
			edgeList.add(new Edge(initialTriangle.getVertices().get(i+2), initialTriangle.getVertices().get(i)));
		}

		return null;
	}

	private Point3d spherecenter(Point3d p1, Point3d p2, Point3d p3) {

		Point3d q1 = new Point3d();
		Point3d q2 = p2.sub(p1);
		Point3d q3 = p3.sub(p1);
		Point3d normal = q2.cross(q3);

		Point3d zAxis = new Point3d(0, 0, 1);
		Point3d rotAxis = normal.cross(zAxis);
		rotAxis = rotAxis.scalarMult(1 / rotAxis.norm());
		double rotAngle = normal.dot(zAxis);
		Point3d q2Rot = rodriguesRotation(q2, rotAxis, rotAngle);
		Point3d q3Rot = rodriguesRotation(q2, rotAxis, rotAngle);
		Point3d center = circumcenter(q2Rot, q3Rot);
		center = rodriguesRotation(center, rotAxis, -rotAngle);
		center = center.add(q1);

		return center;
	}

	private Point3d rodriguesRotation(Point3d q2, Point3d rotAxis, double rotAngle) {

		double cos = Math.cos(rotAngle);
		double sin = Math.sin(rotAngle);
		return q2.scalarMult(cos).add(rotAxis.cross(q2).scalarMult(sin)).add(rotAxis.scalarMult(rotAxis.dot(q2)*(1-cos)));
	}

	// triangle ABC needs to be translate so A = Origin. So A drops out of formula. Z Coordinate is ignored.
	private Point3d circumcenter(Point3d B, Point3d C) {

		double D = 2 * (B.x*C.y-B.y*C.x);
		double
				Ux =
				(C.y*(Math.pow(B.x, 2) + Math.pow(B.y, 2))+B.y*(Math.pow(C.x, 2) + Math.pow(C.y, 2))) / D;
		double
				Uy =
				(C.x*(Math.pow(B.x, 2) + Math.pow(B.y, 2))+B.x*(Math.pow(C.x, 2) + Math.pow(C.y, 2))) / D;
		return new Point3d(Ux, Uy, 0);
	}

}
