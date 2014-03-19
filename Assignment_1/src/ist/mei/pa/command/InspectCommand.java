package ist.mei.pa.command;

import java.lang.reflect.Field;
import java.util.ArrayList;

import ist.mei.pa.Inspector;

public class InspectCommand extends FieldAccessCommand {
	
	
	
	public InspectCommand(Inspector inspector, String fieldName) {
		super(inspector, fieldName);
	}

	@Override
	public boolean canExecute() {		
		ArrayList<Field> fields = getPossibleFields();
		boolean currentHasField = fields.size() > 0;
		
		return currentHasField;
	}


	@Override
	public void execute() {
		ArrayList<Field> fields = getPossibleFields();
		assert fields.size() > 0 : "There must be at least a field...";
		Field field = fields.get(0);
		Object curr = getInspector().getCurrent();
		
		// field declaring class is the same or a superclass of curr.
		assert field.getDeclaringClass().isAssignableFrom(curr.getClass());
		try {
			field.setAccessible(true);
			Object value = field.get(curr);
			getInspector().setCurrent(value);
		} catch (IllegalArgumentException e) {
			throw new RuntimeException(e);
		} catch (IllegalAccessException e) {
			throw new RuntimeException(e);
		}
	}
}
