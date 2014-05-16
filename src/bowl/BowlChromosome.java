package bowl;

import bowl.genetic.Chromosome;
import bowl.genetic.Gen;
import gui.DoublePoint;
import gui.SplineType;
import splines.Spline;

import java.util.ArrayList;
import java.util.List;

/**
 * Author: Fabian Fränz <f.fraenz@t-online.de>
 * Date: 13/05/14
 * Project: CAD
 * Version: 1.0
 * Description: TODO Add description.
 */
public class BowlChromosome extends Chromosome {

	private List<BowlGen> gens;
	private SplineType type;

	private BowlChromosome(List<BowlGen> gens, SplineType type) {
		this.gens = gens;
		this.type = type;
	}

	public BowlChromosome(int size, SplineType type) {

		gens = new ArrayList<>();
		for (int i = 0; i < size; i++) {
			BowlGen gen = new BowlGen();
			gen.randomize();
			gens.add(gen);
		}
		this.type = type;
	}

	@Override public Object expressGenotype() {

		Spline spline = type.createInstance();
		DoublePoint point = new DoublePoint(0, 0);
		spline.add(point);
		for (BowlGen gen : gens) {
			Vector vector = gen.getPhenotype();
			point = (DoublePoint) point.addValue(new DoublePoint(vector.getX(), vector.getY()));
			spline.add(point);
		}

		return spline;
	}

	@Override public Chromosome duplicate() {

		List<BowlGen> duplicatedGens = new ArrayList<>();
		for (BowlGen gen : gens) {
			duplicatedGens.add((BowlGen) gen.duplicate());
		}
		return new BowlChromosome(duplicatedGens, type);
	}

	@Override public int size() {

		return gens.size();
	}

	@Override public Gen getGenAtIndex(int index) {

		return gens.get(index);
	}

	@Override public void setGenAtIndex(Gen gen, int index) {
		gens.set(index, (BowlGen)gen);
	}

	@Override public List<? extends Gen> getGens() {

		return gens;
	}

	public SplineType getType() {

		return type;
	}
}
