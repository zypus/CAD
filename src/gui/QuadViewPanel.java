package gui;

import surface.Solid;

import javax.swing.JPanel;
import java.awt.GridLayout;

/**
 * Author: Fabian Fränz <f.fraenz@t-online.de>
 * Date: 04/06/14
 * Project: CAD
 * Version: 1.0
 * Description: TODO Add description.
 */
public class QuadViewPanel extends JPanel {

	private SubSpaceView topLeft;
	private SubSpaceView topRight;
	private SubSpaceView botLeft;
	private SubSpaceView botRight;

	private QuadViewPanel() {
		setup();
	}

	private void setup() {

		setLayout(new GridLayout(2,2));
		topLeft = new SubSpaceView(new double[]{0,0,1});
		topRight = new SubSpaceView(new double[]{1,0,0});
		botLeft = new SubSpaceView(new double[]{0,1,0});
		botRight = new FullSpaceView();
		add(topLeft);
		add(topRight);
		add(botLeft);
		add(botRight);

	}

	private void setSolid(Solid solid) {
		topLeft.setSolid(solid);
		topRight.setSolid(solid);
		botLeft.setSolid(solid);
		botRight.setSolid(solid);
	}

}
