package bowl;

import gui.DoublePoint;
import gui.Spline2D;
import gui.SplineType;
import splines.Point;
import splines.Spline;
import splines.SplineArea;
import splines.SplineProperty;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Author: Fabian Fränz <f.fraenz@t-online.de>
 * Date: 19/05/14
 * Project: CAD
 * Version: 1.0
 * Description: TODO Add description.
 */
public class BowlHillClimber implements BowlCreator {

	private final double INCREMENT = 10;
	protected SplineProperty numerator;
	protected SplineProperty denominator;
	private Spline currentBestSpline = null;
	private double num;
	private double den;
	private double currentRatio;
	private int temperature = 30;
	protected SplineType type;

	public void setup(SplineProperty numerator, SplineProperty denominator, SplineType type) {

		Random random = new Random(System.currentTimeMillis());

		this.numerator = numerator;
		this.denominator = denominator;
		this.type = type;
		currentRatio = 0;
		Rectangle.Double rectangle = new Rectangle.Double();
		while (currentRatio == 0 || rectangle.width > 250 || rectangle.height > 150) {
			currentBestSpline = type.createInstance();
			currentBestSpline.add(new DoublePoint(0, 0));
			for (int i = 0; i < 4; i++) {
				currentBestSpline.add(new DoublePoint(1 + random.nextInt(124), random.nextInt(150)));
			}
//			currentBestSpline.add(new DoublePoint(1+random.nextInt(124), random.nextInt(250)));
			//		currentBestSpline.add(new DoublePoint(random.nextInt(200), random.nextInt(200)));
			Spline completedSpline = makeSplineComplete(currentBestSpline);
			Spline2D spline2d = new Spline2D(completedSpline, type);
			rectangle = spline2d.getBoundingBox();
			double area = rectangle.width*rectangle.height;
			num = ((numerator instanceof SplineArea) ? area - numerator.getValue(currentBestSpline) : numerator.getValue(currentBestSpline));
			den = denominator.getValue(currentBestSpline);
			currentRatio = num / den * ((isMonotone(currentBestSpline)) ? 1 : 0);
//			currentRatio /= rectangle.width*rectangle.height/100;
		}
	}

	public boolean advance() {

		Spline testedSpline = type.createInstance();
		testedSpline.addAll(currentBestSpline);
		boolean climbed = false;
		for (int i = 1; i < currentBestSpline.size() && !climbed; i++) {
			Point point = currentBestSpline.get(i);
			for (int x = -1; x <= 1 && !climbed; x++) {
				for (int y = -1; y <= 1 && !climbed; y++) {
					testedSpline.set(i, new DoublePoint(point.getX() + x * INCREMENT * temperature, point.getY() * y * INCREMENT * temperature));
					Spline completedSpline = makeSplineComplete(testedSpline);
					Spline2D spline2d = new Spline2D(completedSpline, type);
					Rectangle.Double rectangle = spline2d.getBoundingBox();
					double area = rectangle.width*rectangle.height;
					if (rectangle.width < 250 && rectangle.height < 150) {
						double tempNum = ((numerator instanceof SplineArea) ? area - numerator.getValue(completedSpline) : numerator.getValue(completedSpline)) ;
						double tempDen = denominator.getValue(completedSpline);
						double
								newRatio =
								tempNum / tempDen * ((isMonotone(completedSpline)) ? 1 : 0);
//						newRatio /= rectangle.width * rectangle.height / 100;
						if (newRatio != 0 && newRatio > currentRatio) {
							System.out.println("rectangle = " + rectangle);
							currentRatio = newRatio;
							num = tempNum;
							den = tempDen;
							currentBestSpline = testedSpline;
							climbed = true;
						}
					}
				}
			}
		}
		if (temperature > 1) {
			temperature--;
		}

		return temperature > 1 || climbed;
	}

	protected boolean isMonotone(Spline spline) {

		double stepsize = 0.25;
		int midPoint = (spline.size()-1)/2;
		if (spline.size() <= 1) {
			return true;
		}
		Point point1 = spline.s(midPoint);
		Point point2 = spline.s(midPoint + stepsize);
		int sign1 = (int) Math.signum(point1.getY() - point2.getY());
		int sign2 = (int) Math.signum(point1.getX() - point2.getX());
		point1 = point2;
		int iterations = (int) (midPoint / stepsize);
		for (int i = 2; i <= iterations; i++) {
			double u = midPoint + i * stepsize;
			point2 = spline.s(u);
			int nextSign1 = (int) Math.signum(point1.getY() - point2.getY());
			if (nextSign1 != 0) {
				if (sign1 == 0) {
					sign1 = nextSign1;
				} else if (sign1 != nextSign1) {
					return false;
				}
			}
			int nextSign2 = (int) Math.signum(point1.getX() - point2.getX());
			if (nextSign2 != 0) {
				if (sign2 == 0) {
					sign2 = nextSign2;
				} else if (sign2 != nextSign2) {
					return false;
				}
			}
			point1 = point2;
		}
		return true;
	}

	private Spline makeSplineComplete(Spline s) {

		Spline spline = type.createInstance();
		for (int i = s.size() - 1; i > 0; i--) {
			Point point = s.get(i);
			spline.add(new DoublePoint(-point.getX(), point.getY()));
		}
		spline.addAll(s);

		return spline;
	}

	public Spline getCurrentBestSpline() {

		return makeSplineComplete(currentBestSpline);
	}

	@Override public List<Bowl> getResult() {

		List<Bowl> bowls = new ArrayList<>();
		HillBowl bowl = new HillBowl(type, getCurrentBestSpline());
		bowl.setCustomInfo(
				numerator.getName() + ": " + (int)(num*100)/100.0 + "\n" + denominator.getName() + ": " + (int)(den*100)/100.0
				+ "\nRatio: "
				+ (int)(currentRatio*100)/100.0
		);
		bowls.add(bowl);
		return bowls;
	}

	@Override public String getInfo() {

		return "";
	}

	public class HillBowl extends Bowl {

		private Spline spline;

		public HillBowl(SplineType type, Spline spline) {

			super(type);
			this.spline = spline;
		}

		@Override public Object getPhenotype() {

			return spline;
		}

	}

}
