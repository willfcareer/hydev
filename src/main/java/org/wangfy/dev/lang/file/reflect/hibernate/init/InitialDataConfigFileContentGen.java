package org.wangfy.dev.lang.file.reflect.hibernate.init;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
/**
 * @This class implement write data from database to  hibernate init configure file
 * @author wangfuye
 *
 */
public class InitialDataConfigFileContentGen {
	private File file;
	private FileWriter fw;
	
	private Connection conn;	
	private Statement stmt;
	private String driverClassName;
	private String url;
	private String username;
	private String password;
	private String dataSet;
	
	private String simpleFileName;
	private String strTableNames;
	private String tableNames[];
	private List<String> columnNames = new ArrayList<String>();
	private List resultValues = new ArrayList();
	
	private StringBuffer buffer = new StringBuffer();
	
	public String[] splitTableNames(){
		this.tableNames = this.strTableNames.split(",");
		return tableNames;
	}
	public String createNewDataSet(){
		int pos = this.dataSet.lastIndexOf("/");
		String newDataSet = this.dataSet.substring(0, pos+1);
		newDataSet = newDataSet+this.simpleFileName;
		return newDataSet;
	}
	public void getConnection(){
		try{			
			Class.forName(this.driverClassName);
			conn=DriverManager.getConnection(url, username, password);	
			stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);

		}catch(Exception e){
			e.printStackTrace();
		}
	}
	public ResultSet query(String tableName){
		try{
			ResultSet rs=stmt.executeQuery("select * from "+tableName);
			ResultSetMetaData rsm = rs.getMetaData();			
			int colNum = 0;
			colNum = rsm.getColumnCount();
			for(int i = 1;i <= colNum;i++)
			{
			String name = rsm.getColumnName(i);
			this.columnNames.add(name);
			} 
			while(rs.next()){
				List<String> rowLs = new ArrayList<String>();
				for(int j = 1;j <= colNum;j++){	
					rowLs.add(rs.getString(j));
				}
				this.resultValues.add(rowLs);
			}						
		}catch(SQLException sqlEx){
			sqlEx.printStackTrace();
		}

		return null;
	}
	private XmlFile createFile(){
		XmlFile xmlFile = new XmlFile();
		xmlFile.setPrefix("<?xml version='1.0' encoding='UTF-8'?>\n<dataset>");
		xmlFile.setSuffix("</dataset>");
		return xmlFile;
	}
	private Table createTable(String tableName,List<String> columnNames,List resultValues){
		Table t = new Table();
		t.setPrefix("<!--[Start of table "+tableName+"]-->\n\t<table name=\""+tableName+"\">");
		t.setSuffix("</table>\n\t<!--[end of "+tableName+"]-->\n");	
		t.setSpaceCount(1);
		Iterator it = columnNames.iterator();
		while(it.hasNext()){
			t.addValue(createColumn((String)it.next()));
		}
		Iterator resuIt = resultValues.iterator();
		while(resuIt.hasNext()){
			List<String> strValues = (List<String>)resuIt.next();
			t.addValue(createRow(strValues));
		}		
		return t;
	}
	private Column createColumn(String columnName){
		Column column = new Column();
		column.setPrefix("<column>");
		column.setSuffix("</column>");
		column.setSpaceCount(2);
		column.addValue(columnName);
		column.setNewLine(false);
		return column;
		
	}
	private Row createRow(List<String> strValues){
		Row row = new Row();
		row.setSpaceCount(2);
		row.setPrefix("<row>");
		row.setSuffix("</row>");
		Iterator it = strValues.iterator();
		while(it.hasNext()){
			row.addValue(createValue((String)it.next()));
		}	
		return row;		
	}
	private Value createValue(String strValue){
		Value value = new Value();
		value.setSpaceCount(3);
		value.setNewLine(false);
		value.setPrefix("<value>");
		value.setSuffix("</value>");
		value.addValue(strValue);
		return value;
	}
	public String mainProcess() throws IOException{
		this.splitTableNames();		
		this.getConnection();
		XmlFile xmlFile = this.createFile();
		for(int i = 0; i < this.tableNames.length; i++){
			this.query(this.tableNames[i]);
			Table t = this.createTable(tableNames[i], columnNames, resultValues);
			xmlFile.addValue(t);
			this.columnNames.clear();
			this.resultValues.clear();
		}		
		this.buffer.append(xmlFile.toString());
//		System.out.println(this.buffer);
		//写入文件
		String newDataSet = this.createNewDataSet();
		file = new File(newDataSet);		
		if(file.exists()){			
			file.delete();	
		}
		fw = new FileWriter(file);
		fw.write(this.buffer.toString());
		//因为系统将维持此bean实例，所以应该将buffer清空，否则将产生冗余数据
		this.buffer.delete(0, this.buffer.length());
		fw.close();
		return newDataSet;
	}
	/*
	 * 
	 */
	public String getDataSet() {
		return dataSet;
	}
	public void setDataSet(String dataSet) {
		this.dataSet = dataSet;
	}
	public String getDriverClassName() {
		return driverClassName;
	}
	public void setDriverClassName(String driverClassName) {
		this.driverClassName = driverClassName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	
	
	
	
	
	
	
	
	
	// inner class

	class XmlFile extends InitDataConfStructure{
		
	}
	class Table extends InitDataConfStructure{
		
	}
	class Column extends InitDataConfStructure{
		
	}
	
	class Row extends InitDataConfStructure{
		
	}
	class Value extends InitDataConfStructure{
//		最深层不能再调用父类的toString，需要进行覆盖，否则产生空指针异常

		@Override
		public String toString() {
			List ls = this.getValues();
			Iterator it = ls.iterator();
			while(it.hasNext()){
				String strValue = (String)it.next();
				return this.generateSpace()+this.getPrefix()+strValue+this.getSuffix();
			}
			return null;
		}
	}
	
	public void setStrTableNames(String strTableNames) {
		this.strTableNames = strTableNames;
	}
	public String getSimpleFileName() {
		return simpleFileName;
	}
	public void setSimpleFileName(String simpleFileName) {
		this.simpleFileName = simpleFileName;
	}
	
}
