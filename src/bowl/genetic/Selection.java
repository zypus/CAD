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
	private double percentage;

	public abstract List<Individual> select(List<Individual> population);

	public double getPercentage() {

		return percentage;
	}

	public void setPercentage(double percentage) {

		this.percentage = percentage;
	}
}
