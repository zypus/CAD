package gui;

import org.scilab.forge.scirenderer.Canvas;
import org.scilab.forge.scirenderer.buffers.ElementsBuffer;
import org.scilab.forge.scirenderer.buffers.IndicesBuffer;
import org.scilab.forge.scirenderer.shapes.geometry.DefaultGeometry;
import org.scilab.forge.scirenderer.shapes.geometry.Geometry;
import org.scilab.forge.scirenderer.tranformations.Rotation;
import org.scilab.forge.scirenderer.tranformations.Vector3d;
import splines.Point;
import splines.Spline;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.List;

/**
 * Author: Fabian Fränz <f.fraenz@t-online.de>
 * Date: 18/05/14
 * Project: CAD
 * Version: 1.0
 * Description: TODO Add description.
 */
public class BowlFactory {

	private static final int ELEMENTS_SIZE = 4;

	/**
	 * Private constructor.
	 * This is an utility class.
	 */
	private BowlFactory() {

	}

	/**
	 * @param canvas the canvas where the buffers are created.
	 * @return a Tetrahedron geometry.
	 */
	public static DefaultGeometry createBowl(Canvas canvas, Spline spline) {

		int centralPoint = (spline.size()-1)/2;

		Point midPoint = spline.s(centralPoint);

		List<Point> interpolatedPoints = new ArrayList<>();

		double stepSize = 0.25;
		int iterations = (int)((spline.size()-1-centralPoint)/stepSize);
		for (int i = 1; i <= iterations; i++) {
			double u = centralPoint+i*stepSize;
			// rebase
			interpolatedPoints.add(spline.s(u).addValue(midPoint.manipulate(-1)).manipulate(0.005));
		}

		Vector3d midVector = new Vector3d(0, 0, 0);

		FloatBuffer vertices = FloatBuffer.allocate(ELEMENTS_SIZE + 12*interpolatedPoints.size() * ELEMENTS_SIZE);
		vertices.rewind();
		vertices.put(midVector.getDataAsFloatArray(ELEMENTS_SIZE));

		for (int r = 0; r < 12; r++) {
			Rotation rotation = new Rotation(2*Math.PI * r / 12.0, new Vector3d(0,0,1));
			for (Point point : interpolatedPoints) {
				Vector3d vector = new Vector3d(0, point.getX(), point.getY());
				vector = rotation.conjugate(vector);
				vertices.put(vector.getDataAsFloatArray(ELEMENTS_SIZE));
			}
		}
		vertices.rewind();

		IntBuffer indices = IntBuffer.allocate(12*3+24*3*(interpolatedPoints.size()-1));
		indices.rewind();

		// base 12 triangles
		for (int r = 0; r < 12; r++) {
			indices.put(0);
			indices.put(1 + r * interpolatedPoints.size());
			if (r == 0) {
				indices.put(1 + 11 * interpolatedPoints.size());
			} else {
				indices.put(1 + (r-1) * interpolatedPoints.size());
			}
		}

		// rest
		for (int r = 0; r < 12; r++) {
			for (int i = 0; i < interpolatedPoints.size()-1; i++) {
				if (r == 11) {
					indices.put((i + 1) + r * interpolatedPoints.size());
					indices.put((i + 1));
					indices.put((i + 2) + r * interpolatedPoints.size());
					indices.put((i + 2) + r * interpolatedPoints.size());
					indices.put((i + 1));
					indices.put((i + 2));
				} else {
					indices.put((i+1) + r * interpolatedPoints.size());
					indices.put((i+1) + (r+1) * interpolatedPoints.size());
					indices.put((i+2) + r * interpolatedPoints.size());
					indices.put((i+2) + r * interpolatedPoints.size());
					indices.put((i+1) + (r+1) * interpolatedPoints.size());
					indices.put((i+2) + (r+1) * interpolatedPoints.size());
				}
			}
		}
		indices.rewind();

		IntBuffer edgesIndices = IntBuffer.allocate(2*12*(interpolatedPoints.size()+1+12*2));
		edgesIndices.rewind();
		for (int r = 0; r < 12; r++) {
			edgesIndices.put(0);
			edgesIndices.put(1 + r * interpolatedPoints.size());
			for (int i = 0; i < interpolatedPoints.size() - 1; i++) {
				edgesIndices.put((i + 1) + r * interpolatedPoints.size());
				edgesIndices.put((i + 2) + r * interpolatedPoints.size());
			}

		}
		for (int r = 0; r < 12; r++) {
			for (int i = interpolatedPoints.size(); i <= interpolatedPoints.size(); i++) {
				// edge
				edgesIndices.put(i + r * interpolatedPoints.size());
				if (r == 11) {
					edgesIndices.put(i);
				} else {
					edgesIndices.put(i + (r + 1) * interpolatedPoints.size());
				}
			}
		}

		edgesIndices.rewind();

		canvas.getBuffersManager().createElementsBuffer();
		ElementsBuffer vertexBuffer = canvas.getBuffersManager().createElementsBuffer();
		IndicesBuffer indicesBuffer = canvas.getBuffersManager().createIndicesBuffer();
		IndicesBuffer edgesIndicesBuffer = canvas.getBuffersManager().createIndicesBuffer();

		vertexBuffer.setData(vertices, ELEMENTS_SIZE);
		indicesBuffer.setData(indices);
		edgesIndicesBuffer.setData(edgesIndices);

		DefaultGeometry geometry = new DefaultGeometry();
		geometry.setFillDrawingMode(Geometry.FillDrawingMode.TRIANGLES);
		geometry.setLineDrawingMode(Geometry.LineDrawingMode.SEGMENTS);
		geometry.setPolygonOffsetMode(true);
		geometry.setWireIndices(edgesIndicesBuffer);
		geometry.setIndices(indicesBuffer);
		geometry.setVertices(vertexBuffer);

		return geometry;
	}

}
