package test;

import ist.meic.pa.TraceVM;

public class TracedTests {
	public static void main(String[] args) throws Throwable {
		for (int i = 0; i < 5; i++) {
			System.out.println("Tracing test"+i);
			TraceVM.main(new String[] { "test.Test"+i });
		}
		System.out.println("DONE!");
	}
}
