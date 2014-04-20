package gui;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.PathIterator;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

public class Line implements Shape {
    public final int x1;
    public final int x2;
    public final int y1;
    public final int y2;
    public Line(int x1, int y1, int x2, int y2) {
        this.x1 = x1;
        this.x2 = x2;
        this.y1 = y1;
        this.y2 = y2;
    }
    public void paint(Graphics g) {
        g.drawLine(this.x1, this.y1, this.x2, this.y2);
    }
	@Override
	public boolean contains(Point2D p) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public boolean contains(Rectangle2D r) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public boolean contains(double x, double y) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public boolean contains(double x, double y, double w, double h) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public Rectangle getBounds() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public Rectangle2D getBounds2D() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public PathIterator getPathIterator(AffineTransform at) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public PathIterator getPathIterator(AffineTransform at, double flatness) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public boolean intersects(Rectangle2D r) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public boolean intersects(double x, double y, double w, double h) {
		// TODO Auto-generated method stub
		return false;
	}
}