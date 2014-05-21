package bowl.genetic;

/**
 * Author: Fabian Fränz <f.fraenz@t-online.de>
 * Date: 13/05/14
 * Project: CAD
 * Version: 1.0
 * Description: TODO Add description.
 */
public class SinglePointMutator
		extends Mutator {

	public SinglePointMutator(double chance, int multiplicity) {

		setChangeChance(chance);
		setMultiplicity(multiplicity);
	}

	@Override protected void mutation(Chromosome c) {

		int mutationPoint = random.nextInt(c.size());
		Gen newGen = c.getGenAtIndex(mutationPoint).duplicate();
		newGen.randomize();
		c.setGenAtIndex(newGen, mutationPoint);
	}
}
