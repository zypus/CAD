package gui;

import intersection.Point;
import intersection.PolyChecker;
import intersection.Segment;
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
	ShowCoordinates showCoo;
	private List<List<Shape>> shapes = new ArrayList<List<Shape>>();
	private List<List<Segment>> polygons = new ArrayList<List<Segment>>();
	private List<Shape> currentShape = null;
	private Shape shapeStartIndicator = null;
	private List<Integer> selectedShapes1 = new ArrayList<Integer>();
	private List<Integer> selectedShapes2 = new ArrayList<Integer>();
	private List<List<Point>> intersections = new ArrayList<List<Point>>();
	private Integer hover = -1;
	private Point2D mousePoint = new Point2D.Double(0, 0);
	private String mouseText = "";
	private int pressedButton = 0;
	private boolean drawLines = true;
	private int myWidth;
	private int myHeight;
	private JPanel scroll = null;
	private Font font = new Font("Arial", Font.BOLD, 15);
	private List<Spline> nonSelectedSplines = new ArrayList<>();
	private List<Spline> selectedSplines = new ArrayList<>();

	public OutputComponent() {

		showCoo = new ShowCoordinates(this);

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
						if (currentShape == null) {
							currentShape = new ArrayList<Shape>();
							shapes.add(currentShape);
							p = e.getPoint();
							scroll.add(new JLabel("Currently drawn ..."));
							first = true;
						} else {
							p = ((Line2D) currentShape.get(currentShape.size() - 1)).getP2();
						}
						if (show && !first) {
							currentShape.add(new Line2D.Double(p, ((Line2D) currentShape.get(0)).getP1()));
							polygons.add(createPoly(currentShape));
							currentShape = null;
						} else {
							currentShape.add(new Line2D.Double(p, e.getPoint()));
						}
					} else if (e.getButton() == MouseEvent.BUTTON3 && isInside) {
						pressedButton = 2;
						polygons.add(createPoly(currentShape));
						currentShape = null;
					}
				} else {
					boolean changed = false;
					for (int s = 0; s < shapes.size(); s++) {
						for (Shape line : shapes.get(s)) {
							double length = ((Line2D) line).getP1().distance(((Line2D) line).getP2());
							double
									mouseDistance =
									((Line2D) line).getP1().distance(e.getPoint()) + ((Line2D) line).getP2().distance(e.getPoint());
							if (mouseDistance <= length + 1) {
								changed = true;
								if (((e.getModifiers() & MouseEvent.CTRL_MASK) == MouseEvent.CTRL_MASK)) {
									if (selectedShapes2.size() > 0 && !((e.getModifiers() & MouseEvent.SHIFT_MASK)
																		== MouseEvent.SHIFT_MASK)) {
										selectedShapes2.clear();
									}
									//									if (selectedShapes1.contains(s)) {
									//										selectedShapes1.remove(s);
									//									}
									if (selectedShapes2.contains(s)) {
										selectedShapes2.remove(s);
									} else {
										selectedShapes2.add(s);
									}
								} else {
									if (selectedShapes1.size() > 0 && !((e.getModifiers() & MouseEvent.SHIFT_MASK)
																		== MouseEvent.SHIFT_MASK)) {
										selectedShapes1.clear();
									}
									//									if (selectedShapes2.contains(s)) {
									//										selectedShapes2.remove(s);
									//									}
									if (selectedShapes1.contains(s)) {
										selectedShapes1.remove(s);
									} else {
										selectedShapes1.add(s);
									}
								}
							}
						}
					}
					if (!changed) {
						selectedShapes1.clear();
						selectedShapes2.clear();
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
				if (showIndicator(e) && currentShape != null && currentShape.size() > 1) {
					currentShape = null;
				}
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
					if (showIndicator(e)) {
						Line2D shape = (Line2D) currentShape.get(currentShape.size() - 1);
						shape.setLine(shape.getP1(), ((Line2D) currentShape.get(0)).getP1());
					} else {
						Line2D shape = (Line2D) currentShape.get(currentShape.size() - 1);
						shape.setLine(shape.getP1(), e.getPoint());
					}
					showCoo.update(e.getX(), e.getY());

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
											//											if (merged.isEmpty()) {
											//												List<List<Segment>> comb = new ArrayList<List<Segment>>();
											//												comb.add(polygons.get(i));
											//												comb.add(polygons.get(hover));
											//												merged = intersection.PolyChecker.polygoncreator(comb);
											//											}
											//											else {
											//												List<List<Segment>> comb = new ArrayList<List<Segment>>();
											//												comb.add(merged);
											//												comb.add(polygons.get(i));
											//												merged = intersection.PolyChecker.polygoncreator(comb);
											//											}
										} else if (!PolyChecker.insideCheck(polygons.get(hover), polygons.get(i))) {
											inside = true;
										}
									}

								}
								if (selectedShapes2.size() > 0) {
									for (Integer j : selectedShapes2) {
										if (PolyChecker.intersectionCheck(polygons.get(hover), polygons.get(j))) {
											//											intersecting = true;
											//											if (merged.isEmpty()) {
											//												List<List<Segment>> comb = new ArrayList<List<Segment>>();
											//												comb.add(polygons.get(hover));
											//												comb.add(polygons.get(i));
											//												merged = intersection.PolyChecker.polygoncreator(comb);
											//											}
											//											else {
											//												List<List<Segment>> comb = new ArrayList<List<Segment>>();
											//												comb.add(merged);
											//												comb.add(polygons.get(i));
											//												merged = intersection.PolyChecker.polygoncreator(comb);
											//											}
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

				if (currentShape != null && Math.abs(e.getX() - ((Line2D) currentShape.get(0)).getX1()) < 10
					&& Math.abs(e.getY() - ((Line2D) currentShape.get(0)).getY1()) < 10) {
					if (shapeStartIndicator == null) {
						shapeStartIndicator =
								new Ellipse2D.Double(((Line2D) currentShape.get(0)).getX1() - 8,
													 ((Line2D) currentShape.get(0)).getY1() - 8,
													 16,
													 16);
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

	public boolean isDrawLines() {

		return drawLines;
	}

	public void setDrawLines(boolean drawLines) {

		this.drawLines = drawLines;
		if (!drawLines && currentShape != null) {
			polygons.add(createPoly(currentShape));
			currentShape = null;
		}
	}

	public void clear() {

		shapes.clear();
		polygons.clear();
		currentShape = null;
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
		g2d.setBackground(Color.BLACK);
		g2d.clearRect(0, 0, getWidth(), getHeight());
		int colorCounter = 0;
		g2d.setStroke(new BasicStroke(2));
		boolean selected = false;
		g2d.setFont(font);
		FontMetrics metric = g2d.getFontMetrics(font);
		int shapeCount = 0;
		for (int s = 0; s < shapes.size(); s++) {
			if (!selectedShapes1.isEmpty() && selectedShapes1.contains(s)) {
				g2d.setStroke(new BasicStroke(4));
				g2d.setPaint(Color.GREEN);
				selected = true;
			} else if (!selectedShapes2.isEmpty() && selectedShapes2.contains(s)) {
				g2d.setStroke(new BasicStroke(4));
				g2d.setPaint(Color.RED);
				selected = true;
			} else {
				g2d.setStroke(new BasicStroke(2));
				g2d.setPaint(COLORS[colorCounter % COLORS.length]);
				selected = false;
			}
			int centerX = 0;
			int centerY = 0;
			int counter = 0;
			boolean closed = true;
			for (Shape line : shapes.get(s)) {
				g2d.draw(line);
				if (selected) {
					Color c = g2d.getColor();
					g2d.setPaint(Color.WHITE);
					String label = "(" + (int) ((Line2D) line).getX1() + ", " + (int) ((Line2D) line).getY1() + ")";
					int length = metric.stringWidth(label);
					//					Path2D path = new Path2D.Double();
					g2d.drawString(label, (float) ((Line2D) line).getX1() - length / 2, (float) ((Line2D) line).getY1() + 20);
					centerX += ((Line2D) line).getX1();
					centerY += ((Line2D) line).getY1();
					counter++;
					if (line == shapes.get(s).get(shapes.get(s).size() - 1) && !((Line2D) line).getP2()
																							   .equals(((Line2D) shapes.get(s)
																													   .get(0)).getP1())) {
						label = "(" + (int) ((Line2D) line).getX2() + ", " + (int) ((Line2D) line).getY2() + ")";
						length = metric.stringWidth(label);
						g2d.drawString(label, (float) ((Line2D) line).getX2() - length / 2, (float) ((Line2D) line).getY2() + 20);
						centerX += ((Line2D) line).getX2();
						centerY += ((Line2D) line).getY2();
						counter++;
						closed = false;
					}
					g2d.setPaint(c);
				}
			}
			if (selected) {
				//				Color c = g2d.getColor();
				//				g2d.setPaint(Color.BLACK);
				//				g2d.fill(new Rectangle2D.Double(centerX/counter-40, centerY/counter-30, 80, 60));
				//				g2d.setPaint(Color.WHITE);
				//				g2d.setStroke(new BasicStroke(2));
				//				g2d.draw(new Rectangle2D.Double(centerX/counter-40, centerY/counter-30, 80, 60));
				//				int offset = 0;
				//				if (closed) {
				//					String label = "(Area)";
				//					int length = metric.stringWidth(label);
				//					g2d.drawString(label, (float)centerX/counter-length/2, centerY/counter-8);
				//					offset = 16;
				//				}
				//				String label = "<- "+polyLength+" ->";
				//				int length = metric.stringWidth(label);
				//				g2d.drawString(label, (float)centerX/counter-length/2, centerY/counter+offset);
				//				g2d.setPaint(c);
			}
			colorCounter++;
			if (shapeCount < ((currentShape == null) ? scroll.getComponentCount() : scroll.getComponentCount() - 1)) {
				if (!((Line2D) shapes.get(s).get(shapes.get(s).size() - 1)).getP2().equals(((Line2D) shapes.get(s).get(0)).getP1())) {
					((JLabel) scroll.getComponent(shapeCount)).setText(
							"open -> Length: " + (int) PolyChecker.parameter(polygons.get(shapeCount)));
				} else {
					((JLabel) scroll.getComponent(shapeCount)).setText(
							"closed -> Length: " + (int) PolyChecker.parameter(polygons.get(shapeCount)) + " Area: "
							+ (int) PolyChecker.area(polygons.get(shapeCount))
					);
				}

			}
			shapeCount++;
		}
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
		int l = metric.stringWidth(mouseText);
		g2d.drawString(mouseText, (int) mousePoint.getX(), (int) mousePoint.getY());
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

		if (currentShape != null) {
			polygons.add(createPoly(currentShape));
			currentShape = null;
		}
		shapes.add(shape);
		polygons.add(createPoly(shape));
		repaint();
	}
}
