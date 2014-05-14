package test;

import ist.meic.pa.Trace;

class A1 {};

class B1 extends A1 {};


public class Test5 {
	
	void test() {
		A1 instance = new B1();
		
		Trace.print(instance);
		B1 obj = (B1) instance;
		obj.getClass();
		Trace.print(instance);
	}
	
	
	public static void main(String[] args) {
		System.out.println("Test dummy");
		(new Test5()).test();
	}
}
