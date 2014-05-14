package bowl.genetic;

/**
 * Author: Fabian Fränz <f.fraenz@t-online.de>
 * Date: 13/05/14
 * Project: CAD
 * Version: 1.0
 * Description:
 */
public abstract class Crossover extends GeneticChange {

	public Chromosome cross(Chromosome c1, Chromosome c2) {

		if (shouldChange()) {
			return crossing(c1, c2);
		}
		return c1;
	}

	protected abstract Chromosome crossing(Chromosome c1, Chromosome c2);
}
