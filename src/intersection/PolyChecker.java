package intersection;

import intersection.Intersection;
import intersection.IntersectionAlgorithm;
import intersection.Point;
import intersection.Segment;
import intersection.SimpleAlgorithm;

import java.util.ArrayList;
import java.util.List;

public class PolyChecker {

	private static IntersectionAlgorithm intersecter = new SimpleAlgorithm();

	public static void main(String[] args) {
		List<Segment> poly1 = new ArrayList<Segment>();
		poly1.add(new Segment(new Point(1, 3), new Point(4, 6)));
		poly1.add(new Segment(new Point(4, 6), new Point(7, 3)));
		poly1.add(new Segment(new Point(7, 3), new Point(4, 1)));
		poly1.add(new Segment(new Point(4, 1), new Point(1, 3)));
		List<Segment> poly2 = new ArrayList<Segment>();
		poly2.add(new Segment(new Point(3, 4), new Point(4, 5)));
		poly2.add(new Segment(new Point(4, 5), new Point(5, 4)));
		poly2.add(new Segment(new Point(5, 4), new Point(4, 3)));
		poly2.add(new Segment(new Point(4, 3), new Point(3, 4)));

		/*
		 * List<Segment> poly3 = new ArrayList<Segment>();
		 * poly3.add(new Segment(new Point(1, 1), new Point(9, 1)));
		 * poly3.add(new Segment(new Point(9, 1), new Point(9, 7)));
		 * poly3.add(new Segment(new Point(9, 7), new Point(1, 7)));
		 * poly3.add(new Segment(new Point(1, 7), new Point(1, 1)));
		 * List<Segment> poly4 = new ArrayList<Segment>();
		 * poly4.add(new Segment(new Point(3, 2), new Point(6, 2)));
		 * poly4.add(new Segment(new Point(6, 2), new Point(6, 4)));
		 * poly4.add(new Segment(new Point(6, 4), new Point(3, 4)));
		 * poly4.add(new Segment(new Point(3, 4), new Point(3, 2)));
		 * List<Segment> poly5 = new ArrayList<Segment>();
		 * poly5.add(new Segment(new Point(2, 3), new Point(5, 3)));
		 * poly5.add(new Segment(new Point(5, 3), new Point(5, 6)));
		 * poly5.add(new Segment(new Point(5, 6), new Point(2, 6)));
		 * poly5.add(new Segment(new Point(2, 6), new Point(2, 3)));
		 * List<List> polylist = new ArrayList<List>();
		 * polylist.add(poly4);
		 * polylist.add(poly5);
		 * List<Segment> polynew = new ArrayList<Segment>();
		 * polynew = polygoncreator(polylist);
		 * System.out.println(polynew);
		 * System.out.println(area(polynew));
		 */
		// System.out.println(area(poly5));
		System.out.println(insideCheck(poly1, poly2));

		/*
		 * System.out.println(area(poly3));
		 * System.out.println(parameter(poly4));
		 */

	}

	public static List<Point> findIntersectionPoints(List<Segment> poly1, List<Segment> poly2) {
		List<Intersection> intersections = intersecter.findIntersections(poly1, poly2);

		List<Point> intersectionPoints = new ArrayList<Point>();
		for (Intersection inter : intersections) {
			intersectionPoints.add(inter.getIntersectionPoint());
		}
		return intersectionPoints;
	}

	public static boolean intersectionCheck(List<Segment> poly1, List<Segment> poly2) {
		List<Intersection> intersections = intersecter.findIntersections(poly1, poly2);
		return !intersections.isEmpty();
	}

	public static double area(List<Segment> arraylist) {
		double lowestnumber = arraylist.get(0)
										.getLeftPoint()
										.getY();
		for (int i = 0; i < arraylist.size(); i++) {
			if (arraylist.get(i)
							.getStart()
							.getY() < lowestnumber) {
				lowestnumber = arraylist.get(i)
										.getStart()
										.getY();
			}
		}
		double g = 0;
		if (lowestnumber < 0) {
			g = lowestnumber * -1;
		}

		int cntr = 0;
		double area = 0;
		while (arraylist.size() != cntr) {
			double x1 = arraylist.get(cntr)
									.getStart()
									.getX() + g;
			double y1 = arraylist.get(cntr)
									.getStart()
									.getY() + g;
			double x2 = arraylist.get(cntr)
									.getEnd()
									.getX() + g;
			double y2 = arraylist.get(cntr)
									.getEnd()
									.getY() + g;
			area = area + segmentArea(x1, y1, x2, y2);
			cntr++;
		}
		if (area < 0) {
			area = area * -1;
		}
		return area;
	}

	public static double segmentArea(double x1, double y1, double x2, double y2) {
		double averageHeight = (y1 + y2) / 2;
		double width = x2 - x1;
		double segmentArea = averageHeight * width;
		return segmentArea;
	}

	public static double parameter(List<Segment> arraylist) {
		int cntr = 0;
		double parameter = 0;
		while (arraylist.size() != cntr) {
			double x1 = arraylist.get(cntr)
									.getLeftPoint()
									.getX();
			double y1 = arraylist.get(cntr)
									.getLeftPoint()
									.getY();
			double x2 = arraylist.get(cntr)
									.getRightPoint()
									.getX();
			double y2 = arraylist.get(cntr)
									.getRightPoint()
									.getY();
			parameter = parameter + lineLength(x1, y1, x2, y2);
			cntr++;
		}
		return parameter;
	}

	public static double lineLength(double x1, double y1, double x2, double y2) {
		double xlength = x1 - x2;
		double ylength = y1 - y2;
		if (xlength < 0)
			xlength = xlength * -1;
		if (ylength < 0)
			ylength = ylength * -1;
		double linelength = Math.sqrt((xlength * xlength) + (ylength * ylength));
		return linelength;
	}

	public static boolean insideCheck(List<Segment> outside, List<Segment> inside) {
		boolean answer = false;

		/*
		 * for(int i = 0;i<temp.size();i++){
		 * System.out.println(temp.get(i));
		 * }
		 */

		List<Intersection> intersections = intersecter.findIntersections(outside, inside);

		/*
		 * for (Intersection intersection : intersections) {
		 * System.out.println(intersection.getIntersectionPoint());
		 * }
		 */
		System.out.println(intersections.size());
		int intersectsize = intersections.size();

		Point maximumX = outside.get(0)
								.getRightPoint();
		for (int i = 1; i < outside.size(); i++) {
			if (outside.get(i)
						.getRightPoint()
						.getX() > maximumX.getX()) {
				maximumX = outside.get(i)
									.getRightPoint();
			}
		}
		Point insidePoint = new Point(inside.get(0)
											.getLeftPoint());
		Point insidePoint2 = new Point(inside.get(0)
												.getRightPoint());
		Point newpoint = new Point(maximumX.getX() + 1, maximumX.getY());
		Segment checkLine = new Segment(insidePoint, newpoint);
		Segment checkLine2 = new Segment(insidePoint2, newpoint);
		List<Segment> temp2 = new ArrayList<Segment>();
		List<Segment> temp3 = new ArrayList<Segment>();
		for (int i = 0; i < outside.size(); i++) {
			temp2.add(outside.get(i));
			temp3.add(outside.get(i));
		}
		temp2.add(checkLine);
		temp3.add(checkLine2);
		for (int i = 0; i < temp2.size(); i++) {
			System.out.println(temp2.get(i));
		}
		List<Intersection> intersections2 = intersecter.findIntersections(temp2);

		List<Intersection> intersections42 = intersecter.findIntersections(temp3);
		/*
		 * System.out.println(intersections2.size());
		 * System.out.println(intersections2.get(0));
		 * System.out.println(intersections2.get(0).getIntersectionPoint());
		 * System.out.println(intersections42.size());
		 * System.out.println(intersections42.get(0));
		 * System.out.println(intersections42.get(0).getIntersectionPoint());
		 */
		int intersecsize2 = intersections2.size();
		int intersecsize42 = intersections42.size();
		if (intersecsize2 % 2 == 0 && intersecsize42 % 2 == 0) {
			answer = false;
		} else {
			answer = true;
		}

		return answer;
	}

	public static boolean insideCheck(List<Segment> outside, Point toCheck) {
		// System.out.println("point = " + toCheck);
		List<Segment> outsideCopy = new ArrayList<Segment>();

		for (int i = 0; i < outside.size(); i++) {
			outsideCopy.add(outside.get(i));
		}
		boolean answer = false;
		Point maximumX = outsideCopy.get(0)
									.getRightPoint();
		for (int i = 1; i < outsideCopy.size(); i++) {
			if (outsideCopy.get(i)
							.getRightPoint()
							.getX() > maximumX.getX()) {
				maximumX = outsideCopy.get(i)
										.getRightPoint();
			}
		}

		Point newpoint = new Point(maximumX.getX() + 1, maximumX.getY());
		Segment checkLine = new Segment(toCheck, newpoint);
		List<Segment> temp = outside;
		temp.add(checkLine);

		List<Intersection> intersections = intersecter.findIntersections(temp);

		/*
		 * System.out.println(";;;;;;;");
		 * for (Intersection intersection : intersections) {
		 * System.out.println(intersection.getIntersectionPoint());
		 * }
		 */

		if (intersections.size() != 0) {
			answer = false;
		} else {

			if (intersections.size() % 2 == 0) {
				answer = false;
			} else {
				answer = true;
			}
		}
		return answer;
	}

	public static List<Segment> polygoncreator(List<List<Segment>> polygons) {
		List<List<Segment>> polygonsCopy = new ArrayList<List<Segment>>();

		// System.out.println("gg"+polygons);

		List<Segment> masterpoly = new ArrayList<Segment>();
		// System.out.println("gsdfs"+polygons.get(0).get(0));
		for (int i = 0; i < polygons.size(); i++) {
			for (int j = 0; j < polygons.get(i)
										.size(); j++) {
				masterpoly.add((Segment) polygons.get(i)
													.get(j));
			}
		}
		/*
		 * for(int i = 0;i<masterpoly.size();i++){
		 * System.out.println(masterpoly.get(i));
		 * }
		 */

		List<Intersection> intersections = intersecter.findIntersections(masterpoly);
		/*
		 * for (Intersection intersection : intersections) {
		 * System.out.println(intersection.getIntersectionPoint());
		 * }
		 * System.out.println(intersections.size());
		 */
		for (int i = 0; i < masterpoly.size(); i++) {
			for (int j = 0; j < intersections.size(); j++) {
				/*
				 * System.out.println("masterpoly = "+masterpoly.get(i).getStart());
				 * System.out.println("intersections = " +intersections.get(j).getIntersectionPoint());
				 * System.out.println(masterpoly.get(i).getStart().getX()==intersections.get(j).getIntersectionPoint().getX
				 * () && masterpoly.get(i).getStart().getY()==intersections.get(j).getIntersectionPoint().getY());
				 */if (masterpoly.get(i)
									.getStart()
									.getX() == intersections.get(j)
															.getIntersectionPoint()
															.getX() && masterpoly.get(i)
																					.getStart()
																					.getY() == intersections.get(j)
																											.getIntersectionPoint()
																											.getY()) {
					intersections.remove(j);
				}
			}
		}
		/*
		 * System.out.println(intersections.size());
		 * for (Intersection intersection : intersections) {
		 * System.out.println(intersection.getIntersectionPoint());
		 * }
		 */
		for (int i = 0; i < intersections.size(); i++) {
			Segment segment1 = new Segment(intersections.get(i)
														.getIntersectionPoint(), intersections.get(i)
																								.getFirstSegment()
																								.getLeftPoint());
			Segment segment2 = new Segment(intersections.get(i)
														.getIntersectionPoint(), intersections.get(i)
																								.getFirstSegment()
																								.getRightPoint());
			Segment segment3 = new Segment(intersections.get(i)
														.getIntersectionPoint(), intersections.get(i)
																								.getSecondSegment()
																								.getLeftPoint());
			Segment segment4 = new Segment(intersections.get(i)
														.getIntersectionPoint(), intersections.get(i)
																								.getSecondSegment()
																								.getRightPoint());
			masterpoly.add(segment1);
			masterpoly.add(segment2);
			masterpoly.add(segment3);
			masterpoly.add(segment4);
		}

		/*
		 * System.out.println("========="); for(int i =
		 * 0;i<masterpoly.size();i++){ System.out.println(masterpoly.get(i)); }
		 */

		for (int j = 0; j < polygons.size(); j++) {
			int removecounter = 0;
			List<Segment> array = new ArrayList<Segment>();
			for (int p = 0; p < polygons.get(j)
										.size(); p++) {

				array.add((Segment) polygons.get(j)
											.get(p));
			}
			System.out.println("array = " + array);
			int fuckingMasterPolySize = masterpoly.size();
			for (int i = fuckingMasterPolySize - 1; i > 0; i--) {
				boolean removed = false;
				for (int k = 0; k < polygons.get(j)
											.size(); k++) {
					if (removed == true) {

					} else {
						/*
						 * if ((masterpoly.get(i).getLeftPoint() == array.get(k).getLeftPoint() ||
						 * masterpoly.get(i).getLeftPoint() == array.get(k).getRightPoint())&&
						 * (masterpoly.get(i).getRightPoint() == array.get(k).getLeftPoint() ||
						 * masterpoly.get(i).getRightPoint() == array.get(k).getRightPoint())
						 * ) {
						 * System.out.println(true);
						 * System.out.println(masterpoly.get(i));
						 * } else
						 */{
							Point maximumX = array.get(0)
													.getRightPoint();

							for (int l = 1; l < array.size(); l++) {
								if (array.get(l)
											.getRightPoint()
											.getX() > maximumX.getX()) {
									maximumX = array.get(l)
													.getRightPoint();
								}
							}

							Point insidePoint = new Point(masterpoly.get(i)
																	.getStart()
																	.getX(), masterpoly.get(i)
																						.getStart()
																						.getY());
							Point insidePoint2 = new Point(masterpoly.get(i)
																		.getEnd()
																		.getX(), masterpoly.get(i)
																							.getEnd()
																							.getY());
							System.out.println("the coordinate being checked = " + masterpoly.get(i));
							// System.out.println("j = "+j);
							Point newpoint1 = new Point(maximumX.getX() + 1, maximumX.getY());
							Point newpoint2 = new Point(maximumX.getX() + 1, maximumX.getY());
							Segment checkLine = new Segment(insidePoint, newpoint1);
							Segment checkLine2 = new Segment(insidePoint2, newpoint2);
							List<Segment> temp2 = new ArrayList<Segment>();
							List<Segment> temp3 = new ArrayList<Segment>();
							for (int b = 0; b < array.size(); b++) {
								temp2.add(array.get(b));
								temp3.add(array.get(b));
							}
							temp2.add(checkLine);
							temp3.add(checkLine2);
							for (int c = 0; c < temp2.size(); c++) {
								System.out.println(temp2.get(c));
							}
							List<Intersection> intersections2 = intersecter.findIntersections(temp2);
							List<Intersection> intersections3 = intersecter.findIntersections(temp3);
							System.out.println("the amount of intersections = " + intersections2.size());
							System.out.println("the amount of intersections = " + intersections3.size());
							int intersecsize2 = intersections2.size();
							int intersecsize3 = intersections3.size();
							if (intersecsize2 != 0)
								System.out.println(intersections2.get(0)
																	.getIntersectionPoint());
							if (intersecsize2 > 1)
								System.out.println(intersections2.get(1)
																	.getIntersectionPoint());
							if (intersecsize3 != 0)
								System.out.println(intersections3.get(0)
																	.getIntersectionPoint());
							if (intersecsize2 % 2 != 0 && intersecsize3 % 2 != 0) {
								System.out.println("the removed segment ranges from " + masterpoly.get(i)
																									.getStart() + " to "
													+ masterpoly.get(i)
																.getEnd());
								System.out.println();
								masterpoly.remove(i);
								removed = true;
								removecounter++;
							}

						}

					}

				}
			}
		}
		for (int i = 0; i < intersections.size(); i++) {
			masterpoly.remove(intersections.get(i)
											.getFirstSegment());
			masterpoly.remove(intersections.get(i)
											.getSecondSegment());
		}
		int size = masterpoly.size();
		List<Segment> orderedMasterpoly = new ArrayList<Segment>();
		orderedMasterpoly.add(masterpoly.remove(0));
		for (int i = 1; i < size; i++) {
			boolean found = false;
			Point pointOfInterest = orderedMasterpoly.get(i - 1)
														.getSecondPoint();
			for (Segment s : masterpoly) {
				if (pointOfInterest.getX() == s.getFirstPoint()
												.getX() && pointOfInterest.getY() == s.getFirstPoint()
																						.getY()) {
					masterpoly.remove(s);
					orderedMasterpoly.add(s);
					found = true;
					break;
				} else if (pointOfInterest.getX() == s.getSecondPoint()
														.getX() && pointOfInterest.getY() == s.getSecondPoint()
																								.getY()) {
					masterpoly.remove(s);
					orderedMasterpoly.add(new Segment(s.getSecondPoint(), s.getFirstPoint()));
					found = true;
					break;
				}
			}
			if (!found)
				break;
		}
		return orderedMasterpoly;
	}
}
