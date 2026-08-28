package de.manuelanker.app.toolbox;

public class OSDisk {

	public static String getOSName() {
		String os = System.getProperty("os.name").toLowerCase();

		if (os.contains("win")) {
			return "windows";
		} else if (os.contains("mac")) {
			return "mac";
		} else {
			return "linux";
		}
	}

	public static boolean isWindows() {
		return getOSName().equals("windows");
	}

	public static String getNobBuildCommand() {
		if (isWindows()) {
			return "gcc -o nob.exe nob.c && nob.exe";
		} else {
			return "cc -o nob nob.c && ./nob";
		}
	}
}
