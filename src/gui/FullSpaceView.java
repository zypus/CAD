package gui;

/**
 * Author: Fabian Fränz <f.fraenz@t-online.de>
 * Date: 04/06/14
 * Project: CAD
 * Version: 1.0
 * Description: TODO Add description.
 */
public class FullSpaceView extends SubSpaceView {

	public FullSpaceView(QuadViewPanel owner) {

		super(new double[]{0,0,1}, owner);
		removeMouseListener(getAdapter());
		removeMouseMotionListener(getAdapter());

		addMouseListener(getMra());
		addMouseMotionListener(getMra());
	}
}
