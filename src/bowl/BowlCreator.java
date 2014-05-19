package bowl;

import bowl.genetic.Individual;
import gui.SplineType;
import splines.SplineProperty;

import java.util.List;

/**
 * Author: Fabian Fränz <f.fraenz@t-online.de>
 * Date: 19/05/14
 * Project: CAD
 * Version: 1.0
 * Description: TODO Add description.
 */
public interface BowlCreator {

	void setup(SplineProperty numerator, SplineProperty denominator, SplineType type);
	boolean advance();

	List<? extends Individual> getResult();

}
