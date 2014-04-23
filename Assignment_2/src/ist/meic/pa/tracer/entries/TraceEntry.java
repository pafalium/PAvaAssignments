package ist.meic.pa.tracer.entries;

/**
 * A {@link TraceEntry} is to be used as a means to store
 * the access history of an object (where it was passed
 * as an argument and where it was returned).
 * @author Pedro-170
 */
public abstract interface TraceEntry {
	/**
	 * 
	 * @return a {@link String} representing the entry.
	 */
	public String getOutput();
}
