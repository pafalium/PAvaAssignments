package ist.mei.pa.command.parser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

class ValueParser {
	protected static final String INTEGER ="\\-?[\\d]+"; 
	protected static final String VALUE = INTEGER;
	private static Pattern _integerPattern = Pattern.compile(INTEGER);
	
	public ValueParseResult parseValue(String newValueText) {
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
	
	protected class ValueParseResult {
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
