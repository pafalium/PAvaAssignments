package ist.mei.pa.command.parser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import ist.mei.pa.Inspector;
import ist.mei.pa.command.Command;
import ist.mei.pa.command.InspectCommand;

public class InspectCommandParser extends CommandParser {

	public InspectCommandParser(Inspector inspector) {
		super(inspector);
	}

	private static Pattern _pattern = Pattern.compile("i[\\s]+("+IDENTIFIER+")");
	
	@Override
	public Command parseCommand(String line) {
		Matcher matcher = _pattern.matcher(line);
		if (!matcher.matches())
			return null;
		String fieldName = matcher.group(1);
		return new InspectCommand(_inspector, fieldName);
	}

}
