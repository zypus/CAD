package gui;

import splines.Point;

import java.util.List;

/**
 * Author: Fabian Fränz <f.fraenz@t-online.de>
 * Date: 04/05/14
 * Project: CAD
 * Version: 1.0
 * Description:
 */
public interface Selectable {

	SelectionType selectionStatus = SelectionType.UNSELECTED;

	List<Point> selectablePoints();
	boolean onlySelectableOnPoints();
	void setSelectionStatus(SelectionType selected);

	SelectionType getSelectionStatus();
}
