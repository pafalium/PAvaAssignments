package ist.mei.pa.command.parser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

class ValueParser {
	protected static final String INTEGER ="\\-?[\\d]+";
	protected static final String STRING = "\"[^\"]*\"";
	protected static final String VALUE = INTEGER + "|" + STRING;
	private static Pattern _integerPattern = Pattern.compile(INTEGER);
	private static Pattern _stringPattern = Pattern.compile(STRING);
	
	public ValueParseResult parseValue(String newValueText) {
		Matcher matcher;
		Object result = null;
		boolean success = false;
		
		matcher = _integerPattern.matcher(newValueText);
		if (matcher.matches()) {
			result = Integer.parseInt(newValueText);
			success = true;
		}
		matcher = _stringPattern.matcher(newValueText);
		if (matcher.matches()) {
			result = newValueText.substring(1,newValueText.length()-1);
			success = true;
		}
		return new ValueParseResult(success, result);
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
