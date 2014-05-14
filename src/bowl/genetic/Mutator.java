package bowl.genetic;

/**
 * Author: Fabian Fränz <f.fraenz@t-online.de>
 * Date: 13/05/14
 * Project: CAD
 * Version: 1.0
 * Description: TODO Add description.
 */
public abstract class Mutator extends GeneticChange {

	public void mutate(Chromosome c) {
		if (shouldChange()) {
			mutation(c);
		}
	}

	protected abstract void mutation(Chromosome c);

}
