package ist.mei.pa.command;

import ist.mei.pa.Inspector;

public class SaveLastResultCommand extends Command {

	private String _saveName;
	
	public SaveLastResultCommand(Inspector inspector, String saveName) {
		super(inspector);
		_saveName = saveName;
	}

	@Override
	public boolean canExecute() {
		return true;
	}

	@Override
	public void execute() {
		getInspector().saveLastResult(_saveName);
		getInspector().printLine("Saved last result to '"+_saveName+"'.");
	}

}
