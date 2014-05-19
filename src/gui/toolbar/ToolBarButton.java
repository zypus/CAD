package gui.toolbar;

import javax.swing.Action;
import javax.swing.Icon;
import javax.swing.JButton;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;

/**
 * Author: Fabian Fränz <f.fraenz@t-online.de>
 * Date: 18/05/14
 * Project: CAD
 * Version: 1.0
 * Description: TODO Add description.
 */
public class ToolBarButton extends JButton {

	public ToolBarButton() {

	}

	public ToolBarButton(Icon icon) {

		super(icon);
	}

	public ToolBarButton(String text) {

		super(text);
	}

	public ToolBarButton(Action a) {

		super(a);
	}

	public ToolBarButton(String text, Icon icon) {

		super(text, icon);
	}

	@Override protected void paintComponent(Graphics g) {

		Graphics2D g2 = (Graphics2D) g;
		// border
		Ellipse2D.Double circle = new Ellipse2D.Double(0, 0, getWidth(), getHeight());
		g2.setColor(getBackground());
		g2.fill(circle);
		g2.setColor(getForeground());
		FontMetrics metrics = getFontMetrics(getFont());
		int length = metrics.stringWidth(getText());
		if (getIcon() != null) {
			g2.setClip(circle);
			getIcon().paintIcon(this,g,0,0);
		} else {
			g2.drawString(getText(), getWidth() / 2 - length / 2, getHeight() / 2 - length / 2);
		}
	}
}
