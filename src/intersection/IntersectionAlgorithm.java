package intersection;

import java.util.List;

public interface IntersectionAlgorithm {
	List<Intersection> findIntersections(List<Segment>... polygons);
}
