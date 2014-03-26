package ist.mei.pa.command;

public class SharedThings {
	static final Class<?>[] wrappers = { Integer.class, Boolean.class,
			Character.class, Float.class, Double.class, Byte.class,
			Short.class, Long.class };
	static final Class<?>[] primitives = { int.class, boolean.class,
			char.class, float.class, double.class, byte.class, short.class,
			long.class };

	/**
	 * Determine if instanceType can be the type of an instance 
	 * referenced by a variable of type destination.
	 * If  {@code destination var = (instanceType) instance; } is possible.
	 * @param destination
	 * @param instanceType
	 * @return
	 */
	public static boolean assignableFrom(Class<?> destination, Class<?> instanceType) {
		boolean normalAssignable = destination.isAssignableFrom(instanceType);
		if (normalAssignable)
			return true;
		if (!destination.isPrimitive())
			return false;

		for (int i = 0; i < wrappers.length; i++) {
			if (instanceType.equals(wrappers[i]) && destination.equals(primitives[i]))
				return true;
		}
		return false;
	}
	
	public static boolean isWrapper(Class<?> type) {
		for (int i = 0; i < wrappers.length; i++) {
			if (type.equals(wrappers[i]))
				return true;
		}
		return false;
	}
}
