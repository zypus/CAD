package gui;

import bowl.BowlMaker;
import gui.tools.Tool;
import gui.tools.ToolDelegate;
import gui.tools.drag.Draggable;
import gui.tools.drag.Dragger;
import gui.tools.draw.SplineDrawer;
import gui.tools.select.PointSelecter;
import gui.tools.select.Selectable;
import gui.tools.select.Selecter;
import gui.tools.select.SelectionObserver;
import intersection.Point;
import intersection.Segment;
import splines.Spline;
import splines.SplineArea;
import splines.SplineLength;
import splines.SplineSolidOfRevolution;
import splines.SplineSurfaceOfRevolution;

import javax.swing.JComponent;
import javax.swing.JPanel;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.beans.Transient;
import java.util.ArrayList;
import java.util.List;

public class OutputComponent
		extends JComponent {

	private static final Color[] COLORS = { Color.WHITE, Color.BLUE, Color.MAGENTA, Color.ORANGE, Color.PINK, Color.YELLOW };
	private static final SplineType[] SPLINE_TYPES = {SplineType.CUBIC, SplineType.LINEAR, SplineType.BEZIER};
	private int splineTypeCounter = 2;
	private static final int SELECTION_THICKNESS = 4;
	private static final double SELECT_SENSITIVITY = 10;
	public static final int INDICATOR_SIZE = 16;
	public static final int DEFAULT_THICKNESS = 2;
	private int colorCounter = 0;
	private ShowCoordinates showCoo;
	private Spline2D currentSpline = null;
	private Shape shapeStartIndicator = null;
	private List<List<Point>> intersections = new ArrayList<>();
	private Integer hover = -1;
	private Point2D mousePoint = new Point2D.Double(0, 0);
	private String mouseText = "";
	private int pressedButton = 0;
	private boolean drawLines = true;
	private JPanel scroll = null;
	private Font font = new Font("Arial", Font.BOLD, 15);
	private List<Spline2D> splines = new ArrayList<>();
	private SplinePoint draggedPoint = null;
	private SplineType currentSplineType = SplineType.BEZIER;
	private boolean showControlPointCoords = true;
	private List<Tool> tools;
	private Selecter splineSelecter;
	private Selecter pointSelecter;
	private Dragger dragger;
	private SplineDrawer drawer;
	private BowlMaker bowlMaker;
	private Panel3d panel3d;
	private boolean bowlMakerEnabled = false;
	private MouseAdapter mouseAdapter;
	private MouseRotationAdapter mouseRotationAdapter;
	private boolean ratioToggle = false;

	public OutputComponent() {

	}

	public void setup() {

		setOpaque(false);

		panel3d = new Panel3d();
		mouseRotationAdapter = panel3d.getMouseRotationAdapter();
		getParent().add(panel3d, new Integer(1), 0);
		getParent().addComponentListener(new ComponentAdapter() {
			@Override public void componentResized(ComponentEvent e) {

				super.componentResized(e);
				panel3d.setBounds(0, 0, e.getComponent().getWidth(), e.getComponent().getHeight());
			}
		});


		showCoo = new ShowCoordinates(this);

		drawer = new SplineDrawer(SplineType.BEZIER);
		splineSelecter = new Selecter();
		pointSelecter = new PointSelecter();
		dragger = new Dragger();

		final OutputComponent reference = this;

		pointSelecter.setDelegate(new ToolDelegate() {
			@Override public void didFinish(Tool tool) {

				Selecter selecter = (Selecter) tool;
				List<Selectable> selectedObjects = selecter.getSelectedObjects();
				List<Draggable> draggables = new ArrayList<>();
				for (Selectable selectedPoint : selectedObjects) {
					draggables.add((Draggable) selectedPoint);
				}

				dragger.setDraggables(draggables);
			}

			@Override public boolean shouldStart(Tool tool) {

				return true;
			}

			@Override public boolean shouldFinish(Tool tool) {

				return true;
			}

			@Override public void didStart(Tool tool) {

			}

			@Override public void didUpdate(Tool tool) {

			}

			@Override public boolean shouldDraw(Tool tool) {

				return !dragger.isDragging();
			}

			@Override public int getWidth() {

				return reference.getWidth();
			}

			@Override public int getHeight() {

				return reference.getHeight();
			}
		});
		splineSelecter.setDelegate(new ToolDelegate() {
			@Override public void didFinish(Tool tool) {
				Selecter selecter = (Selecter) tool;
				List<Selectable> selectedObjects = selecter.getSelectedObjects();
				pointSelecter.setSelectableObjects(null);
				for (Selectable selectable : selectedObjects) {
					List<Selectable> selectablePoints = new ArrayList<>();
					Spline2D spline2d = (Spline2D) selectable;
					for (int i = 0; i < spline2d.getSpline().size(); i++) {
						selectablePoints.add(new SplinePoint(spline2d, i));
					}
					pointSelecter.addSelectables(selectablePoints);
				}
			}

			@Override public boolean shouldStart(Tool tool) {

				return !pointSelecter.hasSelectedObjects();
			}

			@Override public boolean shouldFinish(Tool tool) {

				return !pointSelecter.hasSelectedObjects();
			}

			@Override public void didStart(Tool tool) {

			}

			@Override public void didUpdate(Tool tool) {

			}

			@Override public boolean shouldDraw(Tool tool) {

				return !dragger.isDragging();
			}

			@Override public int getWidth() {

				return reference.getWidth();
			}

			@Override public int getHeight() {

				return reference.getHeight();
			}
		});
		drawer.setDelegate(new ToolDelegate() {
			@Override public void didFinish(Tool tool) {
				currentSpline = null;
			}

			@Override public boolean shouldStart(Tool tool) {

				return true;
			}

			@Override public boolean shouldFinish(Tool tool) {

				return false;
			}

			@Override public void didStart(Tool tool) {

				SplineDrawer drawer = (SplineDrawer) tool;
				currentSpline = drawer.getCurrentSpline();
				splines.add(currentSpline);
				splineSelecter.addSelectable(currentSpline);
			}

			@Override public void didUpdate(Tool tool) {

			}

			@Override public boolean shouldDraw(Tool tool) {

				return true;
			}

			@Override public int getWidth() {

				return reference.getWidth();
			}

			@Override public int getHeight() {

				return reference.getHeight();
			}
		});

		tools = new ArrayList<>();
		tools.add(drawer);
		tools.add(pointSelecter);
		tools.add(dragger);
		tools.add(splineSelecter);

		drawer.activate();

//		// test
//		List<DoublePoint> testControlPoints = new ArrayList<>();
//		testControlPoints.add(new DoublePoint(50, 50));
//		testControlPoints.add(new DoublePoint(100, 100));
//		testControlPoints.add(new DoublePoint(50, 100));
//		testControlPoints.add(new DoublePoint(200, 200));
//		Spline testSpline = new BezierSpline();
//		testSpline.addAll(testControlPoints);
//		Spline2D spline2D = new Spline2D(testSpline, SplineType.BEZIER);
//		spline2D.setColor(Color.GREEN);
//		spline2D.setThickness(2);
//		splines.add(spline2D);
//		splineSelecter.addSelectable(spline2D);
//		// end test

		bowlMaker = new BowlMaker(panel3d);

		add(bowlMaker);

		mouseAdapter = new MouseAdapter() {

			public void mousePressed(MouseEvent e) {

				int shift = (getHeight()-e.getY())-e.getY();
				e.translatePoint(0, shift);
				for (Tool tool : tools) {
					tool.mousePressed(e);
				}
				repaint();
				e.translatePoint(0, -shift);
			}

			public void mouseReleased(MouseEvent e) {

				int shift = (getHeight() - e.getY()) - e.getY();
				e.translatePoint(0, shift);
				for (Tool tool : tools) {
					tool.mouseReleased(e);
				}
				repaint();
				e.translatePoint(0, -shift);
			}

			public void mouseExited(MouseEvent e) {

				int shift = (getHeight() - e.getY()) - e.getY();
				e.translatePoint(0, shift);
				showCoo.resetLabel();
				for (Tool tool : tools) {
					tool.mouseExited(e);
				}
				e.translatePoint(0, -shift);
			}

			public void mouseEntered(MouseEvent e) {

				int shift = (getHeight() - e.getY()) - e.getY();
				e.translatePoint(0, shift);
				for (Tool tool : tools) {
					tool.mouseEntered(e);
				}
				e.translatePoint(0, -shift);
			}

			public void mouseDragged(MouseEvent e) {

				int shift = (getHeight() - e.getY()) - e.getY();
				e.translatePoint(0, shift);

				showCoo.update(e.getX(), e.getY());

				for (Tool tool : tools) {
					tool.mouseDragged(e);
				}

				mousePoint = e.getPoint();

				repaint();
				e.translatePoint(0, -shift);
			}

			public void mouseMoved(MouseEvent e) {

				int shift = (getHeight() - e.getY()) - e.getY();
				e.translatePoint(0, shift);

				showCoo.update(e.getX(), e.getY());
				mousePoint = e.getPoint();
				mouseText = "";

				for (Tool tool : tools) {
					tool.mouseMoved(e);
				}

				repaint();
				e.translatePoint(0, -shift);
			}

		};
		addMouseListener(mouseAdapter);
		addMouseMotionListener(mouseAdapter);

	}

	public List<Segment> createPoly(List<Shape> shape) {

		List<Segment> poly = new ArrayList<Segment>();
		for (Shape line : shape) {
			poly.add(new Segment(new Point(((Line2D) line).getX1(), ((Line2D) line).getY1()),
								 new Point(((Line2D) line).getX2(), ((Line2D) line).getY2())));
		}
		return poly;
	}

	public boolean isDrawLines() {

		return drawLines;
	}

	public SplineType setDrawing() {

		if (drawer.isActive()) {
			drawer.finish();
			splineTypeCounter = (splineTypeCounter + 1) % SPLINE_TYPES.length;
			currentSplineType = SPLINE_TYPES[splineTypeCounter];
		}
//		if (drawLines && currentSplineType.equals(SplineType.BEZIER)) {
//			SplineDrawer newDrawer = new BezierSplineDrawer();
//			newDrawer.setDelegate(drawer.getDelegate());
//			tools.set(tools.indexOf(drawer), newDrawer);
//			drawer = newDrawer;
//		} else if (drawLines) {
			SplineDrawer newDrawer = new SplineDrawer(currentSplineType);
			newDrawer.setDelegate(drawer.getDelegate());
			tools.set(tools.indexOf(drawer), newDrawer);
			drawer = newDrawer;
//		}
		drawer.activate();
		splineSelecter.clear();
		pointSelecter.clear();
		splineSelecter.deactivate();
		pointSelecter.deactivate();
		dragger.deactivate();
		repaint();
		return currentSplineType;
	}

	public void setSelecting() {

		drawer.deactivate();
		splineSelecter.activate();
		pointSelecter.activate();
		dragger.activate();
		repaint();
	}

	public void clear() {

		drawer.finish();
		splines.clear();
		splineSelecter.clear();
		splineSelecter.setSelectableObjects(null);
		pointSelecter.clear();
		pointSelecter.setSelectableObjects(null);
		dragger.setDraggables(null);

		intersections.clear();
		repaint();
	}

	public void toggleBowlMaker(boolean toggle) {
		if (toggle) {
			add(bowlMaker);
			if (ratioToggle) {
				bowlMaker.makeBowl(new SplineArea(), new SplineLength());
			} else {
				bowlMaker.makeBowl(new SplineSolidOfRevolution(), new SplineSurfaceOfRevolution());
			}
			removeMouseListener(mouseAdapter);
			addMouseListener(mouseRotationAdapter);
		} else {
			bowlMaker.stop();
			remove(bowlMaker);
			addMouseListener(mouseAdapter);
			removeMouseListener(mouseRotationAdapter);
			panel3d.drawBowl(null);
		}
		bowlMakerEnabled = toggle;
		repaint();
	}

	protected void paintComponent(Graphics g) {

		super.paintComponent(g);

		Graphics2D g2d = (Graphics2D) g.create();
		AffineTransform tform = AffineTransform.getTranslateInstance(0, getHeight());
		tform.scale(1, -1);
		g2d.setTransform(tform);

		SplineRenderer splineRenderer = new SplineRenderer(g2d);

//		g2d.setBackground(new Color(0,0,0,255));
//		g2d.clearRect(0, 0, getWidth(), getHeight());

		if (!bowlMakerEnabled) {
			g2d.setStroke(new BasicStroke(2));
			int shapeCount = 0;
			for (int i = 0; i < splines.size(); i++) {
				Spline2D spline2d = splines.get(i);
				g2d.setPaint(spline2d.getColor());
				g2d.setStroke(new BasicStroke(DEFAULT_THICKNESS));
				boolean drawControlPoints = false;
				if (spline2d == currentSpline) {
					drawControlPoints = true;
				}
				splineRenderer.renderSplineAtPosition(spline2d, 0, 0, drawControlPoints);
			}
			for (int i = 0; i < splineSelecter.getSelectedObjects().size(); i++) {
				Spline2D spline2d = (Spline2D) splineSelecter.getSelectedObjects().get(i);
				g2d.setStroke(new BasicStroke(SELECTION_THICKNESS));
				splineRenderer.renderSplineAtPosition(spline2d, 0, 0, true);
				if (showControlPointCoords) {
					drawCoords(spline2d, g2d);
				}
			}
			//		if (shapeCount < ((currentSpline == null) ? scroll.getComponentCount() : scroll.getComponentCount() - 1)) {
			//			if (!((Line2D) shapes.get(s).get(shapes.get(s).size() - 1)).getP2().equals(((Line2D) shapes.get(s).get(0)).getP1())) {
			//				((JLabel) scroll.getComponent(shapeCount)).setText(
			//						"open -> Length: " + (int) PolyChecker.parameter(polygons.get(shapeCount)));
			//			} else {
			//				((JLabel) scroll.getComponent(shapeCount)).setText(
			//						"closed -> Length: " + (int) PolyChecker.parameter(polygons.get(shapeCount)) + " Area: "
			//						+ (int) PolyChecker.area(polygons.get(shapeCount))
			//				);
			//			}
			//
			//		}
			if (shapeStartIndicator != null) {
				g2d.draw(shapeStartIndicator);
			}
			if (!intersections.isEmpty()) {
				for (List<Point> inters : intersections) {
					for (Point inter : inters) {
						g2d.setPaint(Color.WHITE);
						g2d.draw(new Ellipse2D.Double(inter.getX() - 4, inter.getY() - 4, 8, 8));
					}
				}
			}
			g2d.setPaint(Color.WHITE);
			g2d.setFont(font);
			FontMetrics metric = g2d.getFontMetrics(font);
			int l = metric.stringWidth(mouseText);
			g2d.drawString(mouseText, (int) mousePoint.getX(), (int) mousePoint.getY());

			for (Tool tool : tools) {
				tool.draw(g2d);
			}
		}

		g2d.dispose();
//		tform.scale(1, -1);
//		g2d.setTransform(tform);

	}

	private void drawCoords(Spline2D spline2d, Graphics2D g2d) {

		g2d = (Graphics2D) g2d.create();
		AffineTransform tform = AffineTransform.getTranslateInstance(0, getHeight());
		tform.scale(1, -1);
		g2d.transform(tform);
		g2d.setFont(font);
		FontMetrics metric = g2d.getFontMetrics(font);
		for (splines.Point point : spline2d.getSpline()) {
			Color c = g2d.getColor();
			g2d.setPaint(Color.WHITE);
			String label = "(" + (int) point.getX() + ", " + (int) point.getY() + ")";
			int length = metric.stringWidth(label);
			g2d.drawString(label, ((float) point.getX()) - length / 2, ((float) (getHeight()-point.getY())) + 20);
			g2d.setPaint(c);
		}
		g2d.dispose();
	}

	@Override
	@Transient
	public Dimension getPreferredSize() {

		int myWidth = getParent().getWidth();
		int myHeight = getParent().getHeight();
		return this.getParent().getSize();
	}

	public void magnifyShapes(List<Shape> shape) {

		for (Shape line : shape) {
			((Line2D.Double) line).x1 *= 20;
			((Line2D.Double) line).x2 *= 20;
			((Line2D.Double) line).y1 *= 20;
			((Line2D.Double) line).y2 *= 20;
		}
	}

	public void translateShapes(List<Shape> shape, double amountX, double amountY) {

		for (Shape line : shape) {
			((Line2D.Double) line).x1 += amountX;
			((Line2D.Double) line).x2 += amountX;
			((Line2D.Double) line).y1 += amountY;
			((Line2D.Double) line).y2 += amountY;
		}
	}

	public void rotateShapes(List<Shape> shape, double angle) {

		for (Shape line : shape) {
			((Line2D.Double) line).x1 = Math.cos(angle) * ((Line2D.Double) line).x1 + Math.sin(angle) * ((Line2D.Double) line).y1;
			((Line2D.Double) line).x2 = Math.cos(angle) * ((Line2D.Double) line).x2 + Math.sin(angle) * ((Line2D.Double) line).y2;
			((Line2D.Double) line).y1 = -Math.sin(angle) * ((Line2D.Double) line).x1 + Math.cos(angle) * ((Line2D.Double) line).y1;
			((Line2D.Double) line).y2 = -Math.sin(angle) * ((Line2D.Double) line).x2 + Math.cos(angle) * ((Line2D.Double) line).y2;
		}
	}

	public void addShapes(List<java.awt.Point> points) {

		drawer.finish();
		Spline spline = currentSplineType.createInstance();
		for (int i = 0; i < points.size(); i++) {
			spline.add(new DoublePoint(points.get(i)));
		}
		Spline2D spline2d = new Spline2D(spline, currentSplineType);
		spline2d.setColor(COLORS[colorCounter]);
		colorCounter = (colorCounter + 1) % COLORS.length;
		splines.add(spline2d);
		splineSelecter.addSelectable(spline2d);
		repaint();
	}

	public void bowlMakerVisualisation(boolean dim) {

		bowlMaker.set3d(dim);
	}

	public boolean isVisualingIn3D() {
		return bowlMaker.get3d();
	}

	public void bowlMakerRatio(boolean toggle) {

		ratioToggle = toggle;
	}

	public boolean getBowlMakerRatio() {
		return ratioToggle;
	}

	public void toggleBowlGeneticHill(boolean toggle) {
		bowlMaker.setGeneticHill(toggle);
	}

	private class SplinePoint
			implements Selectable, Draggable {

		private Spline2D spline2D;
		private int index;
		SelectionType selectionStatus = SelectionType.UNSELECTED;

		@Override public List<splines.Point> getSelectablePoints() {

			List<splines.Point> points = new ArrayList<>();
			points.add(getPoint());
			return points;
		}

		@Override public boolean onlySelectableOnPoints() {

			return true;
		}

		@Override public void setSelectionStatus(SelectionType selected) {

			selectionStatus = selected;
		}

		@Override public Rectangle.Double getBoundingBox() {

			return null;
		}

		@Override public SelectionType getSelectionStatus() {

			return selectionStatus;
		}

		private SplinePoint(Spline2D spline2D, int index) {

			this.spline2D = spline2D;
			this.index = index;
		}

		public Spline2D getSpline2D() {

			return spline2D;
		}

		public int getIndex() {

			return index;
		}

		@Override public void shift(splines.Point p) {

			splines.Point shiftedPoint = getPoint().addValue(p);
			spline2D.getSpline().set(index, shiftedPoint);
		}

		@Override public splines.Point getPoint() {

			return spline2D.getSpline().get(index);
		}

		@Override public boolean equals(Object obj) {

			if (obj instanceof  SplinePoint) {
				SplinePoint p2 = (SplinePoint) obj;
				return p2.getSpline2D() == getSpline2D() && p2.getIndex() == getIndex();
			} else {
				return false;
			}
		}
	}

	public void addSelectionObserver(SelectionObserver observer) {
		splineSelecter.attachSelectionObserver(observer);
		((InfoPanel)observer).setDelegate(this);
	}

	public void removeSelectionObserver(SelectionObserver observer) {
		splineSelecter.detachSelectionObserver(observer);
	}

}
