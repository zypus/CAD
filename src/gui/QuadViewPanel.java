package gui;

import surface.HomogeneousPoint3d;
import surface.NURBSPatchwork;
import surface.NURBSSurface;
import surface.ParametricSurface;
import surface.Solid;
import surface.SolidObserver;
import util.Bound;
import util.ParametricFunction;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.WindowConstants;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
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
//		Solid solid = new NURBSPatchwork(surfaces);

		ParametricFunction x = new ParametricFunction() {
			@Override public double getValue(double u, double v) {

				return 4*Math.sin(u) * Math.cos(v);
			}
		};
		ParametricFunction y = new ParametricFunction() {
			@Override public double getValue(double u, double v) {

				return 4*Math.sin(u) * Math.sin(v);
			}
		};
		ParametricFunction z = new ParametricFunction() {
			@Override public double getValue(double u, double v) {

				return 4*Math.cos(u);
			}
		};
		Solid solid = new ParametricSurface(x, y, z, new Bound(0, Math.PI / 3), new Bound(0, 2 * Math.PI));
		solid.setOpen(true);

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

	private JPanel quadPanel;

	private SubSpaceView topLeft;
	private SubSpaceView topRight;
	private SubSpaceView botLeft;
	private SubSpaceView botRight;

	private InfoLabel areaLabel;
	private InfoLabel volumeLabel;
	private InfoLabel areaByIntegrationLabel;
	private InfoLabel volumeByIntegrationLabel;

	private Thread computationThread = null;

	private QuadViewPanel() {
		setup();
	}

	private void setup() {

		setLayout(new BorderLayout());
		quadPanel = new JPanel();
		quadPanel.setLayout(new GridLayout(2, 2));
		topLeft = new SubSpaceView(new double[]{0,0,1}, this);
		topRight = new SubSpaceView(new double[]{-1,0,0}, this);
		botLeft = new SubSpaceView(new double[]{0,1,0}, this);
		botRight = new FullSpaceView(this);
		quadPanel.add(topLeft);
		quadPanel.add(topRight);
		quadPanel.add(botLeft);
		quadPanel.add(botRight);
		add(quadPanel, BorderLayout.CENTER);

		JPanel sidePanel = new JPanel();
		sidePanel.setLayout(new GridLayout(2, 1));

		JPanel infoPanel = new JPanel();
		infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
		areaLabel = new InfoLabel() {
			@Override public void update() {

				if (!(solid instanceof ParametricSurface)) {
					setText("Area : Computing...");
					double area = solid.getArea();
					setText("Area:\n\t" + area);
					System.out.println("area = " + area);
				}
			}
		};
		areaLabel.setText("");
		volumeLabel = new InfoLabel() {
			@Override public void update() {

				if (!solid.isOpen()) {
					setText("Volume: Computing...");
					double volume = solid.getVolume();
					setText("Volume:\n\t" + volume);
					System.out.println("volume = " + volume);
				} else {
					setText("");
				}

			}
		};
		volumeLabel.setText("");
		areaByIntegrationLabel = new InfoLabel() {
			@Override public void update() {

				if (!Thread.currentThread().isInterrupted()) {
					setText("Area by integration : Computing...");
					if (solid instanceof NURBSSurface) {
						NURBSSurface surface = (NURBSSurface) solid;
						double areaUsingIntegration = surface.getAreaUsingIntegration();
						setText("Area by integration:\n\t" + areaUsingIntegration);
						System.out.println("areaUsingIntegration = " + areaUsingIntegration);
					} else if (solid instanceof NURBSPatchwork) {
						NURBSPatchwork surface = (NURBSPatchwork) solid;
						double areaUsingIntegration = surface.getAreaUsingIntegration();
						setText("Area by integration:\n\t" + areaUsingIntegration);
						System.out.println("areaUsingIntegration = " + areaUsingIntegration);
					} else if (solid instanceof ParametricSurface) {
						double area = solid.getArea();
						setText("Area by integration:\n\t" + area);
						System.out.println("area = " + area);
				   	}else {
						setText("");
					}
				}
			}
		};
		areaByIntegrationLabel.setText("");
		volumeByIntegrationLabel = new InfoLabel() {
			@Override public void update() {

				if (!Thread.currentThread().isInterrupted()) {
					if (!solid.isOpen()) {
						if (solid instanceof NURBSSurface) {
							setText("Volume by integration : Computing...");
							NURBSSurface surface = (NURBSSurface) solid;
							double volumeUsingIntegration = surface.getVolumeUsingIntegration();
							setText("Volume by integration:\n\t" + volumeUsingIntegration);
							System.out.println("volumeUsingIntegration = " + volumeUsingIntegration);
						} else {
							setText("");
						}
					} else {
						setText("");
					}
				}

			}
		};
		volumeByIntegrationLabel.setText("");

		infoPanel.add(areaLabel);
		infoPanel.add(volumeLabel);
		infoPanel.add(areaByIntegrationLabel);
		infoPanel.add(volumeByIntegrationLabel);

		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));

		final QuadViewPanel self = this;

		JButton loadButton = new JButton("Load file");
		loadButton.addActionListener(new ActionListener() {
			@Override public void actionPerformed(ActionEvent e) {

				showLoadDialog();
			}
		});
		JButton saveButton = new JButton("Save to file");
		saveButton.addActionListener(new ActionListener() {
			@Override public void actionPerformed(ActionEvent e) {

				showSaveDialog();
			}
		});
		JButton planeButton = new JButton("Create planar surface");
		planeButton.addActionListener(new ActionListener() {
			@Override public void actionPerformed(ActionEvent e) {

				showPlaneCreationDialog();
			}
		});
		JButton triangleButton = new JButton("Set triangle amount");
		triangleButton.addActionListener(new ActionListener() {
			@Override public void actionPerformed(ActionEvent e) {

				showTriangleDialog();
			}
		});
		JButton integrationButton = new JButton("Integration settings");
		integrationButton.addActionListener(new ActionListener() {
			@Override public void actionPerformed(ActionEvent e) {

				showIntegrationDialog();
			}
		});
		buttonPanel.add(loadButton);
		buttonPanel.add(saveButton);
		buttonPanel.add(planeButton);
		buttonPanel.add(triangleButton);
		buttonPanel.add(integrationButton);
		sidePanel.add(buttonPanel);
		sidePanel.add(infoPanel);

		add(sidePanel, BorderLayout.WEST);

		addKeyListener(new KeyAdapter() {

			boolean cToggle = true;
			boolean fToggle = false;

			@Override public void keyPressed(KeyEvent e) {

				if (e.getKeyCode() == KeyEvent.VK_F) {
					fToggle = !fToggle;
					if (fToggle) {
						quadPanel.setEnabled(false);
						quadPanel.remove(botRight);
						self.remove(quadPanel);
						self.add(botRight, BorderLayout.CENTER);
						self.revalidate();
						quadPanel.repaint();
						botRight.repaint();
					} else {
						self.remove(botRight);
						quadPanel.add(botRight);
						quadPanel.setEnabled(true);
						self.add(quadPanel, BorderLayout.CENTER);
						self.revalidate();
						botRight.repaint();
						quadPanel.repaint();
					}
				} else if (e.getKeyCode() == KeyEvent.VK_C) {
					cToggle = !cToggle;
					topLeft.showControlPoints(cToggle);
					topRight.showControlPoints(cToggle);
					botLeft.showControlPoints(cToggle);
					botRight.showControlPoints(cToggle);
				} else if (e.getKeyChar() == '+') {
					LineDrawer.zoom += 0.1;
					topLeft.update();
					topRight.update();
					botLeft.update();
					botRight.update();
				} else if (e.getKeyChar() == '-') {
					if (LineDrawer.zoom > 0.05) {
						LineDrawer.zoom -= 0.1;
						topLeft.update();
						topRight.update();
						botLeft.update();
						botRight.update();
					}
				} else if (e.getKeyCode() == KeyEvent.VK_L) {
					showLoadDialog();
				} else if (e.getKeyCode() == KeyEvent.VK_S) {
					showSaveDialog();
				} else if (e.getKeyCode() == KeyEvent.VK_R) {
					LineDrawer.switchFace();
					topLeft.update();
					topRight.update();
					botLeft.update();
					botRight.update();
				} else if (e.getKeyCode() == KeyEvent.VK_D) {
					LineDrawer.switchDrawMode();
					topLeft.update();
					topRight.update();
					botLeft.update();
					botRight.update();
				}
			}
		});
		setFocusable(true);
		requestFocus();

	}

	private void showIntegrationDialog() {

		JTextField uSteps = new JTextField();
		JTextField vSteps = new JTextField();
		final JComponent[] input = {
				new JLabel("Steps in u direction"), uSteps,
				new JLabel("Steps in v direction"), vSteps
		};
		JOptionPane.showMessageDialog(this, input, "Integration settings", JOptionPane.PLAIN_MESSAGE);
		if (!uSteps.getText().equals("") && !vSteps.getText().equals("")) {
			Solid solid = topLeft.getSolid();
			solid.setIntegrationStepsU(Integer.parseInt(uSteps.getText()));
			solid.setIntegrationStepsV(Integer.parseInt(vSteps.getText()));
			areaByIntegrationLabel.setText("Area by integration : Recomputing...");
			if (!solid.isOpen()) {
				volumeByIntegrationLabel.setText("Volume by integration : Recomputing...");
			}
			Thread thread = new Thread(new Runnable() {
				@Override public void run() {

					areaByIntegrationLabel.update();
					volumeByIntegrationLabel.update();
				}
			});
			thread.start();
		}

		requestFocus();
	}

	private void showLoadDialog() {

		JFileChooser chooser = new JFileChooser("./files");
		int result = chooser.showOpenDialog(quadPanel);
		if (result == JFileChooser.APPROVE_OPTION) {
			File file = chooser.getSelectedFile();
			Solid solid = SolidFileManager.getInstance().load(file);
			setSolid(solid);
		}
		requestFocus();
	}

	private void showSaveDialog() {

		JFileChooser chooser = new JFileChooser("./files");
		int result = chooser.showSaveDialog(quadPanel);
		if (result == JFileChooser.APPROVE_OPTION) {
			File file = chooser.getSelectedFile();
			if (!file.getName().endsWith(".txt")) {
				file = new File(file.getAbsolutePath() + ".txt");
			}
			SolidFileManager.getInstance().save(file, topLeft.getSolid(), this);
		}
		requestFocus();
	}

	private void showTriangleDialog() {

		JTextField uSteps = new JTextField();
		JTextField vSteps = new JTextField();
		final JComponent[] input = {
				new JLabel("Triangles in u direction"), uSteps,
				new JLabel("Triangles in v direction"), vSteps
		};
		JOptionPane.showMessageDialog(this, input, "Set triangle amounts", JOptionPane.PLAIN_MESSAGE);
		if (!uSteps.getText().equals("") && !vSteps.getText().equals("")) {
			Solid solid = topLeft.getSolid();
			solid.setuSteps(Integer.parseInt(uSteps.getText()));
			solid.setvSteps(Integer.parseInt(vSteps.getText()));
			solid.setChanged();
			topLeft.update();
			topRight.update();
			botLeft.update();
			botRight.update();
			areaLabel.update();
			volumeLabel.update();
		}


		requestFocus();
	}

	private void showPlaneCreationDialog() {

		JTextField width = new JTextField();
		JTextField height = new JTextField();
		JTextField uOrder = new JTextField();
		JTextField vOrder = new JTextField();
		final JComponent[] input = {
				new JLabel("Number of u controls"), width,
				new JLabel("Number of v controls"), height,
				new JLabel("Degree in u direction"), uOrder,
				new JLabel("Degree in v direction"), vOrder
		};
		JOptionPane.showMessageDialog(this, input, "Create plane surface", JOptionPane.PLAIN_MESSAGE);
		if (!width.getText().equals("") && !height.getText().equals("") && !uOrder.getText().equals("") && !vOrder.getText().equals("")) {
			Solid
					solid =
					createPlane(Integer.parseInt(width.getText()), Integer.parseInt(height.getText()), Integer.parseInt(uOrder.getText()),
								Integer.parseInt(vOrder.getText()));
			width.requestFocus();
			setSolid(solid);
		}

		requestFocus();
	}

	private NURBSSurface createPlane(int width, int height, int uOrder, int vOrder) {

		List<List<HomogeneousPoint3d>> controlPoints = new ArrayList<>();
		int w = width - 1;
		int h = height - 1;
		for (int i = 0; i <= w; i++) {
			List<HomogeneousPoint3d> vRow = new ArrayList<>();
			for (int j = 0; j <= h; j++) {
				vRow.add(new HomogeneousPoint3d(3*(double)i/w-1.5, 3*(double)j/h-1.5, 0));
			}
			controlPoints.add(vRow);
		}
		List<Double> uKnots = new ArrayList<>();
		for (int i = 0; i <= width + uOrder; i++) {
			uKnots.add(new Double(i));
		}
		List<Double> vKnots = new ArrayList<>();
		for (int i = 0; i <= height + vOrder; i++) {
			vKnots.add(new Double(i));
		}
		NURBSSurface surface = new NURBSSurface(controlPoints, uKnots, vKnots, uOrder, vOrder);
		surface.setuSteps(width+2);
		surface.setvSteps(height+2);
		return surface;
	}

	public void setSolid(final Solid solid) {
		if (computationThread != null && computationThread.isAlive()) {
			computationThread.interrupt();
		}
		topLeft.setSolid(solid);
		topRight.setSolid(solid);
		botLeft.setSolid(solid);
		botRight.setSolid(solid);
		computationThread = new Thread(new Runnable() {
			@Override public void run() {

				areaLabel.setSolid(solid);
				volumeLabel.setSolid(solid);
				areaByIntegrationLabel.setSolid(solid);
				volumeByIntegrationLabel.setSolid(solid);
			}
		});
		computationThread.start();
	}

	public void update() {

		topLeft.update();
		topRight.update();
		botLeft.update();
		botRight.update();
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
