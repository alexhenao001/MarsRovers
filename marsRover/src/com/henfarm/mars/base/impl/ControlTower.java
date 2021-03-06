package com.henfarm.mars.base.impl;

import java.util.ArrayList;

import com.henfarm.mars.util.MyFileReader;

//Needs to check for other rovers

//Print all of the rover locations
public class ControlTower {

	/*
	 * Singleton Instance - There should only be one control tower which the rovers
	 * are linked to for their commands and safety information.
	 */
	private static ControlTower singletonControlTower = null;

	/*
	 * File Path of the input is set to constant and file i/o is handled by
	 * MyFileReader.java
	 */
	private static final String FILE_PATH = System.getProperty("filePath") + "inputFile.txt";
	//private static final String FILE_PATH = "resources/inputFile.txt";

	/*
	 * Instance Variables
	 * 
	 * inputData : ArrayList containing raw data coming from Earth
	 * (resources/inputFile.txt) roverFleet : ArrayList containing rovers which make
	 * up fleet controlled by ControlTower roverFleetCommandData : ArrayList
	 * containing the commands for the fleet marsMapUpperBounds : int array
	 * containing the upper map bounds provided by the raw data marsMapLowerBounds :
	 * int array containing the lower map bounds which are assumed to be [0,0]
	 */
	private static ArrayList<String> inputData;
	private static ArrayList<Rover> roverFleet;
	private static ArrayList<String> roverFleetCommandData;
	private static int[] marsMapUpperBounds = new int[2];
	private static final int[] marsMapLowerBounds = { 0, 0 };

	/*
	 * Singleton Public ControlTower constructor - there should only be one
	 * ControlTower commanding the fleet
	 */
	public static ControlTower createControlTower() {

		if (singletonControlTower == null)
			singletonControlTower = new ControlTower();

		return singletonControlTower;
	}

	/**
	 * private ControlTower constructor with data from Earth (inputFile.txt) always
	 * available.
	 */
	private ControlTower() {
		readAndStoreDataFromEarth();
	}

	/**
	 * void Method used to deploy (ready) rovers which are standing by for commands
	 */
	public void deployFleet() {
		setMarsMapUpperBounds(inputData.get(0).split(" "));
		roverFleet = new ArrayList<Rover>();
		roverFleetCommandData = new ArrayList<String>();

		for (int i = 1; i < inputData.size(); i += 2) {
			String[] line1 = inputData.get(i).split(" ");
			String line2 = inputData.get(i + 1);

			int x = Integer.valueOf(line1[0]);
			int y = Integer.valueOf(line1[1]);
			char direction = line1[2].charAt(0);

			addRoverToFleet(x, y, direction);
			addRoverFleetCommandData(line2);
		}
	}

	/**
	 * Method used to process commands and have the fleet of all rovers execute
	 * their commands in a rover-sequential approach.
	 * 
	 * @return TRUE once the fleet has executed commands successfully.
	 */
	public boolean commandFleet() {
		boolean commandStatus = false;
		for (int i = 0; i < roverFleet.size(); i++) {
			Rover currentRover = roverFleet.get(i);
			String commandLine = roverFleetCommandData.get(i);
			commandStatus = processCommandLine(currentRover, commandLine);

			if (!commandStatus)
				break;
		}

		return commandStatus;
	}

	/**
	 * Method called in commandFleet() to process and execute the command string
	 * coming from inputFile.txt
	 * 
	 * @param rover       : Rover which should execute the commands
	 * @param commandLine : String of commands for Rover to execute
	 * @return TRUE if the command line was processed successfully.
	 * 
	 */
	public boolean processCommandLine(Rover rover, String commandLine) {
		boolean processStatus = false;
		for (char command : commandLine.toCharArray()) {
			processStatus = rover.processCommand(command);

			if (!processStatus)
				break;
		}
		return processStatus;
	}

	/**
	 * Method used to retrieve the locations of deployed rovers
	 * 
	 * @return ArrayList<String> containing location of deployed rovers.
	 */
	public ArrayList<String> getFleetLocations() {
		ArrayList<String> locations = new ArrayList<>();

		for (int i = 0; i < roverFleet.size(); i++) {
			Rover currentRover = roverFleet.get(i);
			locations.add(currentRover.currentLocationAndDirection());
		}

		return locations;
	}

	/**
	 * @return Instance variable roverFleet
	 */
	public static ArrayList<Rover> getRoverFleet() {
		return roverFleet;
	}

	/**
	 * @return Instance variable inputData
	 */
	public static ArrayList<String> getInputData() {
		return inputData;
	}

	/**
	 * @return Instance variable marsMapUpperBounds
	 */
	public static int[] getMarsMapUpperBounds() {
		return marsMapUpperBounds;
	}

	/**
	 * @return Instance variable marsMapLowerBounds
	 */
	public static int[] getMarsMapLowerBounds() {
		return marsMapLowerBounds;
	}

	/**
	 * Helper method used to identify if coordinates for a rover's intended move are
	 * within boundaries
	 * 
	 * @param roverCoordinates : Coordinates where the rover will move to
	 * @return TRUE if safe for the rover to move to intended coordinates
	 */
	public boolean isSafeForRoverToMove(int[] roverCoordinates) {
		boolean isInBounds = isInBounds(roverCoordinates[0], roverCoordinates[1]);
		// noRoverCollision can be used for an implementation that would prevent rovers
		// from moving to where there exists another rover.
		boolean noRoverCollision = true;

		return isInBounds && noRoverCollision;

	}

	/**
	 * private method used in constructor to get data from earth
	 * (resources/inputFile.txt)
	 */
	private void readAndStoreDataFromEarth() {
		MyFileReader antennae = new MyFileReader();
		ArrayList<String> data = antennae.readFile(FILE_PATH);
		setInputData(data);
	}

	/**
	 * Setter for instance variable
	 * 
	 * @param inputData : ArrayList of String with raw input data indexed by line
	 */
	private static void setInputData(ArrayList<String> inputData) {
		ControlTower.inputData = inputData;
	}

	/**
	 * Setter for instance variable to update the bounds.
	 * 
	 * @param upperBounds : Upper boundary defined from Earth data
	 *                    (resources/inputFile.txt)
	 */
	public static void setMarsMapUpperBounds(String[] upperBounds) {
		marsMapUpperBounds[0] = Integer.valueOf(upperBounds[0]);
		marsMapUpperBounds[1] = Integer.valueOf(upperBounds[1]);
	}

	public static void setSingletonControlTower(ControlTower singletonControlTower) {
		ControlTower.singletonControlTower = singletonControlTower;
	}

	private static void addRoverToFleet(int x, int y, char direction) {
		Rover rover = new Rover(x, y, direction, singletonControlTower);
		roverFleet.add(rover);
	}

	/**
	 * Method which loads the commands to the ControlTower instance variable,
	 * roverFleetCommandData. The index will correspond with the rover at the same
	 * index of the roverFleet array.
	 * 
	 * @param line: String of commands which will be used by the Fleet.
	 */
	private static void addRoverFleetCommandData(String line) {
		roverFleetCommandData.add(line);
	}

	/**
	 * Helper method used to identify if certain x and y coordinates are within the boundaries fo the Mars map.
	 * @param x : X-axis variable checked to see if within the grid
	 * @param y	: Y-axis variable checked to see if within the grid
	 * @return TRUE if both (x,y) are within the grid
	 */
	private static boolean isInBounds(int x, int y) {

		// calculate bound variables
		if (x < marsMapLowerBounds[0] || y < marsMapLowerBounds[1])
			return false;

		if (x > marsMapUpperBounds[0] || y > marsMapUpperBounds[1])
			return false;

		return true;
	}

}
