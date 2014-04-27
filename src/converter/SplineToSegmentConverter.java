package converter;

import intersection.Segment;
import splines.Point;
import splines.Spline;

import java.util.ArrayList;
import java.util.List;

/**
 * Author: Fabian Fränz <f.fraenz@t-online.de>
 * Date: 27/04/14
 * Project: CAD
 * Version: 1.0
 * Description: Class which takes a spline and creates linear segments for intersection tests.
 */
public class SplineToSegmentConverter {

	public static List<Segment> convertToSegments(Spline spline) {

		List<Segment> segments = new ArrayList<>();
		Point leftPoint = spline.get(0);
		for (int i = 1; i < spline.size(); i++) {
			Point rightPoint = spline.get(i);
			Segment
					segment =
					new Segment(new intersection.Point(leftPoint.getX(), leftPoint.getY()),
								new intersection.Point(rightPoint.getX(), rightPoint.getY()));
			segments.add(segment);
		}
		return segments;
	}

}
