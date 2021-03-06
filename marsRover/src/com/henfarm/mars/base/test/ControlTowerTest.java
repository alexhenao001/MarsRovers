package com.henfarm.mars.base.test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

//import org.junit.After;
//import org.junit.Before;
import org.junit.jupiter.api.Test;

import com.henfarm.mars.base.impl.ControlTower;
import com.henfarm.mars.base.impl.Rover;

class ControlTowerTest {

	@Test
	void testEarthInputDataAlwayAvailable() {
		ControlTower.createControlTower();
		ArrayList<String> earthData = ControlTower.getInputData();
		assertFalse(earthData.get(0).isEmpty());
	}

	@Test
	void testDeployFleet() {
		ControlTower controlTower = ControlTower.createControlTower();
		controlTower.deployFleet();
		assertTrue(ControlTower.getRoverFleet().size() > 0);
	}

	/**
	 * ControlTower.commandFleet() is dependent on a fleet being deployed.
	 */
	@Test
	void testCommandFleet() {
		ControlTower controlTower = ControlTower.createControlTower();
		controlTower.deployFleet();
		controlTower.commandFleet();
	}

	/**
	 * ControlTower.commandFleet() is dependent on a fleet being deployed.
	 */
	@Test
	void testProcessCommandLine() {
		ControlTower controlTower = ControlTower.createControlTower();
		
		//Example 1 - from data from problem description
		String commands_1 = "LMLMLMLMM";
		int x1 = 1;
		int y1 = 2;
		char direction1 = 'N';
		int expectedX_1 = 1;
		int expectedY_1 = 3;
		char expectedDirection1 = 'N';
		
		//Example 2 - from data from problem description
		String commands_2 = "MMRMMRMRRM";
		int x2 = 3;
		int y2 = 3;
		char direction2 = 'E';
		int expectedX_2 = 5;
		int expectedY_2 = 1;
		char expectedDirection2 = 'E';

		//Example 1 
		Rover rover1 = new Rover(x1, y1, direction1, controlTower);
		controlTower.processCommandLine(rover1, commands_1);
		assertEquals(expectedX_1, rover1.getCoordinates()[0]);
		assertEquals(expectedY_1, rover1.getCoordinates()[1]);
		assertEquals(expectedDirection1, rover1.getDirection());
		
		//Example 2
		Rover rover2 = new Rover(x2, y2, direction2, controlTower);
		controlTower.processCommandLine(rover2,commands_2);	
		assertEquals(expectedX_2, rover2.getCoordinates()[0]);
		assertEquals(expectedY_2, rover2.getCoordinates()[1]);
		assertEquals(expectedDirection2, rover2.getDirection());
	}

	@Test
	void testGetFleetLocations() {
		ControlTower controlTower = ControlTower.createControlTower();
		ArrayList<String> locations;
		controlTower.deployFleet();
		controlTower.commandFleet();
		locations = controlTower.getFleetLocations();

		assertTrue(locations.size() > 0);
	}

	@Test
	void testIsSafeForRoverToMove() {
		ControlTower contorlTower = ControlTower.createControlTower();
		int[] coordinatesOutOfBoundary1 = { -1, -1 };
		int[] coordinatesWithinBoundary1 = { 0, 0 };

		int[] coordinatesOutOfBoundary2 = { 6, 6 };
		int[] coordinatesWithinBoundary2 = { 5, 5 };

		assertFalse(contorlTower.isSafeForRoverToMove(coordinatesOutOfBoundary1));
		assertTrue(contorlTower.isSafeForRoverToMove(coordinatesWithinBoundary1));

		assertFalse(contorlTower.isSafeForRoverToMove(coordinatesOutOfBoundary2));
		assertTrue(contorlTower.isSafeForRoverToMove(coordinatesWithinBoundary2));
	}

}
