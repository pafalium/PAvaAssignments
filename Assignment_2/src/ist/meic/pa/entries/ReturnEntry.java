package ist.meic.pa.entries;

public class ReturnEntry extends TraceEntry {

	protected String signature;
	
	public ReturnEntry(String sig, String file, int linenum) {
		super(file,linenum);
		signature = sig;
	}
	
	@Override
	public String getOutput() {
		return "  <- " + signature + " on " + filename + ":" + linenum;
	}

}
