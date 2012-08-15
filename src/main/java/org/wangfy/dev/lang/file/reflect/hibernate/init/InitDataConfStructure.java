package org.wangfy.dev.lang.file.reflect.hibernate.init;
/**
 * @author wangfuye
 * @Definition the configure file structure
 */
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class InitDataConfStructure {
	private  String prefix;
	private  String suffix;	
	private  List values = new ArrayList();
	private boolean newLine = true;
	private int spaceCount = 0;
	
	public void addValue(Object obj){
		this.values.add(obj);
	}
	public void delValue(Object obj){
		this.values.remove(obj);
	}
	public String formatValues(){
		StringBuffer buffer = new StringBuffer();

		for(Iterator it = this.values.iterator(); it.hasNext();){	
			Object o = it.next();
			buffer.append(o.toString());
		}
		return buffer.toString();
	}
	public String generateSpace(){
		String newLine="\n";
		String space = "";
		for(int i = 0; i < spaceCount; i++){
			space += "\t";
		}
		space = newLine + space;
		return space;
	}
	@Override
	public String toString() {
		if(this.newLine)
			return this.generateSpace()+this.prefix+this.formatValues()+this.generateSpace()+this.suffix;
		else
			return this.generateSpace()+this.prefix+this.formatValues()+this.suffix;
	}
	public boolean isNewLine() {
		return newLine;
	}
	public void setNewLine(boolean newLine) {
		this.newLine = newLine;
	}
	public String getPrefix() {
		return prefix;
	}
	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}
	public String getSuffix() {
		return suffix;
	}
	public void setSuffix(String suffix) {
		this.suffix = suffix;
	}
	public List getValues() {
		return values;
	}
	public void setValues(List values) {
		this.values = values;
	}
	public int getSpaceCount() {
		return spaceCount;
	}
	public void setSpaceCount(int spaceCount) {
		this.spaceCount = spaceCount;
	}

}
