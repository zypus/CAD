package splines;

/**
 * Created by fabian on 20/04/14.
 */
public class Polynom {
    private final double[] coefficients;

    public Polynom(double... coefficients) {
        this.coefficients = coefficients;
    }

    public double[] getCoefficients() {
        return coefficients;
    }

    public Polynom derivative() {
        return null;
    }

    public Polynom integral(double constantTerm) {
        return null;
    }
}
