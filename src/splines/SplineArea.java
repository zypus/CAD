package splines;

import java.awt.Rectangle;
import java.util.ArrayList;
import splines.CubicSpline;

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
		double area = 0;
		for(int i = 0;i<reflexU.size()-1;i++){
			area += areaOfInterval(reflexU.get(i),reflexU.get(i+1));
		}
        
		if(spline instanceof CubicSpline){
			CubicSpline c = (CubicSpline)spline;
			if(c.isClosed()){
				area = 0;
				int count = 0;
				for(int i=0;i<reflexU.size()-1;i++){
					if(count%2==0){
						area += areaOfInterval(reflexU.get(i),reflexU.get(i+1));
						count++;
						System.out.println("adding area between "+reflexU.get(i)+ " and " + reflexU.get(i+1) + " of " + areaOfInterval(reflexU.get(i),reflexU.get(i+1)));
					}
					else{
						area -= areaOfInterval(reflexU.get(i),reflexU.get(i+1));
						count++;
						System.out.println("subtracting interval between " +reflexU.get(i)+ " and " + reflexU.get(i+1) +"and"+ areaOfInterval(reflexU.get(i),reflexU.get(i+1)));
					}
				}
				if(count%2 ==0){
					area+=areaOfInterval(spline.size()-1 - reflexU.get(reflexU.size()-1),0);
					area+=areaOfInterval(0,reflexU.get(0));
					System.out.println("subtracting interval between " +reflexU.get(reflexU.size()-1)+ " and " + reflexU.get(0) +"and"+ areaOfInterval(reflexU.get(reflexU.size()-1),reflexU.get(0)));
                    
				}
				else{
					area-=areaOfInterval(spline.size()-1 - reflexU.get(reflexU.size()-1),0);
					area-=areaOfInterval(0,reflexU.get(0));
					System.out.println("adding interval between " +reflexU.get(reflexU.size()-1)+ " and " + reflexU.get(0) +"and"+ areaOfInterval(reflexU.get(reflexU.size()-1),reflexU.get(0)));
				}
			}
		}
        
		return Math.abs(area);
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
		if (spline instanceof CubicSpline){
			CubicSpline c = (CubicSpline) spline;
			if (c.isClosed()){
				return R[STEPS-1][STEPS-1];
			}
		}
		double area = rect.getMaxY()*rect.width;
		return R[STEPS-1][STEPS-1] - area;
	}
    
	private ArrayList<Double> getIntervals(){
		double length = spline.size()-1;
		double increment = length/1000;
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
		System.out.println(reflexPoints);
        
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
