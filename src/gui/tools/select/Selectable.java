package gui.tools.select;

import gui.SelectionType;
import splines.Point;

import java.awt.Rectangle;
import java.util.List;

/**
 * Author: Fabian Fränz <f.fraenz@t-online.de>
 * Date: 04/05/14
 * Project: CAD
 * Version: 1.0
 * Description:
 */
public interface Selectable {

	List<Point> getSelectablePoints();
	boolean onlySelectableOnPoints();
	void setSelectionStatus(SelectionType selected);
	Rectangle.Double getBoundingBox();

	SelectionType getSelectionStatus();
}
