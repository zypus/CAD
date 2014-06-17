package gui;

import org.scilab.forge.scirenderer.Canvas;
import org.scilab.forge.scirenderer.implementation.jogl.JoGLCanvasFactory;
import org.scilab.forge.scirenderer.shapes.geometry.DefaultGeometry;
import org.scilab.forge.scirenderer.tranformations.Rotation;
import org.scilab.forge.scirenderer.tranformations.Vector3d;
import surface.Point3d;
import surface.Solid;
import surface.SolidObserver;

import javax.media.opengl.awt.GLJPanel;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

/**
 * Author: Fabian Fränz <f.fraenz@t-online.de>
 * Date: 04/06/14
 * Project: CAD
 * Version: 1.0
 * Description: TODO Add description.
 */
public class SubSpaceView extends GLJPanel
		implements SolidObserver {

	private static Point3d selectedPoint = null;

	private double[] mask = {0,0,0};
	private double[] inverseMask;
	private boolean catchedMouse = false;

	private Solid solid = null;

	private MouseAdapter adapter;
	private MouseRotationAdapter mra;

	protected Canvas canvas;
	protected LineDrawer drawer;

	boolean showControlPoints = true;

	private  QuadViewPanel owner;

	public SubSpaceView(final double[] viewingVector, final QuadViewPanel owner) {

		this.owner = owner;

		this.mask = new double[3];
		if (viewingVector[0] != 0) {
			mask[1] = 1;
		} else if (viewingVector[1] != 0) {
			mask[0] = 1;
		} else if (viewingVector[2] != 0) {
			mask[2] = 1;
		}

		inverseMask = new double[3];

		for (int i = 0; i < this.mask.length; i++) {
			if (this.mask[i] == 0) {
				inverseMask[i] = 1;
			} else {
				inverseMask[i] = 0;
			}
		}

		canvas = JoGLCanvasFactory.createCanvas(this);

		mra = new MouseRotationAdapter(
				new Rotation(Math.toRadians(90), new Vector3d(viewingVector[0], viewingVector[1], viewingVector[2])),
				canvas
		);

		drawer = new LineDrawer(canvas, mra);
		canvas.setMainDrawer(drawer);

		final SubSpaceView view = this;

		adapter = new MouseAdapter() {

			private boolean drag = false;
			private final double precision = 0.2;
			public boolean dragged = false;

			@Override public void mousePressed(final MouseEvent e) {

				if (solid != null && catchedMouse) {

					Point3d mousePoint = translateMousePoint(e.getPoint());
					if (selectedPoint != null && selectedPoint.mult(new Point3d(inverseMask)).distance(mousePoint) < precision) {
						drag = true;
					} else {
						double smallestDistance = 10000;
						selectedPoint = null;
						if (solid.getAllPoints() != null) {
							for (Point3d point3d : solid.getAllPoints()) {
								double distance = point3d.mult(new Point3d(inverseMask)).distance(mousePoint);
								if (distance < precision && distance < smallestDistance) {
									selectedPoint = point3d;
									smallestDistance = distance;
								}
							}
							if (selectedPoint != null) {
								drag = true;
							} else {
								solid.addPoint(mousePoint);
							}
						}
					}
					owner.update();
				}
			}

			@Override public void mouseDragged(final MouseEvent e) {

				if (drag) {
					dragged = true;
					Point3d mousePoint = translateMousePoint(e.getPoint());
					Point3d newPoint = mousePoint.mult(new Point3d(inverseMask)).add(selectedPoint.mult(new Point3d(mask)));
					selectedPoint = solid.replacePoint(selectedPoint, newPoint);
					solid.setChanged();
					owner.update();
				}
			}

			@Override public void mouseReleased(MouseEvent e) {

				if (dragged) {
					owner.setSolid(solid);
				}
				dragged = false;
				drag = false;
			}

			@Override public void mouseEntered(MouseEvent e) {

				catchedMouse = true;
			}

			@Override public void mouseExited(MouseEvent e) {

				catchedMouse = false;
			}

			private Point3d translateMousePoint(Point point) {

				double[] xyz = { 0, 0, 0 };
				boolean first = true;
				if (mask[0] == 1) {
					xyz[2] = -(2 * point.getX() / view.getWidth() - 1) * 1/LineDrawer.zoom;
					xyz[1] = -(2 * point.getY() / view.getHeight() - 1) * 1 / LineDrawer.zoom;
				} else if (mask[1] == 1) {
					xyz[0] = (2 * point.getX() / view.getWidth() - 1) * 1 / LineDrawer.zoom;
					xyz[2] = (2 * point.getY() / view.getHeight() - 1) * 1 / LineDrawer.zoom;
				} else if (mask[2] == 1) {
					xyz[1] = (2 * point.getX() / view.getWidth() - 1) * 1 / LineDrawer.zoom;
					xyz[0] = (2 * point.getY() / view.getHeight() - 1) * 1 / LineDrawer.zoom;
				}


				return new Point3d(xyz[0], xyz[1], xyz[2]);
			}
		};
		addMouseListener(adapter);
		addMouseMotionListener(adapter);

	}

	public MouseAdapter getAdapter() {

		return adapter;
	}

	public MouseRotationAdapter getMra() {

		return mra;
	}

	public Solid getSolid() {

		return solid;
	}

	public void setSolid(Solid solid) {

		if (this.solid != null) {
			this.solid.detachObserver(this);
		}
		this.solid = solid;
		if (solid != null) {
			solid.attachObserver(this, 3);
		}
		update();
	}

	public void showControlPoints(boolean show) {
		showControlPoints = show;
		update();
	}

	@Override public void update() {
		if (solid != null) {
			List<DefaultGeometry> geometry = solid.createGeometry(canvas);
			drawer.setGeometry(geometry);
			if (showControlPoints) {
				drawer.setControlPoints(solid.getAllPoints());
			} else {
				drawer.setControlPoints(null);
			}
			drawer.setSelectedPoint(selectedPoint);
			canvas.redraw();
		}
	}
}
