package bowl.genetic;

import java.util.ArrayList;
import java.util.List;

/**
 * Author: Fabian Fränz <f.fraenz@t-online.de>
 * Date: 13/05/14
 * Project: CAD
 * Version: 1.0
 * Description: TODO Add description.
 */
public class ElitistSelection extends Selection {

	public ElitistSelection(double selectPercentage) {

		setPercentage(selectPercentage);
	}

	@Override public List<Individual> select(List<Individual> population) {

		List<Individual> selectedIndividuals = new ArrayList<>();
		for (int i = 0; i < (int)(population.size()*getPercentage()); i++) {
			if (i < population.size()) {
				selectedIndividuals.add(population.get(i));
			}
		}
		return selectedIndividuals;
	}

}
