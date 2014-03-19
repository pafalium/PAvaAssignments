package ist.mei.pa;

import ist.mei.pa.command.Command;
import ist.mei.pa.command.parser.CommandParser;
import ist.mei.pa.command.parser.InspectCommandParser;
import ist.mei.pa.command.parser.QuitCommandParser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class Inspector {
	/** Object currently being inspected.*/
	private Object _current;
	/** Input stream reader for this Inspector.*/
	BufferedReader _in = new BufferedReader(new InputStreamReader(System.in));
	/** Parser for all the Commands accepted by this Inspector.*/
	ArrayList<CommandParser> _parsers;
	
	public Inspector() {
		_parsers = new ArrayList<CommandParser>();
		_parsers.add(new QuitCommandParser(this));
		_parsers.add(new InspectCommandParser(this));
	}
	
	
	public void inspect(Object o) throws Exception {
		setCurrent(o);
		
		while (_current != null) {
			String line = promptUser("> ");
			
			Command com = parseCommand(line);
			if(com.canExecute()) {
				com.execute();
			}
		}
	}

	private Command parseCommand(String line) {
		Command parsedCommand = null;
		for (CommandParser parser : _parsers) {
			parsedCommand = parser.parseCommand(line);
			if(parsedCommand != null)
				break;
		}
		return parsedCommand;
	}

	//TODO print object
	public void setCurrent(Object newCurrent) {
		printObject(newCurrent);
		if (newCurrent.getClass().isPrimitive()) {
			return;
		}
		_current = newCurrent;
	}

	public void removeCurrent() {
		_current = null;
	}

	public Object getCurrent() {
		return _current;
	}
	
	private String promptUser(String msg) throws IOException {
		System.err.println(msg);
		String line = _in.readLine();
		return line;
	}
}
