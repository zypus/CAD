package gui.tools.select;

import gui.DoublePoint;
import gui.tools.ToolConstants;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.geom.Ellipse2D;
import java.util.ArrayList;

/**
 * Author: Fabian Fränz <f.fraenz@t-online.de>
 * Date: 12/05/14
 * Project: CAD
 * Version: 1.0
 * Description:
 */
public class PointSelecter extends Selecter {

	@Override public void mousePressed(MouseEvent e) {

		super.mousePressed(e);
		if (isHandlingEvents()) {
			DoublePoint mousePosition = new DoublePoint(e.getPoint());
			java.util.List<Selectable> selection = new ArrayList<>();
			for (int i = 0; i < selectableObjects.size() && selection.isEmpty(); i++) {
				Selectable selectable = selectableObjects.get(i);
				java.util.List<splines.Point> selectablePoints = selectable.getSelectablePoints();
				for (int j = 0; j < selectablePoints.size() && selection.isEmpty(); j++) {
					splines.Point point = selectablePoints.get(j);
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
			if (!selection.isEmpty()) {
				selectedObjects.add(selection.get(0));
			}
			finished();
		}

	}

	@Override public void draw(Graphics2D g2) {

		if (shouldDraw()) {
			super.draw(g2);
		}
		java.util.List<Selectable> selectedPoints = getSelectedObjects();
		g2.setStroke(ToolConstants.INDICATOR_STROKE);
		g2.setColor(Color.WHITE);
		for (Selectable point : selectedPoints) {
			Ellipse2D circle =
					new Ellipse2D.Double(point.getSelectablePoints().get(0).getX() - ToolConstants.INDICATOR_SIZE / 2,
										 point.getSelectablePoints().get(0).getY() - ToolConstants.INDICATOR_SIZE / 2,
										 ToolConstants.INDICATOR_SIZE,
										 ToolConstants.INDICATOR_SIZE);
			g2.draw(circle);
		}
	}
}
