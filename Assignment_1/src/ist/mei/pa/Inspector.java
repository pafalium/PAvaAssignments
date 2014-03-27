package ist.mei.pa;

import ist.mei.pa.command.Command;
import ist.mei.pa.command.SharedThings;
import ist.mei.pa.command.parser.CallMethodCommandParser;
import ist.mei.pa.command.parser.CommandParser;
import ist.mei.pa.command.parser.InspectCommandParser;
import ist.mei.pa.command.parser.InspectPreviousCommandParser;
import ist.mei.pa.command.parser.ModifyFieldCommandParser;
import ist.mei.pa.command.parser.QuitCommandParser;
import ist.mei.pa.command.parser.SaveLastResultParser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Inspector {
	/** Object currently being inspected.*/
	private Object _current;
	/** The last command result if any. 
	 * (opposed to _current also stores primitive wrapper types)*/
	private Object _lastResult;
	/**	Saved results. */
	private Map<String, Object> _savedResults;
	/** Previously inspected objects in order.*/
	private ArrayList<Object> _inspectionStack;
	/** Input stream reader for this Inspector.*/
	private BufferedReader _in = new BufferedReader(new InputStreamReader(System.in));
	/** Parser for all the Commands accepted by this Inspector.*/
	private ArrayList<CommandParser> _parsers;
	
	public Inspector() {
		_savedResults = new HashMap<String, Object>();
		_inspectionStack = new ArrayList<Object>();
		_parsers = new ArrayList<CommandParser>();
		_parsers.add(new QuitCommandParser(this));
		_parsers.add(new InspectCommandParser(this));
		_parsers.add(new ModifyFieldCommandParser(this));
		_parsers.add(new CallMethodCommandParser(this));
		_parsers.add(new SaveLastResultParser(this));
		_parsers.add(new InspectPreviousCommandParser(this));
	}
	
	
	public void inspect(Object o) throws Exception {
		setCurrent(o);
		
		while (_current != null) {
			String line = promptUser("> ");
			
			boolean commandFound = false;
			Command com = parseCommand(line);
			if(com != null && com.canExecute()) {
				commandFound = true;
				com.execute();
			}
			if (!commandFound)
				System.err.println("No command was executed.");
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

	public void setCurrent(Object newCurrent) {
		_lastResult = newCurrent;
		printObject(newCurrent);
		if (newCurrent==null||newCurrent.getClass().isPrimitive()
				||SharedThings.isWrapper(newCurrent.getClass())) {
			return;
		}
		if (_current != null) {
			_inspectionStack.add(_current);
		}
		_current = newCurrent;
	}
	
	public void setPreviousCurrent() {
		if(_inspectionStack.size() < 1)
			return;
		Object last = _inspectionStack.get(_inspectionStack.size()-1);
		_inspectionStack.remove(_inspectionStack.size()-1);
		_current = last;
	}
	
	public int inspectionStackSize() {
		return _inspectionStack.size();
	}

	private void printObject(Object newCurrent) {
		if (newCurrent == null) {
			System.err.println("null");
			return;
		}
		if (SharedThings.isWrapper(newCurrent.getClass())) {
			System.err.println(newCurrent);
			return;
		}
	    int i,j;
	    Field[] fields = newCurrent.getClass().getDeclaredFields();
	    Method[] methods = newCurrent.getClass().getDeclaredMethods();
	    
	    System.err.println(newCurrent + " is an instace of class" + newCurrent.getClass().getName());
        System.err.println("---------------------");
        System.err.println("Fields:");
        for(i=0;i<fields.length;i++){
            try {   //TO DO declaration type of each field (private, protected, ...)
                fields[i].setAccessible(true);
            	System.err.println(fields[i].getGenericType() +" "+ fields[i].getName() + " = " + fields[i].get(newCurrent));
            } catch (IllegalArgumentException e) {
                throw new RuntimeException(e);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }
        System.err.println("---------------------");
        System.err.println("Methods:");
        for(i=0;i<methods.length;i++){
            methods[i].setAccessible(true);
        	System.err.println("Name: " + methods[i].getName() + "\n\treturn type: " + methods[i].getReturnType());
            for(j=0;j<methods[i].getParameterTypes().length;j++)
                System.err.println("\tparameter" + j + " type: " + methods[i].getParameterTypes()[j].getName());
        }
    }

	public void printCurrent() {
		printObject(_current);
	}
	
	public void printLine(String line) {
		System.err.println(line);
	}

    public void removeCurrent() {
		_current = null;
	}

	public Object getCurrent() {
		return _current;
	}
	
	public Object getLastResult() {
		return _lastResult;
	}
	
	public void saveLastResult(String name) {
		_savedResults.put(name, _lastResult);
	}
	
	public Object getSavedResult(String name) {
		return _savedResults.get(name);
	}
	
	public boolean hasSavedResult(String name) {
		return _savedResults.containsKey(name);
	}
	
	private String promptUser(String msg) throws IOException {
		System.err.print(msg);
		String line = _in.readLine();
		return line;
	}
}
