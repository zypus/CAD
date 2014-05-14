package bowl.genetic;

/**
 * Author: Fabian Fränz <f.fraenz@t-online.de>
 * Date: 13/05/14
 * Project: CAD
 * Version: 1.0
 * Description: TODO Add description.
 */
public interface Gen<P> {

	P getPhenotype();
	void randomize();
	Gen<P> duplicate();
}
