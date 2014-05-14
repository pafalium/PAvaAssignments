package ist.meic.pa.entries;

public class PassEntry extends TraceEntry {

	protected String signature;
	
	public PassEntry(String sig, String file, int linenum) {
		super(file,linenum);
		signature = sig;
	}
	
	@Override
	public String getOutput() {
		return "  -> " + signature + " on " + filename + ":" + linenum;
	}

}
