package ist.meic.pa.entries;

public class ReturnEntry extends TraceEntry {

	public ReturnEntry(String sig, String file, int linenum) {
		super(sig,file,linenum);
	}
	
	@Override
	public String getOutput() {
		return "  <- " + signature + "on " + filename + ":" + linenum;
	}

}
