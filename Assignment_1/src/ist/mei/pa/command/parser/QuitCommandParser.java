package ist.mei.pa.command.parser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import ist.mei.pa.Inspector;
import ist.mei.pa.command.Command;
import ist.mei.pa.command.QuitCommand;

public class QuitCommandParser extends CommandParser {

	private static Pattern _pattern = Pattern.compile("q[\\s]*");
	
	public QuitCommandParser(Inspector inspector) {
		super(inspector);
	}

	@Override
	public Command parseCommand(String line) {
		Command com = null;
		Matcher matcher = _pattern.matcher(line);
		if (matcher.matches())
			com = new QuitCommand(_inspector);
		return com;
	}
}
