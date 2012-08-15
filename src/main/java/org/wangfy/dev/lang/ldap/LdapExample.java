package org.wangfy.dev.lang.ldap;

import java.util.Hashtable;
import javax.naming.Context;
import javax.naming.NamingException;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;

public class LdapExample {

	public static void main(String[] args) {
		String root = "dc=openv,dc=com"; // root
		Hashtable env = new Hashtable();
		env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
		env.put(Context.PROVIDER_URL, "ldap://think-wfy/" + root);
		env.put(Context.SECURITY_AUTHENTICATION, "simple");
		env.put(Context.SECURITY_PRINCIPAL, "cn=Manager,dc=openv,dc=com");
		env.put(Context.SECURITY_CREDENTIALS, "secret");
		DirContext dirCtx = null;
		try {
			dirCtx = new InitialDirContext(env);
			
			System.out.println("认证成功");
		} catch (javax.naming.AuthenticationException e) {
			e.printStackTrace();
			System.out.println("认证失败");
		} catch (Exception e) {
			System.out.println("认证出错：");
			e.printStackTrace();
		}

		if (dirCtx != null) {
			try {
				dirCtx.close();
			} catch (NamingException e) {
				// ignore
			}
		}
	}
}
