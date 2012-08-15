package org.wangfy.dev.catalina;



public interface Container {
	
    public void addContainerListener(LifecycleListener listener);
	
    public void removeContainerListener(LifecycleListener listener);
}
