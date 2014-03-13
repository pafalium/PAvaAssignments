package ist.mei.pa.command;

import java.lang.reflect.Field;

import ist.mei.pa.Inspector;

public class InspectCommand extends Command {
	
	private String _fieldName;
	
	public InspectCommand(Inspector inspector, String fieldName) {
		super(inspector);
		_fieldName = fieldName;
	}

	@Override
	public boolean canExecute() {
		Object current = getInspector().getCurrent();
		
		Field[] fields = getPossibleFields();
		boolean currentHasField = fields.length > 0;
		
		return currentHasField;
	}

	@Override
	public void execute() {
		
	}
	
}
