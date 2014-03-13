package ist.mei.pa.command;

import ist.mei.pa.Inspector;

public abstract class Command {
	private Inspector _inspector;
	
	Command(Inspector inspector) {
		_inspector = inspector;
	}
	
	public Inspector getInspector() {
		return _inspector;
	}
	
	public abstract boolean canExecute();
	public abstract void execute();
}
