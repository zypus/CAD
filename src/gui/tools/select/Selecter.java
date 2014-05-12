package gui.tools.select;

import gui.DoublePoint;
import gui.SelectionType;
import gui.tools.Tool;
import splines.Point;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Author: Fabian Fränz <f.fraenz@t-online.de>
 * Date: 04/05/14
 * Project: CAD
 * Version: 1.0
 * Description: Class used to select selectable objects.
 */
public class Selecter extends Tool {

	protected List<Selectable> selectableObjects = new ArrayList<>();
	protected List<Selectable> selectedObjects = new ArrayList<>();
	private boolean active;
	protected double selectionSensitivity = 10;
	protected Rectangle2D selectionFrame = null;
	private Point startPoint = null;

	public void addSelectable(Selectable selectable) {

		if (!selectableObjects.contains(selectable)) {
			selectableObjects.add(selectable);
		}
	}

	public void addSelectables(Collection<Selectable> selectables) {

		selectableObjects.addAll(selectables);
	}

	public void removeSelectable(Selectable selectable) {
		selectableObjects.remove(selectable);
		selectedObjects.remove(selectable);
	}

	public void setSelectableObjects(List<Selectable> selectableObjects) {

		if (selectableObjects == null) {
			this.selectableObjects = new ArrayList<>();
		}
		else {
			this.selectableObjects = selectableObjects;
		}
	}

	public void clear() {
		selectableObjects.clear();
		selectedObjects.clear();
	}

	public boolean hasSelectedObjects() {

		return !selectedObjects.isEmpty();
	}

	public List<Selectable> getSelectedObjects() {

		return selectedObjects;
	}

	public double getSelectionSensitivity() {

		return selectionSensitivity;
	}

	public void setSelectionSensitivity(double selectionSensitivity) {

		this.selectionSensitivity = selectionSensitivity;
	}

	@Override public void mousePressed(MouseEvent e) {

		super.mousePressed(e);
		if (isHandlingEvents() && shouldStart()) {
			startPoint = new DoublePoint(e.getPoint());
		}
	}

	@Override public void mouseReleased(MouseEvent e) {

		super.mouseReleased(e);
		if (isHandlingEvents() && shouldFinish() && startPoint != null) {
			DoublePoint mousePosition = new DoublePoint(e.getPoint());
			List<Selectable> selection = new ArrayList<>();
			if (selectionFrame == null) {
				for (int i = 0; i < selectableObjects.size() && selection.isEmpty(); i++) {
					Selectable selectable = selectableObjects.get(i);
					List<Point> selectablePoints = selectable.getSelectablePoints();
					for (int j = 0; j < selectablePoints.size() && selection.isEmpty(); j++) {
						Point point = selectablePoints.get(j);
						if (selectable.onlySelectableOnPoints()) {
							double distance = mousePosition.distance(point);
							if (distance < selectionSensitivity) {
								selection.add(selectable);
							}
						} else {
							if (j < selectablePoints.size() - 1) {
								double firstDistance = mousePosition.distance(point);
								double secondDistance = mousePosition.distance(selectablePoints.get(j + 1));
								double realDistance = point.distance(selectablePoints.get(j + 1));
								double difference = Math.abs(firstDistance + secondDistance - realDistance);
								if (difference < selectionSensitivity) {
									selection.add(selectable);
								}
							}
						}
					}
				}
			} else {
				for (Selectable selectable : selectableObjects) {
					List<Point> selectablePoints = selectable.getSelectablePoints();
					boolean included = true;
					for (int j = 0; j < selectablePoints.size() && included; j++) {
						Point point = selectablePoints.get(j);
						if (!selectionFrame.contains(point.getX(), point.getY())) {
							included = false;
						}
					}
					if (included) {
						selection.add(selectable);
					}
				}
			}
			boolean shiftSelect = ((e.getModifiers() & MouseEvent.SHIFT_MASK) == MouseEvent.SHIFT_MASK);
			if (shiftSelect && !selection.isEmpty()) {
				for (Selectable selected : selection) {
					SelectionType
							type =
							(selected.getSelectionStatus() == SelectionType.UNSELECTED) ?
							SelectionType.SELECTED :
							SelectionType.UNSELECTED;
					selected.setSelectionStatus(type);
					if (type == SelectionType.UNSELECTED) {
						selectedObjects.remove(selected);
					} else {
						selectedObjects.add(selected);
					}
				}
			} else if (!selection.isEmpty()) {
				selectedObjects.clear();
				for (Selectable selected : selection) {
					selected.setSelectionStatus(SelectionType.SELECTED);
					selectedObjects.add(selected);
				}
			} else {
				selectedObjects.clear();
			}
			finished();
		}
		selectionFrame = null;
		startPoint = null;
	}

	@Override public void mouseDragged(MouseEvent e) {

		super.mouseDragged(e);
		if (isHandlingEvents() && startPoint != null) {
			double width = e.getPoint().getX()-startPoint.getX();
			double height = e.getPoint().getY() - startPoint.getY();
			Point upperLeftPoint = new DoublePoint(startPoint);
			if (width < 0) {
				upperLeftPoint = new DoublePoint(e.getPoint().getX(), upperLeftPoint.getY());
			}
			if (height < 0) {
				upperLeftPoint = new DoublePoint(upperLeftPoint.getX(), e.getPoint().getY());
			}
			selectionFrame = new Rectangle2D.Double(upperLeftPoint.getX(), upperLeftPoint.getY(), Math.abs(width), Math.abs(height));
		}
	}

	@Override
	public void draw(Graphics2D g2) {
		if (shouldDraw()) {
			if (selectionFrame != null) {
				g2.setColor(Color.WHITE);
				g2.setStroke(new BasicStroke(2));
				g2.draw(selectionFrame);
			}
		}
	}

}
