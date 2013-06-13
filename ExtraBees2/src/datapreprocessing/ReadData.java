package datapreprocessing;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class ReadData {
	
	private String filename;
	private boolean fileExisted=false;
	
	
	public ReadData(String filename)
	{
		this.filename=filename;
	}
	

	public boolean fileExisted()
	{
		File dataFile =new File(filename);
		fileExisted = dataFile.exists();
		return fileExisted;
	}
	
	
}
