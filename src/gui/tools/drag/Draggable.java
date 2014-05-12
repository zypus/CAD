package gui.tools.drag;

import splines.Point;

/**
 * Author: Fabian Fränz <f.fraenz@t-online.de>
 * Date: 11/05/14
 * Project: CAD
 * Version: 1.0
 * Description:
 */
public interface Draggable {
	void shift(Point p);
	Point getPoint();
}
