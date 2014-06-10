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

	public SubSpaceView(final double[] mask) {

		this.mask = mask;

		inverseMask = new double[3];

		for (int i = 0; i < mask.length; i++) {
			if (mask[i] == 0) {
				inverseMask[i] = 1;
			} else {
				inverseMask[i] = 0;
			}
		}

		canvas = JoGLCanvasFactory.createCanvas(this);

		mra = new MouseRotationAdapter(
				new Rotation(Math.toRadians(90), new Vector3d(mask[0], mask[1], mask[2])),
				canvas
		);

		drawer = new LineDrawer(canvas, mra);
		canvas.setMainDrawer(drawer);

		final SubSpaceView view = this;

		adapter = new MouseAdapter() {

			private boolean drag = false;

			@Override public void mousePressed(MouseEvent e) {

				if (solid != null && catchedMouse) {

					Point3d mousePoint = translateMousePoint(e.getPoint());
					if (selectedPoint != null && selectedPoint.mult(new Point3d(inverseMask)).distance(mousePoint) < 10) {
						drag = true;
					} else {
						double smallestDistance = 10000;
						selectedPoint = null;
						for (Point3d point3d : solid.getAllPoints()) {
							double distance = point3d.distance(mousePoint);
							if (distance < 10 && distance < smallestDistance) {
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
			}

			@Override public void mouseDragged(MouseEvent e) {

				if (drag) {
					Point3d newPoint = translateMousePoint(e.getPoint()).add(selectedPoint.mult(new Point3d(mask)));
					solid.replacePoint(selectedPoint, newPoint);
					selectedPoint = newPoint;
				}
			}

			@Override public void mouseReleased(MouseEvent e) {

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
				for (int i = 0; i < mask.length; i++) {
					if (mask[i] == 0) {
						if (first) {
							xyz[i] = (-point.getX() + view.getWidth()/2)*0.01;
							first = false;
						} else {
							xyz[i] = (-point.getY() + view.getHeight()/2)*0.01;
						}
					}
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

	@Override public void update() {
		if (solid != null) {
			DefaultGeometry geometry = solid.createGeometry(canvas);
			drawer.setGeometry(geometry);
			canvas.redraw();
		}
	}
}
