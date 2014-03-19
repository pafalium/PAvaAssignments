package ist.mei.pa;

import ist.mei.pa.command.Command;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Inspector {
	/** Object currently being inspected.*/
	private Object _current;
	BufferedReader _in = new BufferedReader(new InputStreamReader(System.in));
	
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

	//TODO print object
	public void setCurrent(Object newCurrent) {
		printObject(newCurrent);
		if (newCurrent.getClass().isPrimitive()) {
			return;
		}
		_current = newCurrent;
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
