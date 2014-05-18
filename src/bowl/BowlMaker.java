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
	private List<Individual> result;

	private boolean threeD = false;
	private Panel3d panel;
	private SplineType type = SplineType.BEZIER;

	public BowlMaker(Panel3d panel) {

		this.panel = panel;
	}

	public void set3d(boolean enabled) {
		threeD = enabled;
	}

	public boolean get3d() {
		return threeD;
	}

	public void makeBowl(SplineProperty nominator, SplineProperty denominator) {

		List<Crossover> crossovers = new ArrayList<>();
		crossovers.add(new RandomSingleCrossover(1.0));

		List<Mutator> mutators = new ArrayList<>();
		mutators.add(new SinglePointMutator(0.2, 3));
		mutators.add(new ShrinkMutator(0.1));
		mutators.add(new GrowMutator(0.2));

		Bowl template = new Bowl(type);

		BowlEvaluator evaluator = new BowlEvaluator(nominator, denominator);

		Selection selection = new ElitistSelection(0.1);

		List<Filter> filters = new ArrayList<>();
		filters.add(new LowFitnessFilter(0.01));

		final EvolutionChamber evolutionChamber = new EvolutionChamber(template, 100, mutators, crossovers, evaluator, selection, filters);
		evolutionChamber.initializeEvolution();
		Thread evoThread = new Thread(new Runnable() {
			@Override public void run() {

				for (int i = 0; i < 20; i++) {
					currentGeneration = i;
					evolutionChamber.advanceEvolution();
					result = evolutionChamber.getEvolutionResult();
					for (int j = 0; j < result.size(); j++) {
						currentBowl = (Bowl) result.get(j);
						info = "Generation: "+i+"\n";
						info += "Suspect: "+(j+1)+" / "+result.size()+"\n";
						repaint();
						try {
							Thread.sleep(50);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
				}
				currentBowl = (Bowl) result.get(0);
				info = "Generation: " + currentGeneration + "\n";
				info += "Suspect: Best individual\n";
				repaint();
			}
		});
		evoThread.start();
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
