package gui;

import org.scilab.forge.scirenderer.Canvas;
import org.scilab.forge.scirenderer.Drawer;
import org.scilab.forge.scirenderer.DrawingTools;
import org.scilab.forge.scirenderer.SciRendererException;
import org.scilab.forge.scirenderer.implementation.jogl.JoGLCanvasFactory;
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
import org.scilab.forge.scirenderer.tranformations.Rotation;
import org.scilab.forge.scirenderer.tranformations.Transformation;
import org.scilab.forge.scirenderer.tranformations.TransformationFactory;
import org.scilab.forge.scirenderer.tranformations.Vector3d;
import splines.BezierSpline;
import splines.Spline;

import javax.media.opengl.awt.GLJPanel;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;
import java.awt.BorderLayout;
import java.awt.Dimension;

/**
 * Author: Fabian Fränz <f.fraenz@t-online.de>
 * Date: 18/05/14
 * Project: CAD
 * Version: 1.0
 * Description: TODO Add description.
 */
public class Panel3d extends GLJPanel {

	public static void main(String[] args) {

		JFrame frame = new JFrame();
		frame.add(new Panel3d(), BorderLayout.CENTER);

		Dimension screenSize = new Dimension(800, 800);
//		Dimension screenSize = Toolkit.getDefaultToolkit()
//									  .getScreenSize();

		frame.setPreferredSize(new Dimension(screenSize.width,
											 screenSize.height - 40));
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		//frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		SwingUtilities.updateComponentTreeUI(frame);
		frame.pack();
		frame.setVisible(true);
	}

	private final Canvas canvas;

	private final LineDrawer drawer;

	private final MouseRotationAdapter mra;

	/**
	 * Private constructor.
	 * This is the main class.
	 */
	public Panel3d() {

		canvas = JoGLCanvasFactory.createCanvas(this);


		/**
		 * Add a mouse rotation adapter.
		 */
		mra = new MouseRotationAdapter(
				new Rotation(Math.toRadians(90), new Vector3d(1, 0, 0)),
				canvas
		);

		drawer = new LineDrawer(canvas, mra);
		canvas.setMainDrawer(drawer);
	}

	public MouseRotationAdapter getMouseRotationAdapter() {
		return mra;
	}

	public void drawBowl(Spline spline) {
		drawer.drawBowl(spline);
		canvas.redraw();
	}

	/**
	 * @author Pierre Lando
	 */
	private final class LineDrawer
			implements Drawer {

		private static final String MESSAGE_TEXT = "";//"Press 'F' to switch culling mode";
		private DefaultGeometry bowl = null;
		private final MouseRotationAdapter mra;
		private final Texture message;

		/**
		 * Default constructor.
		 *
		 * @param canvas               the canvas where the drawing will be performed.
		 * @param mouseRotationAdapter the used for interactivity.
		 */
		public LineDrawer(Canvas canvas, MouseRotationAdapter mouseRotationAdapter) {

			Spline spline = new BezierSpline();
			spline.add(new DoublePoint(-1, 1));
			spline.add(new DoublePoint(-1, 0));
			spline.add(new DoublePoint(0, 0));
			spline.add(new DoublePoint(1, 0));
			spline.add(new DoublePoint(1, 1));

//			bowl = BowlFactory.createBowl(canvas, spline);
//			bowl.setFaceCullingMode(Geometry.FaceCullingMode.BOTH);
			message = createMessage(canvas);
			mra = mouseRotationAdapter;
		}

		public void drawBowl(Spline spline) {

			if (spline == null) {
				bowl = null;
			} else {
				bowl = BowlFactory.createBowl(canvas, spline);
				bowl.setFaceCullingMode(Geometry.FaceCullingMode.BOTH);
			}
		}

		/**
		 * Switch culled face.
		 */
		public void switchFace() {

			switch (bowl.getFaceCullingMode()) {
			case CCW:
				bowl.setFaceCullingMode(Geometry.FaceCullingMode.CW);
				break;
			default:
				bowl.setFaceCullingMode(Geometry.FaceCullingMode.CCW);
			}
		}

		@Override
		public void draw(DrawingTools dt) {

			dt.clear(new Color(0f, 0f, 0f));

			if (bowl != null) {
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
					  .pushRightMultiply(TransformationFactory.getScaleTransformation(.5, .5, .5));

					dt.getTransformationManager()
					  .getModelViewStack()
					  .pushRightMultiply(TransformationFactory.getRotationTransformation(mra.getRotation()));

					Appearance appearance = new Appearance();
					appearance.setLineColor(new Color(.2f, .2f, .2f));
					appearance.setLineWidth(3);
					dt.draw(bowl, appearance);
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

			final TextEntity text = new TextEntity(MESSAGE_TEXT);
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

}
