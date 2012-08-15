package org.wangfy.dev.j2ee.jaas;

import java.io.IOException;

import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.NameCallback;
import javax.security.auth.callback.PasswordCallback;
import javax.security.auth.callback.UnsupportedCallbackException;

public class HyCallbackHandler implements CallbackHandler
{

	public void handle(Callback[] callbacks) throws IOException, UnsupportedCallbackException
	{
		for (Callback callback : callbacks)
		{
			if (callback instanceof NameCallback)
			{
				NameCallback nameCallback = (NameCallback) callback;
				System.out.println(nameCallback.getPrompt());
				nameCallback.setName("wangfy");
			} else if (callback instanceof PasswordCallback)
			{
				PasswordCallback passCallback = (PasswordCallback) callback;
				System.out.println(passCallback.getPrompt());
				passCallback.setPassword(new char[] { 'w', 'a', 'n', 'g', 'f', 'y' });
			}
		}
	}

}
