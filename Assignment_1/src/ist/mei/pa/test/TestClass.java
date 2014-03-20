package ist.mei.pa.test;

import java.lang.reflect.Method;

import ist.mei.pa.Inspector;

public class TestClass {
	public static void main(String[] args) throws Exception {
		Inspector inspector = new Inspector();
		TestClass obj = new TestClass();
		inspector.inspect(obj);
		System.out.println(int.class.isAssignableFrom(Integer.class));
		System.out.println(Integer.class.isAssignableFrom(int.class));
		System.out.println(int.class.isAssignableFrom(int.class));
		System.out.println(Integer.TYPE);
		Method m = obj.getClass().getDeclaredMethod("giveInt",int.class);
		m.setAccessible(true);
		m.invoke(obj, new Integer(10));
	}
	
	private String _private = "Tests inited string";
	public String _public;
	protected String _protected;
	String _package;
	
	private Integer _integer = 10;
	private int _int;
	
	private void meth() {
		System.out.println("O METH FOI CHAMADO!!!");
	}
	
	private void giveInt(int i) {
		System.out.println("Given int = "+i);
	}
	private void giveInt(Integer i) {
		System.out.println("Given Integer = "+i);
	}
	
	private Object getOther() {
		return new TestClass();
	}
}
