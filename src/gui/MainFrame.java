package gui;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Point;
import java.awt.Shape;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.geom.Line2D;
import java.util.ArrayList;
import java.util.List;

public class MainFrame
		extends JFrame {

	public static Dimension screenSize = Toolkit.getDefaultToolkit()
												.getScreenSize();
	static double screenMultiplier = 1;
	private static OutputComponent outputComponent;

	public MainFrame() {
		super();
		JLayeredPane layeredPanel = new JLayeredPane();
		final JPanel background = new JPanel();
		background.setBackground(Color.BLACK);
		layeredPanel.setBounds(0, 0, screenSize.width, screenSize.height);
		layeredPanel.setPreferredSize(screenSize);
		outputComponent = new OutputComponent();
		layeredPanel.add(background, new Integer(0), 0);
		layeredPanel.add(outputComponent, new Integer(2), 0);
		outputComponent.setup();
		layeredPanel.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 1, Color.GRAY));

		layeredPanel.addComponentListener(new ComponentAdapter() {
			@Override public void componentResized(ComponentEvent e) {

				super.componentResized(e);
				outputComponent.setBounds(0,0, e.getComponent().getWidth(), e.getComponent().getHeight());
				background.setBounds(0, 0, e.getComponent().getWidth(), e.getComponent().getHeight());
			}
		});

		JPanel sidePanel = new JPanel();
		sidePanel.setLayout(new BoxLayout(sidePanel, BoxLayout.Y_AXIS));
		final JButton paint = new JButton("Draw "+SplineType.BEZIER.toString());
		paint.putClientProperty("JComponent.sizeVariant", "large");
		paint.setBackground(Color.LIGHT_GRAY);
		paint.setFocusPainted(false);
		final JButton select = new JButton("Select");
		select.putClientProperty("JComponent.sizeVariant", "large");
		select.setBackground(Color.LIGHT_GRAY);
		select.setFocusPainted(false);
		final JButton clear = new JButton("Clear");
		clear.putClientProperty("JComponent.sizeVariant", "large");
		clear.setBackground(Color.LIGHT_GRAY);
		clear.setFocusPainted(false);
		paint.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				SplineType type = outputComponent.setDrawing();
				paint.setText("Draw "+type.toString());
				paint.setBackground(Color.BLACK);
				paint.setForeground(Color.WHITE);
				select.setBackground(Color.LIGHT_GRAY);
				select.setForeground(Color.BLACK);
				clear.setBackground(Color.LIGHT_GRAY);
				clear.setForeground(Color.BLACK);
			}
		});
		paint.setBackground(Color.BLACK);
		paint.setForeground(Color.WHITE);
		select.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				outputComponent.setSelecting();
				select.setBackground(Color.BLACK);
				select.setForeground(Color.WHITE);
				paint.setBackground(Color.LIGHT_GRAY);
				paint.setForeground(Color.BLACK);
				clear.setBackground(Color.LIGHT_GRAY);
				clear.setForeground(Color.BLACK);
			}
		});

		clear.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				outputComponent.clear();
			}
		});

		final JButton bowl = new JButton("Bowl Maker disabled");
		bowl.putClientProperty("JComponent.sizeVariant", "large");
		bowl.setBackground(Color.LIGHT_GRAY);
		bowl.setFocusPainted(false);
		bowl.addActionListener(new ActionListener() {

			boolean toggle = false;

			@Override
			public void actionPerformed(ActionEvent e) {

				toggle = !toggle;
				outputComponent.toggleBowlMaker(toggle);
				bowl.setBackground((toggle) ? Color.DARK_GRAY : Color.LIGHT_GRAY);
				bowl.setForeground((toggle) ? Color.WHITE : Color.BLACK);
				bowl.setText((toggle) ? "Bowl Maker enabled" : "Bowl Maker disabled");
			}
		});

		final JButton dim = new JButton("2D");
		dim.putClientProperty("JComponent.sizeVariant", "large");
		dim.setBackground(Color.LIGHT_GRAY);
		dim.setFocusPainted(false);
		dim.addActionListener(new ActionListener() {

			boolean toggle = false;

			@Override
			public void actionPerformed(ActionEvent e) {

				toggle = !toggle;
				outputComponent.BowlMakerVisualisation(toggle);
				dim.setText((toggle) ? "3D" : "2D");
			}
		});

		//sidePanel.add(paint);
		//sidePanel.add(select);
		sidePanel.setBorder(new TitledBorder(new EtchedBorder()));
		Box panelBox = Box.createVerticalBox();
		panelBox.setMinimumSize(new Dimension(5000, 400));
		JLabel label1 = new JLabel("Polygon Project made by:");
		label1.setFont(new Font("Arial", Font.BOLD, 15));
		JLabel label2 = new JLabel(" Fabian Fränz");
		JLabel label3 = new JLabel(" Milan Woudenberg");
		JLabel label8 = new JLabel(" Nathan Dobbie");
		JLabel label4 = new JLabel(" Krasimir Stoyanov");
		JLabel label5 = new JLabel(" Stefano Pozzi");
		sidePanel.setBorder(new TitledBorder(new EtchedBorder()));
		Component verticalStrut = Box.createVerticalStrut(10);
		panelBox.add(verticalStrut);
		panelBox.add(select);
		panelBox.add(verticalStrut);
		panelBox.add(paint);
		panelBox.add(verticalStrut);
		panelBox.add(clear);
		panelBox.add(verticalStrut);
		panelBox.add(bowl);
		panelBox.add(verticalStrut);
		panelBox.add(dim);
		panelBox.add(verticalStrut);
		panelBox.setBorder(new TitledBorder(new EtchedBorder()));
		panelBox.add(label1);
		panelBox.add(verticalStrut);
		panelBox.add(label2);
		panelBox.add(verticalStrut);
		panelBox.add(label3);
		panelBox.add(verticalStrut);
		panelBox.add(label4);
		panelBox.add(verticalStrut);
		panelBox.add(label8);
		panelBox.add(verticalStrut);
		panelBox.add(label5);
		panelBox.add(verticalStrut);
		panelBox.setAlignmentX(Component.LEFT_ALIGNMENT);
//		JLabel background = new JLabel(new ImageIcon("C:\\Users\\Spoil\\monkey.jpg"));
//		background.setLayout(new FlowLayout());
		//this.(background);
		sidePanel.add(panelBox);

		JPanel scrollPanel = new JPanel();
		JScrollPane scroll = new JScrollPane(scrollPanel);
		scrollPanel.setLayout(new BoxLayout(scrollPanel, BoxLayout.PAGE_AXIS));
		scroll.setMaximumSize(new Dimension(550, 400));
		scroll.setMinimumSize(new Dimension(550, 400));
		scroll.setAlignmentX(Component.LEFT_ALIGNMENT);

		InfoPanel infoPanel = new InfoPanel();
		outputComponent.addSelectionObserver(infoPanel);
		scrollPanel.add(infoPanel);

		sidePanel.add(scroll);

		this.add(layeredPanel, BorderLayout.CENTER);
		this.add(sidePanel, BorderLayout.WEST);
		// Menu bar init
		JMenuBar menuBar = new JMenuBar();

		// "File" menu item init
		JMenu menu1 = new JMenu("File");

		// "New" sub menu init
		JMenu mi1_1 = new JMenu("File");
		JMenuItem mi1_1_1 = new JMenuItem("New Shape");
		mi1_1.add(mi1_1_1);

		JMenuItem mi1_2 = new JMenuItem("Save file");
		JMenuItem mi1_3 = new JMenuItem("Load file");
		JMenuItem mi1_6 = new JMenuItem("Exit");
		menu1.add(mi1_1);
		menu1.add(mi1_2);
		menu1.add(mi1_3);
		menu1.addSeparator();
		menu1.add(mi1_6);

		// "Algorithm" menu item init
		JMenu menu2 = new JMenu("Algorithms");
		JMenuItem mi2_1 = new JMenuItem("Perform Bentley–Ottmann Algorithm");
		JMenuItem mi2_2 = new JMenuItem("Perform Balaban Algorithm");
		menu2.add(mi2_1);
		menu2.add(mi2_2);

		JMenu menu4 = new JMenu("Help");
		JMenuItem mi4_1 = new JMenuItem("Documentation");
		JMenuItem mi4_2 = new JMenuItem("Credits");
		menu4.add(mi4_1);
		menu4.add(mi4_2);

		// Adding menu bar to JFrame
		menuBar.add(menu1);
		menuBar.add(menu2);
		menuBar.add(menu4);
		this.setJMenuBar(menuBar);

		// Adding listener for menuItems
		JMenuItem[] menuItems = { mi1_1_1, mi1_2, mi1_3, mi1_6, mi2_1, mi2_2,
								  mi4_1, mi4_2 };

		for (JMenuItem item : menuItems) {
			item.addActionListener(new MenuItemListener(this));
		}
		this.setJMenuBar(menuBar);
		this.setPreferredSize(new Dimension(screenSize.width,
											 screenSize.height - 40));
		this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		screenSize.height *= screenMultiplier;
		screenSize.width *= screenMultiplier;
		this.setExtendedState(JFrame.MAXIMIZED_BOTH);
		SwingUtilities.updateComponentTreeUI(this);
		this.pack();
		this.setVisible(true);
	}

	private void createToolbar() {

	}

	public void drawPolyLine(ArrayList<Point> points) {

		List<Shape> shape = new ArrayList<>();
		for (int i = 0; i < points.size() - 1; i++) {
			shape.add(new Line2D.Double(points.get(i), points
					.get(i + 1)));
		}
		outputComponent.rotateShapes(shape, Math.PI);
		outputComponent.magnifyShapes(shape);
		outputComponent.translateShapes(shape, screenSize.width / 2, screenSize.height / 2);
		outputComponent.addShapes(shape);
	}

}