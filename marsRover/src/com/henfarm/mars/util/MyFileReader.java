package com.henfarm.mars.util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Basic FileReader
 * 
 * For sake of time there is no testing occurring on this class. Executive decision to focus available resources
 * on the design and implementation of the Rover and ControlTower classes and corresponding unit tests.
 * 
 * This file reader is one which has been tested and proven to work for quite some time and implementation will not change for this class.
 * 
 * @author alexhenao
 *
 */
public class MyFileReader {

	public MyFileReader() {
	}

	public ArrayList<String> readFile(String path) {

		ArrayList<String> lines = new ArrayList<>();
		BufferedReader br = null;

		try {
			br = new BufferedReader(new FileReader(path));

			String contentLine = null;
			while ((contentLine = br.readLine()) != null) {
				lines.add(contentLine);
			}
		} catch (IOException ioe) {
			ioe.printStackTrace();
		} finally {
			try {
				if (br != null)
					br.close();

			} catch (IOException ioe) {
				System.out.println("Error in closing the BufferedReader");
			}
		}

		return lines;
	}

}
