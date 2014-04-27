package splines;

import java.util.*;

/**
 * Created by fabian on 19/04/14.
 */
public abstract class Spline implements List<Point> {
    protected final java.util.List<Point> controlPoints = new ArrayList<Point>();
    private Interval interval;

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
        return controlPoints.add(point);
    }

    @Override
    public boolean remove(Object o) {
        return controlPoints.remove(o);
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        return false;
    }

    @Override
    public boolean addAll(Collection<? extends Point> c) {
        return controlPoints.addAll(c);
    }

    @Override
    public boolean addAll(int index, Collection<? extends Point> c) {
        return controlPoints.addAll(index, c);
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        return controlPoints.retainAll(c);
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        return controlPoints.retainAll(c);
    }

    @Override
    public void clear() {
        controlPoints.clear();
    }

    @Override
    public Point get(int index) {
        return controlPoints.get(index);
    }

    @Override
    public Point set(int index, Point element) {
        return controlPoints.set(index, element);
    }

    @Override
    public void add(int index, Point element) {
        controlPoints.add(index, element);
    }

    @Override
    public Point remove(int index) {
        return controlPoints.remove(index);
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
