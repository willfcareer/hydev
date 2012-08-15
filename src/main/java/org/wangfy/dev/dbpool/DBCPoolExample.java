package org.wangfy.dev.dbpool;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.commons.dbcp.BasicDataSource;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class DBCPoolExample {

	private final Log log = LogFactory.getLog(this.getClass());

	private BasicDataSource dataSource;
	
	private int n;

	public void init() {
		log.debug("init BasicDataSource");
		long start = System.currentTimeMillis();
		dataSource = new BasicDataSource();
		dataSource.setDriverClassName("com.mysql.jdbc.Driver");
		dataSource.setPassword("root");
		dataSource.setUsername("root");
		dataSource.setUrl("jdbc:mysql://localhost:3306/idm");
		dataSource.setMaxActive(50);
		log.debug("finish dataSource init - " + (System.currentTimeMillis() - start) + " ms");
	}

	public void destroy() throws SQLException{
		dataSource.close();
		log.debug("close dataSource");
	}
	public void query() throws SQLException {
		log.debug("prepare get connection");
		long start = System.currentTimeMillis();
		Connection conn = dataSource.getConnection();
		log.debug("get connection success - " + (System.currentTimeMillis() - start) + " ms - " + (++n));
		log.debug("current activate conn - " + dataSource.getNumActive());
		Statement stmt = conn.createStatement();
		String sql = "select * from t_ssosite";
		stmt.executeQuery(sql);
		log.debug("finish query, prepare close connection");
		stmt.close();
		conn.close();
		log.debug("close connection success");
	}

	public static void main(String[] args) throws SQLException {
		DBCPoolExample dbcpEx = new DBCPoolExample();
		dbcpEx.init();
		for(int i = 0; i < 100; i++){
			dbcpEx.query();			
		}
		dbcpEx.destroy();
	}

}
