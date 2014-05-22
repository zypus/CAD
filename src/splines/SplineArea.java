package splines;

import util.integration.ParametrizedTrapezoidRule;

import java.awt.Rectangle;
import java.util.ArrayList;

/**
 * Created by fabian on 19/04/14.
 */
public class SplineArea implements SplineProperty {
	private int STEPS = 11;
	private Spline spline;
	@Override
	public double getValue(final Spline spline) {
		this.spline = spline;

		ArrayList<Double> reflexU = getIntervals();
		double area = 0;
		for(int i = 0;i<reflexU.size()-1;i++){
			area += areaOfInterval(reflexU.get(i),reflexU.get(i+1));
		}

		if (spline.isClosed()) {
			area = 0;
			int count = 0;
			for (int i = 0; i < reflexU.size() - 1; i++) {
				if (count % 2 == 0) {
					area += Math.abs(areaOfInterval(reflexU.get(i), reflexU.get(i + 1)));
					count++;
//					System.out.println("adding area between " + reflexU.get(i) + " and " + reflexU.get(i + 1) + " of " + areaOfInterval(
//							reflexU.get(i),
//							reflexU.get(i + 1)));
				} else {
					area -= Math.abs(areaOfInterval(reflexU.get(i), reflexU.get(i + 1)));
					count++;
//					System.out.println("subtracting interval between " + reflexU.get(i) + " and " + reflexU.get(i + 1) + "and"
//									   + areaOfInterval(reflexU.get(i), reflexU.get(i + 1)));
				}
			}
			if (count % 2 == 0) {
//				System.out.println("area before " + area);
				area += Math.abs(areaOfInterval(spline.size() - 1 - reflexU.get(reflexU.size() - 1), 0));
				area += Math.abs(areaOfInterval(0, reflexU.get(0)));
//				System.out.println("area after " + area);

			} else {
//				System.out.println("area before " + area);
//				System.out.println(areaOfInterval(spline.size() - 1 - reflexU.get(reflexU.size() - 1), 0));
//				System.out.println(areaOfInterval(0, reflexU.get(0)));
				area -= Math.abs(areaOfInterval(spline.size() - 1 - reflexU.get(reflexU.size() - 1), 0));
				area -= Math.abs(areaOfInterval(0, reflexU.get(0)));
//				System.out.println("area after " + area);
			}
		}

//		if (spline.isClosed()) {
////			double length = new SplineLength().getValue(spline);
//			int n = spline.size();
//			double d[] = new double[n - 1];
//			double sum = 0;
//			for (int i = 0; i < n - 1; i++) {
//				d[i] = Math.sqrt(Math.pow(spline.get(i + 1).getX() - spline.get(i).getX(), 2) + Math.pow(spline.get(
//						i + 1).getY() - spline.get(i).getY(), 2));
//				sum += d[i];
//			}
//			final double[] s = new double[n];
//			s[0] = 0;
//			for (int i = 0; i < n - 1; i++) {
//				s[i + 1] = (s[i] + d[i]);
//			}
//			for (int i = 0; i < n; i++) {
//				s[i] = s[i] / sum;
//			}
//			final SplineFunctionX fx = new SplineFunctionX(spline);
//			final SplineFunctionY fy = new SplineFunctionY(spline);
//			ParametrizedIntegrator integrator = new ParametrizedSimpsonsRule();
//			final Function sFunction =  new Function() {
//				@Override public double evaluate(double u) {
//
//					int i = (int)Math.floor(u);
//					double fract = u-Math.floor(u);
//					if (i >= spline.size()-1) {
//						return 1;
//					} else {
//						return s[i] + fract * (s[i + 1] - s[i]);
//					}
//				}
//			};
//			Function integral = new Function() {
//
//				ParametrizedDifferentiator differentiator = new ParametrizedSimpleDifference(0.001);
//
//				@Override public double evaluate(double u) {
//
////					double u = spline.size()-1;
////					for (int i = 0; i < s.length-1; i++) {
////						if (t > s[i]) {
////							u = i + (t-s[i])/(s[i+1]-s[i]);
////						}
////					}
//					return fx.evaluate(u)*differentiator.differentiate(fy, sFunction, u)-fy.evaluate(u)*differentiator.differentiate(fx, sFunction, u);
//				}
//			};
//			area = sum * 0.5 * integrator.integrate(integral, sFunction, 0, spline.size()-1, 100);
//		}

		return Math.abs(area);
	}

	private double areaOfInterval(double uMin, double uMax){
		ParametrizedTrapezoidRule paraRule = new ParametrizedTrapezoidRule();
		double h[] = new double[STEPS];
		for (int i=0;i<STEPS;i++){
			h[i] = (Math.abs(uMax-uMin)) / (Math.pow(2, i-1));
		}

		double R[][] = new double[STEPS][STEPS];
		SplineFunctionX fx = new SplineFunctionX(spline);
		SplineFunctionY fy = new SplineFunctionY(spline);
		for (int k = 0; k< STEPS;k++){
			R[k][0] = Math.abs(paraRule.integrate(fy, fx, uMin, uMax, (int) Math.pow(2, k)));
		}
		for (int m = 1;m<STEPS;m++){
			for (int n = m;n<STEPS;n++){
				R[n][m] = R[n][m-1] + (1/(Math.pow(4,m)-1)) * (R[n][m-1] - R[n-1][m-1]);
			}
		}
		if (spline instanceof CubicSpline){
			CubicSpline c = (CubicSpline) spline;
			if (c.isClosed()){
				return R[STEPS-1][STEPS-1];
			}
		}
		Rectangle.Double rect = getBoundingBox(spline, 0, spline.size()-1);
		double area = rect.getMinY()*rect.width;

		return R[STEPS-1][STEPS-1] - area;
	}

	 private Rectangle.Double getBoundingBox(Spline spline, double uMin, double uMax) {

		double minX = 999999;
		double minY = 999999;
		double maxX = -999999;
		double maxY = -999999;
		int iteration = (int)((uMax-uMin)*10);
		for (int i = 0; i <= iteration; i++) {
			double u = (double)i/(double)iteration*(uMax-uMin) + uMin;
			Point p = spline.s(u);
			if (p.getX() < minX) {
				minX = p.getX();
			}
			if (p.getY() < minY) {
				minY = p.getY();

			}
			if (p.getX() > maxX) {
				maxX = p.getX();
			}
			if (p.getY() > maxY) {
				maxY = p.getY();
			}
		}
		return new Rectangle.Double(minX, minY, maxX - minX, maxY - minY);
	}

	private ArrayList<Double> getIntervals(){
		double length = spline.size()-1;
		double increment = length/100;
		ArrayList<Double> reflexPoints = new ArrayList();

		double xGap = spline.s(0).getX() + spline.s(increment).getX();
		Point p = spline.s(increment);

		reflexPoints.add(0.0);
		for (double i = 2*increment;i< length; i += increment){
			Point q = spline.s(i);
			double newGap = q.getX() - p.getX();

			if (!sameSign(xGap, newGap)&&i!=2*increment){
				reflexPoints.add(i-increment);
			}

			xGap = newGap;
			p = q;
		}
		reflexPoints.add((double)spline.size()-1);

		if(spline instanceof CubicSpline){
			CubicSpline c = (CubicSpline) spline;
			if (c.isClosed()){
				reflexPoints.remove(0);
				reflexPoints.remove(reflexPoints.size()-1);
				if(reflexPoints.size()==1){
					if (reflexPoints.get(0)!=0){
						reflexPoints.add(0.0);
					}
					if (reflexPoints.get(0)!=reflexPoints.size()-1){
						reflexPoints.add((double)reflexPoints.size()-1);
					}
				}
			}
		}
//		System.out.println(reflexPoints);

		return reflexPoints;
	}

	private boolean sameSign(double xGap, double newGap) {
		return ((xGap<0) == (newGap<0));
	}


	@Override
	public String getName() {
		return "Area";
	}
}
