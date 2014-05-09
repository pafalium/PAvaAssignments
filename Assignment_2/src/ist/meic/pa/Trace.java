package ist.meic.pa;

import ist.meic.pa.entries.PassEntry;
import ist.meic.pa.entries.ReturnEntry;
import ist.meic.pa.entries.TraceEntry;

import java.util.ArrayList;
import java.util.IdentityHashMap;
import java.util.Map;

/**
 * This class is responsible for printing an Object's trace history that was
 * collected until the call of the Trace.print method.
 * 
 * @author Pedro-170
 * 
 */
public class Trace {
	/**
	 * Map containing all tracing history for every object.
	 */
	public static Map<Object, ArrayList<TraceEntry>> traceHistory = new IdentityHashMap<Object, ArrayList<TraceEntry>>();

	public static void print(Object obj) {
		ArrayList<TraceEntry> objHistory = traceHistory.get(obj);
		if (objHistory == null)
			System.err.println("Tracing for " + obj + " is nonexistent!");
		else {
			System.err.println("Tracing for " + obj);
			for (TraceEntry entry : objHistory) {
				System.err.println(entry.getOutput());
			}
		}
	}

	public static void traceArguments(Object[] args, String sig, String file,
			int line) {
		for (int i = 0; i < args.length; i++) {
			if (!traceHistory.containsKey(args[i])) {
				traceHistory.put(args[i], new ArrayList<TraceEntry>());
			}
			PassEntry entr = new PassEntry(sig, file, line);
			traceHistory.get(args[i]).add(entr);
		}
	}

	public static void traceReturn(Object ret, String sig, String file, int line) {
		if (!traceHistory.containsKey(ret)) {
			traceHistory.put(ret, new ArrayList<TraceEntry>());
		}
		ReturnEntry entr = new ReturnEntry(sig, file, line);
		traceHistory.get(ret).add(entr);
	}
}
