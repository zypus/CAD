package gui.tools.drag;

import gui.DoublePoint;
import gui.tools.Tool;
import gui.tools.ToolConstants;
import splines.Point;

import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

/**
 * Author: Fabian Fränz <f.fraenz@t-online.de>
 * Date: 11/05/14
 * Project: CAD
 * Version: 1.0
 * Description:
 */
public class Dragger extends Tool {

	private List<Draggable> draggables = new ArrayList<>();;
	private boolean dragging = false;
	private DoublePoint previousMousePosition;

	public void setDraggables(List<Draggable> draggables) {

		this.draggables = draggables;
		if (draggables == null) {
			this.draggables = new ArrayList<>();
		}
	}

	public boolean isDragging() {

		return dragging;
	}

	@Override public void mousePressed(MouseEvent e) {

		super.mousePressed(e);
		dragging = false;
		if (isHandlingEvents()) {
			DoublePoint mousePosition = new DoublePoint(e.getPoint());
			for (Draggable draggable : draggables) {
				if (mousePosition.distance(draggable.getPoint()) < ToolConstants.SELECT_SENSITIVITY) {
					previousMousePosition = mousePosition;
					dragging = true;
					break;
				}
			}
		}
	}

	@Override public void mouseReleased(MouseEvent e) {

		super.mouseReleased(e);
		dragging = false;
		previousMousePosition = null;
	}

	@Override public void mouseDragged(MouseEvent e) {

		super.mouseDragged(e);
		if (isHandlingEvents() && dragging && previousMousePosition != null) {
			DoublePoint mousePosition = new DoublePoint(e.getPoint());
			Point shift = mousePosition.sub(previousMousePosition);
			for (Draggable draggable : draggables) {
				draggable.shift(shift);
			}
			previousMousePosition = mousePosition;
		}
	}
}
