package splines;

	/**
	 * Created by fabian on 19/04/14.
	 */

public class BSpline extends Spline{
	protected int n;
	protected int k = 4;
	protected int[] t;
	@Override
	protected void sizeChanged(){
		n = size();
		t = new int[n+k];
		for(int i=0;i<(n+k);i++){
			if (i<k){t[i]=0;}
			if (i>n){t[i]=n-k+2;}
			else{t[i]=i-k+1;}
		}

	}

	@Override
    public Point s(double u) {

		int flooredU = (int) Math.floor(u);
		double x = get(flooredU).getX();
		if (flooredU < size()-1) {
			x += (get(flooredU+1).getX()-x) * (u- flooredU);
		}

		double cntr = 0;
		int nk = k;
		for(int i = 0;i<n;i++){
			cntr = cntr + N(u, i, nk);
		}

		Point p = get(0).createPoint(x, cntr);

        return p;
    }
	public double N(double u, int i, int nk){
		double result;
		if(nk == 0){
			if(u<t[i] && u>= t[i]+1){
				result = 1;
			}
			else{
				result = 0;
			}
		}
		else{
			result = ((u-t[i])*N(u, i, nk-1))/(t[i+nk-1]-t[i])+((t[i+nk]-u)*N(u, i+1, nk-1)/(t[i+nk]-t[i+1]));
		}
		return result;
	}
}
