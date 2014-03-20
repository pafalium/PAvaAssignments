package ist.mei.pa.command;

public class SharedThings {
	static final Class<?>[] wrappers = { Integer.class, Boolean.class,
			Character.class, Float.class, Double.class, Byte.class,
			Short.class, Long.class };
	static final Class<?>[] primitives = { int.class, boolean.class,
			char.class, float.class, double.class, byte.class, short.class,
			long.class };

	public static boolean assignableFrom(Class<?> primitive, Class<?> other) {
		boolean normalAssignable = primitive.isAssignableFrom(other);
		if (normalAssignable)
			return true;
		if (!primitive.isPrimitive())
			return false;

		for (int i = 0; i < wrappers.length; i++) {
			if (other.equals(wrappers[i]) && primitive.equals(primitives[i]))
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
