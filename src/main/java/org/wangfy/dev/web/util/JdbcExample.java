package org.wangfy.dev.web.util;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Random;

public class JdbcExample {

	private static Connection con;

	private static Statement stmt;

	private static String oracleDriver = "oracle.jdbc.driver.OracleDriver";

	private static String mysqlDriver = "com.mysql.jdbc.Driver";

	private static String oracleUrl = "jdbc:oracle:thin:@192.168.92.129:1521:idm";

	private static String mysqlUrl = "jdbc:mysql://10.10.13.244:3306/idm";

	private static String username = "idm";

	private static String password = "idm";

	static {
		try {
			/*
			 * 加载、实例化驱动程序，并向DriverManager注册自身
			 */
			Class.forName(oracleDriver).newInstance();
			con = java.sql.DriverManager.getConnection(oracleUrl, username, password);
			stmt = con.createStatement();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void insertUsers() throws SQLException {
		String sql;
		int offset = 0;
		for (int i = 1; i <= 5000; i++) {
			con.setAutoCommit(false);
			sql = "INSERT INTO T_IDM_ACCOUNT(ID,NAME,PASSWORD,EMAIL) values(" + (++offset) + ",'" + "user" + offset + "','000000','email" + i + "')";
			stmt.executeUpdate(sql);
			con.commit();
			System.out.println(sql);
		}
	}

	public void insertOrganization() throws SQLException {
		String sql;
		con.setAutoCommit(false);
		sql = "INSERT INTO T_IDM_ORGANIZATION(ID,NAME,CHINESE) values('1','css','css')";
		stmt.executeUpdate(sql);
		con.commit();
		System.out.println(sql);

	}

	public void query() throws Exception {
		String sql = "SELECT * FROM t_ssosite";
		System.out.println(sql);
		ResultSet rs = stmt.executeQuery(sql);
		while (rs.next()) {
			System.out.println(rs.getString("siteid") + " " + rs.getString("NAME"));
		}

	}

	public void delete() throws Exception {
		String sql = "DELETE FROM T_IDM_ACCOUNT";
		stmt.executeUpdate(sql);
	}

	public static void main(String[] args) {
		JdbcExample jdbcExample = new JdbcExample();
		try {
//			jdbcExample.delete();
//			jdbcExample.insertUsers();
			 jdbcExample.query();
			if (stmt != null)
				stmt.close();
			if (con != null)
				con.close();
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(1);
		}
	}

}
