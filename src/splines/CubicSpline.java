package splines;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * Created by fabian on 19/04/14.
 */
public class CubicSpline extends Spline {

	private int n;
	private double[] h;
	private double[] curvatures;
	private double[][] spline;

	public CubicSpline(){
	}

	public Point s(double u) {
		if(u%1==0){
			return get((int)u);
		}
		int i = (int) Math.floor(u);
		double fract = u - i;
		double xVal = fract * (get(i+1).getX() - get(i).getX());
		
		if(n==2){
			double yVal = get(i).getY() + fract * (get(i+1).getY() - get(i).getY());
			return get(0).createPoint(xVal+ get(i).getX(), yVal);
		}
		sortPoints();
		this.spline = computeSpline();		
		
		double yVal = spline[i][0] * Math.pow(xVal, 3)
				+ spline[i][1] * Math.pow(xVal, 2) + spline[i][2]
				* (xVal) + spline[i][3];
		
		return get(0).createPoint(xVal + get(i).getX(), yVal);
	}
	
	public double[][] computeSpline() {
		h = makeH();
		curvatures = computeCurvatures();
		double[][] sp = new double[n - 1][];
		for (int i = 0; i < sp.length; i++) {
			sp[i] = computePolynomial(i);
		}
		return sp;
	}
	
	private double[] makeH(){
		double[] h = new double[n - 1];
		for (int i = 0; i < h.length; i++) {
			h[i] = get(i + 1).getX() - get(i).getX();

		}
		return h;
	}
	
	public double[] computeCurvatures() {
		// create matrix
		double[][] matrix = new double[n - 2][n - 2];

		matrix[0][0] = 2 * (h[0] + h[1]);
		for (int i = 1; i < matrix.length; i++) {
			matrix[i][i] = 2 * (h[0 + i] + h[1 + i]);
			matrix[i - 1][i] = h[i];
			matrix[i][i - 1] = h[i];

		}

		// create vector
		double[] vector = new double[n - 2];

		for (int i = 0; i < vector.length; i++) {
			vector[i] = (get(2 + i).getY() - get(i+1).getY()) / h[1 + i]
					- (get(i+1).getY() - get(i).getY()) / h[0 + i];
			vector[i] *= 6;
		}

		// solve system
		double[] solution = gaussianElimination(matrix, vector);
		double[] curvatures = new double[solution.length + 2]; // natural
																// boundary
																// conditions,
																// first and
																// last one = 0
		System.arraycopy(solution, 0, curvatures, 1, solution.length);

		return curvatures;
	}
	
	@Override
	protected void sizeChanged(){
//		sortPoints();
		n = size();
//		this.spline= computeSpline();
	}
	
	private void sortPoints(){
		Collections.sort(controlPoints, new Comparator<Point>(){
			@Override public int compare (Point p1, Point p2){
				if (p2.getX() > p1.getX()){
					return -1;
				}
				if (p2.getX() < p1.getX()){
					return 1;
				}
				return 1;
			}
		});
	}
	

	public double[] gaussianElimination(double[][] matrix, double[] vector) {

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
	
	public double[] computePolynomial(int i) {
		double[] polynomial = new double[4]; // P(x) = axÂ³+bxÂ²+cx+d
		polynomial[0] = (curvatures[i + 1] - curvatures[i]) / (6 * h[i]); // a
		polynomial[1] = curvatures[i] / 2; // b
		polynomial[2] = (get(i+1).getY() - get(i).getY()) / h[i]
				- (2 * h[i] * curvatures[i] + h[i] * curvatures[i + 1]) / 6; // c
		polynomial[3] = get(i).getY(); // d
		return polynomial;
	}


}
