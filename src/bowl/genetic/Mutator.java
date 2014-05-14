package bowl.genetic;

import java.util.Random;

/**
 * Author: Fabian Fränz <f.fraenz@t-online.de>
 * Date: 13/05/14
 * Project: CAD
 * Version: 1.0
 * Description: TODO Add description.
 */
public abstract class Mutator {

	protected Random random = new Random(System.currentTimeMillis());
	protected double mutationChance;

	public void mutate(Chromosome c) {
		if (random.nextDouble() < mutationChance) {
			mutation(c);
		}
	}

	protected abstract void mutation(Chromosome c);

	public double getMutationChance() {

		return mutationChance;
	}

	public void setMutationChance(double mutationChance) {

		this.mutationChance = mutationChance;
	}
}
