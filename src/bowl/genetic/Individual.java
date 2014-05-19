package bowl.genetic;

import java.util.Comparator;

/**
 * Author: Fabian Fränz <f.fraenz@t-online.de>
 * Date: 12/05/14
 * Project: CAD
 * Version: 1.0
 * Description:
 */
public abstract class Individual {

	private double fitness;
	private Chromosome chromosome;

	public double getFitness() {

		return fitness;
	}
	public void setFitness(double fitness) {

		this.fitness = fitness;
	}
	public Chromosome getChromosome() {

		return chromosome;
	}

	public void setChromosome(Chromosome chromosome) {

		this.chromosome = chromosome;
	}

	public abstract Individual dubplicate();

	public abstract Object getPhenotype();

	public abstract Individual createIndividual();

	public Comparator<Individual> getComparator() {
		return new Comparator<Individual>() {
			@Override public int compare(Individual o1, Individual o2) {

				if (o1.getFitness() < o2.getFitness()) {
					return 1;
				} else if (o1.getFitness() > o2.getFitness()) {
					return -1;
				} else {
					return 0;
				}
			}
		};
	}

}
