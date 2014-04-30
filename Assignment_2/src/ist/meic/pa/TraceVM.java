package ist.meic.pa;

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
			classLoader.delegateLoadingOf("ist.meic.pa.Trace");
			classLoader.delegateLoadingOf("ist.meic.pa.TraceVM");
			classLoader.delegateLoadingOf("ist.meic.pa.entries.");
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
			file = "\""+file+"\"";
			sig = "\""+sig+"\"";
			/*String argsTemplate = 
					"for(int my$i=0; my$i<$args.length; my$i++) {\n"+
					"   if(!ist.meic.pa.Trace.traceHistory.containsKey($args[my$i])){\n"+
					"      ist.meic.pa.Trace.traceHistory.put($args[my$i],new java.util.ArrayList());\n"+
					"   }\n"+
					"   ist.meic.pa.entries.PassEntry entr = new ist.meic.pa.entries.PassEntry("+sig+","+file+","+line+");\n"+
					"   ist.meic.pa.Trace.traceHistory.get($args[my$i]).add(entr);\n"+
					"}";
			String retTemplate = 
					"java.lang.Object res$ = $proceed($$);\n"+
					"if(!Trace.traceHistory.containsKey(res$)){\n"+
					"   ist.meic.pa.Trace.traceHistory.put(res$,new java.util.ArrayList());\n"+
					"}\n"+
					"ist.meic.pa.entries.ReturnEntry entr = new ist.meic.pa.entries.ReturnEntry("+sig+","+file+","+line+");\n"+
					"ist.meic.pa.Trace.traceHistory.get(res$).add(entr);\n"+
					"$_ = ($r) res$;\n";
			String template = "{"+argsTemplate+retTemplate+"}";*/
			String novaTemplate = 
					"{\n ist.meic.pa.Trace.traceArguments($args,"+sig+","+file+","+line+");\n"+
					"    Object ret$ = $proceed($$);\n"+
					"    ist.meic.pa.Trace.traceReturn(ret$,"+sig+","+file+","+line+");\n"+
					"    $_ = ($r) ret$;\n"+
					"}\n";
			System.out.println(novaTemplate);
			m.replace(novaTemplate);
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
			file = "\""+file+"\"";
			sig = "\""+sig+"\"";
			/*String argsTemplate = 
					"for(int my$i=0; my$i<$args.length; my$i++) {\n"+
					"   if(!ist.meic.pa.Trace.traceHistory.containsKey($args[my$i])){\n"+
					"      ist.meic.pa.Trace.traceHistory.put($args[my$i],new java.util.ArrayList());\n"+
					"   }\n"+
					"   ist.meic.pa.entries.PassEntry entr = new ist.meic.pa.entries.PassEntry("+sig+","+file+","+line+");\n"+
					"   ist.meic.pa.Trace.traceHistory.get($args[my$i]).add(entr);\n"+
					"}\n";
			String retTemplate = 
					"java.lang.Object res$ = $proceed($$);\n"+
					"if(!ist.meic.pa.Trace.traceHistory.containsKey(res$)){\n"+
					"   ist.meic.pa.Trace.traceHistory.put(res$,new java.util.ArrayList());\n"+
					"}\n"+
					"ist.meic.pa.entries.ReturnEntry entr = new ist.meic.pa.entries.ReturnEntry("+sig+","+file+","+line+");\n"+
					"ist.meic.pa.Trace.traceHistory.get(res$).add(entr);\n"+
					"$_ = ($r) res$;\n";
			String template = "{"+argsTemplate+retTemplate+"}";*/
			String novaTemplate = 
					"{\n ist.meic.pa.Trace.traceArguments($args,"+sig+","+file+","+line+");\n"+
					"    Object ret$ = $proceed($$);\n"+
					"    ist.meic.pa.Trace.traceReturn(ret$,"+sig+","+file+","+line+");\n"+
					"    $_ = ($r) ret$;\n"+
					"}\n";
			System.out.println(novaTemplate);
			e.replace(novaTemplate);
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
			/*String retTemplate = 
					"java.lang.Object res$ = $proceed($$);\n"+
					"if(!ist.meic.pa.Trace.traceHistory.containsKey(res$)){\n"+
					"   ist.meic.pa.Trace.traceHistory.put(res$,new java.util.ArrayList());\n"+
					"}\n"+
					"ist.meic.pa.entries.ReturnEntry entr = new ist.meic.pa.entries.ReturnEntry("+sig+","+file+","+line+");\n"+
					"ist.meic.pa.Trace.traceHistory.get(res$).add(entr);\n"+
					"$_ = ($r) res$;\n";
			String template = "{"+retTemplate+"}";*/
			String novaTemplate = 
					"{\n "+
					"    Object ret$ = $proceed($$);\n"+
					"    ist.meic.pa.Trace.traceReturn(ret$,"+sig+","+file+","+line+");\n"+
					"    $_ = ($r) ret$;\n"+
					"}\n";
			System.out.println(novaTemplate);
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
			/*String argsTemplate = 
					"for(int my$i=0; my$i<$args.length; my$i++) {\n"+
					"   if(!ist.meic.pa.Trace.traceHistory.containsKey($args[my$i])){\n"+
					"      ist.meic.pa.Trace.traceHistory.put($args[my$i],new java.util.ArrayList());\n"+
					"   }\n"+
					"   ist.meic.pa.entries.PassEntry entr = new ist.meic.pa.entries.PassEntry("+sig+","+file+","+line+");\n"+
					"   ist.meic.pa.Trace.traceHistory.get($args[my$i]).add(entr);\n"+
					"}\n";
			String proceedTemplate = 
					"$proceed($$);\n";
			String template = "{"+argsTemplate+proceedTemplate+"}";*/
			String novaTemplate = 
					"{\n ist.meic.pa.Trace.traceArguments($args,"+sig+","+file+","+line+");\n"+
					"    $proceed($$);\n"+
					"}\n";
			System.out.println(novaTemplate);
			c.replace(novaTemplate);
		}
	}
}