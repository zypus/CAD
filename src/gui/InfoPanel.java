package gui;

import gui.tools.select.Selectable;
import gui.tools.select.SelectionObserver;
import splines.SplineArea;
import splines.SplineLength;
import splines.SplineObserver;

import javax.swing.JComponent;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

/**
 * Author: Fabian Fränz <f.fraenz@t-online.de>
 * Date: 16/05/14
 * Project: CAD
 * Version: 1.0
 * Description: TODO Add description.
 */
public class InfoPanel
		extends JComponent
		implements SelectionObserver, SplineObserver {

	Spline2D currentlyDrawnSpline = null;
	List<Spline2D> splines = new ArrayList<>();

	private boolean tracking = false;

	OutputComponent delegate = null;

	public OutputComponent getDelegate() {

		return delegate;
	}

	public void setDelegate(OutputComponent delegate) {

		this.delegate = delegate;
	}

	public InfoPanel() {
		MouseAdapter adapter = new MouseAdapter() {
			@Override public void mouseEntered(MouseEvent e) {

				super.mouseEntered(e);
				tracking = true;
			}

			@Override public void mouseExited(MouseEvent e) {

				super.mouseExited(e);
				tracking = false;
				for (Spline2D spline : splines) {
					spline.setFilled(false);
				}
			}

			@Override public void mouseMoved(MouseEvent e) {

				super.mouseMoved(e);
				if (tracking) {
					for (Spline2D spline : splines) {
						spline.setFilled(false);
					}
					int index = e.getY() / 52;
					if (index < splines.size()) {
						splines.get(index).setFilled(true);
					}
					delegate.repaint();
				}
			}

			@Override public void mouseClicked(MouseEvent e) {

				super.mouseClicked(e);
				if (tracking) {
					int index = e.getY() / 52;
					if (index < splines.size()) {
						Spline2D spline2d = splines.get(index);
						if (spline2d.getType().equals(SplineType.BEZIER)) {
							spline2d.switchToType(SplineType.CUBIC);
						} else if (spline2d.getType().equals(SplineType.CUBIC)) {
							spline2d.switchToType(SplineType.LINEAR);
						} else if (spline2d.getType().equals(SplineType.LINEAR)) {
							spline2d.switchToType(SplineType.BEZIER);
						}
					}
					delegate.repaint();
					repaint();
				}
			}
		};
		addMouseListener(adapter);
		addMouseMotionListener(adapter);
	}

	@SuppressWarnings("unchecked") @Override public void update(List<? extends Selectable> selectables) {

		for (Spline2D spline : splines) {
			spline.getSpline().removeObserver(this);
		}
		splines = (List<Spline2D>) selectables;
		for (Spline2D spline : splines) {
			spline.getSpline().addObserver(this);
		}
		getParent().repaint();
	}

	@Override public Dimension getPreferredSize() {

		return new Dimension(200, splines.size()*52);
	}



	@Override protected void paintComponent(Graphics g) {

		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;

		int offset = 0;
		for (int i = (currentlyDrawnSpline != null) ?-1: 0; i < splines.size(); i++) {
			Spline2D spline2d;
			if (i == -1) {
				spline2d = currentlyDrawnSpline;
			}
			else {
				spline2d = splines.get(i);
			}
			g2.setStroke(new BasicStroke(2));
			g2.setColor(spline2d.getColor());
			g2.fill(new Rectangle.Double(0, offset, 200, 50));
			double brightness = spline2d.getColor().getRed() + spline2d.getColor().getGreen() + spline2d.getColor().getBlue();
			if (brightness > 250) {
				g2.setColor(Color.BLACK);
			} else {
				g2.setColor(Color.WHITE);
			}
			g2.draw(new Rectangle.Double(0,offset,200, 50));
			String info = spline2d.getType().toString()+"\n";
			info += "Length: "+(long)(new SplineLength().getValue(spline2d.getSpline())*100)/100.0;
			info += "\nArea: "+(long)(new SplineArea().getValue(spline2d.getSpline())*100)/100.0;
			Font font = new Font("Arial", Font.BOLD, 15);
			FontMetrics metric = g2.getFontMetrics(font);
			int lineoffset = 0;
			g2.setFont(font);
			g2.setStroke(new BasicStroke(2));
			String[] lines = info.split("\\r?\\n");
			int blockHeight = lines.length * (metric.getHeight()-15);
			for (String line : lines) {
				int l = metric.stringWidth(line);
				g2.drawString(line, 100 - l / 2, 20-blockHeight/2+offset+lineoffset);
				lineoffset += metric.getHeight()-5;
			}
			offset += 52;
		}
	}

	public Spline2D getCurrentlyDrawnSpline() {

		return currentlyDrawnSpline;
	}

	public void setCurrentlyDrawnSpline(Spline2D currentlyDrawnSpline) {

		if (currentlyDrawnSpline == null) {
			if (this.currentlyDrawnSpline != null) {
				this.currentlyDrawnSpline.getSpline().removeObserver(this);
			}
			this.currentlyDrawnSpline = null;
		} else {
			this.currentlyDrawnSpline = currentlyDrawnSpline;
			currentlyDrawnSpline.getSpline().addObserver(this);
		}
	}

	@Override public void observedSplineChanged() {
		repaint();
	}
}
