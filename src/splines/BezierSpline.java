package splines;

import java.util.ArrayList;

/**
 * Created by fabian on 19/04/14.
 * Edited by nathan on 26/04/14.
 */
public class BezierSpline extends Spline {
	private final double EPSILON = 1e-10;
	public BezierSpline(){}

    @Override
    public Point s(double u) {
    	    	
    	int intPart = (int) Math.floor(u);
    	if (u==size()-1){
    		return get(intPart);
    	}
    	
    	int leftIndex = intPart;
    	while(leftIndex%3 != 0){
    		leftIndex--;
    	};
    	Point leftKnot = get(leftIndex);
    	    	
    	Point p = leftKnot.createPoint(0,0);
    	double t = (u/3 - (int)Math.floor(u/3));
    	for (int i = 0; i <= 3 ; i++){
    		p = p.addValue(get(leftIndex+i).manipulate(binom(3,i)*Math.pow(t,i)*Math.pow(1-t,3-i)));
    	}
    	return p;
    }

    private double binom(int n, int k){
    	double coeff = 1;
    	for (int i = 1; i<=n; i++){
    		coeff *= i;
    	}
    	for (int i = 1; i<=k; i++){
    		coeff /= i;
    	}
    	for (int i = 1; i<=(n-k); i++){
    		coeff /= i;
    	}
    	return coeff;
    }
}
