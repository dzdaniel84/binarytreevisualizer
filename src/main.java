             import java.util.*;
import processing.core.*;
import java.io.*;

@SuppressWarnings("serial")
public class main extends PApplet{
	int grey = color(150, 150, 150);
    int red = color(255, 0, 0);
    int green = color(0, 255, 0);
    int blue = color(0, 0, 255);
    
	final int START = 1;
	final int END = 2;
	final int WALL = -1;
	final int VISITED = 10;
	
	static final int x = 0;
	static final int y = 1;
	
	static final int width = 1000;
    static final int height = 800;
    static final int gridwidth = 15;
    
    boolean showLineGrid = true;
    
    Grid g = new Grid(this, 600/gridwidth, 600/gridwidth);
    
    static Location[] walls;
    
    boolean greenMode = false; 
    boolean redMode = false;
    
    boolean BFS = false;
    boolean DFS = false;
	
    int oldmouseX = 0;
    int oldmouseY = 0;
    
    ArrayDeque<Location> Horizon = new ArrayDeque<Location>();
    boolean doFirst = true;
    Location c;
    
    PFont font;
    
    boolean saveOn = false;
    boolean finishOn = false;
	public void setup(){
		size(width, height);
        
		try {importFile("Walls.txt");}
		catch (FileNotFoundException e) {System.err.println("File Not Found");}
		
		g.setStart(0, 6);
		g.setEnd(36, 6);
		   
        frameRate(25);

        font = createFont("Ariel", 16, true);
	}
	
	public void draw(){
		background(255);
		
		textAlign(LEFT);
		textFont(font);
		fill(red);
		textSize(32);
		text("Pathfinding Simulator", 630, 50); 
		text("v0.5a", 630, 90);
		
		fill(blue);
		textSize(16);
		text("Key Commands-\nq: Quit\nr: Reset\nc: Clear Walls\na: Add Random Walls\nb: BFS\nd: DFS\nt: Toggle Grid\ns: Save", 630, 150);
		text("Block Types-\nGreen: Begin\nRed: End\nGrey: Wall\nBlue: Visited", 630, 400);
		if (BFS) text("Current Mode: BFS", 630, 550);
		if (DFS) text("Current Mode: DFS", 630, 550);
		if (!BFS && !DFS) text("Current Mode: None", 630, 550);
		textSize(20);
		text("Directions: Drag around start/end positions. Click/Drag to add boxes.\nThis simulates Breadth-First Search (BFS) and Depth-First Search (DFS).", 5, 650);
		
		fill(0);
		textSize(12);
		text("Made by Daniel Zhang. 2014", 5, 750);
		
		line(0, 600, 600, 600);
		line(600, 0, 600, 600);
		
		if(showLineGrid){
        	for (int X = 0; X <= 600; X+=gridwidth){line(0, X, 600, X);}
        	for (int Y = 0; Y <= 600; Y += gridwidth){line(Y, 0, Y, 600);}
        }
		
		GridLoop();
		
		if (BFS == true){
			
			if(doFirst){
				Horizon.add(g.getStart());
				doFirst = false;
			}
			
			do{
				c = Horizon.removeFirst();
			} while(g.grid[c.x][c.y] == VISITED);
			
			if (c.x == g.getEnd().x && c.y == g.getEnd().y){
				fillSquare(255, c.x, c.y);
				finishOn = true;
				BFS = false;
				reset();
			} else {
				ArrayList<Location> a = g.getEmptyNeighbors(c);
				
				g.setVisited(c.x, c.y);
				fillSquare(255, c.x, c.y);
				
				String ab = "";
				
				for (int i = 0; i < a.size(); i++){
					Horizon.add(a.get(i));
					ab = ab + a.get(i).x + "," + a.get(i).y + " " + g.grid[a.get(i).x][a.get(i).y] + "\n";
				}
				
				//JOptionPane.showMessageDialog(null, "Current: " + c.x + "," + c.y + "\n" + ab);
			}
		}
		
		if (DFS == true){
			if(doFirst){
				Horizon.add(g.getStart());
				doFirst = false;
			}
			
			do{
				c = Horizon.removeFirst();
			} while(g.grid[c.x][c.y] == VISITED);
			
			if (c.x == g.getEnd().x && c.y == g.getEnd().y){
				fillSquare(255, c.x, c.y);
				finishOn = true;
				DFS = false;
				reset();
			} else {
				ArrayList<Location> a = g.getEmptyNeighbors(c);
				
				g.setVisited(c.x, c.y);
				fillSquare(255, c.x, c.y);
				
				String ab = "";
				
				for (int i = 0; i < a.size(); i++){
					Horizon.addFirst(a.get(i));
					ab = ab + a.get(i).x + "," + a.get(i).y + " " + g.grid[a.get(i).x][a.get(i).y] + "\n";
				}
				
				//JOptionPane.showMessageDialog(null, "Current: " + c.x + "," + c.y + "\n" + ab);
			}
			
		}
		
		if (finishOn){
			fill(255);
			rect(250, 250, 500, 300);
			fill(0);
			textSize(32);
			textAlign(CENTER);
			text("Finished", 500, 400);
		}
		
		if (saveOn){
			fill(255);
			rect(250, 250, 500, 300);
			fill(0);
			textSize(32);
			textAlign(CENTER);
			text("Saved", 500, 400);
		}
	}
	
	public void mouseClicked(){
		if (mouseX > 0 && mouseX < 600 && mouseY > 0 && mouseY < 600){
			if(mouseX < 600 && mouseY < 600 && g.grid[mouseX/gridwidth][mouseY/gridwidth] == 0){
				g.grid[mouseX/gridwidth][mouseY/gridwidth] = WALL;
			} else if (mouseX < 600 && mouseY < 600 && g.grid[mouseX/gridwidth][mouseY/gridwidth] == WALL){
				g.grid[mouseX/gridwidth][mouseY/gridwidth] = 0;
			}
		}
		
		saveOn = false;
		finishOn = false;
	}
	
	public void mouseDragged(){
		//onscreen
		if (mouseX > 0 && mouseX < 600 && mouseY > 0 && mouseY < 600){
			if(g.grid[mouseX/gridwidth][mouseY/gridwidth] == START) greenMode = true;
			if(g.grid[mouseX/gridwidth][mouseY/gridwidth] == END) redMode = true;
			
			if(!greenMode && !redMode && notOnSameSquare()){
				if(mouseX < 600 && mouseY < 600 && g.grid[mouseX/gridwidth][mouseY/gridwidth] == 0){
					g.grid[mouseX/gridwidth][mouseY/gridwidth] = WALL;
				} else if (mouseX < 600 && mouseY < 600 && g.grid[mouseX/gridwidth][mouseY/gridwidth] == WALL){
					g.grid[mouseX/gridwidth][mouseY/gridwidth] = 0;
				}
			}
			
			if(greenMode){
				if(g.grid[mouseX/gridwidth][mouseY/gridwidth] == 0 || g.grid[mouseX/gridwidth][mouseY/gridwidth] == WALL) g.setStart(mouseX/gridwidth, mouseY/gridwidth);
			}
			
			if(redMode){
				if(g.grid[mouseX/gridwidth][mouseY/gridwidth] == 0 || g.grid[mouseX/gridwidth][mouseY/gridwidth] == WALL) g.setEnd(mouseX/gridwidth, mouseY/gridwidth);
			}
		}
		
		oldmouseX = mouseX;
		oldmouseY = mouseY;
	}
	
	public void mouseReleased(){
		greenMode = false;
		redMode = false;
	}
	
	public void keyPressed(){
		if (key == 'b'){
			if (DFS == false) BFS = !BFS;
			if (DFS == true) BFS = false;
		}
		if (key == 'd'){
			if (BFS == true) DFS = false;
			if (BFS == false) DFS = !DFS;
		}
		if (key == 't') showLineGrid = !showLineGrid;
		if (key == 'q') System.exit(0);
		if (key == 'r') reset();
		if (key == 'a') randomize();
		if (key == 'c') clear();
		if (key == 's'){
			try {saveMap();}
			catch (FileNotFoundException e) {System.err.println("FILE NOT FOUND.");}
		}
	}
	
	public void saveMap() throws FileNotFoundException{
		PrintWriter out = new PrintWriter(new File("Walls.txt"));
		
		int counter = 0;
		for (int X = 0; X < g.grid.length; X++){
			for (int Y = 0; Y < g.grid[0].length; Y++){
				if (g.grid[X][Y] == WALL) counter++;
			}
		}
		
		out.println(counter);
		
		for (int X = 0; X < g.grid.length; X++){
			for (int Y = 0; Y < g.grid[0].length; Y++){
				if (g.grid[X][Y] == WALL) out.println(X + " " + Y);
			}
		}
		
		out.close();
		
		saveOn = true;
	}
	
	public void randomize(){
		Random gen = new Random();
		
		for (int i = 0; i < 10; i++){
			int a = gen.nextInt(40);
			int b = gen.nextInt(40);
			if (g.grid[a][b] == 0) g.grid[a][b] = WALL;
		}
	}
	
	public void clear(){
		for (int X = 0; X < g.grid.length; X++){
			for (int Y = 0; Y < g.grid[0].length; Y++){
				if (g.grid[X][Y] == WALL) g.grid[X][Y] = 0;
			}
		}
	}
	
	public boolean notOnSameSquare(){
		//System.out.println(mouseX + " " + oldmouseX + ":" + mouseY + " " + oldmouseY);
		if((mouseX/gridwidth - oldmouseX/gridwidth == 0) && (mouseY/gridwidth - oldmouseY/gridwidth == 0)) return false;
		return true;
	}
	
	public void GridLoop(){
		for (int X = 0; X < g.grid.length; X++){
			for (int Y = 0; Y < g.grid[0].length; Y++){
				if (g.grid[X][Y] == START) fillSquare(green, X, Y);
				else if (g.grid[X][Y] == END) fillSquare(red, X, Y);
				else if (g.grid[X][Y] == WALL) fillSquare(grey, X, Y);
				if (g.grid[X][Y] == VISITED) fillSquare(blue, X, Y);
			}
		}
	}
	
	public void reset(){
		for (int X = 0; X < g.grid.length; X++){
			for (int Y = 0; Y < g.grid[0].length; Y++){
				if (g.grid[X][Y] == VISITED) g.grid[X][Y] = 0;
			}
		}
		
		BFS = false;
		DFS = false;
		
		Horizon.clear();
		
		Random gen = new Random();
		g.setStart(gen.nextInt(40), gen.nextInt(40));
		//Horizon.add(g.getStart());
		doFirst = true;
	}
	
	public void fillSquare(int color, int x, int y){
		fill(color);
        rect(x*gridwidth, y*gridwidth, gridwidth, gridwidth);
	}
	
	@SuppressWarnings("resource")
	public void importFile(String filename) throws FileNotFoundException {
		Scanner scanner = new Scanner(new FileReader(filename));
		
		walls = new Location[scanner.nextInt()];
		
		while (scanner.hasNextInt()) {
			g.setWall(scanner.nextInt() ,scanner.nextInt());
		}
	}
	
	public static void main(String[] args){
		PApplet.main(new String[] {"main"});
	}

}
