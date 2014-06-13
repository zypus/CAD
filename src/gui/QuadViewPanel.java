package gui;

import surface.NURBSPatchwork;
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

		NURBSSurface solid1 = new NURBSSurface(controlPoints, uKnots, vKnots);

		List<List<Point3d>> controlPoints2 = new ArrayList<>();
		List<Point3d> set12 = new ArrayList<>();
		set12.add(new Point3d(1.5, 1.5, -4));
		set12.add(new Point3d(0.5, 1.5, -2));
		set12.add(new Point3d(-0.5, 1.5, 1));
		set12.add(new Point3d(-1.5, 1.5, -2));
		controlPoints2.add(set12);
		List<Point3d> set22 = new ArrayList<>();
		set22.add(new Point3d(1.5, 0.5, -1));
		set22.add(new Point3d(0.5, 0.5, -3));
		set22.add(new Point3d(-0.5, 0.5, 0));
		set22.add(new Point3d(-1.5, 0.5, 1));
		controlPoints2.add(set22);
		List<Point3d> set32 = new ArrayList<>();
		set32.add(new Point3d(1.5, -0.5, -4));
		set32.add(new Point3d(0.5, -0.5, 0));
		set32.add(new Point3d(-0.5, -0.5, -3));
		set32.add(new Point3d(-1.5, -0.5, -4));
		controlPoints2.add(set32);
		List<Point3d> set42 = new ArrayList<>();
		set42.add(new Point3d(1.5, -1.5, 2));
		set42.add(new Point3d(0.5, -1.5, 2));
		set42.add(new Point3d(-0.5, -1.5, 0));
		set42.add(new Point3d(-1.5, -1.5, 1));
		controlPoints2.add(set42);

		List<Double> uKnots2 = new ArrayList<>();
		uKnots2.add(0.0);
		uKnots2.add(1.0);
		uKnots2.add(2.0);
		uKnots2.add(3.0);
		uKnots2.add(4.0);
		uKnots2.add(5.0);
		uKnots2.add(6.0);
		uKnots2.add(7.0);

		List<Double> vKnots2 = new ArrayList<>();
		vKnots2.add(0.0);
		vKnots2.add(1.0);
		vKnots2.add(2.0);
		vKnots2.add(3.0);
		vKnots2.add(4.0);
		vKnots2.add(5.0);
		vKnots2.add(6.0);
		vKnots2.add(7.0);

		NURBSSurface solid2 = new NURBSSurface(controlPoints2, uKnots2, vKnots2);

		List<NURBSSurface> surfaces = new ArrayList<>();
		surfaces.add(solid1);
		surfaces.add(solid2);
		Solid solid = new NURBSPatchwork(surfaces);

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
		topRight = new SubSpaceView(new double[]{-1,0,0});
		botLeft = new SubSpaceView(new double[]{0,-1,0});
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
