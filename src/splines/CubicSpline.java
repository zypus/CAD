package splines;

<<<<<<< HEAD
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

=======
>>>>>>> master
/**
 * Created by fabian on 19/04/14.
 */
public class CubicSpline extends Spline {

<<<<<<< HEAD
	private int n;
	private double[] h;
	private double[] curvatures;
	private double[][] spline;
=======
	public static void main(String[] args) {
		double[][] A = {{0,2,1},{1,-2,-3},{-1,1,2}};
		double[] b = {-8,0,3};
		double[] c = new CubicSpline().gaussianElimination(A, b);
		for (int i = 0; i < A.length; i++) {
			for (int j = 0; j < A[0].length; j++) {
				double v = A[i][j];
				System.out.println(v);
			}
			System.out.println();
		}
		for (int i = 0; i < b.length; i++) {
			double v = b[i];
			System.out.println(v);
		}
		for (int i = 0; i < c.length; i++) {
			double v = c[i];
			System.out.println(v);
		}

	}

	private int n;
	private double[] h;
	private double[] s;
	private double[] xRight;
	private double[] yRight;
	private double[][] left;
	private double[][] xCoeff;
	private double[][] yCoeff;
>>>>>>> master

	public CubicSpline(){
	}

	public Point s(double u) {
		if(u%1==0){
			return get((int)u);
		}
<<<<<<< HEAD
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
=======

		int i = (int) Math.floor(u);
		double fract = u - i;
		if(n==2){
			double xVal = fract * (get(i+1).getX() - get(i).getX());
			double yVal = get(i).getY() + fract * (get(i+1).getY() - get(i).getY());
			return get(0).createPoint(xVal+ get(i).getX(), yVal);
		}

		setCoefficients();
		double t = s[i] +fract*(s[i+1]-s[i]);

		double yVal = yCoeff[i][0] * Math.pow(t, 3) + yCoeff[i][1] * Math.pow(t, 2) + yCoeff[i][2] * (t) + yCoeff[i][3];
		double xVal = xCoeff[i][0] * Math.pow(t, 3) + xCoeff[i][1] * Math.pow(t, 2) + xCoeff[i][2] * (t) + xCoeff[i][3];

		return get(0).createPoint(xVal , yVal);
>>>>>>> master
	}

	@Override
	protected void sizeChanged(){
<<<<<<< HEAD
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
=======
		n = size();
	}

	private boolean isClosed() {
		return get(0).getX() == get(size()-1).getX() && get(0).getY() == get(size() - 1).getY();
	}

	private void setCoefficients(){
		setSHValues();
		setLeftRightMatrix();
//		System.out.println("left");
//		for (int i = 0; i < left.length; i++) {
//			for (int j = 0; j < left[0].length; j++) {
//				double v = left[i][j];
//				System.out.println(v);
//			}
//		}
		double xDeriv[] = gaussianElimination(left,xRight);
		double yDeriv[] = gaussianElimination(left,yRight);
//		System.out.println("xRight");
//		for (int i = 0; i < xRight.length; i++) {
//			double v = xRight[i];
//			System.out.println(v);
//		}
//		System.out.println("yRight");
//		for (int i = 0; i < yRight.length; i++) {
//			double v = yRight[i];
//			System.out.println(v);
//		}
		xCoeff = new double[n-1][4];
		yCoeff = new double[n-1][4];
		double[][] smallLeft = new double[4][4];
//		smallLeft = new double[][]{{0,2,0,0},{0,0,0,1},{1,1,1,1},{6,2,0,0}};
		for(int i =0;i<n-1;i++){
			double t1 = s[i];
			double t2 = s[i+1];

			smallLeft = new double[][] { { 6*t1, 2, 0, 0 }, { Math.pow(t1,3), Math.pow(t1, 2), t1, 1 }, { Math.pow(t2, 3), Math.pow(t2, 2), t2, 1 }, { 6*t2, 2, 0, 0 } };
			double[] smallXR = new double[]{xDeriv[i],get(i).getX(),get(i+1).getX(),xDeriv[i+1]};
			double[] smallYR = new double[]{yDeriv[i],get(i).getY(),get(i+1).getY(),yDeriv[i+1]};
			double[] xC = gaussianElimination(smallLeft, smallXR);
			double[] yC = gaussianElimination(smallLeft, smallYR);

			for (int j = 0;j<4;j++){
				xCoeff[i][j] = xC[j];
				yCoeff[i][j] = yC[j];
>>>>>>> master
			}
//			for (int k = 0; k < n-1; k++) {
//			    for (int j = 0; j < 4; j++) {
//			        System.out.print(yCoeff[k][j] + " ");
//			    }
//			    System.out.print("\n");
//			}
		}
<<<<<<< HEAD

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

=======
	}

	private void setSHValues(){
		double d[] = new double[n-1];
		double sum = 0;
		for (int i=0;i<n-1;i++){
			d[i] = Math.sqrt(Math.pow(get(i+1).getX() - get(i).getX(),2) + Math.pow(get(i+1).getY() - get(i).getY(),2));
			sum+=d[i];
		}
		s = new double[n];
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

//		System.out.println("h");
//		for (int i = 0; i < h.length; i++) {
//			double v = h[i];
//			System.out.println(v);
//		}
//		System.out.println("end h");

		for(int i = 0;i<n-2;i++){ //set left and right
			left[i+1][i] = h[i];
			left[i+1][i+1] = 2*(h[i]+h[i+1]);
			left[i+1][i+2] = h[i+1];

			Xright[i+1] = (get(i+2).getX()-get(i+1).getX()) / h[i+1] - (get(i+1).getX()-get(i).getX()) / h[i];
			Yright[i+1] = (get(i+2).getY()-get(i+1).getY()) / h[i+1] - (get(i+1).getY()-get(i).getY()) / h[i];
		}

		if (isClosed()) {
			left[0][0] = 2 * h[0];
			left[0][1] = h[0];
			left[0][n-2] = h[n-2];
			left[0][n-1] = 2 * h[n-2];
//			left[n-1][n-2] = h[n-2];
//			left[n-1][0] = h[0];
//			left[n-1][n-1] = 2* (h[n-2] + h[0]);
			left[n-1][n-1] = -1;
			left[n-1][0] = 1;
			Xright[0] = (get(1).getX() - get(0).getX()) / h[0] - (get(n-1).getX() - get(n-2).getX()) / h[n-2];
			Yright[0] = (get(1).getY() - get(0).getY()) / h[0] - (get(n-1).getY() - get(n-2).getY()) / h[n-2];
			Xright[n-1] = 0;
			Yright[n-1] = 0;
//			Xright[n-1] = (get(n-1).getX() - get(n-2).getX()) / h[n-2] - (get(1).getX() - get(0).getX()) / h[0];
//			Yright[n-1] = (get(n-1).getY() - get(n-2).getY()) / h[n-2] - (get(1).getY() - get(0).getY()) / h[0];
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
//		for (int k = 0; k < Xright.length; k++) {
////		    for (int j = 0; j < left[0].length; j++) {
//		        System.out.print(Xright[k] + " ");
////		    }
//		    System.out.print("\n");
//		}

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
>>>>>>> master

		return solution;
	}
}
