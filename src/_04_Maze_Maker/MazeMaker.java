package _04_Maze_Maker;

import java.util.ArrayList;
import java.util.Random;
import java.util.Stack;


public class MazeMaker{
	
	private static int width;
	private static int height;
	
	private static Maze maze;
	
	private static Random randGen = new Random();
	private static Stack<Cell> uncheckedCells = new Stack<Cell>();
	
	
	public static Maze generateMaze(int w, int h){
		width = w;
		height = h;
		maze = new Maze(width, height);
		
		//4. select a random cell to start
		int randX = randGen.nextInt(width);
		int randY = randGen.nextInt(height);
		Cell randCell = maze.getCell(randX, randY);
		//5. call selectNextPath method with the randomly selected cell
		selectNextPath(randCell);
		if (randGen.nextBoolean()) {
			maze.getCell(0, randGen.nextInt(height)).setWestWall(false);
			maze.getCell(4, randGen.nextInt(height)).setEastWall(false);	
		} else {
		maze.getCell(randGen.nextInt(width), 0).setNorthWall(false);
		maze.getCell(randGen.nextInt(width), 4).setSouthWall(false);
		}
		return maze;
	}
	
	

	//6. Complete the selectNextPathMethod
	private static void selectNextPath(Cell currentCell) {
		//A. mark cell as visited
		currentCell.setBeenVisited(true);
		//B. check for unvisited neighbors using the cell
		ArrayList<Cell> neighbors = getUnvisitedNeighbors(currentCell);
		
		//C. if has unvisited neighbors,
	if(!neighbors.isEmpty()) {
		
			//C1. select one at random.
			Cell n = neighbors.get(randGen.nextInt(neighbors.size()));
			//C2. push it to the stack
			uncheckedCells.push(n);
			//C3. remove the wall between the two cells
				removeWalls(currentCell,n);
			//C4. make the new cell the current cell and mark it as visited
		    currentCell = n;
			currentCell.setBeenVisited(true);
			//C5. call the selectNextPath method with the current cell
			selectNextPath(currentCell);
	}
		else {
			//D1. if the stack is not empty
			if(uncheckedCells.isEmpty()!=true) {
				// D1a. pop a cell from the stack
				Cell poppedCell = uncheckedCells.pop();
				// D1b. make that the current cell
				currentCell = poppedCell;
				// D1c. call the selectNextPath method with the current cell
				selectNextPath(currentCell);
		
				}
			
			}
	
		
}
			
	
	//7. Complete the remove walls method.
	//   This method will check if c1 and c2 are adjacent.
	//   If they are, the walls between them are removed.
	private static void removeWalls(Cell c1, Cell c2) {
		if(c1.getX()-1==c2.getX()) {
			c1.setWestWall(false);
			c2.setEastWall(false);
		}
		 if(c1.getX()+1==c2.getX()) {
			c1.setEastWall(false);
			c2.setWestWall(false);
		}
		 if(c1.getY()-1==c2.getY()) {
			 c1.setNorthWall(false);
			 c2.setSouthWall(false);
		 }
		 if(c1.getY()+1==c2.getY()) {
			 c1.setSouthWall(false);
			 c2.setNorthWall(false);
		 }
	
		
	}
	
	//8. Complete the getUnvisitedNeighbors method
	//   Any unvisited neighbor of the passed in cell gets added
	//   to the ArrayList
	private static ArrayList<Cell> getUnvisitedNeighbors(Cell c) {
		ArrayList<Cell> unvisited = new ArrayList<Cell>();
		
			if(c.getX()-1>=0&&maze.getCell(c.getX()-1, c.getY()).hasBeenVisited()!=true) {
				unvisited.add(maze.getCell(c.getX()-1, c.getY()));
			}
		 
			if(c.getX()+1<width&&maze.getCell(c.getX()+1, c.getY()).hasBeenVisited()!=true) {
				unvisited.add(maze.getCell(c.getX()+1, c.getY()));
			}
			if(c.getY()-1>=0&&maze.getCell(c.getX(), c.getY()-1).hasBeenVisited()!=true) {
				unvisited.add(maze.getCell(c.getX(), c.getY()-1));
			
		 }
			if(c.getY()+1<height&&maze.getCell(c.getX(), c.getY()+1).hasBeenVisited()!=true) {
				unvisited.add(maze.getCell(c.getX(), c.getY()+1));
			}
	
		return unvisited;
	}
}
