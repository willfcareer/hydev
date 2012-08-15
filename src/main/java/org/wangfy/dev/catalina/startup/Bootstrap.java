package org.wangfy.dev.catalina.startup;

import org.wangfy.dev.catalina.Context;
import org.wangfy.dev.catalina.Lifecycle;
import org.wangfy.dev.catalina.LifecycleListener;
import org.wangfy.dev.catalina.SimpleContext;
import org.wangfy.dev.catalina.SimpleContextLifecycleListener;

public final class Bootstrap {
	public static void main(String[] args) {
		
		System.out.println(Bootstrap.class.getSimpleName()+" start application...");

		Context context = new SimpleContext();

		LifecycleListener listener = new SimpleContextLifecycleListener();

		((Lifecycle) context).addLifecycleListener(listener);

		try {

			((Lifecycle) context).start();

			// make the application wait until we press _Zuhe key.
			System.in.read();
			((Lifecycle) context).stop();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}