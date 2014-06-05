package surface;

import java.util.ArrayList;
import java.util.List;

/**
 * Author: Fabian Fränz <f.fraenz@t-online.de>
 * Date: 05/06/14
 * Project: CAD
 * Version: 1.0
 * Description: TODO Add description.
 */
public class ChansAlgorithm {

	private static final double INF = 1e99;
	private static final Point NIL = new Point(new Point3d(INF, INF, INF));

	private double time(Point p, Point q, Point r) {

		if (p == NIL || q == NIL || r == NIL) {
			return INF;
		} else {
			return (q.getX() - p.getX()) * (r.getZ() - p.getZ()) - (r.getX() - p.getX()) * (q.getZ() - p.getZ()) / turn(p, q, r);
		}
	}

	private double turn(Point p, Point q, Point r) {

		if (p == NIL || q == NIL || r == NIL) {
			return 1;
		} else {
			return (q.getX() - p.getX()) * (r.getY() - p.getY()) - (r.getX() - p.getX()) * (q.getY() - p.getY());
		}
	}

	private Point sort(List<Point> P, int n) {

		Point a, b, c;
		Point head = new Point(new Point3d(0, 0, 0));
		if (n == 1) {
			P.get(0).setNext(NIL);
			return P.get(0);
		}
		a = sort(P.subList(0, n / 2), n / 2);
		b = sort(P.subList(n / 2, n), n - n / 2);
		c = head;
		do {
			if (a.getX() < b.getX()) {
				c.setNext(a);
				c = a;
				a = a.getNext();
			} else {
				c.setNext(b);
				c = b;
				b = b.getNext();
			}
		} while (c != NIL);

		return head.getNext();
	}

	private void hull(List<Point> list, int n, PointableArray A, PointableArray B) {

		Point u, v, mid;
		double[] t = new double[6];
		double oldt, newt;
		int i, j, k, l, minl = 6;

		if (n == 1) {
			A.set(0, NIL);
			list.get(0).setPrev(NIL);
			list.get(0).setNext(NIL);
			return;
		}

		for (u = list.get(0), i = 0; i < n / 2 - 1; i++) {
			u = u.getNext();
		}
		v = u.getNext();
		mid = u.getNext();
		hull(list, n / 2, B.dublicate(), A.dublicate());
		PointableArray newB = B.dublicate();
		PointableArray newA = A.dublicate();
		newB.setPointer(B.getPointer()+n/2*2);
		newA.setPointer(A.getPointer()+n/2*2);
		hull(list.subList(list.indexOf(mid), n), n - n / 2, newB, newA);

		while (true) {
			if (turn(u, v, v.getNext()) < 0) {
				v = v.getNext();
			} else if (turn(u.getPrev(), u, v) < 0) {
				u = u.getPrev();
			} else {
				break;
			}
		}

		for (i = 0, k = 0, j = n/2*2, oldt = -INF; ; oldt = newt) {
			t[0] = time(B.get(i).getPrev(), B.get(i), B.get(i).getNext());
			t[1] = time(B.get(j).getPrev(), B.get(j), B.get(j).getNext());
			t[2] = time(u, u.getNext(), v);
			t[3] = time(u.getPrev(), u, v);
			t[4] = time(u, v.getPrev(), v);
			t[5] = time(u, v, v.getNext());
			for (newt = INF, l = 0; l < 6; l++) {
				if (t[l] > oldt && t[l] < newt) {
					minl = l;
					newt = t[l];
				}
			}
			if (newt == INF) {
				break;
			}
			switch (minl) {
			case 0 :
				if (B.get(i).getX() < u.getX()) {
					A.set(k++, B.get(i));
				}
				B.get(i++).act();
				break;
			case 1:
				if (B.get(j).getX() > v.getX()) {
					A.set(k++, B.get(j));
				}
				B.get(j++).act();
				break;
			case 2:
				A.set(k++, u.getNext());
				u = u.getNext();
				break;
			case 3:
				A.set(k++, u);
				u = u.getPrev();
				break;
			case 4:
				A.set(k++, v.getPrev());
				v = v.getPrev();
				break;
			case 5:
				A.set(k++, v);
				v = v.getNext();
				break;
			}
		}
		A.set(k, NIL);

		u.setNext(v); // now go back in time to update pointers
		v.setPrev(u);
		for (k--; k >= 0; k--) {
			if (A.get(k).getX() <= u.getX() || A.get(k).getX() >= v.getX()) {
				A.get(k).act();
				if (A.get(k) == u) {
					u = u.getPrev();
				} else if (A.get(k) == v) {
					v = v.getNext();
				}
			} else {
				u.setNext(A.get(k));
				A.get(k).setPrev(u);
				v.setPrev(A.get(k));
				A.get(k).setNext(v);
				if (A.get(k).getX() < mid.getX()) {
					u = A.get(k);
				} else {
					v = A.get(k);
				}
			}
		}
	}

	public Triangles computeConvexHull(List<Point3d> points) {

		int n = points.size();
		List<Point> P = new ArrayList<>(n);
		for (Point3d point : points) {
			P.add(new Point(point));
		}
		List<Point> list = new ArrayList<>();
		for (Point next = sort(P, n); next != NIL; next = next.getNext()) {
			list.add(next);
		}
		Point[] A_array = new Point[2*n];
		Point[] B_array = new Point[2*n];
		PointableArray A = new PointableArray(A_array);
		PointableArray B = new PointableArray(B_array);
		hull(list, n, A, B);

		List<Integer> indices = new ArrayList<>();
		for (int i = 0; A.get(i) != NIL; A.get(i++).act()) {
			indices.add(P.indexOf(A.get(i).getPrev()));
			indices.add(P.indexOf(A.get(i)));
			indices.add(P.indexOf(A.get(i).getNext()));
		}

		return new Triangles(points, indices);
	}

	private static class Point {

		private Point3d actualPoint;
		private Point next;
		private Point prev;

		private Point(Point3d actualPoint) {

			this.actualPoint = actualPoint;
			next = null;
			prev = null;
		}

		public void act() {
			if (prev.getNext() != this) { // insert
				prev.setNext(this);
				next.setPrev(this);
			} else { // delete
				prev.setNext(next);
				next.setPrev(prev);
			}
		}

		public Point getNext() {

			return next;
		}

		public void setNext(Point next) {

			this.next = next;
		}

		public Point getPrev() {

			return prev;
		}

		public void setPrev(Point prev) {

			this.prev = prev;
		}

		public double getX() {

			return actualPoint.getX();
		}

		public double getY() {

			return actualPoint.getY();
		}

		public double getZ() {

			return actualPoint.getZ();
		}
	}

	private class PointableArray {

		Point[] array;
		int pointer = 0;

		private PointableArray(Point[] array) {

			this.array = array;
		}

		public int getPointer() {

			return pointer;
		}

		public void setPointer(int pointer) {

			this.pointer = pointer;
		}

		public Point get(int i) {

			return array[pointer+i];
		}

		public void set(int i, Point point) {

			array[pointer+i] = point;
		}

		public PointableArray dublicate() {

			PointableArray pa = new PointableArray(array);
			pa.setPointer(pointer);

			return pa;
		}
	}

}
