package gui.tools;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Author: Fabian Fränz <f.fraenz@t-online.de>
 * Date: 07/05/14
 * Project: CAD
 * Version: 1.0
 * Description:
 */
public class Tool extends MouseAdapter implements Drawable {

	private boolean handleEvents = true;
	private boolean active = false;
	private ToolDelegate delegate = null;
	private boolean draw = true;

	public boolean isActive() {

		return active;
	}

	public ToolDelegate getDelegate() {

		return delegate;
	}

	public void setDelegate(ToolDelegate delegate) {

		this.delegate = delegate;
	}

	@Override public void mouseEntered(MouseEvent e) {

		super.mouseEntered(e);
		handleEvents = true;
	}

	@Override public void mouseExited(MouseEvent e) {

		super.mouseExited(e);
		handleEvents = false;
	}

	public boolean isHandlingEvents() {

		return active && handleEvents && (delegate == null || delegate.shouldStart(this));
	}

	@Override public void draw(Graphics2D g2) {

	}

	protected void finished() {

		if (delegate != null) {
			delegate.didFinish(this);
		}
	}

	protected void started() {

		if (delegate != null) {
			delegate.didStart(this);
		}
	}

	protected void updated() {

		if (delegate != null) {
			delegate.didUpdate(this);
		}
	}

	protected boolean shouldDraw() {

		return draw && delegate.shouldDraw(this);
	}

	public void activate() {
		active = true;
		draw = true;
	}

	public void deactivate() {
		finished();
		active = false;
		draw = false;
	}
}
