package gui;

import surface.NURBSSurface;
import surface.Point3d;
import surface.Solid;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;

/**
 * Author: Fabian Fränz <f.fraenz@t-online.de>
 * Date: 04/06/14
 * Project: CAD
 * Version: 1.0
 * Description: TODO Add description.
 */
public class QuadViewPanel extends JPanel {

	public static void main(String[] args) {

		JFrame frame = new JFrame("Project Phase 3 - Surfaces/Solids");
		frame.setSize(new Dimension(1000, 1000));
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		QuadViewPanel panel = new QuadViewPanel();
		frame.setContentPane(panel);

		List<List<Point3d>> controlPoints = new ArrayList<>();
		List<Point3d> set1 = new ArrayList<>();
		set1.add(new Point3d(-1.5,-1.5,4));
		set1.add(new Point3d(-0.5,-1.5,2));
		set1.add(new Point3d(0.5,-1.5,-1));
		set1.add(new Point3d(1.5,-1.5,2));
		controlPoints.add(set1);
		List<Point3d> set2 = new ArrayList<>();
		set2.add(new Point3d(-1.5, -0.5, 1));
		set2.add(new Point3d(-0.5, -0.5, 3));
		set2.add(new Point3d(0.5, -0.5, 0));
		set2.add(new Point3d(1.5, -0.5, -1));
		controlPoints.add(set2);
		List<Point3d> set3 = new ArrayList<>();
		set3.add(new Point3d(-1.5, 0.5, 4));
		set3.add(new Point3d(-0.5, 0.5, 0));
		set3.add(new Point3d(0.5, 0.5, 3));
		set3.add(new Point3d(1.5, 0.5, 4));
		controlPoints.add(set3);
		List<Point3d> set4 = new ArrayList<>();
		set4.add(new Point3d(-1.5, 1.5, -2));
		set4.add(new Point3d(-0.5, 1.5, -2));
		set4.add(new Point3d(0.5, 1.5, 0));
		set4.add(new Point3d(1.5, 1.5, -1));
		controlPoints.add(set4);

		List<Double> uKnots = new ArrayList<>();
		uKnots.add(0.0);
		uKnots.add(1.0);
		uKnots.add(2.0);
		uKnots.add(3.0);
		uKnots.add(4.0);
		uKnots.add(5.0);
		uKnots.add(6.0);
		uKnots.add(7.0);

		List<Double> vKnots = new ArrayList<>();
		vKnots.add(0.0);
		vKnots.add(1.0);
		vKnots.add(2.0);
		vKnots.add(3.0);
		vKnots.add(4.0);
		vKnots.add(5.0);
		vKnots.add(6.0);
		vKnots.add(7.0);

		Solid solid = new NURBSSurface(controlPoints, uKnots, vKnots);

//		ParametricFunction x = new ParametricFunction() {
//			@Override public double getValue(double u, double v) {
//
//				return Math.sin(u) * Math.cos(v);
//			}
//		};
//		ParametricFunction y = new ParametricFunction() {
//			@Override public double getValue(double u, double v) {
//
//				return Math.sin(u) * Math.sin(v);
//			}
//		};
//		ParametricFunction z = new ParametricFunction() {
//			@Override public double getValue(double u, double v) {
//
//				return Math.cos(u);
//			}
//		};
//		Solid solid = new ParametricSurface(x, y, z, new Bound(0, Math.PI / 3), new Bound(0, 2 * Math.PI));

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
