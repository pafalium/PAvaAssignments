package ist.mei.pa.command;

import java.lang.reflect.Field;
import java.util.ArrayList;

import ist.mei.pa.Inspector;

public class ModifyFieldCommand extends FieldAccessCommand {

	private Object _newValue;
	
	public ModifyFieldCommand(Inspector inspector, String fieldName, Object newValue) {
		super(inspector, fieldName);
		_newValue = newValue;
	}
	
	@Override
	public boolean canExecute() {
		ArrayList<Field> fields = getPossibleFields();
		if (fields.size() < 0)
			return false;
		assert _newValue != null;
		// field type is a superclass of _newValue's class
		return fields.get(0).getType().isAssignableFrom(_newValue.getClass());
	}

	@Override
	public void execute() {
		ArrayList<Field> fields = getPossibleFields();
		assert fields.size() > 0;
		Field field = fields.get(0);
		try {
			field.setAccessible(true);
			field.set(getInspector().getCurrent(), _newValue);
		} catch (IllegalArgumentException e) {
			// TODO shouldn't happen
			throw new RuntimeException(e);
		} catch (IllegalAccessException e) {
			throw new RuntimeException(e);
		}
	}

}
