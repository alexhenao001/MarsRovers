# Mars Rovers

Hi and thanks for providing this awesome problem to work on!

In this repo you'll find my first crack at implementing the Mars Rovers solution in Java. It's my sincere hope you enjoy reviewing it as much as I enjoyed designing and implementing it!



# Design

The first steps in designing the solution were to understand the problem. Rovers need to be able to move within a grid where the upper bounds are provided in a text file. The information for the Rover instantiation and commands to execute are also provided in this same text file. 

There is some notion of fleet command provided by a singleton class.

Rovers should be able to process a single command

Rovers should only move if it's safe


## Assumptions
The application solutions assume that the data provided in the file is sound and structured as the problem describes. This assumption also includes that for every Rover data line in the input file there is a corresponding command data line.

The application holds these assumptions critically and in order to work the data should conform to the assumptions:
 1.  The input is provided in a file called **inputFile.txt**
 2. The **inputFile.txt is formatted properly** as described in the problem description
 3. The **first line of the input will contain the boundary data** 
 4. The following set of lines will come in pairs such that **for every line of Rover information** there will be a corresponding line containing commands for the rover to execute.
 5. The utility classes used in this application are assumed to work and were not designed to be tested.


## Objects
The objects which were designed to hold the Mars business logic are the following:

 1. **ControlTower**
 2. **Rover**

**ControlTower** - this object is what communicates with Earth. It stores the data that is coming from an input file and always has it ready to command the entire rover fleet in a rover-sequential approach (one rover at a time). The data includes the Mars grid boundaries, Rover instantiation information, and the commands for the Rover fleet to execute.

**Rover** - this object has a direct link with the single control tower commanding the rover fleet. It's designed to process a single command at a time (L,R,M). It checks with the ControlTower before it completes an 'M' command to ensure it's safely moving within the boundaries.

Along with each of the primary  objects exist corresponding test classes:
**ControlTowerTest** + **RoverTest**

Also, there are a couple of utility classes which are used to read the input file and to drive the main class which prints out to the console.
**MyFileReader** + **SolutionDriver**

## Algorithm

The driving solution algorithm is pretty straight forward. 

 1. A ControlTower is built and it immediately starts communications with external file input, "Earth".
 2. The ControlTower then deploys the number of Rovers.
 3. The ControlTower then commands the Rovers to execute the stored commands.

The fun part of this problem was figuring how to map the directions and the corresponding calculations. For example if the Rover must move north then that means to add the current position (x,y) of the rover with the north movement coordinate map (0,1).

## Running The Solution
In the resources directory there exists:

 1. MarsRovers.jar
 2. inputFile.txt
 3. runSolution.sh

Execute the runSolution.sh and see that the the application yields the expected output.

```
