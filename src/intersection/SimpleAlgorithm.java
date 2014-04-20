package intersection;

import java.util.ArrayList;
import java.util.List;

public class SimpleAlgorithm
		implements IntersectionAlgorithm {

	public List<Intersection> findIntersections(List<Segment>... polygons) {
		List<Segment> segments = new ArrayList<Segment>();
		for (List<Segment> polygon : polygons) {
			segments.addAll(polygon);
		}
		return intersections(segments);
	}

	private List<Intersection> intersections(List<Segment> segments) {
		List<Intersection> intersections = new ArrayList<Intersection>();
		for (int i = 0; i < segments.size(); i++) {
			for (int j = i + 1; j < segments.size(); j++) {
				if (segments.get(i)
							.isIntersecting(segments.get(j))) {
					Intersection inter = new Intersection(segments.get(i), segments.get(j));
					if (!intersections.contains(inter)) {
						intersections.add(inter);
					}
				}
			}
		}
		return intersections;
	}

}
