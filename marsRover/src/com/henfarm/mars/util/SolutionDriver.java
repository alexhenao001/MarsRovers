package com.henfarm.mars.util;

import java.util.ArrayList;

import com.henfarm.mars.base.impl.ControlTower;

public class SolutionDriver {

	public static void main(String[] args) {
		
		ControlTower controlTower = ControlTower.createControlTower();
		controlTower.deployFleet();
		controlTower.commandFleet();
		
		
		ArrayList<String> processedFleetLocations= controlTower.getFleetLocations();
		
		for(String location : processedFleetLocations)
			System.out.println(location);
	
	}

}
