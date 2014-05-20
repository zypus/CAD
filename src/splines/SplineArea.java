package splines;

import java.awt.Rectangle;
import java.util.ArrayList;

import util.integration.ParametrizedTrapezoidRule;
import gui.Spline2D;

/**
 * Created by fabian on 19/04/14.
 */
public class SplineArea implements SplineProperty {
	private int STEPS = 10;
	private Spline spline;
	@Override
	public double getValue(Spline spline) {
		this.spline = spline;

		ArrayList<Double> reflexU = getIntervals();
		double length = 0;
		for(int i = 0;i<reflexU.size()-1;i++){
			length += areaOfInterval(reflexU.get(i),reflexU.get(i+1));
			System.out.println("this is an interval ");
		}
		return Math.abs(length);
	}
	
	private double areaOfInterval(double uMin, double uMax){
		Spline2D d2 = new Spline2D(spline, null);
		Rectangle.Double rect = d2.getBoundingBox();
		ParametrizedTrapezoidRule paraRule = new ParametrizedTrapezoidRule();
		double h[] = new double[STEPS];
		for (int i=0;i<STEPS;i++){
			h[i] = (Math.abs(uMax-uMin)) / (Math.pow(2, i-1));
		}
		
		double R[][] = new double[STEPS][STEPS];
		SplineFunctionX fx = new SplineFunctionX(spline);
		SplineFunctionY fy = new SplineFunctionY(spline);
		for (int k = 0; k< STEPS;k++){
			R[k][0] = paraRule.integrate(fy, fx, uMin, uMax, (int)Math.pow(2,k));
		}
		for (int m = 1;m<STEPS;m++){
			for (int n = m;n<STEPS;n++){
				R[n][m] = R[n][m-1] + (1/(Math.pow(4,m)-1)) * (R[n][m-1] - R[n-1][m-1]);
			}			
		}	
		double area = rect.getMaxY()*rect.width;
		return R[STEPS-1][STEPS-1] - area;
		
	}

	private ArrayList<Double> getIntervals(){
		double length = spline.size()-1;
		double increment = length/STEPS;
		ArrayList<Double> reflexPoints = new ArrayList();

		double xGap = spline.s(0).getX() + spline.s(increment).getX();
		Point p = spline.s(increment);
		
		reflexPoints.add(0.0);
		for (double i = 2*increment;i< length; i += increment){
			Point q = spline.s(i);
			double newGap = q.getX() - p.getX();

			if (!sameSign(xGap, newGap)){
				reflexPoints.add(i-increment);
			}
			xGap = newGap;
			p = q;			
		}
		reflexPoints.add((double)spline.size()-1);

		return reflexPoints;
	}

	private boolean sameSign(double xGap, double newGap) {
		return ((xGap<0) == (newGap<0));
	}


	@Override
	public String getName() {
		return null;
	}
}
