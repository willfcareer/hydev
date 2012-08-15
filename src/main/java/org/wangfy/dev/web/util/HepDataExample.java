package org.wangfy.dev.web.util;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class HepDataExample {
	
	private static String oracleDriver = "oracle.jdbc.driver.OracleDriver";
	
	private static String oracleUrl = "jdbc:oracle:thin:@192.168.94.98:1521:idm";	
	
	private static String oracleusername = "idm";
	
	private static String oraclepassword = "idm";	
	
	
	private static String mysqlDriver = "com.mysql.jdbc.Driver";
	
	private static String mysqlUrl = "jdbc:mysql://192.168.94.98:1521/idm";

	private static String mysqlusername = "root";
	
	private static String mysqlpassword = "root";	
	
	
	private static String mssqlDriver = "net.sourceforge.jtds.jdbc.Driver";
	
	private static String mssqlUrl = "jdbc:jtds:sqlserver://192.168.70.50:1433/workdb";	
	
	private static String mssqlusername = "sa";
	
	private static String mssqlpassword = "apple127";

	/**
	 * @param args
	 * @throws ClassNotFoundException 
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 * @throws SQLException 
	 */

	public static Connection getConnection(String drivername,String username, String password,String dburl) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException{
		Class.forName(drivername).newInstance();
		Connection conn = java.sql.DriverManager.getConnection(dburl,username,password);
		return conn;
		
	}
	
	public static void insertDate(Connection conn,String id,String name, String pass, String chinese, String email) {		
		String sql = "insert into t_idm_account (ID,NAME,PASSWORD,CHINESE,EMAIL,ORGANIZATION_ID) values('"+id+"','"+name+"','"+pass+"','"+chinese+"','"+email+"','1')";		
		try{			
			Statement stmt = conn.createStatement();
			conn.setAutoCommit(false);
			stmt.execute(sql);
			conn.commit();
			stmt.close();
		}catch(SQLException sqlEx){
			sqlEx.printStackTrace();
		}
		
	}
	
	public static String[] getColumnValues(ResultSet rs) throws SQLException{
		String userid = rs.getString("userid");
		String name = rs.getString("login_name");
		String pass = rs.getString("password_value");
		String chinese = rs.getString("real_name");
		String email = rs.getString("email");
		return new String[]{userid,name,pass,chinese,email};		
	}	

	public static void deleteOrganization(Connection conn) throws Exception {
		String sql = "DELETE FROM T_IDM_ORGANIZATION";
		Statement stmt = conn.createStatement();
		stmt.executeUpdate(sql);
		stmt.close();
	}
	public static void deleteApplication(Connection conn) throws Exception {
		String sql = "DELETE FROM T_IDM_APPLICATION";
		Statement stmt = conn.createStatement();
		stmt.executeUpdate(sql);
		stmt.close();
		System.out.println(sql);
	}	
	public static void deleteMethod(Connection conn) throws Exception {
		String sql = "DELETE FROM T_IDM_AUTHORITY_METHOD";
		Statement stmt = conn.createStatement();
		stmt.executeUpdate(sql);
		System.out.println(sql);
		sql = "DELETE FROM T_IDM_METHOD";
		stmt.executeUpdate(sql);
		stmt.close();
		System.out.println(sql);
	}
	public static void deleteUrl(Connection conn) throws Exception {
		String sql = "DELETE FROM T_IDM_AUTHORITY_URL";
		Statement stmt = conn.createStatement();
		stmt.executeUpdate(sql);
		System.out.println(sql);
		sql = "DELETE FROM T_IDM_URL";
		stmt.executeUpdate(sql);
		stmt.close();
		System.out.println(sql);
	}	
	public static void deleteAuthority(Connection conn) throws Exception {
		Statement stmt = conn.createStatement();
		String sql = "DELETE FROM T_IDM_ACCOUNT_AUTHORITY";
		stmt.executeUpdate(sql);
		sql = "DELETE FROM T_IDM_ROLE_AUTHORITY";
		stmt.executeUpdate(sql);
		sql = "DELETE FROM T_IDM_AUTHORITY";
		stmt.executeUpdate(sql);
		stmt.close();
		System.out.println(sql);
	}
	public static void deleteRole(Connection conn) throws Exception {
		Statement stmt = conn.createStatement();
		String sql = "DELETE FROM T_IDM_ACCOUNT_ROLE";
		stmt.executeUpdate(sql);
		sql = "DELETE FROM T_IDM_ROLE";
		stmt.executeUpdate(sql);
		stmt.close();
		System.out.println(sql);
	}
	public static void deleteAccount(Connection conn) throws Exception {
		String sql = "DELETE FROM T_IDM_ACCOUNT WHERE NAME LIKE TEST%";
		Statement stmt = conn.createStatement();
		stmt.executeUpdate(sql);
		stmt.close();
		System.out.println(sql);
	}	
	public static void insertOrganization(Connection conn) throws SQLException {
		String sql;
		conn.setAutoCommit(false);
		sql = "INSERT INTO T_IDM_ORGANIZATION(ID,NAME,CHINESE) values('1','HEP','高教出版社')";
		Statement stmt = conn.createStatement();
		stmt.executeUpdate(sql);
		conn.commit();
		stmt.close();
		System.out.println(sql);
	}
	
	public static void insertAuthority(Connection conn) throws SQLException {
		String sql;
		conn.setAutoCommit(false);
		sql = "INSERT INTO T_IDM_AUTHORITY(ID,NAME,CHINESE) values('0','AUTH_BASIC','基本权限')";
		Statement stmt = conn.createStatement();
		stmt.executeUpdate(sql);
		conn.commit();
		stmt.close();
		System.out.println(sql);
	}	
	public static void insertAccount(Connection conn) throws SQLException {
		String sql;
		int offset = 900005;
		Statement stmt = conn.createStatement();
		for (int i = 1; i <= 300000; i++) {
			conn.setAutoCommit(false);
			sql = "INSERT INTO T_IDM_ACCOUNT(ID,NAME,PASSWORD,EMAIL,ORGANIZATION_ID) values(" + (++offset) + ",'" + "user" + offset + "','000000','email" + offset+ "','1')";
			stmt.executeUpdate(sql);
			conn.commit();
			
			System.out.println(sql);
		}
		stmt.close();
	}
	
	public static void insertAccountAuthority(Connection conn) throws SQLException{
		String sql;
		int offset = 5;
		Statement stmt = conn.createStatement();
		for(int i = 1; i <= 600005; i++){
			conn.setAutoCommit(false);
			sql = "INSERT INTO T_IDM_ACCOUNT_AUTHORITY(ACCOUNT_ID,AUTHORITY_ID) VALUES('"+(++offset)+"','0')";
			stmt.executeUpdate(sql);
			conn.commit();
			System.out.println(sql);
		}
		stmt.close();
		
	}
	
	public static void run(Connection srcConn, Connection destConn) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException{
		String sql = "SELECT * FROM ac_users_all";	
		Statement stmt = srcConn.createStatement();
		ResultSet rs = stmt.executeQuery(sql);	
		int n = 0;
		while(rs.next()){
			String[] paras = getColumnValues(rs);
			insertDate(destConn,paras[0],paras[1],paras[2],paras[3],paras[4]);
			System.out.println("record finished -> " + (++n));
		}
		System.out.println("all finished");		
	}
	
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
//		HepDataExample hepExample = new HepDataExample();
		Connection srcConn = getConnection(mssqlDriver,mssqlusername,mssqlpassword,mssqlUrl);
		Connection destConn = getConnection(oracleDriver,oracleusername,oraclepassword,oracleUrl);	
		//delete exist data
//		deleteMethod(destConn);
//		deleteUrl(destConn);
//		deleteApplication(destConn);
//		deleteAuthority(destConn);
//		deleteRole(destConn);
//		deleteAccount(destConn);
//		deleteOrganization(destConn);
		
		//insert operation
//		insertOrganization(destConn);
//		insertAuthority(destConn);
//		run(srcConn,destConn);
		insertAccount(destConn);
//		insertAccountAuthority(destConn);
	}

}
