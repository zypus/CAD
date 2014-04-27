package splines;

import java.util.ArrayList;

/**
 * Created by fabian on 19/04/14.
 * Edited by nathan on 26/04/14.
 */
public class BezierSpline extends Spline {

	public BezierSpline(){}

    @Override
    public Point s(double u) {
    	double t = getInterval().getXSpan() / u;
    	int k = size()-1;
    	Point p;

    	for (int i=0; i<k; i++){
    		double scale = binom(k,i) * Math.pow(1-t, k-i);
    		p = p.addValue(get(i).manipulate(scale));
    	}
    	p = p.addValue(get(k).manipulate(Math.pow(t,k)));

        return p;
    }

    private double binom(int n, int k){
    	double coeff = 1;
    	for (int i= n-k+1; i<=n; i++){
    		coeff *= i;
    	}
    	for (int i = 1; i<k; i++){
    		coeff /= i;
    	}
    	return coeff;
    }
}
