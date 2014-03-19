package ist.mei.pa.command.parser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import ist.mei.pa.Inspector;
import ist.mei.pa.command.Command;
import ist.mei.pa.command.ModifyFieldCommand;

public class ModifyFieldParser extends CommandParser {

	private static final String INTEGER ="\\-?[\\d]+"; 
	private static final String VALUE = INTEGER;
	private static Pattern _pattern = Pattern.compile("m[\\s]+("+IDENTIFIER+")[\\s]+("+VALUE+")");
	private static Pattern _integerPattern = Pattern.compile(INTEGER);
	
	public ModifyFieldParser(Inspector inspector) {
		super(inspector);
	}

	@Override
	public Command parseCommand(String line) {
		Matcher matcher = _pattern.matcher(line);
		if(!matcher.matches())
			return null;
		String fieldName = matcher.group(1);
		String newValueText = matcher.group(2);
		ValueParseResult res = parseValue(newValueText);
		ModifyFieldCommand com = null;
		if (res.wasSuccessful()) {
			com = new ModifyFieldCommand(_inspector, fieldName, res.value());
		}
		return com;
	}
	
	private ValueParseResult parseValue(String newValueText) {
		Matcher matcher;
		Object result;
		boolean success = false;
		
		matcher = _integerPattern.matcher(newValueText);
		if (matcher.matches()) {
			result = Integer.parseInt(newValueText);
			success = true;
			return new ValueParseResult(success, result);
		}
		return new ValueParseResult(false, null);
	}

	class ValueParseResult {
		private Object _value;
		private boolean _success;
		public ValueParseResult(boolean success, Object value) {
			_success = success;
			_value = value;
		}
		public Object value() {
			return _value;
		}
		public boolean wasSuccessful() {
			return _success;
		}
		
	}
}
