package ist.mei.pa.command.parser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import ist.mei.pa.Inspector;
import ist.mei.pa.command.Command;
import ist.mei.pa.command.ModifyFieldCommand;

public class ModifyFieldCommandParser extends CommandParser {

	
	private static Pattern _pattern = Pattern.compile("m[\\s]+("+IDENTIFIER+")[\\s]+("+ValueParser.VALUE+")[\\s]*");
	
	
	public ModifyFieldCommandParser(Inspector inspector) {
		super(inspector);
	}

	@Override
	public Command parseCommand(String line) {
		Matcher matcher = _pattern.matcher(line);
		if(!matcher.matches())
			return null;
		String fieldName = matcher.group(1);
		String newValueText = matcher.group(2);
		ValueParser parser = new ValueParser(_inspector);
		ValueParser.ValueParseResult res = parser.parseValue(newValueText);
		ModifyFieldCommand com = null;
		if (res.wasSuccessful()) {
			com = new ModifyFieldCommand(_inspector, fieldName, res.value());
		}
		return com;
	}
}
