package gui;

import java.io.BufferedReader;
import java.io.FileReader;
import java.nio.file.Path;

public abstract class FileReaderBuffered {

	private Path filePath;
	private boolean consolePrint;

	public FileReaderBuffered(Path filePath, boolean consolePrint) {

		this.filePath = filePath;
		this.consolePrint = consolePrint;
	}

	public void read() {

		try {
			BufferedReader br = new BufferedReader(new FileReader(
					filePath.toString()));
			String strLine = "";

			// Reads header
			strLine = br.readLine();
			if (strLine != null) {
				processHeaderLine(strLine);
			}

			// Reads data
			strLine = br.readLine();
			while (strLine != null) {
				if (consolePrint) {
					System.out.println(strLine);
					Thread.sleep(100);
				}
				// ? br.mark(0);
				// ? br.reset();
				processLine(strLine);
				strLine = br.readLine();

			}
			System.out.println("Reading done!");
			br.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public abstract void processHeaderLine(String strLine);

	public abstract void processLine(String strLine);

	public Path getFilePath() {

		return filePath;
	}

	public boolean getConsolePrint() {

		return consolePrint;
	}
}
