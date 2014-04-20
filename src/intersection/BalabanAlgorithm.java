package intersection;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import debug.Debugger;

public class BalabanAlgorithm
		implements IntersectionAlgorithm {

	private static final boolean logging = true;
	private Debugger debugger = null;

	public static void main(String[] args) {
		/*
		 * List<Segment> poly1 = new ArrayList<Segment>();
		 * poly1.add(new Segment(new Point(1, 3), new Point(4, 6)));
		 * poly1.add(new Segment(new Point(4, 6), new Point(7, 3)));
		 * poly1.add(new Segment(new Point(7, 3), new Point(4, 1)));
		 * poly1.add(new Segment(new Point(4, 1), new Point(1, 3)));
		 * List<Segment> poly2 = new ArrayList<Segment>();
		 * poly2.add(new Segment(new Point(3, 4), new Point(4, 5)));
		 * poly2.add(new Segment(new Point(4, 5), new Point(5, 4)));
		 * poly2.add(new Segment(new Point(5, 4), new Point(4, 3)));
		 * poly2.add(new Segment(new Point(4, 3), new Point(3, 4)));
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
		 * List<Segment> segments = new ArrayList<Segment>(poly4);
		 * segments.addAll(poly5);
		 * BalabanAlgorithm balaban = new BalabanAlgorithm();
		 * List<Intersection> intersections = balaban.intersectingPairs(segments);
		 * System.out.println("Found intersections:");
		 * for (Intersection intersection : intersections) {
		 * System.out.println(intersection.getIntersectionPoint());
		 * }
		 */
	}

	public List<Intersection> findIntersections(List<Segment>... polygons) {
		List<Segment> segments = new ArrayList<Segment>();
		for (List<Segment> polygon : polygons) {
			segments.addAll(polygon);
		}
		return intersectingPairs(segments);
	}

	Map<Float, List<Segment>> s = null;
	List<Point> p = null;
	List<Intersection> intersections = null;

	public List<Intersection> intersectingPairs(List<Segment> segments) {
		int N = segments.size();
		s = new HashMap<Float, List<Segment>>();
		p = new ArrayList<Point>();
		for (Segment segment : segments) {
			p.add(segment.getLeftPoint());
			p.add(segment.getRightPoint());
			segment.setSB(0);
		}
		// TODO: write own sort methode
		Collections.sort(p, new Comparator<Point>() {
			public int compare(Point p1, Point p2) {
				if (p1.getX() < p2.getX()) {
					return -1;
				} else if (p1.getX() > p2.getX()) {
					return 1;
				} else {
					if (p1.getY() < p2.getY()) {
						return -1;
					} else if (p1.getY() > p2.getY()) {
						return 1;
					} else {
						return 0;
					}
				}
			}
		});
		log("p: " + p);
		float xValue = p.get(0)
						.getX();
		s.put(xValue, new ArrayList<Segment>());
		for (Point point : p) {
			if (point.getX() > xValue) {
				xValue = point.getX();
				s.put(xValue, new ArrayList<Segment>());
			}
			if (!s.get(xValue)
					.contains(point.getOwner())) {
				s.get(xValue)
					.add(point.getOwner());
			}
		}
		log("s: " + s);
		List<Segment> Lr = new ArrayList<Segment>();
		Lr.addAll(s.get(p.get(1 - 1)
							.getX()));
		List<Segment> Ir = new ArrayList<Segment>(segments);
		log("segments: " + segments);
		log("s(1): " + s.get(p.get(1 - 1)
								.getX()));
		log("s(2N): " + s.get(p.get(2 * N - 1)
								.getX()));
		Ir.removeAll(s.get(p.get(1 - 1)
							.getX()));
		Ir.removeAll(s.get(p.get(2 * N - 1)
							.getX()));
		intersections = new ArrayList<Intersection>();
		List<Segment> parentStaircase = new ArrayList<Segment>();
		List<Segment> rightBoundedSegments = new ArrayList<Segment>();
		treeSearch(Lr, Ir, parentStaircase, 1, 2 * N, rightBoundedSegments);
		return intersections;
	}

	private int recursionLevel = 0;

	private void treeSearch(List<Segment> leftBoundedSegments, List<Segment> innerSegments, List<Segment> parentStaircase, int leftBound_b,
			int rightBound_e, List<Segment> rightBoundedSegments) {
		log("---------------Entering level " + (++recursionLevel) + " of recursion---------------");
		log("Bounds: " + leftBound_b + " " + rightBound_e);
		log("leftBoundedSegments: " + leftBoundedSegments);
		log("innerSegments: " + innerSegments);
		log("parentStaircase: " + parentStaircase);
		debug(	recursionLevel,
				leftBoundedSegments,
				new ArrayList<Segment>(),
				innerSegments,
				leftBound_b,
				rightBound_e,
				new ArrayList<Segment>(),
				intersections);
		if (rightBound_e - leftBound_b == 1) {
			log("Searching in strip");
			searchInStrip(leftBoundedSegments, leftBound_b, rightBound_e, rightBoundedSegments);
			log("rightBoundedSegments: " + rightBoundedSegments);
			debug(	recursionLevel,
					leftBoundedSegments,
					rightBoundedSegments,
					innerSegments,
					leftBound_b,
					rightBound_e,
					new ArrayList<Segment>(),
					intersections);
			log("--------------Leaving level " + (recursionLevel--) + " of recursion----------------");
			return;
		}
		List<Segment> originalSegments = new ArrayList<Segment>();
		List<Segment> leftChild_leftBoundedSegments = new ArrayList<Segment>();
		newSplit(leftBoundedSegments, parentStaircase, leftBound_b, rightBound_e, originalSegments, leftChild_leftBoundedSegments);
		log("originalSegments: " + originalSegments);
		log("leftChild_leftBoundedSegments: " + leftChild_leftBoundedSegments);
		List<Segment> staircase = new ArrayList<Segment>();
		List<Integer> SB_v = new ArrayList<Integer>();
		buildQSB(originalSegments, parentStaircase, leftBound_b, rightBound_e, staircase, SB_v);
		log("staircase: " + staircase);
		log("SB_v: " + SB_v);
		debug(	recursionLevel,
				leftBoundedSegments,
				new ArrayList<Segment>(),
				innerSegments,
				leftBound_b,
				rightBound_e,
				staircase,
				intersections);
		for (Segment s : leftChild_leftBoundedSegments) {
			int location = findLocation(staircase, s, 0, leftBound_b);
			// s.setSB(0);
			s.setSB(SB_v.get(location));
		}
		log("Looking for intersections with leftChild_leftBoundedSegments");
		for (Intersection inter : findIntersections(staircase, leftChild_leftBoundedSegments, leftBound_b, rightBound_e)) {
			if (!intersections.contains(inter)) {
				intersections.add(inter);
			}
		}
		// intersections.addAll(findIntersections(staircase, leftChild_leftBoundedSegments, leftBound_b, rightBound_e));
		int c = (int) Math.floor((leftBound_b + rightBound_e) / 2);
		log("Split: " + p.get(c - 1)
							.getX() + " bounds: " + leftBound_b + " " + rightBound_e);
		log("" + p.get(c - 1)
					.getOwner());
		List<Segment> leftChild_innerSegments = new ArrayList<Segment>();
		List<Segment> rightChild_innerSegments = new ArrayList<Segment>();
		for (int i = 0; i < innerSegments.size(); i++) {
			Segment inner = innerSegments.get(i);
			if (inner.isInner(p.get(leftBound_b - 1)
								.getX(), p.get(c - 1)
											.getX())) {
				leftChild_innerSegments.add(innerSegments.get(i));
				// i--;
			} else if (inner.isInner(p.get(c - 1)
										.getX(), p.get(rightBound_e - 1)
													.getX())) {
				rightChild_innerSegments.add(innerSegments.get(i));
				// i--;
			}
		}
		log("innerSegments: " + innerSegments);
		log("leftChild_innerSegments: " + leftChild_innerSegments);
		log("rightChild_innerSegments: " + rightChild_innerSegments);
		List<Segment> leftChild_rightBoundedSegments = new ArrayList<Segment>();
		treeSearch(leftChild_leftBoundedSegments, leftChild_innerSegments, staircase, leftBound_b, c, leftChild_rightBoundedSegments);
		List<Segment> rightChild_leftBoundedSegments = new ArrayList<Segment>(leftChild_rightBoundedSegments);
		if (p.get(c - 1)
				.equals(p.get(c - 1)
							.getOwner()
							.getLeftPoint())) {
			System.out.println("TTTTTTTTTTEEEEEEEEEEEESSSSSSSSSSSSTTTTTTTTTTTT");
			for (Segment segment : s.get(p.get(c - 1)
											.getX())) {
				if (!rightChild_leftBoundedSegments.contains(segment)) {
					rightChild_leftBoundedSegments.add(segment);
				}
			}
		} else {
			rightChild_leftBoundedSegments.removeAll(s.get(p.get(c - 1)
															.getX()));
		}
		log("rightChild_leftBoundedSegments: " + rightChild_leftBoundedSegments);
		List<Segment> rightChild_rightBoundedSegments = new ArrayList<Segment>();
		treeSearch(rightChild_leftBoundedSegments, rightChild_innerSegments, staircase, c, rightBound_e, rightChild_rightBoundedSegments);
		for (Segment s : rightChild_rightBoundedSegments) {
			int location = findLocation(staircase, s, 0, leftBound_b);
			// s.setSB(0);
			s.setSB(SB_v.get(location));
		}
		// TODO: look for already found intersections
		log("Looking for intersections with rightChild_rightBoundedSegments");
		for (Intersection inter : findIntersections(staircase, rightChild_rightBoundedSegments, leftBound_b, rightBound_e)) {
			if (!intersections.contains(inter)) {
				intersections.add(inter);
			}
		}
		// intersections.addAll(findIntersections(staircase, rightChild_rightBoundedSegments, leftBound_b,
		// rightBound_e));
		log("SB_v: " + SB_v);
		for (Segment s : innerSegments) {
			int location = findLocation(staircase, s, s.getSB(), leftBound_b);
			// s.setSB(0);
			s.setSB(SB_v.get(location));
		}
		log("Looking for intersections with innerSegments");
		for (Intersection inter : findIntersections(staircase, innerSegments, leftBound_b, rightBound_e)) {
			if (!intersections.contains(inter)) {
				intersections.add(inter);
			}
		}
		// intersections.addAll(findIntersections(staircase, innerSegments, leftBound_b, rightBound_e));
		rightBoundedSegments.addAll(merge(staircase, rightChild_rightBoundedSegments, rightBound_e));
		log("Merged: " + rightBoundedSegments);
		debug(recursionLevel, leftBoundedSegments, rightBoundedSegments, innerSegments, leftBound_b, rightBound_e, staircase, intersections);
		log("--------------Leaving level " + (recursionLevel--) + " of recursion----------------");
	}

	public static void log(String string) {
		if (logging) {
			System.out.println(string);
		}
	}

	private List<Segment> merge(List<Segment> staircase, List<Segment> rightChild_rightBoundedSegments, int rightBound_e) {
		List<Segment> merged = new ArrayList<Segment>();
		int counter1 = 0;
		int counter2 = 0;
		for (int i = 0; i < staircase.size() + rightChild_rightBoundedSegments.size(); i++) {
			if (counter1 >= staircase.size()
				|| (counter2 < rightChild_rightBoundedSegments.size() && staircase.get(counter1)
																					.isAtXBelow(p.get(rightBound_e - 1)
																									.getX(),
																								rightChild_rightBoundedSegments.get(counter2)))) {
				if (!merged.contains(rightChild_rightBoundedSegments.get(counter2))) {
					merged.add(rightChild_rightBoundedSegments.get(counter2));
				}
				counter2++;
				if (counter2 >= rightChild_rightBoundedSegments.size()) {
					counter2 = rightChild_rightBoundedSegments.size() - 1;
				}
			} else {
				if (!merged.contains(staircase.get(counter1))) {
					merged.add(staircase.get(counter1));
				}
				counter1++;
				if (counter1 >= staircase.size()) {
					counter1 = staircase.size() - 1;
				}
			}
		}
		return merged;
	}

	private List<Intersection> findIntersections(List<Segment> staircase, List<Segment> S, int leftBound_b, int rightBound_e) {
		List<Intersection> intersections = new ArrayList<Intersection>();
		log("staircase: " + staircase);
		float leftBoundX = p.get(leftBound_b - 1)
							.getX();
		float rightBoundX = p.get(rightBound_e - 1)
								.getX();
		for (Segment s : S) {
			log("Trying to find intersection between " + staircase + " and " + s);
			int i = s.getSB();
			while (i < staircase.size() && i >= 0 && staircase.get(i)
																.isIntersecting(s)) {
				intersections.add(new Intersection(staircase.get(i), s));
				log("Found intersection: " + new Intersection(staircase.get(i), s));
				i--;
			}
			i = s.getSB() + 1;
			while (i < staircase.size() && staircase.get(i)
													.isIntersecting(s)) {
				intersections.add(new Intersection(staircase.get(i), s));
				log("Found intersection: " + new Intersection(staircase.get(i), s));
				i++;
			}
		}
		return intersections;
	}

	private int findLocation(List<Segment> staircase, Segment s, int i, int leftBound_b) {
		while (i < staircase.size() && staircase.get(i)
												.isAtXBelow(p.get(leftBound_b - 1)
																.getX(), s)) {
			i++;
		}
		if (i >= staircase.size()) {
			i = staircase.size() - 1;
			if (i < 0) {
				i = 0;
			}
		}
		log("Location found at " + i);
		return i;
	}

	private void searchInStrip(List<Segment> leftBoundedSegments, int leftBound_b, int rightBound_e, List<Segment> rightBoundedSegments) {
		List<Segment> Q = new ArrayList<Segment>();
		List<Segment> Ll = new ArrayList<Segment>();
		split(leftBoundedSegments, leftBound_b, rightBound_e, Q, Ll);
		if (Ll.isEmpty()) {
			log("Q: " + Q);
			rightBoundedSegments.addAll(Q);
			return;
		} else if (Ll.size() > 1 && !Q.isEmpty()) {
			log("Q: " + Q);
			log("Ll: " + Ll);
			// TODO: Is it correct to reuse the the stair intersection methode?
			for (Intersection inter : findIntersections(Q, Ll, leftBound_b, rightBound_e)) {
				if (!intersections.contains(inter)) {
					intersections.add(inter);
				}
			}
			// intersections.addAll(findIntersections(Q, Ll, leftBound_b, rightBound_e));
			List<Segment> Rl = new ArrayList<Segment>();
			searchInStrip(Ll, leftBound_b, rightBound_e, Rl);
			rightBoundedSegments.addAll(merge(Q, Rl, rightBound_e));
		}
	}

	private void split(List<Segment> L, int leftBound_b, int rightBound_e, List<Segment> Q, List<Segment> Ll) {
		Q.clear();
		Ll.clear();
		float leftBoundX = p.get(leftBound_b - 1)
							.getX();
		float rightBoundX = p.get(rightBound_e - 1)
								.getX();
		for (int j = 1; j <= L.size(); j++) {
			Segment s = L.get(j - 1);
			if (s.isSpanning(leftBoundX, rightBoundX)
				&& (Q.size() == 0 || !s.isIntersectingWithinBounds(Q.get(Q.size() - 1), leftBoundX, rightBoundX))) {
				Q.add(s);
			} else {
				Ll.add(s);
			}
		}
	}

	private void newSplit(List<Segment> leftBoundedSegments, List<Segment> parentStaircase, int leftBound_b, int rightBound_e,
			List<Segment> originalSegments, List<Segment> leftChild_leftBoundedSegments) {
		originalSegments.clear();
		leftChild_leftBoundedSegments.clear();
		int i = 1;
		float leftBoundX = p.get(leftBound_b - 1)
							.getX();
		float rightBoundX = p.get(rightBound_e - 1)
								.getX();
		for (int j = 1; j <= leftBoundedSegments.size(); j++) {
			while (i <= parentStaircase.size() && parentStaircase.get(i - 1)
																	.isAtXBelow(leftBoundX, leftBoundedSegments.get(j - 1))) {
				i++;
			}
			Segment s = leftBoundedSegments.get(j - 1);
			if (s.isSpanning(leftBoundX, rightBoundX)
				&& (i - 2 - 1 < 0 || i - 2 - 1 >= parentStaircase.size() || !s.isIntersectingWithinBounds(	parentStaircase.get(i - 2 - 1),
																											leftBoundX,
																											rightBoundX))
				&& (i + 1 - 1 < 0 || i + 1 - 1 >= parentStaircase.size() || !s.isIntersectingWithinBounds(	parentStaircase.get(i + 1 - 1),
																											leftBoundX,
																											rightBoundX))
				&& (originalSegments.size() == 0 || !s.isIntersectingWithinBounds(	originalSegments.get(originalSegments.size() - 1),
																					leftBoundX,
																					rightBoundX))) {
				originalSegments.add(s);
			} else {
				leftChild_leftBoundedSegments.add(s);
			}
		}
	}

	private void buildQSB(List<Segment> originalSegments, List<Segment> parentStaircase, int leftBound_b, int rightBound_e,
			List<Segment> staircase, List<Integer> SB_v) {
		SB_v.clear();
		SB_v.add(0);
		staircase.clear();
		int i = 1;
		float leftBoundX = p.get(leftBound_b - 1)
							.getX();
		float rightBoundX = p.get(rightBound_e - 1)
								.getX();
		for (int j = 1; j <= originalSegments.size(); j++) {
			while (i <= parentStaircase.size() && parentStaircase.get(i - 1)
																	.isAtXBelow(leftBoundX, originalSegments.get(j - 1))) {
				if (i % 4 == 0 && !parentStaircase.get(i - 1)
													.isIntersectingWithinBounds(originalSegments.get(j - 1), leftBoundX, rightBoundX)
					&& !parentStaircase.get(i - 1)
										.isIntersectingWithinBounds(originalSegments.get(j - 1 - 1), leftBoundX, rightBoundX)) {
					staircase.add(parentStaircase.get(i - 1));
					SB_v.add(i);
				}
				i++;
			}
			staircase.add(originalSegments.get(j - 1));
			if (i - 1 - 1 >= 0 && originalSegments.get(j - 1)
													.isIntersectingWithinBounds(parentStaircase.get(i - 1 - 1), leftBoundX, rightBoundX)) {
				SB_v.add(i - 2);
			} else {
				SB_v.add(i - 1);
			}
		}
	}

	private void debug(int recursionLevel, List<Segment> leftSegments, List<Segment> rightSegments, List<Segment> innerSegments,
			int leftBound, int rightBound, List<Segment> staircase, List<Intersection> intersections) {
		if (debugger != null) {
			Map<String, Object> info = new HashMap<String, Object>();
			info.put("recursionLevel", new Integer(recursionLevel));
			info.put("leftSegments", leftSegments);
			info.put("rightSegments", rightSegments);
			info.put("innerSegments", innerSegments);
			info.put("leftBound", new Double(p.get(leftBound - 1)
												.getX()));
			info.put("rightBound", new Double(p.get(rightBound - 1)
												.getX()));
			info.put("staircase", staircase);
			info.put("intersections", intersections);

			debugger.debug(info);
		}
	}

}
