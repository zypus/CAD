package surface;

/**
 * Author: Fabian Fränz <f.fraenz@t-online.de>
 * Date: 03/06/14
 * Project: CAD
 * Version: 1.0
 * Description: TODO Add description.
 */
public class Point3d {

	private double x,y,z;

	public Point3d() {
		x = 0;
		y = 0;
		z = 0;
	}

	public Point3d(double x, double y, double z) {

		this.x = x;
		this.y = y;
		this.z = z;
	}

	public Point3d(double[] vector) {

		this.x = vector[0];
		this.y = vector[1];
		this.z = vector[2];
	}

	public double getX() {

		return x;
	}

	public double getY() {

		return y;
	}

	public double getZ() {

		return z;
	}

	public double distance(Point3d point3d) {
		double diffX = x-point3d.getX();
		double diffY = y-point3d.getY();
		double diffZ = z-point3d.getZ();

		return Math.sqrt(Math.pow(diffX, 2) + Math.pow(diffY, 2) + Math.pow(diffZ, 2));
	}

	public Point3d add(Point3d point3d) {

		return new Point3d(x + point3d.getX(), y + point3d.getY(), z + point3d.getZ());
	}

	public Point3d mult(Point3d point3d) {

		return new Point3d(x * point3d.getX(), y * point3d.getY(), z * point3d.getZ());
	}
}
