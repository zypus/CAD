package surface;

import org.scilab.forge.scirenderer.tranformations.Vector3d;

/**
 * Author: Fabian Fränz <f.fraenz@t-online.de>
 * Date: 03/06/14
 * Project: CAD
 * Version: 1.0
 * Description: TODO Add description.
 */
public class Point3d {

	public double x,y,z;

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

	public Point3d(Vector3d vector3d) {

		this.x = vector3d.getX();
		this.y = vector3d.getY();
		this.z = vector3d.getZ();
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

	public double get(int i) {
		switch (i) {
		case 0:
			return x;
		case 1:
			return y;
		case 2:
			return z;
		default:
			System.out.println("Should not happen!");
		}
		return 0;
	}

	public void set(int i, double val) {

		switch (i) {
		case 0:
			x = val;
		break;
		case 1:
			y = val;
		break;
		case 2:
			z = val;
		break;
		default:
			System.out.println("Should not happen!");
		}
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

	public Point3d scalarMult(double scalar) {

		return new Point3d(x * scalar, y * scalar, z * scalar);
	}

	public Point3d sub(Point3d point3d) {

		return new Point3d(x - point3d.getX(), y - point3d.getY(), z - point3d.getZ());
	}

	public Point3d cross(Point3d point3d) {

		double newX = y * point3d.getZ() - z * point3d.getY();
		double newY = z * point3d.getX() - x * point3d.getZ();
		double newZ = x * point3d.getY() - y * point3d.getX();
		return new Point3d(newX, newY, newZ);
	}

	public double dot(Point3d point3d) {

		return x*point3d.getX() + y*point3d.getY() + z*point3d.getZ();
	}

	public double norm() {

		return Math.sqrt(Math.pow(x,2)+ Math.pow(y, 2)+ Math.pow(z, 2));
	}

	public String toString() {

		return "( "+x+" "+y+" "+z+" )";
	}
}
