# Mars Rovers

Hi and thanks for providing this awesome problem to work on!

In this repo you'll find my first crack at implementing the Mars Rovers solution in Java. It's my sincere hope you enjoy reviewing it as much as I enjoyed designing and implementing it!



# Design

The first steps in designing the solution were to understand the problem. Rovers need to be able to move within a grid where the upper bounds are provided in a text file. The information for the Rover instantiation and commands to execute are also provided in this same text file. 

Rovers shouldn't leave the grid boundaries which are provided.

## Assumptions
The application solutions assume that the data provided in the file is sound and structured as the problem describes. This assumption also includes that for every Rover data line in the input file there is a corresponding command data line.

The application holds these assumptions critically and in order to work the data should conform to the assumptions:
 1.  The input is provided in a file called **inputFile.txt**
 2. The **inputFile.txt is formatted properly** as described in the problem description
 3. The **first line of the input will contain the boundary data** 
 4. The following set of lines will come in pairs such that **for every line of Rover information** there will be a corresponding line containing commands for the rover to execute.
