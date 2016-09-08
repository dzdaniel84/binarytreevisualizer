import java.util.ArrayList;

import processing.core.*;

public class Grid extends PApplet{
	int grey = color(150, 150, 150);
    int red = color(255, 0, 0);
    int green = color(0, 255, 0);
    int white = color(255, 255, 255);
    int blue = color(0, 0, 255);
    
	final int START = 1;
	final int END = 2;
	final int WALL = -1;
	final int VISITED = 10;
	final int gridwidth = 15;
	
	PApplet p;
	int rows;
	int cols;
	int[][] grid;
	
	Location start = new Location(0, 0);
	Location end = new Location(1, 1);
	
	int sx = 0, sy = 0, ex = 1, ey = 1;
	
	public Grid(PApplet p, int cols, int rows){
		this.p = p;
		this.rows = rows;
		this.cols = cols;
		grid = new int[rows][cols];
	}
	
	public void setStart(int x, int y){
		start = new Location(x , y);
		grid[x][y] = START;
		grid[sx][sy] = 0;
		sx = x;
		sy = y;
	}
	
	public Location getStart(){
		return new Location(sx, sy);
	}
	
	public void setEnd(int x, int y){
		end = new Location(x, y);
		grid[x][y] = END;
		grid[ex][ey] = 0;
		ex = x;
		ey = y;
	}
	
	public Location getEnd(){
		return new Location(ex, ey);
	}
	
	public void setWall(int x, int y){
		grid[x][y] = WALL;
	}
	
	public void setVisited(int x, int y){
		//System.out.println("(" + x + "," + y + ") has been set to visited. ");
		grid[x][y] = VISITED;
		
		p.fill(blue);
        p.rect(x*gridwidth, y*gridwidth, gridwidth, gridwidth);
	}
	
	public ArrayList<Location> getEmptyNeighbors(Location loc){
		ArrayList<Location> a = new ArrayList<Location>();
		/*NW*/ 
		if (loc.x > 0 && loc.y > 0){
			if (grid[loc.x-1][loc.y-1] == 0 || grid[loc.x-1][loc.y-1] == END){
				a.add(new Location(loc.x-1, loc.y-1));
			}
		}
		/*SE*/ 
		if (loc.x < rows-1 && loc.y < cols-1){
			if (grid[loc.x+1][loc.y+1] == 0 || grid[loc.x+1][loc.y+1] == END){
				a.add(new Location(loc.x+1, loc.y+1));
			}
		}
		/*SW*/ 
		if (loc.x > 0 && loc.y < cols-1){
			if (grid[loc.x-1][loc.y+1] == 0 || grid[loc.x-1][loc.y+1] == END){
				a.add(new Location(loc.x-1, loc.y+1));
			}
		}
		/*NE*/ 
		if (loc.x > rows-1 && loc.y > 0){
			if (grid[loc.x+1][loc.y-1] == 0 || grid[loc.x+1][loc.y-1] == END){
				a.add(new Location(loc.x+1, loc.y-1));
			}
		}
		/*E*/ 
		if (loc.x > 0){
			if (grid[loc.x-1][loc.y] == 0 || grid[loc.x-1][loc.y] == END){
				a.add(new Location(loc.x-1, loc.y));
			}
		}
		/*W*/ 
		if (loc.x < rows-1){
			if (grid[loc.x+1][loc.y] == 0 || grid[loc.x+1][loc.y] == END){
				a.add(new Location(loc.x+1, loc.y));
			}
		}
		/*N*/ 
		if (loc.y > 0){
			if (grid[loc.x][loc.y-1] == 0 || grid[loc.x][loc.y-1] == END){
				a.add(new Location(loc.x, loc.y-1));
			}
		}
		/*S*/
		if (loc.y < cols-1){
			if (grid[loc.x][loc.y+1] == 0 || grid[loc.x][loc.y+1] == END){
				a.add(new Location(loc.x, loc.y+1));
			}
		}
		return a;
	}
	
	public ArrayList<Location> getValidNeighbors(Location loc){
		ArrayList<Location> a = new ArrayList<Location>();
		/*NW*/
		if (loc.x > 0 && loc.y > 0){
			if (grid[loc.x-1][loc.y-1] == WALL || grid[loc.x-1][loc.y-1] == VISITED){
				a.add(new Location(loc.x-1, loc.y-1));
			}
		}
		/*SE*/ 
		if (loc.x < rows-1 && loc.y < cols-1){
			if (grid[loc.x+1][loc.y+1] == WALL || grid[loc.x+1][loc.y+1] == VISITED){
				a.add(new Location(loc.x+1, loc.y+1));
			}
		}
		/*SW*/ 
		if (loc.x > 0 && loc.y < cols-1){
			if (grid[loc.x-1][loc.y+1] == WALL || grid[loc.x-1][loc.y+1] == VISITED){
				a.add(new Location(loc.x-1, loc.y+1));
			}
		}
		/*NE*/ 
		if (loc.x > rows-1 && loc.y > 0){
			if (grid[loc.x+1][loc.y-1] == WALL || grid[loc.x+1][loc.y-1] == VISITED){
				a.add(new Location(loc.x+1, loc.y-1));
			}
		}
		/*E*/ 
		if (loc.x > 0){
			if (grid[loc.x-1][loc.y] == WALL || grid[loc.x-1][loc.y] == VISITED){
				a.add(new Location(loc.x-1, loc.y));
			}
		}
		/*W*/ 
		if (loc.x < rows-1){
			if (grid[loc.x+1][loc.y] == WALL || grid[loc.x+1][loc.y] == VISITED){
				a.add(new Location(loc.x+1, loc.y));
			}
		}
		/*N*/ 
		if (loc.y > 0){
			if (grid[loc.x][loc.y-1] == WALL || grid[loc.x][loc.y-1] == VISITED){
				a.add(new Location(loc.x, loc.y-1));
			}
		}
		/*S*/ 
		if (loc.y < cols-1){
			if (grid[loc.x][loc.y+1] == WALL || grid[loc.x][loc.y+1] == VISITED){
				a.add(new Location(loc.x, loc.y+1));
			}
		}
		return a;
	}
	
}
