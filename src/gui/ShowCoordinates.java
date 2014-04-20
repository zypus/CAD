package gui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.geom.Point2D;
import java.awt.geom.Point2D.Double;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class ShowCoordinates extends JPanel {
	private JLabel label;
	private Double point1;
	Double point2;

	public ShowCoordinates(Container cont) {
		build(cont);
	}

	private void build(Container cont) {
		cont.setLayout(new BoxLayout(cont, BoxLayout.PAGE_AXIS));
		label = new JLabel();
		resetLabel();
		cont.add(label);
		label.setForeground(Color.WHITE);
		label.setAlignmentX(Component.RIGHT_ALIGNMENT);
	}

	public void update(double x, double y) {
		if (x < 0 || y < 0) {
			point2 = null;
			updateLabel();
			return;
		}
		if (point2 == null) {
			point2 = new Point2D.Double();
		}
		point2.x = (int) x;
		point2.y = (int) y;
		updateLabel();
	}

	public void updatePoint1(Double pt) {
		point1 = pt;
		updateLabel();
	}

	public void resetLabel() {
		point2 = null;
		updateLabel();
	}

	protected void updateLabel() {
		String msg = "";
		if ((point1 == null) && (point2 == null)) {
			msg = "You can move the cursor within the framed region.";
		} else {
			if (point2 != null) {
				msg += "The coordinates are (" + point2.getX() + ", "
						+ point2.getY() + "). ";
			}
		}
		label.setText(msg);
	}

	private static void create() {
		JFrame f = new JFrame("Show Coordinates");
		ShowCoordinates showCoo = new ShowCoordinates(f);
		showCoo.build(f.getContentPane());
		f.pack();
		f.show();
	}

}