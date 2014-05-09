package test;

public class Test4 {
	Object o = new Object();
	int i = m(o);
	
	int m(Object obj) {
		return obj.hashCode();
	}
	
	public static void main(String[] args) {
		System.out.println("Test dummy");
		new Test4();
	}
}
