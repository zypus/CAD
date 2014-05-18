package gui.toolbar;

import javax.swing.BoxLayout;
import javax.swing.JPanel;
import java.util.ArrayList;
import java.util.List;

/**
 * Author: Fabian Fränz <f.fraenz@t-online.de>
 * Date: 18/05/14
 * Project: CAD
 * Version: 1.0
 * Description: TODO Add description.
 */
public class ToolBarLayer extends JPanel {

	List<ToolBar> toolBars = new ArrayList<>();

	public ToolBarLayer() {

		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
	}

	public void pushToolBar(ToolBar toolBar) {

		toolBars.add(toolBar);
		toolBar.setBounds(0, 0, getWidth(), 50);
		add(toolBar, 0);

	}

	public void popToolBar() {

		if (toolBars.size() > 0) {
			remove(toolBars.get(toolBars.size() - 1));
			toolBars.remove(toolBars.size() - 1);
		}
	}

	public ToolBar getActiveToolBar(int layer) {

		return toolBars.get(layer);
	}

	public void popToBaseToolBar() {
		for (int i = toolBars.size()-1; i > 0; i++) {
			remove(toolBars.get(i));
			toolBars.remove(i);
		}
	}

}
