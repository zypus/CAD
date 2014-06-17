package gui;

import org.scilab.forge.scirenderer.Canvas;
import org.scilab.forge.scirenderer.Drawer;
import org.scilab.forge.scirenderer.DrawingTools;
import org.scilab.forge.scirenderer.SciRendererException;
import org.scilab.forge.scirenderer.shapes.appearance.Appearance;
import org.scilab.forge.scirenderer.shapes.appearance.Color;
import org.scilab.forge.scirenderer.shapes.geometry.DefaultGeometry;
import org.scilab.forge.scirenderer.shapes.geometry.Geometry;
import org.scilab.forge.scirenderer.texture.AnchorPosition;
import org.scilab.forge.scirenderer.texture.TextEntity;
import org.scilab.forge.scirenderer.texture.Texture;
import org.scilab.forge.scirenderer.texture.TextureDrawer;
import org.scilab.forge.scirenderer.texture.TextureDrawingTools;
import org.scilab.forge.scirenderer.tranformations.DegenerateMatrixException;
import org.scilab.forge.scirenderer.tranformations.Transformation;
import org.scilab.forge.scirenderer.tranformations.TransformationFactory;
import org.scilab.forge.scirenderer.tranformations.Vector3d;
import org.scilab.forge.scirenderer.utils.shapes.geometry.SphereFactory;
import surface.Point3d;

import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;

/**
 * Author: Fabian Fränz <f.fraenz@t-online.de>
 * Date: 04/06/14
 * Project: CAD
 * Version: 1.0
 * Description: TODO Add description.
 */
public class LineDrawer
		implements Drawer {

	public static double zoom = 0.25;
	private static Geometry.FaceCullingMode mode = Geometry.FaceCullingMode.CW;
	private static String messageText = "";//"Press 'F' to switch culling mode";
	private static int drawMode = 0;
	private List<DefaultGeometry> geometry = null;
	private Geometry sphere;
	private Geometry selectedSphere;
	private List<Point3d> controlPoints = new ArrayList<>();
	private static Point3d selectedPoint = null;
	private final MouseRotationAdapter mra;
	private Texture message;
	private Canvas canvas;

	/**
	 * Default constructor.
	 *
	 * @param canvas               the canvas where the drawing will be performed.
	 * @param mouseRotationAdapter the used for interactivity.
	 */
	public LineDrawer(Canvas canvas, MouseRotationAdapter mouseRotationAdapter) {

		message = createMessage(canvas);
		mra = mouseRotationAdapter;
		this.canvas = canvas;
		sphere = SphereFactory.getSingleton().create(canvas, 0.1f, 0, 0);
		selectedSphere = SphereFactory.getSingleton().create(canvas, 0.15f, 0, 0);
	}

	public List<DefaultGeometry> getGeometry() {

		return geometry;
	}

	public void setGeometry(List<DefaultGeometry> geometry) {

		this.geometry = geometry;
	}

	public void setControlPoints(List<Point3d> points) {

		if (points != null) {
			controlPoints = points;
		} else {
			controlPoints = new ArrayList<>();
		}
	}

	public void setSelectedPoint(Point3d point) {

		selectedPoint = point;
	}

	/**
	 * Switch culled face.
	 */
	public static void switchFace() {

		switch (mode) {
		case CW:
			mode = Geometry.FaceCullingMode.CCW;
			break;
		case CCW:
			mode = Geometry.FaceCullingMode.BOTH;
			break;
		default:
			mode = Geometry.FaceCullingMode.CW;
		}
	}

	public static void switchDrawMode() {
		switch (drawMode) {
		case 0:
			drawMode = 1;
			break;
		case 1:
			drawMode = 2;
			break;
		default:
			drawMode = 0;
		}
	}

	public void setMessageText(String text) {

		if (!text.equals("") || !messageText.equals("")) {
			messageText = text;
			message = createMessage(canvas);
		}
	}

	@Override
	public void draw(DrawingTools dt) {

		dt.clear(new Color(0f, 0f, 0f));

		if (geometry != null) {
			try {
				Transformation
						projection =
						TransformationFactory.getPreferredAspectRatioTransformation(dt.getCanvas().getDimension(), 1f);
				dt.getTransformationManager().getProjectionStack().push(projection);
			} catch (DegenerateMatrixException ignored) {
				// Should not occur.
				System.out.println("Crashed 1");
			}

			try {
				dt.draw(message, AnchorPosition.UPPER_LEFT, new Vector3d(-.95, .95, 0));
			} catch (SciRendererException ignored) {
				System.out.println("Crashed 2");
			}

			try {
				dt.getTransformationManager()
				  .getModelViewStack()
				  .pushRightMultiply(TransformationFactory.getScaleTransformation(zoom, zoom, zoom));

				dt.getTransformationManager()
				  .getModelViewStack()
				  .pushRightMultiply(TransformationFactory.getRotationTransformation(mra.getRotation()));

				Appearance appearance = new Appearance();
				appearance.setFillColor(new Color(1f,1f,1f));
				appearance.setLineColor(new Color(.2f, .2f, .2f));
				appearance.setLineWidth(0);
				for (Geometry g : geometry) {
					DefaultGeometry dg = (DefaultGeometry) g;
					dg.setFaceCullingMode(mode);
					switch (drawMode) {
					case 0:
						dg.setFillDrawingMode(Geometry.FillDrawingMode.TRIANGLES);
						dg.setLineDrawingMode(Geometry.LineDrawingMode.SEGMENTS);
						break;
					case 1:
						dg.setFillDrawingMode(Geometry.FillDrawingMode.TRIANGLES);
						dg.setLineDrawingMode(Geometry.LineDrawingMode.NONE);
						break;
					case 2:
						dg.setFillDrawingMode(Geometry.FillDrawingMode.NONE);
						dg.setLineDrawingMode(Geometry.LineDrawingMode.SEGMENTS);
					}
					dt.draw(g, appearance);
				}
				Appearance sphereAppearance = new Appearance();
				for (Point3d point3d : controlPoints) {
					Geometry marker;
					if (selectedPoint != null && point3d == selectedPoint) {
						sphereAppearance.setFillColor(new Color(0f,1f,0f));
						marker = selectedSphere;
					} else {
						sphereAppearance.setFillColor(new Color(1f, 0f, 0f));
						marker = sphere;
					}
					dt.getTransformationManager().getModelViewStack().pushRightMultiply(TransformationFactory.getTranslateTransformation(point3d.x, point3d.y, point3d.z));
					dt.draw(marker, sphereAppearance);
					dt.getTransformationManager().getModelViewStack().pop();
				}
			} catch (SciRendererException ignored) {
				// Should not occur.
				System.out.println("Crashed 3");
			}
		}
	}

	@Override
	public boolean is2DView() {

		return false;
	}

	/**
	 * Create a help message.
	 *
	 * @param canvas the canvas where the message sprite is created.
	 * @return a sprite that draws the message.
	 */
	private Texture createMessage(final Canvas canvas) {

		final TextEntity text = new TextEntity(messageText);
		text.setTextColor(new Color(1f,1f,1f));
		Texture texture = canvas.getTextureManager().createTexture();
		texture.setDrawer(new TextureDrawer() {

			@Override
			public void draw(TextureDrawingTools drawingTools) {

				drawingTools.draw(text, 0, 0);
			}

			@Override
			public Dimension getTextureSize() {

				return text.getSize();
			}

			@Override
			public OriginPosition getOriginPosition() {

				return OriginPosition.UPPER_LEFT;
			}
		});

		return texture;
	}
}
