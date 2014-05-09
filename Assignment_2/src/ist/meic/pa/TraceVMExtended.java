package ist.meic.pa;

import ist.meic.pa.TraceEditor;
import javassist.CannotCompileException;
import javassist.ClassPool;
import javassist.Loader;
import javassist.NotFoundException;
import javassist.Translator;
import javassist.expr.ConstructorCall;
import javassist.expr.ExprEditor;
import javassist.expr.NewArray;

/**
 * Extension of {@link TraceVM}.
 * While using this class objects being passed in {@link ConstructorCall}
 * are traced. Arrays being created are also traced.
 * @author Pedro-170
 *
 */
public class TraceVMExtended extends TraceVM {
	/**
	 * @param args
	 *            the name of the starting {@link Class} followed by the
	 *            arguments to be passed to it.
	 * @throws Throwable 
	 */
	public static void main(String[] args) throws Throwable {
		if (args.length < 1) {
			System.err.println("No starting class provided.");
			System.exit(-1);
		} else {
			Translator translator = new ExtendedTraceTranslator();
			ClassPool pool = ClassPool.getDefault();
			Loader classLoader = new Loader();
			classLoader.delegateLoadingOf("ist.meic.pa.Trace");
			classLoader.delegateLoadingOf("ist.meic.pa.TraceVMExtended");
			classLoader.delegateLoadingOf("ist.meic.pa.entries.");
			classLoader.addTranslator(pool, translator);
			String[] restArgs = new String[args.length - 1];
			System.arraycopy(args, 1, restArgs, 0, restArgs.length);
			classLoader.run(args[0], restArgs);
		}
	}
}

class ExtendedTraceTranslator extends TraceTranslator {
	@Override
	protected ExprEditor createEditor(boolean debug) {
		return new ExtendedTraceEditor(debug);
	}
}

class ExtendedTraceEditor extends TraceEditor {
	public ExtendedTraceEditor(boolean debug) {
		super(debug);
	}
	
	@Override
	public void edit(NewArray a) throws CannotCompileException {
		int line = a.getLineNumber();
		String file = a.getFileName();
		String sig;
		try {
			sig = a.getComponentType().getName();
			for (int i=0; i<a.getCreatedDimensions(); i++)
				sig += "[]";
		} catch (NotFoundException ex) {
			throw new RuntimeException(ex);
		}
		file = "\""+file+"\"";
		sig = "\""+sig+"\"";

		String novaTemplate = 
				"{\n"+
				"    $_ = $proceed($$);\n"+
				"    ist.meic.pa.Trace.traceReturn(($w)$_,"+sig+","+file+","+line+");\n"+
				"}\n";
		debugPrint(novaTemplate);
		a.replace(novaTemplate);
	}
	@Override
	public void edit(ConstructorCall c) throws CannotCompileException {
		int line = c.getLineNumber();
		String file = c.getFileName();
		String sig;
		try {
			sig = c.getConstructor().getLongName();
		} catch (NotFoundException e) {
			throw new RuntimeException(e);
		}
		file = "\""+file+"\"";
		sig = "\""+sig+"\"";
		//assuming I can violate java semantics
		String novaTemplate = 
				"{\n"+
				"    ist.meic.pa.Trace.traceArguments($args,"+sig+","+file+","+line+");\n"+
				"    $proceed($$);\n"+
				"}\n";
		debugPrint(novaTemplate);
		c.replace(novaTemplate);
	}
}