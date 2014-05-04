package gui;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.*;

/**
 * Author: Fabian Fränz <f.fraenz@t-online.de>
 * Date: 04/05/14
 * Project: CAD
 * Version: 1.0
 * Description:
 */
public class MultiSelecter extends MouseAdapter implements Drawable {

	Map<Integer, Selecter> selecterMap = new TreeMap<>();

	public void addSelecter(Selecter selecter, int priority) {
		selecterMap.put(priority, selecter);
	}

	public void removeSelecter(int priority) {

		selecterMap.remove(priority);
	}

	public java.util.List<Selectable> getSelectedObjects() {

		for (Integer key : selecterMap.keySet()) {
			selecterMap;
		}
		return selectedObjects;
	}

	@Override public void mousePressed(MouseEvent e) {

		super.mousePressed(e);
		for (Integer key : selecterMap.keySet()) {
			selecterMap.get(key).mousePressed(e);
		}
	}

	@Override public void mouseReleased(MouseEvent e) {

		super.mouseReleased(e);
		for (Integer key : selecterMap.keySet()) {
			selecterMap.get(key).mouseReleased(e);
		}
	}

	@Override public void mouseDragged(MouseEvent e) {

		super.mouseDragged(e);
		for (Integer key : selecterMap.keySet()) {
			selecterMap.get(key).mousePressed(e);
		}
	}

	@Override public void draw(Graphics2D g2) {

		for (Integer key : selecterMap.keySet()) {
			selecterMap.get(key).draw(g2);
			break;
		}
	}
}
