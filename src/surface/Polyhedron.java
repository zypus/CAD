package surface;

import org.scilab.forge.scirenderer.Canvas;
import org.scilab.forge.scirenderer.buffers.ElementsBuffer;
import org.scilab.forge.scirenderer.buffers.IndicesBuffer;
import org.scilab.forge.scirenderer.shapes.geometry.DefaultGeometry;
import org.scilab.forge.scirenderer.shapes.geometry.Geometry;
import org.scilab.forge.scirenderer.tranformations.Vector3d;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.List;

/**
 * Author: Fabian Fränz <f.fraenz@t-online.de>
 * Date: 03/06/14
 * Project: CAD
 * Version: 1.0
 * Description: TODO Add description.
 */
public class Polyhedron
		extends Solid {

	private List<Point3d> points;
	private Triangles triangles;
	private DefaultGeometry geometry;

	private static final int ELEMENT_SIZE = 4;

	@Override public DefaultGeometry createGeometry(Canvas canvas) {

		triangles = new ChansAlgorithm().computeConvexHull(points);
		FloatBuffer vertices = FloatBuffer.allocate(ELEMENT_SIZE*triangles.getVertices().size());
		vertices.rewind();
		for (Point3d point3d : triangles.getVertices()) {
			Vector3d vector = new Vector3d(point3d.getX(), point3d.getY(), point3d.getZ());
			vertices.put(vector.getDataAsFloatArray(ELEMENT_SIZE));
		}
		vertices.rewind();

		IntBuffer indices = IntBuffer.allocate(triangles.getIndices().size());
		indices.rewind();
		for (Integer index : triangles.getIndices()) {
			indices.put(index);
		}
		indices.rewind();

		canvas.getBuffersManager().createElementsBuffer();
		ElementsBuffer vertexBuffer = canvas.getBuffersManager().createElementsBuffer();
		IndicesBuffer indicesBuffer = canvas.getBuffersManager().createIndicesBuffer();
//			IndicesBuffer edgesIndicesBuffer = canvas.getBuffersManager().createIndicesBuffer();

		vertexBuffer.setData(vertices, ELEMENT_SIZE);
		indicesBuffer.setData(indices);
//			edgesIndicesBuffer.setData(edgesIndices);

		geometry = new DefaultGeometry();
		geometry.setFillDrawingMode(Geometry.FillDrawingMode.TRIANGLES);
//			geometry.setLineDrawingMode(Geometry.LineDrawingMode.SEGMENTS);
		geometry.setPolygonOffsetMode(true);
//			geometry.setWireIndices(edgesIndicesBuffer);
		geometry.setIndices(indicesBuffer);
		geometry.setVertices(vertexBuffer);

		return geometry;
	}

	@Override public double getArea() {

		return 0;
	}

	@Override public double getVolume() {

		return 0;
	}

	@Override public void addPoint(Point3d point3d) {

		points.add(point3d);
		notifyObservers();
	}

	@Override public void removePoint(Point3d point3d) {

		points.remove(point3d);
		notifyObservers();
	}

	@Override public void replacePoint(Point3d point3d, Point3d otherPoint3d) {

		points.set(points.indexOf(point3d), otherPoint3d);
		notifyObservers();
	}

	@Override public void setAllPoints(List<Point3d> points) {

		this.points = points;
		notifyObservers();
	}

	@Override public List<Point3d> getAllPoints() {

		return points;
	}
}
