package surface;

import java.util.ArrayList;
import java.util.List;

/**
 * Author: Fabian Fränz <f.fraenz@t-online.de>
 * Date: 12/06/14
 * Project: CAD
 * Version: 1.0
 * Description: TODO Add description.
 */
public abstract class FieldFunction {

	public abstract FieldValue D(Point3d point3d);

	public class FieldValue {
		public boolean inside;
		public double gradient;

		public FieldValue(boolean inside, double gradient) {

			this.inside = inside;
			this.gradient = gradient;
		}
	}

	public class MetaFieldFunction extends FieldFunction {
		public List<FieldFunction> functionList = new ArrayList<>();

		@Override public FieldValue D(Point3d point3d) {

			FieldValue smallest = new FieldValue(false, 9999999);
			for (FieldFunction fieldFunction : functionList) {
				FieldValue fieldValue = fieldFunction.D(point3d);
				if (Math.abs(fieldValue.gradient) < Math.abs(smallest.gradient) && !(smallest.inside && !fieldValue.inside)) {
					smallest = fieldValue;
				}
			}
			return smallest;
		}
	}

	public class PointFieldFunction extends FieldFunction {
		private Point3d controlPoint;
		private double a = 0;
		private double b = 0;

		public PointFieldFunction(Point3d controlPoint, double a, double b) {

			this.controlPoint = controlPoint;
			this.a = a;
			this.b = b;
		}

		// soft object
		@Override public FieldValue D(Point3d point3d) {

			double r = point3d.distance(controlPoint);
			if (r <= b) {
				return new FieldValue(true, a * (1 - (4*Math.pow(r, 6))/(9*Math.pow(b, 6) + (17*Math.pow(r, 4)/(9*Math.pow(b, 4)) - (22*Math.pow(r, 2))/(9*Math.pow(b, 2))))));
			}
			r -= b;
			return new FieldValue(false,
										 -a * (1 - (4 * Math.pow(r, 6)) / (9 * Math.pow(b, 6) + (17 * Math.pow(r, 4) / (9 * Math.pow(b, 4))
																								- (22 * Math.pow(r, 2)) / (9 * Math.pow(b,
																																		2))))));
		}
	}

	public class LineFieldFunction
			extends FieldFunction {

		private Point3d controlPoint;
		private Point3d vector;
		private double a = 0;
		private double b = 0;

		public LineFieldFunction(Point3d controlPoint1, Point3d controlPoint2, double a, double b) {

			this.controlPoint = controlPoint1;
			this.vector = controlPoint2.sub(controlPoint1);
			this.a = a;
			this.b = b;
		}

		// soft object
		@Override public FieldValue D(Point3d point3d) {

			Point3d ap = controlPoint.sub(point3d);
			double r = ap.sub(vector.scalarMult(ap.dot(vector))).norm();
			if (r <= b) {
				return new FieldValue(true,
									  a * (1 - (4 * Math.pow(r, 6)) / (9 * Math.pow(b, 6) + (17 * Math.pow(r, 4) / (9 * Math.pow(b, 4))
																							 - (22 * Math.pow(r, 2)) / (9 * Math.pow(b,
																																	 2))))));
			}
			r -= b;
			return new FieldValue(false,
								  -a * (1 - (4 * Math.pow(r, 6)) / (9 * Math.pow(b, 6) + (17 * Math.pow(r, 4) / (9 * Math.pow(b, 4))
																						 - (22 * Math.pow(r, 2)) / (9 * Math.pow(b, 2))))));
		}
	}

}
