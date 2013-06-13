package onlyFortesting;

import datapreprocessing.ReadData;

public class testReading {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ReadData test = new ReadData("/Users/yangmin/Downloads/bbexport-wiz2 - Copy.csv");
		System.out.println(test.fileExisted());
	}

}
