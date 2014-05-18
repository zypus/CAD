package bowl.genetic;

/**
 * Author: Fabian Fränz <f.fraenz@t-online.de>
 * Date: 14/05/14
 * Project: CAD
 * Version: 1.0
 * Description: TODO Add description.
 */
public class ShrinkMutator extends Mutator {

	public ShrinkMutator(double chance) {

		setChangeChance(chance);
	}

	@Override protected void mutation(Chromosome c) {

		if (c.size() > 1) {
			c.getGens().remove(c.size()-1);
		}
	}
}
