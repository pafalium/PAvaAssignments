package ist.mei.pa.command;

import java.lang.reflect.Field;
import java.util.ArrayList;

import ist.mei.pa.Inspector;

public class InspectCommand extends Command {
	
	private String _fieldName;
	
	public InspectCommand(Inspector inspector, String fieldName) {
		super(inspector);
		_fieldName = fieldName;
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
		
		assert field.getDeclaringClass() == curr.getClass();
		try {
			field.setAccessible(true);
			Object value = field.get(curr);
			getInspector().setCurrent(value);
		} catch (IllegalArgumentException e) {
			// TODO shouldn't happen
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private ArrayList<Field> getPossibleFields() {
		Object curr = getInspector().getCurrent();
		Class<?> currentClass = curr.getClass();
		
		ArrayList<Field> possibleFields = new ArrayList<Field>();
		while (currentClass != Object.class) {
			try {
				Field currentField = currentClass.getDeclaredField(_fieldName);
				possibleFields.add(currentField);
			} catch (NoSuchFieldException e) {
				//Do nothing
			} catch (SecurityException e) {
				// TODO investigate this
				e.printStackTrace();
			}
			currentClass = currentClass.getSuperclass();
		}
		
		return possibleFields;
	}
}
