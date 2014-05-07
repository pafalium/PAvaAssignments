package test;

class Test3Aux {
	int getInt() {
		return 1000;
	}
	
	Integer getInteger() {
		return 2000;
	}
	
	int toInt(Integer i) {
		return i.intValue();
	}
	
	Integer toInteger(int i) {
		return i;
	}
	
	void test() {
		getInt();
		
		getInteger();
		
		toInteger(3000);
		
		toInt(new Integer(4000));
	}
}

public class Test3 {
	public static void main(String args[]) {
        (new Test3Aux()).test();
    }
}
