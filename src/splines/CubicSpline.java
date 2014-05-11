package splines;

import java.util.ArrayList;

/**
 * Created by fabian on 19/04/14.
 */
public class CubicSpline extends Spline {
	private final double EPSILON = 1e-10;
	private double[][] tridiagMatrix;
	private boolean isClosed;

	public CubicSpline(){
		isClosed = false;
	}

	public Point s(double u) {
		if(u%1==0){
			return get((int)u);
		}
		double[] coeffs = getCoefficients(u);
		Point p = evaluateCoefficients(coeffs, u);
		return p;
	}
	
	private Point evaluateCoefficients(double[] c, double u){
		int intPart = (int) Math.floor(u);
		double fract = u-intPart;
		
		double t = u - intPart;    	
		double y = 0;
		
		y = c[3] * Math.pow((t), 3)
				+ c[2] * Math.pow((t), 2)
				+ c[1] * (t)
				+ c[0];
		
		double x = fract*(get(intPart+1).getX() - get(intPart).getX()) + get(intPart).getX();
		
		return get(0).createPoint(x, y);
	}
	
	
	private double[] getCoefficients(double u){
		int i = (int)Math.floor(u);
		
		double[][] matrix = getTridiagonalMatrix();
		double[] column = getRightSide();
		double[] D = solveSystem(matrix, column);
		
		double a = get(i).getY();
		double b = D[i];
		double c = 3*(get(i+1).getY() - get(i).getY()) - 2*D[i] - D[i+1];
		double d = 2*(get(i).getY() - get(i+1).getY()) + D[i] + D[i+1];
		
		double[] coeffs = {a,b,c,d};
//		System.out.println(a + " and " + b + " and " + c +" and " + d);
		return coeffs;		
	}
	
	@Override
	protected void sizeChanged(){
//		tridiagMatrix = getTridiagonalMatrix();
	}
	

	private double[] solveSystem(double[][] A, double[] b){
		int N  = b.length;

		for (int p = 0; p < N; p++) {
			// find pivot row and swap
			int max = p;
			for (int i = p + 1; i < N; i++) {
				if (Math.abs(A[i][p]) > Math.abs(A[max][p])) {
					max = i;
				}
			}
			double[] temp = A[p]; A[p] = A[max]; A[max] = temp;
			double   t    = b[p]; b[p] = b[max]; b[max] = t;
			// singular or nearly singular
			if (Math.abs(A[p][p]) <= EPSILON) {
				throw new RuntimeException("Matrix is singular or nearly singular");
			}
			// pivot within A and b
			for (int i = p + 1; i < N; i++) {
				double alpha = A[i][p] / A[p][p];
				b[i] -= alpha * b[p];
				for (int j = p; j < N; j++) {
					A[i][j] -= alpha * A[p][j];
				}
			}
		}
		// back substitution
		double[] x = new double[N];
		for (int i = N - 1; i >= 0; i--) {
			double sum = 0.0;
			for (int j = i + 1; j < N; j++) {
				sum += A[i][j] * x[j];
			}
			x[i] = (b[i] - sum) / A[i][i];
		}
		return x;
	}

	private double[][] getTridiagonalMatrix(){
		int n = size();

		double matrix[][] = new double[n][n];
		for (int i =0;i<n-1;i++){
			matrix[i][i] = 4;
		}
		for (int i =0;i<n-1;i++){
			matrix[i][i+1] = 1;
			matrix[i+1][i] = 1;
		}
		if(isClosed){
			matrix[0][n-1]= 1;
			matrix[n-1][0] = 1;
			
		}
		else{
			matrix[0][0] = 2;
			matrix[n-1][n-1] = 2;
		}
		//Matrix printer
//		for (int i = 0; i < n; i++) {
//		    for (int j = 0; j < n; j++) {
//		        System.out.print(matrix[i][j] + " ");
//		    }
//		    System.out.print("\n");
//		}
//		System.out.println("-");
		return matrix;
	}

	private double[] getRightSide(){
		int n = size();

		double d[] = new double[n];
		for (int i = 1;i<n-2;i++){
			d[i] = 3*(get(i+1).getY()-get(i-1).getY());
		}
		if(isClosed){
			d[0] = 3*(get(1).getY()-get(n-1).getY());
			d[n-1] = 3*(get(0).getY()-get(n-2).getY());
		}
		else{
			d[0] = 3*(get(1).getY() - get(0).getY());
			d[n-1] = 3*(get(n-1).getY()-get(n-2).getY());
		}
		return d;
	}

}
