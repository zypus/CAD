package splines;

import java.util.*;

/**
 * Author: Fabian Fränz <f.fraenz@t-online.de>
 * Date: 19/04/14
 * Project: CAD
 * Version: 1.0
 * Description: Abstract class which describes a spline object and keeps track of its control points and the interval the spline spans.
 */
public abstract class Spline implements List<Point> {
    protected final java.util.List<Point> controlPoints = new ArrayList<>();
    private Interval interval = new Interval(1, -1, 1, -1);
	private List<SplineObserver> observers = new ArrayList<>();

    /**
     * Interpolates the spline at the specified index.
     * @param u The subindex of control points, starting at 0.
     *          Hence 1.5 would specify is the point exactly between control point 1 and 2.
     * @return The interpolated point.
     */
    public abstract Point s(double u);

    /**
     * Gets the interval for which the spline is defined.
     * @return The interval.
     */
    public Interval getInterval() {
        return interval;
    }

	/**
	 * Iterates over all control points and sets the interval accordingly.
	 */
	private void computeInterval() {

		if (!isEmpty()) {
			double left = controlPoints.get(0).getX();
			double right = controlPoints.get(0).getX();
			double down = controlPoints.get(0).getY();
			double up = controlPoints.get(0).getY();
			for (int i = 1; i < size(); i++) {
				Point point = controlPoints.get(i);
				left = (point.getX() < left) ? point.getX() : left;
				right = (point.getX() > right) ? point.getX() : right;
				down = (point.getY() < down) ? point.getY() : down;
				up = (point.getY() > up) ? point.getY() : up;
			}
			interval = new Interval(left, right, down, up);
		} else {
			interval = new Interval(1,-1,1,-1);
		}
	}

	/**
	 * Increases the interval if the point is not contained in the interval.
	 * @param point The point which should be contained in the interval.
	 */
	private void updateInterval(Point point) {
		if (size() == 1) {
			interval = new Interval(point.getX(), point.getX(), point.getY(), point.getY());
		} else if (!interval.contains(point)) {
			double left = (point.getX() < interval.getLeftBound()) ? point.getX() : interval.getLeftBound();
			double right = (point.getX() > interval.getRightBound()) ? point.getX() : interval.getRightBound();
			double down = (point.getY() < interval.getLowerBound()) ? point.getY() : interval.getLowerBound();
			double up = (point.getY() > interval.getUpperBound()) ? point.getY() : interval.getUpperBound();
			interval = new Interval(left, right,down,up);
		}
	}

	public void addObserver(SplineObserver observer) {

		if (!observers.contains(observer)) {
			observers.add(observer);
		}
	}

	public void removeObserver(SplineObserver observer) {

		observers.remove(observer);
	}

	private void notifyObservers() {

		for (int i = 0; i < observers.size(); i++) {
			SplineObserver observer = observers.get(i);
			observer.observedSplineChanged();
		}
	}

	protected void changed() {

	}

    // All methods of the List interface.

    @Override
    public int size() {
        return controlPoints.size();
    }

    @Override
    public boolean isEmpty() {
        return controlPoints.isEmpty();
    }

    @Override
    public boolean contains(Object o) {
        return controlPoints.contains(o);
    }

    @Override
    public Iterator<Point> iterator() {
        return controlPoints.iterator();
    }

    @Override
    public Object[] toArray() {
        return controlPoints.toArray();
    }

    @Override
    public <T> T[] toArray(T[] a) {
        return controlPoints.toArray(a);
    }

    @Override
    public boolean add(Point point) {
		boolean add = controlPoints.add(point);
		updateInterval(point);
		changed();
		notifyObservers();
		return add;
    }

    @Override
    public boolean remove(Object o) {

		boolean remove = controlPoints.remove(o);
		computeInterval();
		changed();
		notifyObservers();
		return remove;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        return false;
    }

    @Override
    public boolean addAll(Collection<? extends Point> c) {

		boolean all = controlPoints.addAll(c);
		computeInterval();
		changed();
		notifyObservers();
		return all;
    }

    @Override
    public boolean addAll(int index, Collection<? extends Point> c) {

		boolean all = controlPoints.addAll(index, c);
		computeInterval();
		changed();
		notifyObservers();
		return all;
    }

    @Override
    public boolean removeAll(Collection<?> c) {

		boolean removeAll = controlPoints.removeAll(c);
		computeInterval();
		changed();
		notifyObservers();
		return removeAll;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        return controlPoints.retainAll(c);
    }

    @Override
    public void clear() {
        controlPoints.clear();
		computeInterval();
		changed();
		notifyObservers();
    }

    @Override
    public Point get(int index) {
        return controlPoints.get(index);
    }

    @Override
    public Point set(int index, Point element) {

		Point set = controlPoints.set(index, element);
		changed();
		computeInterval();
		notifyObservers();
		return set;
    }

    @Override
    public void add(int index, Point element) {
        controlPoints.add(index, element);
		updateInterval(element);
		changed();
		notifyObservers();
    }

    @Override
    public Point remove(int index) {

		Point remove = controlPoints.remove(index);
		computeInterval();
		changed();
		notifyObservers();
		return remove;
    }

    @Override
    public int indexOf(Object o) {
        return controlPoints.indexOf(o);
    }

    @Override
    public int lastIndexOf(Object o) {
        return controlPoints.lastIndexOf(o);
    }

    @Override
    public ListIterator<Point> listIterator() {
        return controlPoints.listIterator();
    }

    @Override
    public ListIterator<Point> listIterator(int index) {
        return controlPoints.listIterator(index);
    }

    @Override
    public List<Point> subList(int fromIndex, int toIndex) {
        return controlPoints.subList(fromIndex, toIndex);
    }
}
