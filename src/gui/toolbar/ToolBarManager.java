package gui.toolbar;

/**
 * Author: Fabian Fränz <f.fraenz@t-online.de>
 * Date: 18/05/14
 * Project: CAD
 * Version: 1.0
 * Description: TODO Add description.
 */
public class ToolBarManager {

	private static ToolBarManager instance = new ToolBarManager();
	private ToolBarPanel panel;

	public static ToolBarManager getInstance() {

		return instance;
	}

	public void registerPanel(ToolBarPanel panel) {
		this.panel = panel;
	}

	public void push(ToolBarLayer toolBarLayer) {
		panel.pushToolBarLayer(toolBarLayer);
	}

	public void pop() {
		panel.popToolBarLayer();
	}

	public ToolBar getActiveToolBar(int layer) {

		return panel.getActiveToolBar(layer);
	}

	public ToolBarLayer getActiveLayer() {

		return panel.getActiveToolBarLayer();
	}

	private ToolBarManager() {

	}
}
