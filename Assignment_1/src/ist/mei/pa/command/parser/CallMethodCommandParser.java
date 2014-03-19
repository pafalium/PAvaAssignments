package ist.mei.pa.command.parser;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import ist.mei.pa.Inspector;
import ist.mei.pa.command.CallMethodCommand;
import ist.mei.pa.command.Command;

public class CallMethodCommandParser extends CommandParser {

	private static Pattern _pattern = Pattern.compile("c[\\s]+("+IDENTIFIER+")(([\\s]+"+ValueParser.VALUE+")*)");
	
	public CallMethodCommandParser(Inspector inspector) {
		super(inspector);
	}

	@Override
	public Command parseCommand(String line) {
		Matcher matcher = _pattern.matcher(line);
		if(!matcher.matches())
			return null;
		String methodName = matcher.group(1);
		String valuesText = matcher.group(2);
		Object[] methodParams = parseParams(valuesText);
		return new CallMethodCommand(_inspector, methodName, methodParams);
	}

	private Object[] parseParams(String valuesText) {
		String[] possibleValuesText = valuesText.split("[\\s]+");
		ArrayList<Object> params = new ArrayList<Object>();
		ValueParser parser = new ValueParser();
		for (String possibleValue : possibleValuesText) {
			if (possibleValue.equals(""))
				continue;
			Object value = parser.parseValue(possibleValue);
			params.add(value);
		}
		return params.toArray();
	}

}
