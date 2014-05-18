package gui.toolbar;

import javax.swing.JComponent;
import javax.swing.JPanel;
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

/**
 * Author: Fabian Fränz <f.fraenz@t-online.de>
 * Date: 18/05/14
 * Project: CAD
 * Version: 1.0
 * Description: TODO Add description.
 */
public class ToolBar extends JPanel {

	private List<JComponent> items = new ArrayList<>();

	public void addToolBarItem(JComponent item) {
		item.setBounds(items.size()*getHeight(), 0, getHeight(), getHeight());
		items.add(item);
		add(item);
	}

	public void selectItem(JComponent item) {
		for (JComponent toolBarItem : items) {
			if (item == toolBarItem) {
				toolBarItem.setBackground(Color.BLACK);
				toolBarItem.setForeground(Color.WHITE);
			} else {
				if (!toolBarItem.getBackground().equals(Color.DARK_GRAY)) {
					toolBarItem.setBackground(Color.LIGHT_GRAY);
					toolBarItem.setForeground(Color.BLACK);
				}
			}
		}
	}

	public JComponent getSelectedItem() {

		for (JComponent toolBarItem : items) {
			if (!toolBarItem.getBackground().equals(Color.BLACK)) {
				return toolBarItem;
			}
		}
		return null;
	}

	public void toggleItem(JComponent item) {

		for (JComponent toolBarItem : items) {
			if (item == toolBarItem) {
				if (!toolBarItem.getBackground().equals(Color.DARK_GRAY)) {
					toolBarItem.setBackground(Color.DARK_GRAY);
					toolBarItem.setForeground(Color.WHITE);
				} else {
					toolBarItem.setBackground(Color.LIGHT_GRAY);
					toolBarItem.setForeground(Color.BLACK);
				}
			}
		}
	}

}
