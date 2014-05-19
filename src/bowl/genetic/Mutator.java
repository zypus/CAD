package bowl.genetic;

/**
 * Author: Fabian Fränz <f.fraenz@t-online.de>
 * Date: 13/05/14
 * Project: CAD
 * Version: 1.0
 * Description: TODO Add description.
 */
public abstract class Mutator extends GeneticChange {

	private int multiplicity = 1;

	public void mutate(Chromosome c) {
		for (int i = 0; i < multiplicity; i++) {
			if (shouldChange()) {
				mutation(c);
			}
		}
	}

	public int getMultiplicity() {

		return multiplicity;
	}

	public void setMultiplicity(int multiplicity) {

		this.multiplicity = multiplicity;
	}

	protected abstract void mutation(Chromosome c);

}
