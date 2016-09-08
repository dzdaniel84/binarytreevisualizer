
public class test {
	
	public static void main(String[] args){
		r(9);
	}
	
	public static void r(int a){
		System.out.println(a);
		if (a != 4) r(2*a - 4);
	}

}
