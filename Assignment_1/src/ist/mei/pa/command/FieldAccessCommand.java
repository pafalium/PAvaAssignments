package ist.mei.pa.command;

import java.lang.reflect.Field;
import java.util.ArrayList;

import ist.mei.pa.Inspector;

public abstract class FieldAccessCommand extends Command {

	protected String _fieldName;
	
	public FieldAccessCommand(Inspector inspector, String fieldName) {
		super(inspector);
		_fieldName = fieldName;
	}

	protected ArrayList<Field> getPossibleFields() {
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