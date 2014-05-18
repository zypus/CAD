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
	private List<Mutator> mutators;
	private List<Crossover> crossovers;
	private FitnessEvaluator evaluator;
	private Selection selection;
	private List<Filter> filters;

	private List<Individual> population;

	public EvolutionChamber(Individual individualTemplate, int populationSize,
			List<Mutator> mutators, List<Crossover> crossovers, FitnessEvaluator evaluator, Selection selection, List<Filter> filters ) {

		this.individualTemplate = individualTemplate;
		this.populationSize = populationSize;
		this.mutators = mutators;
		this.crossovers = crossovers;
		this.evaluator = evaluator;
		this.selection = selection;
		this.filters = filters;
	}

	public void initializeEvolution() {
		population = new ArrayList<>();

		for (int i = 0; i < populationSize; i++) {
			population.add(individualTemplate.createIndividual());
		}

		// evaluate individuals
		for (Individual individual : population) {
			evaluator.evaluate(individual);
		}

		// sort individuals
		Collections.sort(population, individualTemplate.getComparator());
	}

	public void advanceEvolution() {

		// selection
		List<Individual> selected = selection.select(population);
		population.clear();
		population.addAll(selected);
		for (int i = 0; i < population.size(); i++) {
			Individual individual = population.get(i);
			for (Filter filter : filters) {
				boolean keep = filter.keep(individual);
				if (!keep) {
					population.remove(i);
					i--;
					break;
				}
			}
		}

		// create next generation
		while (population.size() < populationSize) {
			Chromosome offspringChromosome = selected.get(random.nextInt(selected.size())).getChromosome();
			for (Crossover crossover : crossovers) {
				offspringChromosome = offspringChromosome.crossover(crossover,
																	selected.get(random.nextInt(selected.size())).getChromosome());
			}
			offspringChromosome.mutate(mutators.toArray(new Mutator[1]));
			Individual offspring = individualTemplate.createIndividual();
			offspring.setChromosome(offspringChromosome);
			population.add(offspring);
		}

		// evaluate individuals
		for (Individual individual : population) {
			evaluator.evaluate(individual);
		}

		// sort individuals
		Collections.sort(population, individualTemplate.getComparator());

	}

	public List<Individual> getEvolutionResult() {

		List<Individual> result = new ArrayList<>();
		for (Individual individual : population) {
			result.add(individual.dubplicate());
		}

		return result;

	}

}
