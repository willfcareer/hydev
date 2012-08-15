package org.wangfy.dev.jaas;

import javax.security.auth.login.LoginContext;
import javax.security.auth.login.LoginException;

public class SimpleLogin {
	
	public static void main(String[] args) {
		
		LoginContext loginContext = null;
		try {
			                               
			loginContext = new LoginContext("simple", new SimpleCallbackHandle());
		} catch (LoginException e) {
			
			System.out.println(e.getMessage());
		}
		
		try {

			loginContext.login();
		} catch (LoginException e) {
			
		}
	}
	
}
