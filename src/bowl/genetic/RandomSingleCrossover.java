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

//		System.out.println("c1 = " + c1.size());
//		System.out.println("c2 = " + c2.size());
		Chromosome newChromosome = c1.duplicate();
		assert newChromosome != c1;
		int crossoverPoint = random.nextInt(Math.min(c1.size(), c2.size()));
		for (int i = crossoverPoint; i < newChromosome.size(); i++) {
			if (i < c2.size()) {
				newChromosome.setGenAtIndex(c2.getGenAtIndex(i).duplicate(), i);
			}
		}
		assert c1.size() == newChromosome.size();
//		System.out.println("newChromosome = " + newChromosome.size());
		return newChromosome;
	}
}