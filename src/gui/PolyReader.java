package gui;

import java.awt.*;
import java.nio.file.Path;
import java.util.ArrayList;

public class PolyReader
		extends FileReaderBuffered {

	ArrayList<Point> points;

	public PolyReader(Path filePath, boolean consolePrint,
			ArrayList<Point> points) {

		super(filePath, consolePrint);
		this.points = points;
	}

	@Override
	public void processHeaderLine(String strLine) {

		processLine(strLine);
	}

	@Override
	public void processLine(String strLine) {

		int tabIndex = 0;
		while (strLine.charAt(tabIndex) != '	') {
			tabIndex++;
		}
		Double d1 = Double.parseDouble(strLine.substring(0, tabIndex));
		Double d2 = Double.parseDouble(strLine.substring(tabIndex + 1,
														 strLine.length()));

		Point p = new Point();
		p.setLocation(d1, d2);
		points.add(p);
		System.out.println(p.getX() + " ... " + p.getY());
	}

	public ArrayList<Point> getPoints() {

		return points;
	}

}
