package gui;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.Box;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

public class MenuItemListener implements ActionListener {

	private static final String fileName = "/polyline.txt";

	private DrawLines drawLinesFrame;

	public MenuItemListener(DrawLines drawLinesFrame) {
		this.drawLinesFrame = drawLinesFrame;
	}

	public void actionPerformed(ActionEvent e) {
		switch (e.getActionCommand()) {
		case "Bentley–Ottmann Algorithm":
			action_BO_Alg();
			break;
		case "Perform Balaban Algorithm":
			action_balaban();
			break;
		case "Save file":
			save();
			break;
		case "Load file":
			load();
			break;
		case "Exit":
			action_exit();
			break;
		case "Credits":
			actionCreditsFrame();
			break;
		case "Documentation":
			actionDocumentationFrame();
			break;
		default:
			break;
		}
	}

	public void save() {
		// TODO implement function
	}

	public void load() {
		Scanner scan;
		String fileName = JOptionPane.showInputDialog(null,
				"Enter the name of a file");

		try {
			String canPath = new File(".").getCanonicalPath() + "/";
			canPath += fileName+".txt";
			Path fileSource = Paths.get(canPath);
			PolyReader pr = new PolyReader(fileSource, false,
					new ArrayList<Point>());

			pr.read();
			drawLinesFrame.drawPolyLine(pr.getPoints());
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}

	public void action_exit() {
		System.exit(0);
	}

	public void action_balaban() {
		// TODO implement function
	}

	private void action_BO_Alg() {
		// TODO implement function
	}

	private void actionCreditsFrame() {
		JFrame frame = new JFrame();
		JPanel panel = new JPanel();
		Box panelBox = Box.createVerticalBox();
		JLabel label1 = new JLabel("Polygon Project made by:");
		label1.setFont(new Font("Arial", Font.BOLD, 20));
		JLabel label2 = new JLabel(" Fabian Fränz");
		JLabel label3 = new JLabel(" Milan Woudenberg");
		JLabel label8 = new JLabel(" Nathan Dobbie");
		JLabel label4 = new JLabel(" Krasimir Stoyanov");
		JLabel label5 = new JLabel(" Stefano Pozzi");
		JLabel label6 = new JLabel(
				" Project Group 6                   Knowledge Engineering");
		JLabel label7 = new JLabel(
				" Maastricht University                                   March 2014");
		panel.setBorder(new TitledBorder(new EtchedBorder()));
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
		panelBox.add(Box.createVerticalStrut(75));
		panelBox.add(label6);
		panelBox.add(Box.createVerticalStrut(5));
		panelBox.add(label7);
		panel.add(panelBox);
		frame.add(panel);
		frame.setVisible(true);
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		frame.setLocation(dim.width / 2 - frame.getSize().width / 2, dim.height
				/ 2 - frame.getSize().height / 2);
		frame.setSize(320, 320);

	}

	private void actionDocumentationFrame() {
		JFrame frame = new JFrame();
		JPanel panel = new JPanel();
		Box panelBox = Box.createVerticalBox();
		JLabel label1 = new JLabel("Agenda");
		label1.setFont(new Font("Serif", Font.BOLD, 32));
		JLabel label2 = new JLabel("");
		label2.setFont(new Font("Serif", Font.BOLD, 24));
		JLabel label3 = new JLabel(" ");
		JLabel label4 = new JLabel(" ");
		JLabel label5 = new JLabel("");
		label5.setFont(new Font("Serif", Font.BOLD, 24));
		JLabel label6 = new JLabel("  ");
		JLabel label7 = new JLabel("  ");
		JLabel label8 = new JLabel("  ");
		panel.setBorder(new TitledBorder(new EtchedBorder()));
		panelBox.add(label1);
		panelBox.add(Box.createVerticalStrut(10));
		panelBox.add(label2);
		panelBox.add(Box.createVerticalStrut(10));
		panelBox.add(label3);
		panelBox.add(Box.createVerticalStrut(10));
		panelBox.add(label4);
		panelBox.add(Box.createVerticalStrut(10));
		panelBox.add(label5);
		panel.add(panelBox);
		frame.add(panel);
		frame.setVisible(true);
		frame.setSize(600, 600);
	}
}