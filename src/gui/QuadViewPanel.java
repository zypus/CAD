package gui;

import surface.HomogeneousPoint3d;
import surface.NURBSPatchwork;
import surface.NURBSSurface;
import surface.Solid;
import surface.SolidObserver;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.WindowConstants;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
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
		frame.add(panel, BorderLayout.CENTER);

		List<List<HomogeneousPoint3d>> controlPoints = new ArrayList<>();
		List<HomogeneousPoint3d> set1 = new ArrayList<>();
		set1.add(new HomogeneousPoint3d(1,0,0,1));
		List<HomogeneousPoint3d> set2 = new ArrayList<>();
		set2.add(new HomogeneousPoint3d(1,1,0,Math.sqrt(2)/2));
		List<HomogeneousPoint3d> set3 = new ArrayList<>();
		set3.add(new HomogeneousPoint3d(0,1,0,1));
		List<HomogeneousPoint3d> set4 = new ArrayList<>();
		set4.add(new HomogeneousPoint3d(-1, 1, 0, Math.sqrt(2) / 2));
		List<HomogeneousPoint3d> set5 = new ArrayList<>();
		set5.add(new HomogeneousPoint3d(-1, 0, 0, 1));
		List<HomogeneousPoint3d> set6 = new ArrayList<>();
		set6.add(new HomogeneousPoint3d(-1, -1, 0, Math.sqrt(2) / 2));
		List<HomogeneousPoint3d> set7 = new ArrayList<>();
		set7.add(new HomogeneousPoint3d(0, -1, 0, 1));
		List<HomogeneousPoint3d> set8 = new ArrayList<>();
		set8.add(new HomogeneousPoint3d(1, -1, 0, Math.sqrt(2) / 2));
		List<HomogeneousPoint3d> set9 = new ArrayList<>();
		set9.add(new HomogeneousPoint3d(1, 0, 0, 1));
		controlPoints.add(set1);
		controlPoints.add(set2);
		controlPoints.add(set3);
		controlPoints.add(set4);
		controlPoints.add(set5);
		controlPoints.add(set6);
		controlPoints.add(set7);
		controlPoints.add(set8);
		controlPoints.add(set9);

		List<Double> uKnots = new ArrayList<>();
		uKnots.add(0.0);
		uKnots.add(0.0);
		uKnots.add(0.0);
		uKnots.add(Math.PI/2);
		uKnots.add(Math.PI/2);
		uKnots.add(Math.PI);
		uKnots.add(Math.PI);
		uKnots.add(3*Math.PI/2);
		uKnots.add(3*Math.PI/2);
		uKnots.add(2*Math.PI);
		uKnots.add(2*Math.PI);
		uKnots.add(2*Math.PI);

		List<Double> vKnots = new ArrayList<>();
		vKnots.add(0.0);
		vKnots.add(1.0);
		vKnots.add(2.0);

		NURBSSurface solid1 = new NURBSSurface(controlPoints, uKnots, vKnots, 2, 1);
		solid1.setuSteps(30);
		solid1.setvSteps(10);

		List<List<HomogeneousPoint3d>> controlPoints2 = new ArrayList<>();
		List<HomogeneousPoint3d> set12 = new ArrayList<>();
		set12.add(new HomogeneousPoint3d(1.5, 1.5, -4));
		set12.add(new HomogeneousPoint3d(0.5, 1.5, -2));
		set12.add(new HomogeneousPoint3d(-0.5, 1.5, 1));
		set12.add(new HomogeneousPoint3d(-1.5, 1.5, -2));
		controlPoints2.add(set12);
		List<HomogeneousPoint3d> set22 = new ArrayList<>();
		set22.add(new HomogeneousPoint3d(1.5, 0.5, -1));
		set22.add(new HomogeneousPoint3d(0.5, 0.5, -3));
		set22.add(new HomogeneousPoint3d(-0.5, 0.5, 0));
		set22.add(new HomogeneousPoint3d(-1.5, 0.5, 1));
		controlPoints2.add(set22);
		List<HomogeneousPoint3d> set32 = new ArrayList<>();
		set32.add(new HomogeneousPoint3d(1.5, -0.5, -4));
		set32.add(new HomogeneousPoint3d(0.5, -0.5, 0));
		set32.add(new HomogeneousPoint3d(-0.5, -0.5, -3));
		set32.add(new HomogeneousPoint3d(-1.5, -0.5, -4));
		controlPoints2.add(set32);
		List<HomogeneousPoint3d> set42 = new ArrayList<>();
		set42.add(new HomogeneousPoint3d(1.5, -1.5, 2));
		set42.add(new HomogeneousPoint3d(0.5, -1.5, 2));
		set42.add(new HomogeneousPoint3d(-0.5, -1.5, 0));
		set42.add(new HomogeneousPoint3d(-1.5, -1.5, 1));
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

		List<List<HomogeneousPoint3d>> controlPoints3 = new ArrayList<>();
		List<HomogeneousPoint3d> set13 = new ArrayList<>();
		set13.add(new HomogeneousPoint3d(1, 0, 0, 1));
		set13.add(new HomogeneousPoint3d(1, 0, 1, 1));
		List<HomogeneousPoint3d> set23 = new ArrayList<>();
		set23.add(new HomogeneousPoint3d(1, 1, 0, Math.sqrt(2) / 2));
		set23.add(new HomogeneousPoint3d(1, 1, 1, Math.sqrt(2) / 2));
		List<HomogeneousPoint3d> set33 = new ArrayList<>();
		set33.add(new HomogeneousPoint3d(0, 1, 0, 1));
		set33.add(new HomogeneousPoint3d(0, 1, 1, 1));
		List<HomogeneousPoint3d> set43 = new ArrayList<>();
		set43.add(new HomogeneousPoint3d(-1, 1, 0, Math.sqrt(2) / 2));
		set43.add(new HomogeneousPoint3d(-1, 1, 1, Math.sqrt(2) / 2));
		List<HomogeneousPoint3d> set53 = new ArrayList<>();
		set53.add(new HomogeneousPoint3d(-1, 0, 0, 1));
		set53.add(new HomogeneousPoint3d(-1, 0, 1, 1));
		List<HomogeneousPoint3d> set63 = new ArrayList<>();
		set63.add(new HomogeneousPoint3d(-1, -1, 0, Math.sqrt(2) / 2));
		set63.add(new HomogeneousPoint3d(-1, -1, 1, Math.sqrt(2) / 2));
		List<HomogeneousPoint3d> set73 = new ArrayList<>();
		set73.add(new HomogeneousPoint3d(0, -1, 0, 1));
		set73.add(new HomogeneousPoint3d(0, -1, 1, 1));
		List<HomogeneousPoint3d> set83 = new ArrayList<>();
		set83.add(new HomogeneousPoint3d(1, -1, 0, Math.sqrt(2) / 2));
		set83.add(new HomogeneousPoint3d(1, -1, 1, Math.sqrt(2) / 2));
		List<HomogeneousPoint3d> set93 = new ArrayList<>();
		set93.add(new HomogeneousPoint3d(1, 0, 0, 1));
		set93.add(new HomogeneousPoint3d(1, 0, 1, 1));
		controlPoints3.add(set13);
		controlPoints3.add(set23);
		controlPoints3.add(set33);
		controlPoints3.add(set43);
		controlPoints3.add(set53);
		controlPoints3.add(set63);
		controlPoints3.add(set73);
		controlPoints3.add(set83);
		controlPoints3.add(set93);

		List<Double> uKnots3 = new ArrayList<>();
		uKnots3.add(0.0);
		uKnots3.add(0.0);
		uKnots3.add(0.0);
		uKnots3.add(Math.PI / 2);
		uKnots3.add(Math.PI / 2);
		uKnots3.add(Math.PI);
		uKnots3.add(Math.PI);
		uKnots3.add(3 * Math.PI / 2);
		uKnots3.add(3 * Math.PI / 2);
		uKnots3.add(2 * Math.PI);
		uKnots3.add(2 * Math.PI);
		uKnots3.add(2 * Math.PI);

		List<Double> vKnots3 = new ArrayList<>();
		vKnots3.add(0.0);
		vKnots3.add(1.0);
		vKnots3.add(2.0);
		vKnots3.add(3.0);

		NURBSSurface solid3 = new NURBSSurface(controlPoints3, uKnots3, vKnots3, 2, 1);
		solid3.setuSteps(12);
		solid3.setvSteps(1);

		List<NURBSSurface> surfaces = new ArrayList<>();
//		surfaces.add(solid1);
//		surfaces.add(solid2);
		surfaces.add(solid3);
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

		setLayout(new BorderLayout());
		final JPanel quadPanel = new JPanel();
		quadPanel.setLayout(new GridLayout(2, 2));
		topLeft = new SubSpaceView(new double[]{0,0,1});
		topRight = new SubSpaceView(new double[]{-1,0,0});
		botLeft = new SubSpaceView(new double[]{0,-1,0});
		botRight = new FullSpaceView();
		quadPanel.add(topLeft);
		quadPanel.add(topRight);
		quadPanel.add(botLeft);
		quadPanel.add(botRight);
		add(quadPanel, BorderLayout.CENTER);

		JPanel sidePanel = new JPanel();
		sidePanel.setLayout(new GridLayout(2, 1));

		JPanel infoPanel = new JPanel();
		infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
		final InfoLabel areaLabel = new InfoLabel() {
			@Override public void update() {
				setText("Area: "+solid.getArea());
			}
		};
		areaLabel.setText("Area: unknown");
		final InfoLabel volumeLabel = new InfoLabel() {
			@Override public void update() {

				if (!solid.isOpen()) {
					setText("Volume: " + solid.getVolume());
				} else {
					setText("");
				}

			}
		};
		volumeLabel.setText("Volume: unknown");

		infoPanel.add(areaLabel);
		infoPanel.add(volumeLabel);

		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));

		final QuadViewPanel self = this;

		JButton loadButton = new JButton("Load file");
		loadButton.addActionListener(new ActionListener() {
			@Override public void actionPerformed(ActionEvent e) {

				JFileChooser chooser = new JFileChooser("./files");
				int result = chooser.showOpenDialog(quadPanel);
				if (result == JFileChooser.APPROVE_OPTION) {
					File file = chooser.getSelectedFile();
					Solid solid = SolidFileManager.getInstance().load(file);
					self.setSolid(solid);
					areaLabel.setSolid(solid);
					volumeLabel.setSolid(solid);
				}
			}
		});
		buttonPanel.add(loadButton);
		sidePanel.add(buttonPanel);
		sidePanel.add(infoPanel);

		add(sidePanel, BorderLayout.WEST);

	}

	private void setSolid(Solid solid) {
		topLeft.setSolid(solid);
		topRight.setSolid(solid);
		botLeft.setSolid(solid);
		botRight.setSolid(solid);
	}

	private void toggleDrawLines(boolean toggle) {

	}

	private abstract class InfoLabel extends JLabel implements SolidObserver {

		Solid solid = null;

		public void setSolid(Solid solid) {

			if (this.solid != null) {
				this.solid.detachObserver(this);
			}
			this.solid = solid;
			if (solid != null) {
				solid.attachObserver(this, 3);
			}
			update();
		}

	}

}
