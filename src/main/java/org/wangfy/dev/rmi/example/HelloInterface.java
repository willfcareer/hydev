/**
 * <p>Copyright: Copyright (c) 2008 中国软件与技术服务股份有限公司</p>
 * <p>Company: 应用产品研发中心 架构部</p>
 * <p>http://www.css.com.cn</p>
 */

package org.wangfy.dev.rmi.example;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * @author wangfy 
 * @Email: willfcareer@hotmail.com
 * @Version 1.0
 * @Date:2008-11-12 下午04:28:47 
 * @Since 1.0
 */
public interface HelloInterface extends Remote {

	public String say() throws RemoteException;
	
}
