package ist.mei.pa.command;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;

import ist.mei.pa.Inspector;

public class CallMethodCommand extends Command {

	private String _methodName;
	private Object[] _methodParameters;

	public CallMethodCommand(Inspector inspector, String methodName,
			Object[] parameters) {
		super(inspector);
		_methodName = methodName;
		_methodParameters = parameters;
	}

	@Override
	public boolean canExecute() {
		return getPossibleMethods().size() > 0;
	}

	@Override
	public void execute() {
		ArrayList<Method> methods = getPossibleMethods();
		assert methods.size() > 0;
		Method method = methods.get(0);
			try {
				method.setAccessible(true);
				Object res = method.invoke(getInspector().getCurrent(), _methodParameters);
				if (res != null)
					getInspector().setCurrent(res);
			} catch (IllegalAccessException e) {
				throw new RuntimeException(e);
			} catch (IllegalArgumentException e) {
				// TODO shouldn't happen
				throw new RuntimeException(e);
			} catch (InvocationTargetException e) {
				System.err.println("Exception thorwn by called method. Printing stack trace:");
				e.printStackTrace();
			}
		
	}

	private ArrayList<Method> getPossibleMethods() {
		Object curr = getInspector().getCurrent();
		Class<?> currentClass = curr.getClass();
		
		ArrayList<Class<?>> givenTypes = new ArrayList<Class<?>>(_methodParameters.length);
		for (Object param : _methodParameters) {
			givenTypes.add(param.getClass());
		}
		 
		ArrayList<Method> possibleMethods = new ArrayList<Method>();
		while (currentClass != Object.class) {
			try {
				Method[] methods = currentClass.getDeclaredMethods();
				for (Method method : methods) {
					if (!method.getName().equals(_methodName))
						continue;
					Class<?>[] paramTypes = method.getParameterTypes();
					if (!(paramTypes.length == givenTypes.size()))
						continue;
					boolean allTypesMatch = true;
					for(int i=0; i< paramTypes.length; i++) {
						if(!SharedThings.assignableFrom(paramTypes[i], givenTypes.get(i)))
							allTypesMatch = false;
					}
					if (allTypesMatch)
						possibleMethods.add(method);
				}

			} catch (SecurityException e) {
				throw new RuntimeException(e);
			}

			currentClass = currentClass.getSuperclass();
		}
		
		return possibleMethods;
	}
}
