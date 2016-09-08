
public class Location {
	public int x;
	public int y;
	
	public Location(int x, int y){
		this.x = x;
		this.y = y;
	}
	
	public void setX(int x){this.x = x;}
	public void setY(int y){this.y = y;}
	
	public float getX(){return x;}
	public float getY(){return y;}
	
	public void subtractX(int a){x -= a; if (y%15 != 7.5) y-=y%15-7.5;} 
	public void subtractY(int a){y -= a; if (x%15 != 7.5) x-=x%15-7.5;}
	public void addX(int a){x += a; if (y%15 != 7.5) y-=y%15-7.5;}
	public void addY(int a){y += a; if (x%15 != 7.5) x-=x%15-7.5;}
}
