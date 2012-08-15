/**
 * <p>Copyright: Copyright (c) 2008 中国软件与技术服务股份有限公司</p>
 * <p>Company: 应用产品研发中心 架构部</p>
 * <p>http://www.css.com.cn</p>
 */

package org.wangfy.dev.lang.file.reflect.field;

import java.lang.reflect.Field;

/**
 * @author wangfy 
 * @Email: willfcareer@hotmail.com
 * @Version 1.0
 * @Date:2008-12-5 下午02:08:21 
 * @Since 1.0
 */
public class FieldTest {

	/**
	 * @param args
	 * @throws NoSuchFieldException 
	 * @throws SecurityException 
	 * @throws IllegalAccessException 
	 * @throws IllegalArgumentException 
	 */
	public static void main(String[] args) throws SecurityException, NoSuchFieldException, IllegalArgumentException, IllegalAccessException {
		// TODO Auto-generated method stub
		Account a = new Account();
		a.setId("2");
		a.setName("wangfy");
		Base b = (Base)a;
		Class clazz = b.getClass();
//		Field field = clazz.getField("name");
		Field field = clazz.getDeclaredField("name");
//		field.setAccessible(true);
		Object value = field.get(b);
		System.out.println(value);

	}

}
