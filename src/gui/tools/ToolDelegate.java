package gui.tools;

/**
 * Author: Fabian Fränz <f.fraenz@t-online.de>
 * Date: 07/05/14
 * Project: CAD
 * Version: 1.0
 * Description:
 */
public interface ToolDelegate {
	public void didFinish(Tool tool);

	public boolean shouldStart(Tool tool);

	public boolean shouldFinish(Tool tool);

	public void didStart(Tool tool);

	public void didUpdate(Tool tool);

	public boolean shouldDraw(Tool tool);

	public int getWidth();

	public int getHeight();
}
