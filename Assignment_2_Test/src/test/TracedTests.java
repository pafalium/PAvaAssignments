package test;

import ist.meic.pa.TraceVM;
import ist.meic.pa.TraceVMExtended;

public class TracedTests {
	public static void main(String[] args) throws Throwable {
		//TraceVM.debug = true;
		for (int i = 0; i < 6; i++) {
			System.out.println("Tracing test"+i);
			TraceVM.main(new String[] { "test.Test"+i });
		}
		System.out.println("DONE!");
	}
}
