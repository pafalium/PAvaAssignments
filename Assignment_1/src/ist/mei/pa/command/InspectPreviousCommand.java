package ist.mei.pa.command;

import ist.mei.pa.Inspector;

public class InspectPreviousCommand extends Command {

	private int _steps;
	
	public InspectPreviousCommand(Inspector inspector, int steps) {
		super(inspector);
		_steps = steps;
	}

	@Override
	public boolean canExecute() {
		return getInspector().inspectionStackSize() >= _steps;
	}

	@Override
	public void execute() {
		assert getInspector().inspectionStackSize() >= _steps;
		for (int i=0; i<_steps; ++i) 
			getInspector().setPreviousCurrent();
		getInspector().printCurrent();
	}

}
