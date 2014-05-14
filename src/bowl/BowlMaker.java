package bowl;

import bowl.genetic.*;
import gui.SplineType;

import java.util.ArrayList;
import java.util.List;

/**
 * Author: Fabian Fränz <f.fraenz@t-online.de>
 * Date: 12/05/14
 * Project: CAD
 * Version: 1.0
 * Description:
 */
public class BowlMaker {

	public Bowl makeBowl() {

		List<Crossover> crossovers = new ArrayList<>();
		crossovers.add(new RandomSingleCrossover(1.0));

		List<Mutator> mutators = new ArrayList<>();
		mutators.add(new SinglePointMutator(0.2));
		mutators.add(new ShrinkMutator(0.1));
		mutators.add(new GrowMutator(0.2));

		Bowl template = new Bowl(SplineType.BEZIER);

		BowlEvaluator evaluator = new BowlEvaluator();

		Selection selection = new ElitistSelection(0.1);

		EvolutionChamber evolutionChamber = new EvolutionChamber(template, 100, 10, mutators, crossovers, evaluator, selection);
		evolutionChamber.startEvolution();
		List<Individual> result = evolutionChamber.getEvolutionResult();
		return (Bowl)result.get(0);
	}

}
