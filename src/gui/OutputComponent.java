package gui;

import intersection.Point;
import intersection.PolyChecker;
import intersection.Segment;
import splines.LinearSpline;
import splines.Spline;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.beans.Transient;
import java.util.ArrayList;
import java.util.List;

public class OutputComponent
		extends JComponent {

	private static final Color[] COLORS = { Color.WHITE, Color.BLUE, Color.MAGENTA, Color.ORANGE, Color.PINK, Color.YELLOW };
	private static final Color[] SELECTION_COLORS = { Color.GREEN, Color.RED};
	private static final int SELECTION_THICKNESS = 4;
	private static final double SELECT_SENSITIVITY = 10;
	public static final int INDICATOR_SIZE = 16;
	public static final int DEFAULT_THICKNESS = 2;
	private int colorCounter = 0;
	private ShowCoordinates showCoo;
	private List<List<Shape>> shapes = new ArrayList<>();
	private List<List<Segment>> polygons = new ArrayList<>();
	private Spline2D currentSpline = null;
	private Shape shapeStartIndicator = null;
	private List<Integer> selectedShapes1 = new ArrayList<>();
	private List<Integer> selectedShapes2 = new ArrayList<>();
	private List<List<Point>> intersections = new ArrayList<>();
	private Integer hover = -1;
	private Point2D mousePoint = new Point2D.Double(0, 0);
	private String mouseText = "";
	private int pressedButton = 0;
	private boolean drawLines = true;
	private int myWidth;
	private int myHeight;
	private JPanel scroll = null;
	private Font font = new Font("Arial", Font.BOLD, 15);
	private List<Spline2D> nonSelectedSplines = new ArrayList<>();
	private List<List<Spline2D>> selectedSplines = new ArrayList<>();
	private DraggingPoint draggedPoint = null;
	private SplineType currentSplineType = SplineType.LINEAR;
	private boolean showControlPointCoords = true;

	public OutputComponent() {

		showCoo = new ShowCoordinates(this);

		selectedSplines.add(new ArrayList<Spline2D>());
		selectedSplines.add(new ArrayList<Spline2D>());

		// test
		List<DoublePoint> testControlPoints = new ArrayList<>();
		testControlPoints.add(new DoublePoint(50, 50));
		testControlPoints.add(new DoublePoint(100, 100));
		testControlPoints.add(new DoublePoint(50, 100));
		testControlPoints.add(new DoublePoint(200, 200));
		Spline testSpline = new LinearSpline();
		testSpline.addAll(testControlPoints);
		Spline2D spline2D = new Spline2D(testSpline, SplineType.LINEAR);
		spline2D.setColor(Color.GREEN);
		spline2D.setThickness(2);
		nonSelectedSplines.add(spline2D);
		// end test

		MouseAdapter mouseAdapter = new MouseAdapter() {

			public void mousePressed(MouseEvent e) {

				boolean
						isInside =
						e.getPoint().getX() >= 0 && e.getPoint().getY() >= 0 && e.getPoint().getX() <= myWidth
						&& e.getPoint().getY() <= myHeight;
				if (drawLines) {
					if (e.getButton() == MouseEvent.BUTTON1 && isInside) {
						pressedButton = 1;
						Point2D p;
						boolean show = showIndicator(e);
						boolean first = false;
						if (currentSpline == null) {
							currentSpline = new Spline2D(currentSplineType.createInstance(), currentSplineType);
							currentSpline.setColor(COLORS[colorCounter]);
							colorCounter = (colorCounter+1)%COLORS.length;
							nonSelectedSplines.add(currentSpline);
							scroll.add(new JLabel("Currently drawn ..."));
							first = true;
						}
						if (show && !first) {
							currentSpline.getSpline().add(new DoublePoint(currentSpline.getSpline().get(0)));
							currentSpline = null;
						} else {
							currentSpline.getSpline().add(new DoublePoint(e.getPoint()));
						}
					} else if (e.getButton() == MouseEvent.BUTTON3 && isInside) {
						pressedButton = 2;
						currentSpline = null;
					}
				} else if (isInside) {
					boolean changed;
					boolean multiSelect = ((e.getModifiers() & MouseEvent.SHIFT_MASK)
										   == MouseEvent.SHIFT_MASK);
					List<Spline2D>
							selection =
							selectedSplines.get(((e.getModifiers() & MouseEvent.CTRL_MASK) == MouseEvent.CTRL_MASK) ? 1 : 0);
					DoublePoint mousePos = new DoublePoint(e.getPoint());

					changed = checkSelection(nonSelectedSplines, selection, mousePos, multiSelect);
					for (int i = 0; i < selectedSplines.size() && !changed; i++) {
						changed = checkSelection(selectedSplines.get(i), selection, mousePos, multiSelect);
					}

					if (!changed) {
						for (int i = 0; i < selectedSplines.size(); i++) {
							List<Spline2D> selectioni = selectedSplines.get(i);
							while (!selectioni.isEmpty()) {
								nonSelectedSplines.add(selectioni.remove(0));
							}
						}
						intersections.clear();
					}
					if (selectedShapes1.size() > 0) {
						intersections.clear();
						for (int i = 0; i < selectedShapes1.size(); i++) {
							for (int j = i + 1; j < selectedShapes1.size(); j++) {
								//								System.out.println(polygons.get(selectedShapes1.get(i))+" "+polygons.get(selectedShapes1.get(j)));
								intersections.add(PolyChecker.findIntersectionPoints(polygons.get(selectedShapes1.get(i)),
																					 polygons.get(selectedShapes1.get(j))));
							}
						}
					}
				}
				repaint();
			}

			public void mouseReleased(MouseEvent e) {

				pressedButton = 0;
				if (showIndicator(e) && currentSpline != null && currentSpline.getSpline().size() > 1) {
					currentSpline = null;
				}
				draggedPoint = null;
				repaint();
			}

			public void mouseExited(MouseEvent e) {

				showCoo.resetLabel();
			}

			public void mouseDragged(MouseEvent e) {

				boolean
						isInside =
						e.getPoint().getX() >= 0 && e.getPoint().getY() >= 0 && e.getPoint().getX() <= myWidth
						&& e.getPoint().getY() <= myHeight;
				if (pressedButton == 1 && isInside) {
					Spline spline = currentSpline.getSpline();
					if (showIndicator(e)) {
						spline.set(spline.size() - 1, new DoublePoint( spline.get(0)));
					} else {
						spline.set(spline.size() - 1, new DoublePoint(e.getPoint()));
					}
					showCoo.update(e.getX(), e.getY());

					repaint();
				}
				if (draggedPoint != null) {
					Spline2D spline2d = draggedPoint.getSpline2D();
					if (spline2d.isClosed() && ( draggedPoint.index == 0 || draggedPoint.index == spline2d.getSpline().size()-1)) {
						spline2d.getSpline().set(0, new DoublePoint(e.getPoint()));
						spline2d.getSpline().set(spline2d.getSpline().size()-1, new DoublePoint(e.getPoint()));
					} else{
						spline2d.getSpline().set(draggedPoint.getIndex(), new DoublePoint(e.getPoint()));
					}

					repaint();
				}
			}

			public void mouseMoved(MouseEvent e) {

				showCoo.update(e.getX(), e.getY());
				showIndicator(e);
				mousePoint = e.getPoint();
				mouseText = "";
				for (int s = 0; s < shapes.size(); s++) {
					for (Shape line : shapes.get(s)) {
						double length = ((Line2D) line).getP1().distance(((Line2D) line).getP2());
						double
								mouseDistance =
								((Line2D) line).getP1().distance(e.getPoint()) + ((Line2D) line).getP2().distance(e.getPoint());
						if (mouseDistance <= length + 1) {
							hover = s;
							if (selectedShapes1.size() > 0 && selectedShapes1.contains(s)) {
								boolean inside = false;
								boolean inside2 = false;
								boolean intersecting = false;
								List<Segment> merged = new ArrayList<Segment>();
								List<Segment> merged2 = new ArrayList<Segment>();
								for (Integer i : selectedShapes1) {
									if (i != hover) {
										if (PolyChecker.intersectionCheck(polygons.get(hover), polygons.get(i))) {
											intersecting = true;
										} else if (!PolyChecker.insideCheck(polygons.get(hover), polygons.get(i))) {
											inside = true;
										}
									}

								}
								if (selectedShapes2.size() > 0) {
									for (Integer j : selectedShapes2) {
										if (PolyChecker.intersectionCheck(polygons.get(hover), polygons.get(j))) {
										} else if (PolyChecker.insideCheck(polygons.get(hover), polygons.get(j))) {
											inside2 = true;
										}
									}
								}
								merged = polygons.get(selectedShapes1.get(0));
								for (int m = 1; m < selectedShapes1.size(); m++) {
									List<List<Segment>> comb = new ArrayList<List<Segment>>();
									comb.add(merged);
									comb.add(polygons.get(m));
									merged = PolyChecker.polygoncreator(comb);
								}
								if (inside2) {
									merged2 = polygons.get(selectedShapes2.get(0));
									for (int m = 1; m < selectedShapes2.size(); m++) {
										List<List<Segment>> comb = new ArrayList<List<Segment>>();
										comb.add(merged2);
										comb.add(polygons.get(m));
										merged2 = PolyChecker.polygoncreator(comb);
									}
								}
								if (intersecting) {
									mouseText += "intersecting, combined area: " + PolyChecker.area(merged);
								} else if (inside) {
									mouseText += "inside";
								} else {
									if (inside2) {
										mouseText +=
												"outside, area: " + (PolyChecker.area(polygons.get(hover)) - PolyChecker.area(merged2));
									} else {
										mouseText += "outside";
									}

								}
							}
						}
					}
				}
				repaint();
			}

			private boolean showIndicator(MouseEvent e) {

				if (currentSpline != null && Math.abs(e.getX() - currentSpline.getSpline().get(0).getX()) < SELECT_SENSITIVITY
					&& Math.abs(e.getY() - currentSpline.getSpline().get(0).getY()) < SELECT_SENSITIVITY) {
					if (shapeStartIndicator == null) {
						shapeStartIndicator =
								new Ellipse2D.Double(currentSpline.getSpline().get(0).getX() - INDICATOR_SIZE/2,
													 currentSpline.getSpline().get(0).getY() - INDICATOR_SIZE/2,
													 INDICATOR_SIZE,
													 INDICATOR_SIZE);
					}
					return true;
				} else if (shapeStartIndicator != null) {
					shapeStartIndicator = null;
				}
				return false;
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

	private boolean checkSelection(List<Spline2D> checkedSelection, List<Spline2D> selection, DoublePoint mousePos, boolean multiSelect) {

		boolean changed = false;
		for (int s = 0; s < checkedSelection.size() && !changed; s++) {
			Spline2D spline2d = checkedSelection.get(s);
			if (spline2d.getSpline().getInterval().contains(mousePos, SELECT_SENSITIVITY)) {
				Spline spline = spline2d.getSpline();
				for (int i = 0; i < spline.size() && !changed; i++) {
					splines.Point point = spline.get(i);
					if (Math.abs(point.getX() - mousePos.getX()) < SELECT_SENSITIVITY
						&& Math.abs(point.getY() - mousePos.getY()) < SELECT_SENSITIVITY) {
						if (selection.contains(spline2d)) {
							draggedPoint = new DraggingPoint(spline2d, i);
						} else {
							if (multiSelect) {
								selection.add(checkedSelection.remove(s));
							} else {
								for (int j = 0; j < selectedSplines.size(); j++) {
									List<Spline2D> spline2Ds =  selectedSplines.get(j);
									while (!spline2Ds.isEmpty()) {
										nonSelectedSplines.add(spline2Ds.remove(0));
									}
								}
								selection.add(checkedSelection.remove(s));
							}
						}
						changed = true;
					}
				}
			}
		}
		return changed;
	}

	public boolean isDrawLines() {

		return drawLines;
	}

	public void setDrawLines(boolean drawLines) {

		this.drawLines = drawLines;
		if (!drawLines && currentSpline != null) {
			currentSpline = null;
		}
	}

	public void clear() {

		shapes.clear();
		polygons.clear();
		currentSpline = null;
		nonSelectedSplines.clear();
		for (int i = 0; i < selectedSplines.size(); i++) {
			selectedSplines.get(i).clear();
		}
		selectedShapes1.clear();
		selectedShapes2.clear();
		intersections.clear();
		scroll.removeAll();
		repaint();
	}

	public void setScrollPanel(JPanel scroll) {

		this.scroll = scroll;
	}

	protected void paintComponent(Graphics g) {

		Graphics2D g2d = (Graphics2D) g;
		SplineRenderer splineRenderer = new SplineRenderer(g2d);

		g2d.setBackground(Color.BLACK);
		g2d.clearRect(0, 0, getWidth(), getHeight());
		g2d.setStroke(new BasicStroke(2));
		int shapeCount = 0;
		for (int i = 0; i < nonSelectedSplines.size(); i++) {
			Spline2D spline2d = nonSelectedSplines.get(i);
			g2d.setStroke(new BasicStroke(DEFAULT_THICKNESS));
			g2d.setPaint(spline2d.getColor());
			splineRenderer.renderSplineAtPosition(spline2d.getSpline(), 0, 0, false);
		}
		for (int i = 0; i < selectedSplines.size(); i++) {
			List<Spline2D> selection = selectedSplines.get(i);
			for (int j = 0; j < selection.size(); j++) {
				Spline2D spline2d = selection.get(j);
				g2d.setStroke(new BasicStroke(SELECTION_THICKNESS));
				g2d.setPaint(SELECTION_COLORS[i]);
				splineRenderer.renderSplineAtPosition(spline2d.getSpline(), 0, 0, true);
				if (showControlPointCoords) {
					drawCoords(spline2d, g2d);
				}
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

	}

	private void drawCoords(Spline2D spline2d, Graphics2D g2d) {

		g2d.setFont(font);
		FontMetrics metric = g2d.getFontMetrics(font);
		for (splines.Point point : spline2d.getSpline()) {
			Color c = g2d.getColor();
			g2d.setPaint(Color.WHITE);
			String label = "(" + (int) point.getX() + ", " + (int) point.getY() + ")";
			int length = metric.stringWidth(label);
			g2d.drawString(label, ((float) point.getX()) - length / 2, ((float) point.getY()) + 20);
			g2d.setPaint(c);
		}
	}

	@Override
	@Transient
	public Dimension getPreferredSize() {

		myWidth = getParent().getWidth();
		myHeight = getParent().getHeight();
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
			;
		}
	}

	public void addShapes(List<Shape> shape) {

		if (currentSpline != null) {
			currentSpline = null;
		}
		Spline spline = currentSplineType.createInstance();
		for (int i = 0; i < shape.size(); i++) {
			Line2D.Double line = (Line2D.Double) shape.get(i);
			spline.add(new DoublePoint(line.getP1()));
		}
		Spline2D spline2d = new Spline2D(spline, currentSplineType);
		spline2d.setColor(COLORS[colorCounter]);
		colorCounter = (colorCounter + 1) % COLORS.length;
		nonSelectedSplines.add(spline2d);
		repaint();
	}

	private class DraggingPoint {
		private Spline2D spline2D;
		private int index;

		private DraggingPoint(Spline2D spline2D, int index) {

			this.spline2D = spline2D;
			this.index = index;
		}

		public Spline2D getSpline2D() {

			return spline2D;
		}

		public int getIndex() {

			return index;
		}
	}
}
