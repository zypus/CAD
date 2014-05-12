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
    	    	
    	int intPart = (int) Math.floor(u);
    	if (u==size()-1){
    		return get(intPart);
    	}
    	
    	int LI = getLeftIndex(u);	
    	double t = (u/3 - (int)Math.floor(u/3));
    	double x = 0;
    	double y = 0;
    	for (int i = 0; i <= 3 ; i++){
    		x += get(LI+i).getX() * (binom(3,i)*Math.pow(t,i)*Math.pow(1-t,3-i));
    		y += get(LI+i).getY() * (binom(3,i)*Math.pow(t,i)*Math.pow(1-t,3-i));
    	}
    	
    	return get(0).createPoint(x,y);
    }
    
    private int getLeftIndex(double u){
    	int leftIndex = (int) Math.floor(u);
    	while(leftIndex%3 != 0){
    		leftIndex--;
    	};
    	return leftIndex;
    	
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
