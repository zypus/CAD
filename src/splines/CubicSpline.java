package splines;

import java.util.Arrays;

/**
 * Created by fabian on 19/04/14.
 */
public class CubicSpline extends Spline {

	private int n;
	private double[] h;
	private double[] xRight;
	private double[] yRight;	
	private double[][] left;
	private double[][] xCoeff;
	private double[][] yCoeff;
	private boolean closed;
	
	public CubicSpline(){
	}

	public Point s(double u) {
		if(u%1==0){
			return get((int)u);
		}
			
		int i = (int) Math.floor(u);
		double fract = u - i;
		if(n==2){
			double xVal = fract * (get(i+1).getX() - get(i).getX());
			double yVal = get(i).getY() + fract * (get(i+1).getY() - get(i).getY());
			return get(0).createPoint(xVal+ get(i).getX(), yVal);
		}
		
		setCoefficients();
		double t = fract;
				
		double yVal = yCoeff[i][0] * Math.pow(t, 3) + yCoeff[i][1] * Math.pow(t, 2) + yCoeff[i][2] * (t) + yCoeff[i][3];
		double xVal = xCoeff[i][0] * Math.pow(t, 3) + xCoeff[i][1] * Math.pow(t, 2) + xCoeff[i][2] * (t) + xCoeff[i][3];
		
		return get(0).createPoint(xVal , yVal);
	}
	
	@Override
	protected void sizeChanged(){
		n = size();
	}
	
	private void setCoefficients(){
		setSHValues();
		setLeftRightMatrix();
		double xDeriv[] = gaussianElimination(left,xRight);
		double yDeriv[] = gaussianElimination(left,yRight);
		xCoeff = new double[n-1][4];
		yCoeff = new double[n-1][4];
		double[][] smallLeft = new double[4][4];
		smallLeft = new double[][]{{0,2,0,0},{0,0,0,1},{1,1,1,1},{6,2,0,0}};
		for(int i =0;i<n-1;i++){
			double[] smallXR = new double[]{xDeriv[i],get(i).getX(),get(i+1).getX(),xDeriv[i+1]};
			double[] smallYR = new double[]{yDeriv[i],get(i).getY(),get(i+1).getY(),yDeriv[i+1]};
			double[] xC = gaussianElimination(smallLeft, smallXR);
			double[] yC = gaussianElimination(smallLeft, smallYR);
						
			for (int j = 0;j<4;j++){
				xCoeff[i][j] = xC[j];
				yCoeff[i][j] = yC[j];
			}
//			for (int k = 0; k < n-1; k++) {
//			    for (int j = 0; j < 4; j++) {
//			        System.out.print(yCoeff[k][j] + " ");
//			    }
//			    System.out.print("\n");
//			}
		}				
	}
	
	private void setSHValues(){
		double d[] = new double[n-1];
		double sum = 0;
		for (int i=0;i<n-1;i++){
			d[i] = Math.sqrt(Math.pow(get(i+1).getX() - get(i).getX(),2) + Math.pow(get(i+1).getY() - get(i).getY(),2));
			sum+=d[i];
		}
		double s[] = new double[n];
		s[0] = 0;
		for (int i=0;i<n-1;i++){
			s[i+1] = (s[i] + d[i]);
		}
		for (int i=0;i<n;i++){
			s[i] = s[i]/sum;
		}
		double h[] = new double[n-1];
		for (int i=0;i<n-1;i++){
			h[i]= s[i+1] - s[i];
		}
		this.h = h;
	}
	
	private void setLeftRightMatrix(){
		double[][] left = new double[n][n];
		double[] Xright = new double[n];
		double[] Yright = new double[n];
		
		for(int i = 0;i<n-2;i++){ //set left and right
			left[i+1][i] = h[i];
			left[i+1][i+1] = 2*(h[i]+h[i+1]);
			left[i+1][i+2] = h[i+1];
			
			Xright[i+1] = (get(i+2).getX()-get(i+1).getX()) / h[i+1] - (get(i+1).getX()-get(i).getX()) / h[i];
			Yright[i+1] = (get(i+2).getY()-get(i+1).getY()) / h[i+1] - (get(i+1).getY()-get(i).getY()) / h[i];
		}
		
		if (closed){
			left[0][0] = 2;
			left[0][1] = 1;
			left[0][n-1] = 2;
			left[0][n-2] = 1;
			left[n-1][0] = 1;
			left[n-1][n-1] = -1;
		}
		else{
			left[0][0] = 1;
			left[n-1][n-1] = 1;
			Xright[0] = 0;
			Yright[0] = 1;
			Xright[n-1] = 0;
			Yright[n-1] = 1;
		}
		
		for(int i = 0; i< Xright.length;i++){
			Xright[i] = 6*Xright[i];
			Yright[i] = 6*Yright[i];
		}
		for (int k = 0; k < Xright.length; k++) {
//		    for (int j = 0; j < left[0].length; j++) {
		        System.out.print(Xright[k] + " ");
//		    }
		    System.out.print("\n");
		}
		
		this.left = left;
		this.xRight = Xright;
		this.yRight = Yright;
	}
	
	public double[] gaussianElimination(double[][] aMatrix, double[] aVector) {
		double[][] matrix = new double[aMatrix.length][];
		double[] vector = new double[aVector.length];
		for (int i = 0; i < aMatrix.length; i++) {
			double[] column = new double[aMatrix[0].length];
			System.arraycopy(aMatrix[i], 0, column, 0, aMatrix[0].length);
			matrix[i] = column;
		}
		System.arraycopy(aVector, 0, vector, 0, aVector.length);

		for (int i = 0; i < vector.length; i++) {

			// find pivot row
			int max = i;
			for (int j = i + 1; j < vector.length; j++) {
				if (Math.abs(matrix[j][i]) > Math.abs(matrix[max][i]))
					max = j;
			}

			// swap rows
			double[] tempMatrix = matrix[i];
			double tempVector = vector[i];

			matrix[i] = matrix[max];
			vector[i] = vector[max];

			matrix[max] = tempMatrix;
			vector[max] = tempVector;

			// pivot within matrix and vector
			for (int j = i + 1; j < vector.length; j++) {
				double factor = matrix[j][i] / matrix[i][i];

				vector[j] -= factor * vector[i];

				for (int k = i; k < vector.length; k++) {
					matrix[j][k] -= factor * matrix[i][k];
				}
			}
		}

		// back substitution
		double[] solution = new double[vector.length];
		for (int i = vector.length - 1; i >= 0; i--) {
			double sum = 0.0;

			for (int j = i + 1; j < vector.length; j++) {
				sum += matrix[i][j] * solution[j];
			}

			solution[i] = (vector[i] - sum) / matrix[i][i];

		}

		return solution;
	}
}
