package org.wangfy.dev.j2ee.jaas;

import java.io.File;
import java.io.IOException;

import javax.security.auth.login.LoginContext;
import javax.security.auth.login.LoginException;

public class HyLoginController
{

	public static void main(String[] args) throws IOException
	{
		String jaasConfig = new File(HyLoginController.class.getResource("").getFile()).getAbsolutePath() + File.separator + "jaas.config";
		System.out.println(jaasConfig);
		System.setProperty("java.security.auth.login.config", jaasConfig);
		LoginContext loginContext = null;
		try
		{
			loginContext = new LoginContext("hyjaas", new HyCallbackHandler());
			loginContext.login();
		} catch (LoginException e)
		{
			e.printStackTrace();
		}
	}

}
