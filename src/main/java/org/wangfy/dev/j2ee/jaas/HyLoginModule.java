package org.wangfy.dev.j2ee.jaas;

import java.io.IOException;
import java.util.Map;

import javax.security.auth.Subject;
import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.NameCallback;
import javax.security.auth.callback.PasswordCallback;
import javax.security.auth.callback.UnsupportedCallbackException;
import javax.security.auth.login.LoginException;
import javax.security.auth.spi.LoginModule;

@SuppressWarnings("unused")
public class HyLoginModule implements LoginModule
{

	private String userName;

	private char[] password;

	private Subject subject;

	private CallbackHandler callbackHandler;

	private Map<String, ?> sharedState;

	private Map<String, ?> options;

	private String debug;

	private boolean success;

	public boolean abort() throws LoginException
	{
		System.out.println("abort()");
		return false;
	}

	public boolean commit() throws LoginException
	{
		System.out.println("commit()");
		return success;
	}

	public void initialize(Subject subject, CallbackHandler callbackHandler, Map<String, ?> sharedState, Map<String, ?> options)
	{
		this.subject = subject;
		this.callbackHandler = callbackHandler;
		this.sharedState = sharedState;
		this.options = options;
		this.debug = (String) options.get("debug");
	}

	public boolean login() throws LoginException
	{
		System.out.println("login()");
		Callback[] callbacks = new Callback[2];
		callbacks[0] = new NameCallback("UserName");
		callbacks[1] = new PasswordCallback("Password", false);

		try
		{
			/*
			 * Handle(such as interact with user) before authentication
			 */
			callbackHandler.handle(callbacks);
			userName = ((NameCallback) callbacks[0]).getName();
			password = ((PasswordCallback) callbacks[1]).getPassword();

			if (debug.equals("true"))
			{
				System.out.println("Your input UserName: " + userName);
				System.out.println("Your input Password: " + new String(password));
			}

			/*
			 * Authenticate
			 */
			if (userName.equals("wangfy") && new String(password).equals("wangfy"))
			{
				System.out.println("Authenticate Success");
				success = true;
				return success;
			} else
			{
				System.out.println("Authenticate Failure");
				userName = null;
				password = null;
				success = false;
				return success;
			}
		} catch (IOException e)
		{
			e.printStackTrace();
		} catch (UnsupportedCallbackException e)
		{
			e.printStackTrace();
		}

		return false;
	}

	public boolean logout() throws LoginException
	{
		System.out.println("logout()");
		return false;
	}

}
