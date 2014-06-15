package gui;

import surface.HomogeneousPoint3d;
import surface.NURBSPatchwork;
import surface.NURBSSurface;
import surface.Solid;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Author: Fabian Fränz <f.fraenz@t-online.de>
 * Date: 15/06/14
 * Project: CAD
 * Version: 1.0
 * Description: TODO Add description.
 */
public class SolidFileManager {

	private static SolidFileManager instance = new SolidFileManager();

	public Solid load(File file) {

		Solid solid = null;

		try {
			Scanner scanner = new Scanner(file);
			String type = scanner.next();
			if (type.equals("NURBSSurface")) {
				solid = createNurbsSurface(scanner);
			} else if (type.equals("NURBSPatchwork")) {
				solid = createNurbsPatchwork(scanner);
			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		return solid;
	}

	private Solid createNurbsPatchwork(Scanner scanner) {

		int patchAmount = scanner.nextInt();
		List<NURBSSurface> patches = new ArrayList<>();
		for (int i = 0; i < patchAmount; i++) {
			patches.add(createNurbsSurface(scanner));
		}
		String isOpen = scanner.next();
		boolean open = isOpen.equals("open");
		NURBSPatchwork patchwork = new NURBSPatchwork(patches);
		patchwork.setOpen(open);
		return patchwork;
	}

	private NURBSSurface createNurbsSurface(Scanner scanner) {

		int uControls = scanner.nextInt();
		int vControls = scanner.nextInt();
		int uKnots = scanner.nextInt();
		int vKnots = scanner.nextInt();
		int uOrder = scanner.nextInt();
		int vOrder = scanner.nextInt();
		int uSteps = scanner.nextInt();
		int vSteps = scanner.nextInt();
		scanner.nextLine(); // need to go to next line because after previous next scanner is still before the \n in the previous line
		List<List<HomogeneousPoint3d>> controlPoints = new ArrayList<>();
		for (int u = 0; u < uControls; u++) {
			List<HomogeneousPoint3d> vControlRow = new ArrayList<>();
			for (int v = 0; v < vControls; v++) {
				String pointString = scanner.nextLine();
				String[] values = pointString.split(",");
				if (values.length == 3) {
					HomogeneousPoint3d
							point3d =
							new HomogeneousPoint3d(Double.parseDouble(values[0]), Double.parseDouble(values[1]),
												   Double.parseDouble(values[2]));
					vControlRow.add(point3d);
				} else if (values.length == 4) {
					HomogeneousPoint3d
							point3d =
							new HomogeneousPoint3d(Double.parseDouble(values[0]), Double.parseDouble(values[1]),
												   Double.parseDouble(values[2]), Double.parseDouble(values[3]));
					vControlRow.add(point3d);
				} else {
					System.out.println("Not enough coordinates for control point "+u+" "+v+" found!");
				}
			}
			controlPoints.add(vControlRow);
		}
		String[] uKnotString = scanner.nextLine().split(",");
		List<Double> uKnotList = new ArrayList<>();
		for (int i = 0; i < uKnots; i++) {
			uKnotList.add(Double.parseDouble(uKnotString[i]));
		}
		String[] vKnotString = scanner.nextLine().split(",");
		List<Double> vKnotList = new ArrayList<>();
		for (int i = 0; i < vKnots; i++) {
			vKnotList.add(Double.parseDouble(vKnotString[i]));
		}
		String isOpen = scanner.next();
		boolean open = isOpen.equals("open");
		NURBSSurface surface = new NURBSSurface(controlPoints, uKnotList, vKnotList, uOrder, vOrder);
		surface.setuSteps(uSteps);
		surface.setvSteps(vSteps);
		surface.setOpen(open);
		return surface;
	}

	private SolidFileManager() {

	}

	public static SolidFileManager getInstance() {
		return instance;
	}

}
