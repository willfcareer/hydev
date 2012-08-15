package org.wangfy.dev.catalina;

public interface Context extends Container {
	
    public Object[] getApplicationListeners();
    
    public void setApplicationListeners(Object listeners[]);

    public void addApplicationListener(String listener);
    
    public void removeApplicationListener(String listener);
}
