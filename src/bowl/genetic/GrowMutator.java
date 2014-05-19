package bowl.genetic;

import java.util.List;

/**
 * Author: Fabian Fränz <f.fraenz@t-online.de>
 * Date: 14/05/14
 * Project: CAD
 * Version: 1.0
 * Description: TODO Add description.
 */
public class GrowMutator extends Mutator {

	public GrowMutator(double chance) {
		setChangeChance(chance);
	}

	@Override protected void mutation(Chromosome c) {

		Gen gen = c.getGens().get(0).duplicate();
//		gen.randomize();
		((List<Gen>)c.getGens()).add(gen);
	}
}
