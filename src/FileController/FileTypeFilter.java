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

	@Override
	public boolean accept(File f) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String getDescription() {
		// TODO Auto-generated method stub
		return null;
	}

}
