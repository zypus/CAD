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

	private Vector genotype;

	public  BowlGen() {

	}

	public BowlGen(Vector genotype) {
		this.genotype = genotype;
	}

	@Override public Vector getGenotype() {

		return genotype;
	}

	@Override public Gen<Vector> duplicate() {

		Vector duplicatedGenotype = new Vector(genotype.getX(), genotype.getY());
		return new BowlGen(duplicatedGenotype);
	}

	@Override public void randomize() {

		genotype = new Vector(random.nextDouble() * 125, random.nextDouble() * 150);
	}
}
