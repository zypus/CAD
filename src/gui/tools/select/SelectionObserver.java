package gui.tools.select;

import java.util.List;

/**
 * Author: Fabian Fränz <f.fraenz@t-online.de>
 * Date: 16/05/14
 * Project: CAD
 * Version: 1.0
 * Description: TODO Add description.
 */
public interface SelectionObserver {

	void update(List<? extends Selectable> selectables);
}
