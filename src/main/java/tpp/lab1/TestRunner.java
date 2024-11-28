package tpp.lab1;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class TestRunner {
	private final String className;

	public TestRunner(String className) {
		this.className = className;
	}
	
	public void run() {
		try {
			Class<?> clazz = Class.forName(className);
			System.out.println("Class " + className + " successfully loaded");
			
			Constructor<?> cstr = clazz.getConstructor();
			Object obj = cstr.newInstance();
			
			runTests(clazz, obj);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	public void runTests(Class<?> clazz, Object obj) throws SecurityException {
		int total = 0;
		int successful = 0;
		int failed = 0;
		
		for (Method method : clazz.getDeclaredMethods()) {
			try {
				if (!method.getName().startsWith("test")) {
					System.out.println("\tMethod: " + method.getName() + " is not a test method");
				}
				method.invoke(obj);
				System.out.println("\tTest: " + method.getName() + " SUCCESSFUL");
				successful++;
			} catch (InvocationTargetException e) {
				System.out.println("\tTest: " + method.getName() + " FAILED, error: " + e.getTargetException().getMessage());
				failed++;
			} catch (IllegalAccessException|IllegalArgumentException e) {
				throw new RuntimeException(e);
			} finally {
				total++;
			}
		}
		
		System.out.println(
				"\tTotal methods: " + total + 
				" Total tests: " + total + 
				" Successful tests: " + successful + 
				" Failed tests: " + failed);
	}
}
