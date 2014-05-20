package bowl;

import bowl.genetic.Crossover;
import bowl.genetic.ElitistSelection;
import bowl.genetic.EvolutionChamber;
import bowl.genetic.Filter;
import bowl.genetic.GrowMutator;
import bowl.genetic.Individual;
import bowl.genetic.LowFitnessFilter;
import bowl.genetic.Mutator;
import bowl.genetic.RandomSingleCrossover;
import bowl.genetic.Selection;
import bowl.genetic.ShrinkMutator;
import bowl.genetic.SinglePointMutator;
import gui.Panel3d;
import gui.SplineType;
import splines.Spline;
import splines.SplineProperty;

import javax.swing.JComponent;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;

/**
 * Author: Fabian Fränz <f.fraenz@t-online.de>
 * Date: 12/05/14
 * Project: CAD
 * Version: 1.0
 * Description:
 */
public class BowlMaker extends JComponent {

	Bowl currentBowl = null;
	private int currentGeneration;
	private String info = "";
	private List<? extends Individual> result;

	private boolean threeD = false;
	private boolean geneticHill = false;
	private Panel3d panel;
	private SplineType type = SplineType.CUBIC;

	private Thread creationThread;


	public BowlMaker(Panel3d panel) {

		this.panel = panel;
	}

	public void set3d(boolean enabled) {
		threeD = enabled;
	}

	public boolean get3d() {
		return threeD;
	}

	public boolean isGeneticHill() {

		return geneticHill;
	}

	public void setGeneticHill(boolean geneticHill) {

		this.geneticHill = geneticHill;
	}

	public void makeBowl(SplineProperty numerator, SplineProperty denominator) {

		BowlHillClimber climber = new RandomBowlHillClimber();


		List<Crossover> crossovers = new ArrayList<>();
		crossovers.add(new RandomSingleCrossover(1.0));

		List<Mutator> mutators = new ArrayList<>();
		mutators.add(new SinglePointMutator(0.1, 5));
		mutators.add(new ShrinkMutator(0.05));
		mutators.add(new GrowMutator(0.05));

		Bowl template = new Bowl(type);

		BowlEvaluator evaluator = new BowlEvaluator(numerator, denominator);

		Selection selection = new ElitistSelection(0.1);

		List<Filter> filters = new ArrayList<>();
		filters.add(new LowFitnessFilter(0.01));

		final EvolutionChamber evolutionChamber = new EvolutionChamber(template, 100, mutators, crossovers, evaluator, selection, filters);

		BowlCreator genetic = new BowlCreator() {

			private int generationCounter = 0;

			@Override public void setup(SplineProperty numerator, SplineProperty denominator, SplineType type) {
				evolutionChamber.initializeEvolution();
			}

			@Override public boolean advance() {

				evolutionChamber.advanceEvolution();
				return generationCounter++ < 1000;
			}

			@Override public List<Individual> getResult() {

				return evolutionChamber.getEvolutionResult();
			}

			@Override public String getInfo() {

				return "Generation: "+(generationCounter-1)+"\n";
			}
		};

		final BowlCreator creator = (geneticHill) ? genetic : climber;
		creator.setup(numerator, denominator, type);

		creationThread = new Thread(new Runnable() {
			@Override public void run() {

				info = "";
				boolean advance = creator.advance();
				result = creator.getResult();
				currentBowl = (Bowl) result.get(0);
				info = creator.getInfo();
				repaint();
				while (advance && !Thread.currentThread().isInterrupted()) {
					advance = creator.advance();
					result = creator.getResult();
					info = creator.getInfo();
//					for (Object o : result) {
//						currentBowl = (Bowl) o;
//						repaint();
//											try {
//												Thread.sleep(10);
//											} catch (InterruptedException e) {
//												e.printStackTrace();
//											}
//					}
					currentBowl = (Bowl)result.get(0);
					repaint();
				}
			}
		});
		creationThread.start();
	}

	public void stop() {

		creationThread.interrupt();
	}

	@Override protected void paintComponent(Graphics g) {

		super.paintComponent(g);
		if (currentBowl != null) {
			// bowl
			Graphics2D g2 = (Graphics2D) g;
			Dimension size = getParent().getSize();
			if (threeD) {
				if (currentBowl.isUpsideDown()) {
					panel.drawBowl(mirrorSpline((Spline) currentBowl.getPhenotype()));
				} else {
					panel.drawBowl((Spline)currentBowl.getPhenotype());
				}
			} else {
				panel.drawBowl(null);
				if (currentBowl.isUpsideDown()) {
					g2.translate(size.getWidth() / 2, size.getHeight() / 2);
					currentBowl.draw(g2);
					g2.translate(-size.getWidth() / 2, -size.getHeight() / 2);
				} else {
					g2.rotate(Math.PI);
					g2.translate(-size.getWidth() / 2, -size.getHeight() / 2);
					currentBowl.draw(g2);
					g2.translate(size.getWidth() / 2, size.getHeight() / 2);
					g2.rotate(-Math.PI);
				}
			}
			// render info
			g2.translate(size.getWidth() / 2 +5, size.getHeight() / 2);
			drawInfo(currentBowl.getCustomInfo(), g2);
			g2.translate(-size.getWidth() / 2 - 5, -size.getHeight() / 2);
		}
	}

	private void drawInfo(String bowlInfo, Graphics2D g2) {

		Font font = new Font("Arial", Font.BOLD, 15);
		FontMetrics metric = g2.getFontMetrics(font);
		int offset = 30;
		g2.setColor(Color.GREEN);
		for (String line : bowlInfo.split("\\r?\\n")) {
			int l = metric.stringWidth(line);
			g2.drawString(line, -l / 2, offset);
			offset += metric.getHeight() + 5;
		}
		g2.setColor(Color.CYAN);
		for (String line : info.split("\\r?\\n")) {
			int l = metric.stringWidth(line);
			g2.drawString(line, -l / 2, offset);
			offset += metric.getHeight() + 5;
		}

	}

	private Spline mirrorSpline(Spline spline) {
		Spline mirroredSpline = type.createInstance();
		for (splines.Point point : spline) {
			mirroredSpline.add(point.createPoint(point.getX(), -point.getY()));
		}
		return mirroredSpline;
	}
}
