package bowl.genetic;

import java.util.Random;

/**
 * Author: Fabian Fränz <f.fraenz@t-online.de>
 * Date: 13/05/14
 * Project: CAD
 * Version: 1.0
 * Description: TODO Add description.
 */

public class RandomSingleCrossover
		extends Crossover {

	private Random random = new Random(System.currentTimeMillis());

	public RandomSingleCrossover(double chance) {

		setChangeChance(chance);
	}

	@Override public Chromosome crossing(Chromosome c1, Chromosome c2) {

		Chromosome newChromosome = c1.duplicate();
		int crossoverPoint = random.nextInt(Math.min(c1.size(), c2.size()));
		for (int i = crossoverPoint; i < newChromosome.size(); i++) {
			if (i < c2.size()) {
				newChromosome.setGenAtIndex(c2.getGenAtIndex(i), i);
			}
		}
		return newChromosome;
	}
}