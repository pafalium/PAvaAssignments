package ist.meic.pa.tracer;

import javassist.*;
import javassist.expr.ConstructorCall;
import javassist.expr.ExprEditor;
import javassist.expr.MethodCall;
import javassist.expr.NewArray;
import javassist.expr.NewExpr;

/**
 * Class used to start a program with tracing enabled. It must setup a new
 * {@link ClassLoader} that modifies all classes being loaded to support
 * tracing.
 * 
 * @author Pedro-170
 * 
 */
public class TraceVM {

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
			Translator translator = new TraceTranslator();
			ClassPool pool = ClassPool.getDefault();
			Loader classLoader = new Loader();
			classLoader.addTranslator(pool, translator);
			String[] restArgs = new String[args.length - 1];
			System.arraycopy(args, 1, restArgs, 0, restArgs.length);
			classLoader.run(args[0], restArgs);
		}
	}

}

class TraceTranslator implements Translator {

	@Override
	public void start(ClassPool pool) throws NotFoundException,
			CannotCompileException {
		// do nothing
	}

	@Override
	public void onLoad(ClassPool pool, String classname)
			throws NotFoundException, CannotCompileException {
		try {
			CtClass ctClass = pool.get(classname);
			instrumentTrace(ctClass);
		} catch (NotFoundException e) {
			throw new RuntimeException(e);
		}
	}

	private void instrumentTrace(CtClass ctClass) throws CannotCompileException {
		//TODO implement
		CtBehavior[] behaviours = ctClass.getDeclaredBehaviors();
		
		for (CtBehavior behaviour : behaviours) {
			behaviour.instrument(new TraceEditor());
		}
		//zonas a editar:
		// - initializer blocks (instance and static) - instance are copied to every constructor
		// - method body -
		// - constructors (if not the same as methods) -
		// - finalize blocks (it's a method)
	}
	
	class TraceEditor extends ExprEditor {
		@Override
		public void edit(MethodCall m) throws CannotCompileException {
			int line = m.getLineNumber();
			String file = m.getFileName();
			String sig;
			try {
				sig = m.getMethod().getLongName();
			} catch (NotFoundException e) {
				throw new RuntimeException(e);
			}
			String argsTemplate = 
					"for(int my$i=0; i<$args; i++) {"+
					"   if(!Trace.traceHistory.containsKey($args[my$i])){"+
					"      Trace.traceHistory.put($args[my$i],new ArrayList())"+
					"   }"+
					"   PassEntry entr = new PassEntry("+sig+","+file+","+line+");"+
					"   Trace.traceHistory.get($args[my$i]).add(entr);"+
					"}";
			String retTemplate = 
					"Object res$ = $proceed($$);"+
					"if(!Trace.traceHistory.containsKey(res$)){"+
					"   Trace.traceHistory.put(res$,new ArrayList())"+
					"}"+
					"ReturnEntry entr = new ReturnEntry("+sig+","+file+","+line+");"+
					"Trace.traceHistory.get(res$).add(entr);"+
					"$_ = res$";
			m.replace(argsTemplate+retTemplate);
		}
		@Override
		public void edit(NewExpr e) throws CannotCompileException {
			int line = e.getLineNumber();
			String file = e.getFileName();
			String sig;
			try {
				sig = e.getConstructor().getLongName();
			} catch (NotFoundException ex) {
				throw new RuntimeException(ex);
			}
			String argsTemplate = 
					"for(int my$i=0; i<$args; i++) {"+
					"   if(!Trace.traceHistory.containsKey($args[my$i])){"+
					"      Trace.traceHistory.put($args[my$i],new ArrayList())"+
					"   }"+
					"   PassEntry entr = new PassEntry("+sig+","+file+","+line+");"+
					"   Trace.traceHistory.get($args[my$i]).add(entr);"+
					"}";
			String retTemplate = 
					"Object res$ = $proceed($$);"+
					"if(!Trace.traceHistory.containsKey(res$)){"+
					"   Trace.traceHistory.put(res$,new ArrayList())"+
					"}"+
					"ReturnEntry entr = new ReturnEntry("+sig+","+file+","+line+");"+
					"Trace.traceHistory.get(res$).add(entr);"+
					"$_ = res$";
			e.replace(argsTemplate+retTemplate);
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
			String retTemplate = 
					"Object res$ = $proceed($$);"+
					"if(!Trace.traceHistory.containsKey(res$)){"+
					"   Trace.traceHistory.put(res$,new ArrayList())"+
					"}"+
					"ReturnEntry entr = new ReturnEntry("+sig+","+file+","+line+");"+
					"Trace.traceHistory.get(res$).add(entr);"+
					"$_ = res$";
			a.replace(retTemplate);
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
			//assuming I can violate java semantics
			String argsTemplate = 
					"for(int my$i=0; i<$args; i++) {"+
					"   if(!Trace.traceHistory.containsKey($args[my$i])){"+
					"      Trace.traceHistory.put($args[my$i],new ArrayList())"+
					"   }"+
					"   PassEntry entr = new PassEntry("+sig+","+file+","+line+");"+
					"   Trace.traceHistory.get($args[my$i]).add(entr);"+
					"}";
			String proceedTemplate = 
					"$proceed($$);";
			c.replace(argsTemplate+proceedTemplate);
		}
	}
}