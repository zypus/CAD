import gui.MainFrame;

import javax.swing.*;

/**
 * Created by fabian on 26/04/14.
 */
public class CAD {

	public static void main(String[] args) {

		try {
			for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
				if ("Nimbus".equals(info.getName())) {
					UIManager.setLookAndFeel(info.getClassName());
					break;
				}
			}
		} catch (Exception e) {
			// If Nimbus is not available, you can set the GUI to another look and feel.
		}
		MainFrame window = new MainFrame();
	}

}
