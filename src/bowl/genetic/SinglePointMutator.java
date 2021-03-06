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

	@Override protected void mutation(Chromosome c) {

		int mutationPoint = random.nextInt(c.size());
		c.getGenAtIndex(mutationPoint).randomize();
	}
}
