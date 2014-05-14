package ist.meic.pa.entries;

/**
 * A {@link TraceEntry} is to be used as a means to store
 * the access history of an object (where it was passed
 * as an argument and where it was returned).
 * @author Pedro-170
 */
public abstract class TraceEntry {
	
	protected int linenum;
	protected String filename;
	
	protected TraceEntry(String file, int linenum) {
		filename = file;
		this.linenum = linenum;
	}
	
	/**
	 * 
	 * @return a {@link String} representing the entry.
	 */
	public abstract String getOutput();
}
