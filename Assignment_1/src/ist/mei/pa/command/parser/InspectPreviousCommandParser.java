package ist.mei.pa.command.parser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import ist.mei.pa.Inspector;
import ist.mei.pa.command.Command;
import ist.mei.pa.command.InspectPreviousCommand;

public class InspectPreviousCommandParser extends CommandParser {

	private static final Pattern _pattern = Pattern.compile("b([\\s]+("+ValueParser.INTEGER+"))?[\\s]*");
	
	public InspectPreviousCommandParser(Inspector inspector) {
		super(inspector);
	}

	@Override
	public Command parseCommand(String line) {
		Matcher matcher = _pattern.matcher(line);
		if(!matcher.matches())
			return null;
		String stepsText = matcher.group(2);
		int steps;
		if (stepsText == null || stepsText.equals(""))
			steps = 1;
		else
			steps = Integer.parseInt(stepsText);
		return new InspectPreviousCommand(_inspector, steps);
	}

}
