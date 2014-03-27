package ist.mei.pa.command.parser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import ist.mei.pa.Inspector;
import ist.mei.pa.command.Command;
import ist.mei.pa.command.SaveLastResultCommand;

public class SaveLastResultParser extends CommandParser {

	private static final Pattern _pattern = Pattern.compile("s[\\s]+("+IDENTIFIER+")[\\s]*");
	
	public SaveLastResultParser(Inspector inspector) {
		super(inspector);
	}

	@Override
	public Command parseCommand(String line) {
		Matcher matcher = _pattern.matcher(line);
		if(!matcher.matches())
			return null;
		String saveName = matcher.group(1);
		return new SaveLastResultCommand(_inspector, saveName);
	}

}
