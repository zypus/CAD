package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Point;
import java.awt.Shape;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Line2D;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

public class DrawLines extends JFrame {

	private static OutputComponent outputComponent;
	JPanel panel;
	static double screenMultiplier = 1;
	public static Dimension screenSize = Toolkit.getDefaultToolkit()
			.getScreenSize();

	public static void main(String[] args) {

		DrawLines frame = new DrawLines();

		JPanel panel = new JPanel();
		outputComponent = new OutputComponent();
		panel.add(outputComponent);
		panel.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 1, Color.GRAY));

		JPanel sidePanel = new JPanel();
		sidePanel.setLayout(new BoxLayout(sidePanel, BoxLayout.Y_AXIS));
		final JButton paint = new JButton("Draw");
		paint.setBackground(Color.LIGHT_GRAY);
		paint.setFocusPainted(false);
		final JButton select = new JButton("Select");
		select.setBackground(Color.LIGHT_GRAY);
		select.setFocusPainted(false);
		final JButton clear = new JButton("Clear");
		clear.setBackground(Color.LIGHT_GRAY);
		clear.setFocusPainted(false);
		paint.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				outputComponent.setDrawLines(true);
				paint.setBackground(Color.BLACK);
				paint.setForeground(Color.WHITE);
				select.setBackground(Color.GRAY);
				select.setForeground(Color.BLACK);
				clear.setBackground(Color.GRAY);
				clear.setForeground(Color.BLACK);
			}
		});
		paint.setBackground(Color.BLACK);
		paint.setForeground(Color.WHITE);
		select.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				outputComponent.setDrawLines(false);
				select.setBackground(Color.BLACK);
				select.setForeground(Color.WHITE);
				paint.setBackground(Color.GRAY);
				paint.setForeground(Color.BLACK);	
				clear.setBackground(Color.GRAY);
				clear.setForeground(Color.BLACK);
			}
		});
		
		clear.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				outputComponent.clear();
//				clear.setBackground(Color.BLACK);
//				clear.setForeground(Color.WHITE);
//				select.setBackground(Color.GRAY);
//				select.setForeground(Color.BLACK);
//				paint.setBackground(Color.GRAY);
//				paint.setForeground(Color.BLACK);	
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
		panelBox.add(Box.createVerticalStrut(10));
		panelBox.add(select);
		panelBox.add(Box.createVerticalStrut(10));
		panelBox.add(paint);
		panelBox.add(Box.createVerticalStrut(10));
		panelBox.add(clear);
		panelBox.add(Box.createVerticalStrut(10));
		panelBox.setBorder(new TitledBorder(new EtchedBorder()));
		panelBox.add(label1);
		panelBox.add(Box.createVerticalStrut(10));
		panelBox.add(label2);
		panelBox.add(Box.createVerticalStrut(10));
		panelBox.add(label3);
		panelBox.add(Box.createVerticalStrut(10));
		panelBox.add(label4);
		panelBox.add(Box.createVerticalStrut(10));
		panelBox.add(label8);
		panelBox.add(Box.createVerticalStrut(10));
		panelBox.add(label5);
		panelBox.add(Box.createVerticalStrut(10));
		panelBox.setAlignmentX(Component.LEFT_ALIGNMENT);
		JLabel background=new JLabel(new ImageIcon("C:\\Users\\Spoil\\monkey.jpg")); 
		background.setLayout(new FlowLayout()); 
		//frame.(background);
		sidePanel.add(panelBox);
		
		
		
		JPanel scrollPanel = new JPanel();
		JScrollPane scroll = new JScrollPane(scrollPanel);
		scrollPanel.setLayout(new BoxLayout(scrollPanel, BoxLayout.PAGE_AXIS));
		scroll.setMaximumSize(new Dimension(550, 400));
		scroll.setMinimumSize(new Dimension(550, 400));
		scroll.setAlignmentX(Component.LEFT_ALIGNMENT);
		sidePanel.add(scroll);
		outputComponent.setScrollPanel(scrollPanel);
		frame.add(panel, BorderLayout.CENTER);
		frame.add(sidePanel, BorderLayout.WEST);
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
		frame.setJMenuBar(menuBar);

		// Adding listener for menuItems
		JMenuItem[] menuItems = { mi1_1_1, mi1_2, mi1_3, mi1_6, mi2_1, mi2_2,
				mi4_1, mi4_2 };

		for (JMenuItem item : menuItems) {
			item.addActionListener(new MenuItemListener(frame));
		}
		frame.setJMenuBar(menuBar);
		frame.setPreferredSize(new Dimension(screenSize.width,
				screenSize.height - 40));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		screenSize.height *= screenMultiplier;
		screenSize.width *= screenMultiplier;
		frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		frame.pack();
		frame.setVisible(true);
	}

	public void drawPolyLine(ArrayList<Point> points) {
		List<Shape> shape = new ArrayList<Shape>();
		for (int i = 0; i < points.size() - 1; i++)
			shape.add(new Line2D.Double(points.get(i), points
					.get(i + 1)));
		outputComponent.rotateShapes(shape, Math.PI);
		outputComponent.magnifyShapes(shape);
		outputComponent.translateShapes(shape, screenSize.width/2, screenSize.height/2 );
		outputComponent.addShapes(shape);
	}

}