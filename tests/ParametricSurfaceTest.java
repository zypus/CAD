import org.junit.Before;
import org.junit.Test;
import surface.ParametricSurface;
import util.Bound;
import util.ParametricFunction;
import org.junit.Assert;

public class ParametricSurfaceTest {

	private ParametricSurface surface;

	@Before
	public void setUp() throws Exception {
		ParametricFunction x = new ParametricFunction() {
			@Override public double getValue(double u, double v) {

				return 4*Math.sin(u)*Math.cos(v);
			}
		};
		ParametricFunction y = new ParametricFunction() {
			@Override public double getValue(double u, double v) {

				return 4 * Math.sin(u) * Math.sin(v);
			}
		};
		ParametricFunction z = new ParametricFunction() {
			@Override public double getValue(double u, double v) {

				return 4 * Math.cos(u);
			}
		};
		surface = new ParametricSurface(x, y, z, new Bound(0, Math.PI / 3), new Bound(0, 2 * Math.PI));
	}

	@Test
	public void testGetArea() throws Exception {

		double area = surface.getArea();
		Assert.assertEquals(16 * Math.PI, area, 0.001);
	}
}