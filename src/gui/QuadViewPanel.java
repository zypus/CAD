package gui;

import surface.ParametricSurface;
import surface.Solid;
import util.Bound;
import util.ParametricFunction;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;
import java.awt.Dimension;
import java.awt.GridLayout;

/**
 * Author: Fabian Fränz <f.fraenz@t-online.de>
 * Date: 04/06/14
 * Project: CAD
 * Version: 1.0
 * Description: TODO Add description.
 */
public class QuadViewPanel extends JPanel {

	public static void main(String[] args) {

		JFrame frame = new JFrame("Test");
		frame.setSize(new Dimension(1000, 1000));
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		QuadViewPanel panel = new QuadViewPanel();
		frame.setContentPane(panel);

		ParametricFunction x = new ParametricFunction() {
			@Override public double getValue(double u, double v) {

				return Math.sin(u) * Math.cos(v);
			}
		};
		ParametricFunction y = new ParametricFunction() {
			@Override public double getValue(double u, double v) {

				return Math.sin(u) * Math.sin(v);
			}
		};
		ParametricFunction z = new ParametricFunction() {
			@Override public double getValue(double u, double v) {

				return Math.cos(u);
			}
		};
		Solid solid = new ParametricSurface(x, y, z, new Bound(0, Math.PI / 3), new Bound(0, 2 * Math.PI));

//		Solid solid = new Polyhedron();
//		List<Point3d> points = new ArrayList<>();
//		points.add(new Point3d(0,0,0));
//		points.add(new Point3d(1,10,10));
//		points.add(new Point3d(9,0,10));
//		points.add(new Point3d(10,10,10));
////		points.add(new Point3d(100,100,100));
//
//		solid.setAllPoints(points);

		panel.setSolid(solid);

		frame.setVisible(true);
	}

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

	private void toggleDrawLines(boolean toggle) {

	}

}
