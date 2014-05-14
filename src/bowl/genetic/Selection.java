package bowl.genetic;

import java.util.List;

/**
 * Author: Fabian Fränz <f.fraenz@t-online.de>
 * Date: 13/05/14
 * Project: CAD
 * Version: 1.0
 * Description:
 */
public abstract class Selection {
	private int amountOfSelected;

	public abstract List<Individual> select(List<Individual> population);

	public int getAmountOfSelected() {

		return amountOfSelected;
	}

	public void setAmountOfSelected(int amountOfSelected) {

		this.amountOfSelected = amountOfSelected;
	}
}
