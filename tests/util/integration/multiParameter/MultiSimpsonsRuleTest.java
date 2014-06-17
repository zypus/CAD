package util.integration.multiParameter;

import org.junit.Assert;
import org.junit.Test;
import util.Bound;
import util.ParametricFunction;

public class MultiSimpsonsRuleTest {

	public static void main(String[] args) {

		try {
			new MultiSimpsonsRuleTest().testIntegrate();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testIntegrate() throws Exception {

		MultiIntegrator integrator = new MultiSimpsonsRule();
		ParametricFunction function = new ParametricFunction() {
			@Override public double getValue(double u, double v) {

				return u * Math.pow(v, 2);
			}
		};
		double result = integrator.integrate(function, new Bound(0, 2), new Bound(0,1), 40, 40);
		Assert.assertEquals(2.0/3.0, result, 0.001);
	}
}