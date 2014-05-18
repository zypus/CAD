package splines;

import java.util.ArrayList;

/**
 * Created by fabian on 19/04/14.
 */
public class SplineArea implements SplineProperty {
	private int STEPS = 1000;
	private Spline spline;
	@Override
	public double getValue(Spline spline) {
		this.spline = spline;

		ArrayList<Double> reflexU = getIntervals();
		double length = 0;
		
		for(int i = 0;i<reflexU.size()-1;i++){
			length += areaOfInterval(reflexU.get(i),reflexU.get(i+1)); //alternate
		}
		
		return Math.abs(length);
	}

	private ArrayList<Double> getIntervals(){
		SplineLength lengthCalc = new SplineLength();
		double length = lengthCalc.getValue(spline);
		double increment = length/STEPS;
		ArrayList<Double> reflexPoints = new ArrayList();

		double xGap = spline.s(0).getX() + spline.s(increment).getX();
		Point p = spline.s(increment);

		for (double i = 2*increment;i< length; i += increment){
			Point q = spline.s(i);
			double newGap = q.getX() - p.getX();

			if (!sameSign(xGap, newGap)){
				reflexPoints.add(i-increment);
			}
			xGap = newGap;
			p = q;			
		}

		return reflexPoints;
	}

	private boolean sameSign(double xGap, double newGap) {
		return ((xGap<0) == (newGap<0));
	}

	private double areaOfInterval(double uMin, double uMax){

		double h[] = new double[STEPS];
		for (int i=0;i<STEPS;i++){
			h[i] = (Math.abs(uMax-uMin)) / (Math.pow(2, i-1));
		}

		double R[][] = new double[STEPS][STEPS];
		R[0][0] = (h[0]/2)*(spline.s(uMin).getY() + spline.s(uMax).getY());

		for (int k = 1;k<STEPS;k++){
			double sum = 0;
			for (int i = 1;i<Math.pow(2, STEPS-1)+1;i++){
				sum+=spline.s(uMin +(2*i-1)*h[k]).getY();
			}

			R[k][0] = 0.5 * (R[k-1][0] + h[k-1] * sum);		
		}

		for (int m = 1;m<STEPS;m++){
			for (int n = m;n<STEPS;n++){
				R[n][m] = (1/(Math.pow(4,m)-1))*(Math.pow(4, m)*R[n][m-1] - R[n-1][m-1]);
			}			
		}
		return R[STEPS-1][STEPS-1];

	}

	@Override
	public String getName() {
		return null;
	}
}
