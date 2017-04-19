package refactoring;

import java.awt.FileDialog;
import java.awt.Frame;
import java.io.File;

public class Util {
	public static File selectFile(String fileType) {
		FileDialog fileDialog = new FileDialog((Frame) null, fileType + "-Datei auswählen", FileDialog.LOAD);
		fileDialog.setMultipleMode(false);
		fileDialog.setFile("*." + fileType);
		fileDialog.setVisible(true);
		return fileDialog.getFiles()[0];
	}
}
