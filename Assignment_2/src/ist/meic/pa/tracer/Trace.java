package ist.meic.pa.tracer;

import ist.meic.pa.tracer.entries.TraceEntry;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * This class is responsible for printing an Object's trace history
 * that was collected until the call of the Trace.print method.
 * @author Pedro-170
 *
 */
public class Trace {
	/**
	 * Map containing all tracing history for every object.
	 * Should 
	 */
	public static Map<Object,ArrayList<TraceEntry>> traceHistory = new HashMap<Object,ArrayList<TraceEntry>>();
	
	public static void print(Object obj) {
		ArrayList<TraceEntry> objHistory = traceHistory.get(obj);
		if (objHistory == null)
			System.err.println("Tracing for "+obj+" is nonexistent!");
		else {
			System.err.println("Tracing for "+obj);
			for(TraceEntry entry : objHistory) {
				System.err.println(entry.getOutput());
			}
		}
	}
}
