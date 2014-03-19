package ist.mei.pa.test;

import ist.mei.pa.Inspector;

public class TestClass {
	public static void main(String[] args) throws Exception {
		Inspector inspector = new Inspector();
		TestClass obj = new TestClass();
		inspector.inspect(obj);
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
	
	private void giveInt(Integer i) {
		System.out.println("Given int = "+i);
	}

}
