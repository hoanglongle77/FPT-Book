package FileController;

import java.io.File;
import javax.swing.filechooser.FileFilter;

public class FileTypeFilter extends FileFilter {
	private final String extension;
	private final String description;

	public FileTypeFilter(String extension, String description) {
		super();
		this.extension = extension;
		this.description = description;
	}

	public String getExtension() {
		return extension;
	}

	public String getDescription() {
		return description + String.format(" (*%s)", extension);
	}

	@Override
	public boolean accept(File f) {
		// TODO Auto-generated method stub
		if(f.isDirectory()) {
			return true;
		}
		return f.getName().endsWith(extension);
	}
}
