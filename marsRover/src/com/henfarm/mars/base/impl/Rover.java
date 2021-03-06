package com.henfarm.mars.base.impl;

/**
 * 
 * The Rover class represents a Mars rover which has the ability to process a
 * single command instruction to change direction by turning or move into a current
 * direction if safe to move (within boundaries of the Mars map provided by ControlTower)
 * 
 * @author alexhenao
 *
 */
public class Rover {

	/** Constant to make a rover turn left */
	private static final char TURN_LEFT = 'L';
	/** Constant to make a rover turn right */
	private static final char TURN_RIGHT = 'R';
	/** Constant to make a rover move 1 grid point in the direction it's facing */
	private static final char MOVE_FORWARD = 'M';

	/**
	 * Array of calculation values used to move the rover. Each Index maps to a
	 * specific direction.
	 * 
	 * i.e. ['N', 'E', 'S', 'W']
	 * 
	 */
	private static final int[][] DIRECTION_MOVE_CALCS = { { 0, 1 }, { 1, 0 }, { 0, -1 }, { -1, 0 } };

	/*
	 * index 0 = N index 1 = E index 2 = S index 3 = W
	 */
	private static final char[] DIRECTIONS = { 'N', 'E', 'S', 'W' };

	/** Location of a Rover */
	private int[] coordinates;
	/** Direction a rover is facing */
	private char direction;
	/** Object that controls a fleet of rovers */
	private ControlTower controlTower;

	/**
	 * Rover created with a location and direction along with connection to the Mars
	 * control tower. Assumed that the (x,y) values will always been within the bounds given in the input file.
	 * 
	 * @param x            The x-axis coordinate of a rover.
	 * @param y            The y-axis coordinate of a rover.
	 * @param direction    The direction a rover is facing.
	 * @param controlTower Object that provides rover with commands and safe
	 *                     boundaries data.
	 */
	public Rover(int x, int y, char direction, ControlTower controlTower) {

		this.coordinates = new int[] { x, y };
		this.direction = direction;
		this.controlTower = controlTower;

	}

	/**
	 * Gets a Rover's stored coordinate location.
	 * 
	 * @return An int [] that yields a rover's current location.
	 */
	public int[] getCoordinates() {
		return coordinates;
	}

	/**
	 * Gets the direction a rover is facing.
	 * 
	 * @return A char that represents the direction a rover is facing.
	 */
	public char getDirection() {
		return direction;
	}

	/**
	 * Process a single rover command using the class constants and switch to ensure
	 * the action is valid.
	 * 
	 * @param command - Single command for a Rover to execute
	 * @return boolean - Returns true if a command was successfully completed.
	 */
	public boolean processCommand(char command) {

		switch (command) {
		case TURN_LEFT:
			return this.turnLeft();
		case TURN_RIGHT:
			return this.turnRight();
		case MOVE_FORWARD:
			return this.move();
		default:
			System.out.println("error in Process Command");
			return false;

		}
	}

	/**
	 * Method to give a String representation ofa rover's current location. Uses
	 * StringBuilder class to structure the string
	 * 
	 * i.e. "1 2 E"
	 * 
	 * @return String yielding a rover's location. i.e.: 1 2 E
	 */
	public String currentLocationAndDirection() {
		StringBuilder str = new StringBuilder();
		int[] currentLocVars = this.getCoordinates();

		str.append(currentLocVars[0]).append(" ");
		str.append(currentLocVars[1]).append(" ");
		str.append(this.getDirection());

		return str.toString();

	}

	/**
	 * Rover method called by the move() which sets coordinates if ControlTower says
	 * it's safe for the move() to occur.
	 * 
	 * 
	 * @param coordinates The coordinates to be set for the rover
	 * @return
	 */
	private boolean setCoordinates(int[] coordinates) {
		if (this.controlTower.isSafeForRoverToMove(coordinates)) {
			this.coordinates = coordinates;
			return true;
		}
		return false;
	}

	/**
	 * Rover helper method which is called by the turnLeft() or turnRight() to set
	 * the appropriate direction. Assumed it's always safe to turn.
	 * 
	 * @param directionIndex
	 */
	private void setDirection(int directionIndex) {
		if (directionIndex == -1)
			this.direction = DIRECTIONS[3];
		else if (directionIndex == 4)
			this.direction = DIRECTIONS[0];
		else
			this.direction = DIRECTIONS[directionIndex];
	}

	/**
	 * Rover method which turns the rover to the left. The left turn implies a
	 * change in direction.
	 * 
	 * @return True if the turn successfully occurs. It's assumed this is always
	 *         safe so will always be true.
	 */
	private boolean turnLeft() {
		int directionIndex = this.findCurrentDirectionIndex();

		if (directionIndex >= 0)
			this.setDirection(directionIndex - 1);

		return true;
	}

	/**
	 * Rover method which turns the rover to the right. The right turn implies a
	 * change in direction.
	 * 
	 * @return True if the turn successfully occurs. It's assumed this is always
	 *         safe so will always be true.
	 */
	private boolean turnRight() {
		int directionIndex = this.findCurrentDirectionIndex();

		if (directionIndex >= 0)
			this.setDirection(directionIndex + 1);

		return true;
	}

	/**
	 * Rover method which moves the rover one grid point in the direction it's
	 * currently facing. The move() is implemented to check that the rover will not
	 * fall off the boundary via setCoordinates().
	 * 
	 * @return True is returned if the rover will not go out of the mars grid
	 *         boundaries.
	 */
	private boolean move() {
		int[] coordinates = this.getCoordinates();
		int directionIndex = this.findCurrentDirectionIndex();

		/*
		 * Predefined movement behavior is indexed as [N,E,S,W] and used to calculate
		 * the intended move.
		 */
		int calcCoordinates[] = DIRECTION_MOVE_CALCS[directionIndex];

		return this.setCoordinates(calcNewCoordinates(coordinates, calcCoordinates));

	}

	/**
	 * Method used in move() to calculate and set new coordinates if move() is
	 * possible
	 * 
	 * @param currentCoordinates
	 * @param calcCoordinates
	 * @return
	 */
	private int[] calcNewCoordinates(int[] currentCoordinates, int[] calcCoordinates) {
		int[] newCoordinates = new int[2];

		newCoordinates[0] = currentCoordinates[0] + calcCoordinates[0];
		newCoordinates[1] = currentCoordinates[1] + calcCoordinates[1];

		return newCoordinates;
	}

	/**
	 * Helper method to map the char representation of a rover's current direction
	 * to an index of the DIRECTIONS array.
	 * 
	 * @return The index of final char array, ['N', 'E', 'S', 'W'], containing the
	 *         current direction of a rover
	 */
	private int findCurrentDirectionIndex() {
		char currentDirection = this.getDirection();
		for (int i = 0; i <= DIRECTIONS.length; i++) {
			if (DIRECTIONS[i] == currentDirection)
				return i;
		}
		return -1;
	}
}
