package ist.mei.pa;

import ist.mei.pa.command.Command;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Inspector {
	/** Object currently being inspected.*/
	private Object _current;
	
	public void inspect(Object o) throws Exception {
		_current = o;
		
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		while (_current != null) {
			printCurrent();
			
			promptUser();
			String line = in.readLine();
			
			Command com = parseCommand(line);
			if(com.canExecute()) {
				com.execute();
			}
		}
	}

	public void removeCurrent() {
		_current = null;
	}

	public Object getCurrent() {
		return _current;
	}
	
	public void setCurrent(Object newCurrent) {
		_current = newCurrent;
	}
}
