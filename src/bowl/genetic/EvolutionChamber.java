package bowl.genetic;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * Author: Fabian Fränz <f.fraenz@t-online.de>
 * Date: 13/05/14
 * Project: CAD
 * Version: 1.0
 * Description: TODO Add description.
 */
public class EvolutionChamber {

	private Random random = new Random(System.currentTimeMillis());

	private Individual individualTemplate;
	private int populationSize;
	private int maxGenerations;
	private List<Mutator> mutators;
	private List<Crossover> crossovers;
	private FitnessEvaluator evaluator;
	private Selection selection;

	private List<Individual> population;

	public EvolutionChamber(Individual individualTemplate, int populationSize, int maxGenerations,
			List<Mutator> mutators, List<Crossover> crossovers, FitnessEvaluator evaluator, Selection selection) {

		this.individualTemplate = individualTemplate;
		this.populationSize = populationSize;
		this.maxGenerations = maxGenerations;
		this.mutators = mutators;
		this.crossovers = crossovers;
		this.evaluator = evaluator;
		this.selection = selection;
	}

	public void startEvolution() {

		// create initial population
		population = new ArrayList<>();

		for (int i = 0; i < populationSize; i++) {
			population.add(individualTemplate.createIndividual());
		}

		for (int g = 0; g < maxGenerations; g++) {
			// evaluate individuals
			for (Individual individual : population) {
				evaluator.evalutate(individual);
			}

			// sort individuals
			Collections.sort(population, individualTemplate.getComparator());

			// selection
			List<Individual> selected = selection.select(population);
			population.clear();
			population.addAll(selected);

			// create next generation
			while (population.size() < populationSize) {
				Chromosome offspringChromosome = selected.get(random.nextInt(selected.size())).getChromosome();
				for (Crossover crossover : crossovers) {
					crossover.cross(offspringChromosome, selected.get(random.nextInt(selected.size())).getChromosome());
				}
				offspringChromosome.mutate((Mutator[]) mutators.toArray());
				Individual offspring = individualTemplate.createIndividual();
				offspring.setChromosome(offspringChromosome);
				population.add(offspring);
			}
		}

	}

	public List<Individual> getEvolutionResult() {

		for (Individual individual : population) {
			evaluator.evalutate(individual);
		}
		Collections.sort(population, individualTemplate.getComparator());

		return population;

	}

}
