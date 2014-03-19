package ist.mei.pa.command.parser;

import ist.mei.pa.Inspector;
import ist.mei.pa.command.Command;

public abstract class CommandParser {
	protected Inspector _inspector;
	
	protected static final String ID_START = "[a-zA-Z_]";
	protected static final String IDENTIFIER = ID_START+"[\\w]*";
	
	public CommandParser(Inspector inspector) {
		_inspector = inspector;
	}
	
	public abstract Command parseCommand(String line);
}
