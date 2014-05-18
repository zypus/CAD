package gui.toolbar;

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
public class ToolBarPanel extends JPanel {

	List<ToolBarLayer> toolBarLayers = new ArrayList<>();

	public void pushToolBarLayer(ToolBarLayer layer) {

		if (toolBarLayers.size() > 1) {
			remove(toolBarLayers.get(toolBarLayers.size()-1));
		}
		toolBarLayers.add(layer);
		add(toolBarLayers.get(toolBarLayers.size() - 1));
	}

	public void popToolBarLayer() {

		remove(toolBarLayers.get(toolBarLayers.size()-1));
		toolBarLayers.remove(toolBarLayers.size() - 1);
	}

	public ToolBar getActiveToolBar(int layer) {

		return toolBarLayers.get(toolBarLayers.size()-1).getActiveToolBar(layer);
	}

	public ToolBarLayer getActiveToolBarLayer() {

		return toolBarLayers.get(toolBarLayers.size() - 1);
	}

	@Override public void setBounds(int x, int y, int width, int height) {

		super.setBounds(x, y, width, height);
		for (ToolBarLayer layer : toolBarLayers) {
			layer.setBounds(x,y,width,height);
		}
	}
}
