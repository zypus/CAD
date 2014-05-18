package bowl.genetic;

/**
 * Author: Fabian Fränz <f.fraenz@t-online.de>
 * Date: 16/05/14
 * Project: CAD
 * Version: 1.0
 * Description: TODO Add description.
 */
public class LowFitnessFilter implements Filter {

	private double minFitness;

	public LowFitnessFilter(double minFitness) {

		this.minFitness = minFitness;
	}

	@Override public boolean keep(Individual individual) {

		return individual.getFitness() > minFitness;
	}
}
