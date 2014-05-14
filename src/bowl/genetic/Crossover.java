package bowl.genetic;

/**
 * Author: Fabian Fränz <f.fraenz@t-online.de>
 * Date: 13/05/14
 * Project: CAD
 * Version: 1.0
 * Description:
 */
public interface Crossover {

	Chromosome cross(Chromosome c1, Chromosome c2);
}
