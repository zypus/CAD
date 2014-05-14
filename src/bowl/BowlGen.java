package bowl;

import bowl.genetic.Gen;

import java.util.Random;

/**
 * Author: Fabian Fränz <f.fraenz@t-online.de>
 * Date: 13/05/14
 * Project: CAD
 * Version: 1.0
 * Description: TODO Add description.
 */
public class BowlGen implements Gen<Vector> {

	private static Random random = new Random(System.currentTimeMillis());

	private Vector phenotype;

	public  BowlGen() {

	}

	public BowlGen(Vector phenotype) {
		this.phenotype = phenotype;
	}

	@Override public Vector getPhenotype() {

		return phenotype;
	}

	@Override public Gen<Vector> duplicate() {

		Vector duplicatedPhenotype = new Vector(phenotype.getX(), phenotype.getY());
		return new BowlGen(duplicatedPhenotype);
	}

	@Override public void randomize() {

		phenotype = new Vector(random.nextDouble() * 10.0, random.nextDouble() * 10.0);
	}
}
