package ist.meic.pa.entries;

public class PassEntry extends TraceEntry {

	public PassEntry(String sig, String file, int linenum) {
		super(sig,file,linenum);
	}
	
	@Override
	public String getOutput() {
		return "  -> " + signature + "on " + filename + ":" + linenum;
	}

}
