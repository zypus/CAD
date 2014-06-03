package surface;

/**
 * Author: Fabian Fränz <f.fraenz@t-online.de>
 * Date: 03/06/14
 * Project: CAD
 * Version: 1.0
 * Description: TODO Add description.
 */
public interface Solid {

	GeometryFactory getFactory();
	double getArea();
	double getVolume();

}
