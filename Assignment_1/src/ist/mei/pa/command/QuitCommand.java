package ist.mei.pa.command;

import ist.mei.pa.Inspector;

public class QuitCommand extends Command {

	public QuitCommand(Inspector inspector) {
		super(inspector);
	}
	
	@Override
	public boolean canExecute() {
		return true;
	}

	@Override
	public void execute() {
		getInspector().removeCurrent();
	}
	
}
