package ist.meic.pa.entries;

public class CastEntry extends TraceEntry {

	private String fromClass;
	private String toClass;
	
	public CastEntry(String from, String to, String fileName, int lineno) {
		super (fileName,lineno);
		fromClass = from;
		toClass = to;
	}
	
	@Override
	public String getOutput() {

		return " CAST: ("+toClass+") "+fromClass+ " on "+filename+":"+linenum;
	}

}
