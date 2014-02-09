package org.hy.dev.zk.grvy;

import java.io.IOException;

import groovy.util.GroovyScriptEngine;
import groovy.util.ResourceException;
import groovy.util.ScriptException;

public class ScriptEngine {

	/**
	 * @param args
	 * @throws IOException
	 * @throws ScriptException
	 * @throws ResourceException
	 */
	public static void main(String[] args) throws IOException,
			ResourceException, ScriptException {
		// TODO Auto-generated method stub
		GroovyScriptEngine engine = new GroovyScriptEngine("");
		Task task = new Task(engine);
		task.run();
	}

}
class Task extends Thread {
	
	private GroovyScriptEngine engine;
	
	public Task(GroovyScriptEngine engine) {
		this.engine = engine;
	}
	
	@Override
	public void run() {
		for(int i = 0; i < 6; i++){
			Object obj;
			try {
				obj = engine.run("src/main/java/org/hy/dev/zk/grvy/script.groovy","IamEdwin");
				System.out.println(obj);
				Thread.sleep(1000);
			} catch (ResourceException e) {
				e.printStackTrace();
			} catch (ScriptException e) {
				e.printStackTrace();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
