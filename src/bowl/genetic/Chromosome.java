package bowl.genetic;

/**
 * Author: Fabian Fränz <f.fraenz@t-online.de>
 * Date: 13/05/14
 * Project: CAD
 * Version: 1.0
 * Description:
 */
public abstract class Chromosome {

	public void mutate(Mutator... mutators) {

		for (int i = 0; i < mutators.length; i++) {
			Mutator mutator = mutators[i];
			mutator.mutate(this);
		}
	}
	public Chromosome crossover(Crossover crossover, Chromosome ... chromosomes) {

		Chromosome crossedChromosome = this;
		for (int i = 0; i < chromosomes.length; i++) {
			Chromosome chromosome = chromosomes[i];
			crossedChromosome = crossover.cross(crossedChromosome, chromosome);
		}
		return crossedChromosome;
	}

	public abstract Object expressGenotype();

	public abstract Chromosome duplicate();

	public abstract int size();

	public abstract Gen getGenAtIndex(int index);
	public abstract void setGenAtIndex(Gen gen, int index);

}
