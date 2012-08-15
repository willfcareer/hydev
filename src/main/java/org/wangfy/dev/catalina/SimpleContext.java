package org.wangfy.dev.catalina;

public class SimpleContext implements Context, Lifecycle {

	protected LifecycleSupport lifecycle = new LifecycleSupport(this);

	public void addApplicationListener(String listener) {
		// TODO Auto-generated method stub

	}

	public Object[] getApplicationListeners() {
		// TODO Auto-generated method stub
		return null;
	}

	public void removeApplicationListener(String listener) {
		// TODO Auto-generated method stub

	}

	public void setApplicationListeners(Object[] listeners) {
		// TODO Auto-generated method stub

	}

	public void addContainerListener(LifecycleListener listener) {
		// TODO Auto-generated method stub

	}

	public void removeContainerListener(LifecycleListener listener) {
		// TODO Auto-generated method stub

	}

	public void addLifecycleListener(LifecycleListener listener) {
		this.lifecycle.addLifecycleListener(listener);
		System.out.println(this.getClass().getName() + " added listener: " + listener.getClass().getName());

	}

	public LifecycleListener[] findLifecycleListeners() {
		// TODO Auto-generated method stub
		return null;
	}

	public void removeLifecycleListener(LifecycleListener listener) {
		// TODO Auto-generated method stub

	}

	public void start() throws LifecycleException {
		this.lifecycle.fireLifecycleEvent(BEFORE_START_EVENT, null);
		this.lifecycle.fireLifecycleEvent(START_EVENT, null);
		System.out.println(this.getClass().getName()+" start()");
		this.lifecycle.fireLifecycleEvent(AFTER_START_EVENT, null);
	}

	public void stop() throws LifecycleException {
		this.lifecycle.fireLifecycleEvent(BEFORE_STOP_EVENT, null);
		this.lifecycle.fireLifecycleEvent(STOP_EVENT, null);
		System.out.println(this.getClass().getName()+" stop()");
		this.lifecycle.fireLifecycleEvent(AFTER_STOP_EVENT, null);

	}

}
