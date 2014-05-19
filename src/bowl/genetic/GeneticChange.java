package bowl.genetic;

import java.util.Random;

/**
 * Author: Fabian Fränz <f.fraenz@t-online.de>
 * Date: 14/05/14
 * Project: CAD
 * Version: 1.0
 * Description: TODO Add description.
 */
public class GeneticChange {

	protected Random random = new Random(System.currentTimeMillis());
	private double changeChance;

	public double getChangeChance() {

		return changeChance;
	}

	public void setChangeChance(double changeChance) {

		this.changeChance = changeChance;
	}

	public boolean shouldChange() {

		return random.nextDouble() < changeChance;
	}

}
