package surface;

/**
 * Author: Fabian Fränz <f.fraenz@t-online.de>
 * Date: 15/06/14
 * Project: CAD
 * Version: 1.0
 * Description: TODO Add description.
 */
public class HomogeneousPoint3d extends Point3d {

	public double w;

	public HomogeneousPoint3d(Point3d point3d) {
		super(point3d.getX(), point3d.getY(), point3d.getZ());
		w = 1;
	}

	public HomogeneousPoint3d(double x, double y, double z) {

		super(x, y, z);
		w = 1;
	}

	public HomogeneousPoint3d(double x, double y, double z, double w) {
		super(x,y,z);
		this.w = w;
	}

	public double getW() {

		return w;
	}

	public void setW(double w) {

		this.w = w;
	}
}
