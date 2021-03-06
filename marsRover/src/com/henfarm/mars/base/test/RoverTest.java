package com.henfarm.mars.base.test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.After;
import org.junit.Before;
import org.junit.jupiter.api.Test;

import com.henfarm.mars.base.impl.ControlTower;
import com.henfarm.mars.base.impl.Rover;

class RoverTest {

	private ControlTower controlTower;

	@Before
	public void createControlTower() {
		controlTower = ControlTower.createControlTower();
	}

	@After
	public void tearDownControlTower() {
		controlTower = null;
	}

	@Test
	void testRover() throws Exception{
		int x = 1;
		int y = 2;
		char direction = 'N';
		String[] upperBounds = { "5", "5" };
		ControlTower.setMarsMapUpperBounds(upperBounds);
		Rover rover = new Rover(x, y, direction, controlTower);

		assertEquals(x, rover.getCoordinates()[0]);
		assertEquals(y, rover.getCoordinates()[1]);
		assertEquals(direction, rover.getDirection());
	}

	@Test
	void testGetCoordinates() {
		int x = 3;
		int y = 2;
		char direction = 'N';
		ControlTower controlTower = ControlTower.createControlTower();
		Rover rover = new Rover(x, y, direction, controlTower);

		assertEquals(x, rover.getCoordinates()[0]);
		assertEquals(y, rover.getCoordinates()[1]);
	}

	@Test
	void testGetDirection() {
		int x = 3;
		int y = 2;
		char direction = 'N';
		ControlTower controlTower = ControlTower.createControlTower();
		Rover rover = new Rover(x, y, direction, controlTower);

		char expectedDirection = 'N';
		assertEquals(expectedDirection, rover.getDirection());
	}

	@Test
	void testProcessCommand() {
		int x = 1;
		int y = 2;
		char direction = 'N';

		ControlTower controlTower = ControlTower.createControlTower();

		Rover rover = new Rover(x, y, direction, controlTower);

		char command1 = 'L';
		char expectedDirection1 = 'W';
		rover.processCommand(command1);
		assertEquals(expectedDirection1, rover.getDirection());

		char command2 = 'R';
		char expectedDirection2 = 'N';
		rover.processCommand(command2);
		assertEquals(expectedDirection2, rover.getDirection());

		char command3 = 'M';
		int expectedX = 1;
		int expectedY = 3;
		rover.processCommand(command3);
		assertEquals(expectedX, rover.getCoordinates()[0]);
		assertEquals(expectedY, rover.getCoordinates()[1]);
	}

	@Test
	void testCurrentLocationAndDirectionExample1() {
	
		char[] commands = { 'M', 'M', 'R', 'M', 'M', 'R', 'M', 'R', 'R', 'M' };
		int x = 3;
		int y = 3;
		char direction = 'E';
	
		int expectedX = 5;
		int expectedY = 1;
		char expectedDirection = 'E';
		ControlTower controlTower = ControlTower.createControlTower();
		Rover rover = new Rover(x, y, direction, controlTower);
	
		for (char command : commands)
			rover.processCommand(command);
	
		assertEquals(expectedX, rover.getCoordinates()[0]);
		assertEquals(expectedY, rover.getCoordinates()[1]);
		assertEquals(expectedDirection, rover.getDirection());
	}

	@Test
	void testCurrentLocationAndDirectionExample2() {

		char[] commands = { 'L', 'M', 'L', 'M', 'L', 'M', 'L', 'M', 'M' };
		int x = 1;
		int y = 2;
		char direction = 'N';

		int expectedX = 1;
		int expectedY = 3;
		char expectedDirection = 'N';
		ControlTower controlTower = ControlTower.createControlTower();
		Rover rover = new Rover(x, y, direction, controlTower);

		for (char command : commands)
			rover.processCommand(command);

		assertEquals(expectedX, rover.getCoordinates()[0]);
		assertEquals(expectedY, rover.getCoordinates()[1]);
		assertEquals(expectedDirection, rover.getDirection());
	}

	@Test
	void testSafeMove() {
		
		char[] commands = { 'M', 'M' };
		int x = 0;
		int y = 1;
		char direction = 'S';
		
		int expectedX = 0;
		int expectedY = 0;
		char expectedDirection = 'S';
		
		ControlTower controlTower = ControlTower.createControlTower();
		Rover rover = new Rover(x, y, direction, controlTower);
		
		for (char command : commands)
			rover.processCommand(command);
		
		assertEquals(expectedX, rover.getCoordinates()[0]);
		assertEquals(expectedY, rover.getCoordinates()[1]);
		assertEquals(expectedDirection, rover.getDirection());
	}
}
