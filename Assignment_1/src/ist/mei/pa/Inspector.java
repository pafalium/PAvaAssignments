package ist.mei.pa;

import ist.mei.pa.command.Command;
import ist.mei.pa.command.parser.CallMethodCommandParser;
import ist.mei.pa.command.parser.CommandParser;
import ist.mei.pa.command.parser.InspectCommandParser;
import ist.mei.pa.command.parser.ModifyFieldCommandParser;
import ist.mei.pa.command.parser.QuitCommandParser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;

public class Inspector {
	/** Object currently being inspected.*/
	private Object _current;
	/** Input stream reader for this Inspector.*/
	BufferedReader _in = new BufferedReader(new InputStreamReader(System.in));
	/** Parser for all the Commands accepted by this Inspector.*/
	ArrayList<CommandParser> _parsers;
	
	public Inspector() {
		_parsers = new ArrayList<CommandParser>();
		_parsers.add(new QuitCommandParser(this));
		_parsers.add(new InspectCommandParser(this));
		_parsers.add(new ModifyFieldCommandParser(this));
		_parsers.add(new CallMethodCommandParser(this));
	}
	
	
	public void inspect(Object o) throws Exception {
		setCurrent(o);
		
		while (_current != null) {
			String line = promptUser("> ");
			
			Command com = parseCommand(line);
			if(com.canExecute()) {
				com.execute();
			}
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
		printObject(newCurrent);
		if (newCurrent.getClass().isPrimitive()) {
			return;
		}
		_current = newCurrent;
	}

	private void printObject(Object newCurrent) {
	    int i,j;
	    Field[] fields = newCurrent.getClass().getDeclaredFields();
	    Method[] methods = newCurrent.getClass().getDeclaredMethods();
	    
	    System.out.println(newCurrent + "is an instace of class" + newCurrent.getClass().getName());
        System.out.println("---------------------");
        System.out.println("Fields:");
        for(i=0;i<fields.length;i++){
            System.out.println(fields[i].getGenericType() + fields[i].getName() + "=" + fields[i]);
        }
        System.out.println("---------------------");
        System.out.println("Methods:");
        for(i=0;i<methods.length;i++){
            System.out.printf("Name:" + methods[i].getName() + "return type:" + methods[i].getReturnType());
            for(j=0;j<methods[i].getParameterTypes().length;j++)
                System.out.println("parameter" + j + " type:" + methods[i].getParameterTypes()[j]);
        }
        System.out.printf(">");
    }


    public void removeCurrent() {
		_current = null;
	}

	public Object getCurrent() {
		return _current;
	}
	
	private String promptUser(String msg) throws IOException {
		System.err.println(msg);
		String line = _in.readLine();
		return line;
	}
}
