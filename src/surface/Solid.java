package surface;

import java.util.Collections;
import java.util.List;

/**
 * Author: Fabian Fränz <f.fraenz@t-online.de>
 * Date: 03/06/14
 * Project: CAD
 * Version: 1.0
 * Description: TODO Add description.
 */
public abstract class Solid {

	private List<PriorityObserver> observers;

	public abstract  GeometryFactory getFactory();
	public abstract double getArea();
	public abstract double getVolume();

	public abstract void addPoint(Point3d point3d);
	public abstract void removePoint(Point3d point3d);
	public abstract void replacePoint(Point3d point3d, Point3d otherPoint3d);
	public abstract List<Point3d> getAllPoints();

	// Observer pattern
	public void attachObserver(SolidObserver observer, int priority) {

		observers.add(new PriorityObserver(observer, priority));
		Collections.sort(observers);
	}

	public void detachObserver(SolidObserver observer) {

		int index = -1;
		for (int i = 0; i < observers.size(); i++) {
			if (observers.get(i).getObserver() == observer) {
				index = i;
				break;
			}
		}
		if (index != -1) {
			observers.remove(index);
		}
	}

	private void notifyObservers() {

		for (SolidObserver observer : observers) {
			observer.update();
		}
	}

	private class PriorityObserver
			implements SolidObserver, Comparable<PriorityObserver> {

		private SolidObserver observer;
		private int priority;

		private PriorityObserver(SolidObserver observer, int priority) {

			this.observer = observer;
			this.priority = priority;
		}

		@Override public void update() {

			observer.update();
		}

		public int getPriority() {

			return priority;
		}

		public void setPriority(int priority) {

			this.priority = priority;
		}

		public SolidObserver getObserver() {

			return observer;
		}

		@Override public int compareTo(PriorityObserver o) {

			if (priority < o.getPriority()) {
				return -1;
			} else if (priority > o.getPriority()) {
				return 1;
			} else {
				return 0;
			}
		}
	}

}
