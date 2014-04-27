package gui;

import splines.*;

/**
* Author: Fabian Fränz <f.fraenz@t-online.de>
* Date: 27/04/14
* Project: CAD
* Version: 1.0
* Description: Enum for the different spline types.
*/
public enum SplineType {
	LINEAR(LinearSpline.class),
	CUBIC(CubicSpline.class),
	B(BSpline.class),
	BEZIER(BezierSpline.class);

	public final Class<? extends Spline> type;

	SplineType(Class<? extends Spline> type) {

		this.type = type;
	}

	/**
	 * Creates the spline instance
	 *
	 * @return the instance of the registered spline type
	 */
	public Spline createInstance() {

		try {
			return type.newInstance();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public Class<? extends Spline> getType() {
		return type;
	}

	public String toString() {
		switch (this) {
		case LINEAR: return "LinearSpline";
		case CUBIC: return "CubicSpline";
		case B: return  "BSpline";
		case BEZIER: return  "BezierSpline";
		}
		return "Invalid";
	}
}
