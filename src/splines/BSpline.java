package splines;

	/**
	 * Created by fabian on 19/04/14.
	 */

public class BSpline extends Spline{
	protected int k = 4;
	public BSpline(){
		
	}
	
	@Override
    public Point s(double u) {
		int i = 0;
		int flooredU = (int) Math.floor(u);
		double x = get(flooredU).getX();
		if (flooredU < size()-1) {
			x += (get(flooredU+1).getX()-x) * (u- flooredU);
		}
		for(int j = 0;j<size();j++){
			if(u-j<0&&u-j>=1){
			i = j;
			}
		}
		double y = B(x, i, k);
		Point p = null;
		p.createPoint(x, y);
        return p;
    }
	public double B(double x, int i, int nk){
		double result;
		if(nk == 0){
			if(x<= controlPoints.get(i+1).getX()&&x>controlPoints.get(i).getX()){
				result = 1;
			}
			else{
				result = 0;
			}
		}
		else{
			double firstpart = ((x-controlPoints.get(i).getX())/(controlPoints.get(i+nk).getX()-controlPoints.get(i).getX()))*B(x, i, nk-1);
			double secondpart = ((controlPoints.get(i+nk+1).getX()-x)/(controlPoints.get(i+k+1).getX()-controlPoints.get(i+1).getX()))*B(x, i+1, nk-1);
			result = firstpart + secondpart;
		}
		return result;
	}
}
