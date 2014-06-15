package surface;

import org.scilab.forge.scirenderer.Canvas;
import org.scilab.forge.scirenderer.buffers.ElementsBuffer;
import org.scilab.forge.scirenderer.buffers.IndicesBuffer;
import org.scilab.forge.scirenderer.shapes.geometry.DefaultGeometry;
import org.scilab.forge.scirenderer.shapes.geometry.Geometry;
import org.scilab.forge.scirenderer.tranformations.Vector3d;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Author: Fabian Fränz <f.fraenz@t-online.de>
 * Date: 03/06/14
 * Project: CAD
 * Version: 1.0
 * Description: TODO Add description.
 */
public abstract class Solid {

	private final int ELEMENT_SIZE = 4;
	private boolean open = true;

	private List<PriorityObserver> observers = new ArrayList<>();

	public abstract List<DefaultGeometry> createGeometry(Canvas canvas);
	public abstract double getArea();
	public abstract double getVolume();

	public abstract void addPoint(Point3d point3d);
	public abstract void removePoint(Point3d point3d);
	public abstract Point3d replacePoint(Point3d point3d, Point3d otherPoint3d);
	public abstract void setAllPoints(List<Point3d> points);
	public abstract List<Point3d> getAllPoints();

	protected DefaultGeometry triangulation(Canvas canvas, Triangles triangles) {

		FloatBuffer vertices = FloatBuffer.allocate(ELEMENT_SIZE * triangles.getVertices().size());
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

		IntBuffer edgesIndices = IntBuffer.allocate(2*triangles.getIndices().size());
		edgesIndices.rewind();
		for (int i = 0; i < triangles.getIndices().size(); i+=3) {
			edgesIndices.put(triangles.getIndices().get(i));
			edgesIndices.put(triangles.getIndices().get(i+1));
			edgesIndices.put(triangles.getIndices().get(i+1));
			edgesIndices.put(triangles.getIndices().get(i+2));
			edgesIndices.put(triangles.getIndices().get(i+2));
			edgesIndices.put(triangles.getIndices().get(i));
		}
		edgesIndices.rewind();

		canvas.getBuffersManager().createElementsBuffer();
		ElementsBuffer vertexBuffer = canvas.getBuffersManager().createElementsBuffer();
		IndicesBuffer indicesBuffer = canvas.getBuffersManager().createIndicesBuffer();
		IndicesBuffer edgesIndicesBuffer = canvas.getBuffersManager().createIndicesBuffer();

		vertexBuffer.setData(vertices, ELEMENT_SIZE);
		indicesBuffer.setData(indices);
		edgesIndicesBuffer.setData(edgesIndices);

		DefaultGeometry geometry = new DefaultGeometry();
		geometry.setFillDrawingMode(Geometry.FillDrawingMode.TRIANGLES);
		geometry.setLineDrawingMode(Geometry.LineDrawingMode.SEGMENTS);
		geometry.setPolygonOffsetMode(true);
		geometry.setFaceCullingMode(Geometry.FaceCullingMode.CW);
		geometry.setWireIndices(edgesIndicesBuffer);
		geometry.setIndices(indicesBuffer);
		geometry.setVertices(vertexBuffer);

		return geometry;
	}

	// Observer pattern
	public void attachObserver(SolidObserver observer, int priority) {

		observers.add(new PriorityObserver(observer, priority));
		Collections.sort(observers);
	}

	public void detachObserver(SolidObserver observer) {

		int index = -1;
		for (int i = 0; i < observers.size(); i++) {
			if (observers.get(i).getObserver() == observer) {
				index = i;
				break;
			}
		}
		if (index != -1) {
			observers.remove(index);
		}
	}

	protected void notifyObservers() {

		for (SolidObserver observer : observers) {
			observer.update();
		}
	}

	public boolean isOpen() {

		return open;
	}

	public void setOpen(boolean open) {

		this.open = open;
	}

	private class PriorityObserver
			implements SolidObserver, Comparable<PriorityObserver> {

		private SolidObserver observer;
		private int priority;

		private PriorityObserver(SolidObserver observer, int priority) {

			this.observer = observer;
			this.priority = priority;
		}

		@Override public void update() {

			observer.update();
		}

		public int getPriority() {

			return priority;
		}

		public void setPriority(int priority) {

			this.priority = priority;
		}

		public SolidObserver getObserver() {

			return observer;
		}

		@Override public int compareTo(PriorityObserver o) {

			if (priority < o.getPriority()) {
				return -1;
			} else if (priority > o.getPriority()) {
				return 1;
			} else {
				return 0;
			}
		}
	}

}
