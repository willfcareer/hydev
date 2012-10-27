package org.wangfy.dev.json;

import org.codehaus.jackson.JsonEncoding;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.map.ObjectMapper;
import org.junit.Test;

public class JacksonTest {

	@Test
	public void testJsonGenerator() {
		ObjectMapper mapper = JSONUtils.getObjectMapper();
		try {
			JsonGenerator jsonGenerator = mapper.getJsonFactory().createJsonGenerator(System.out, JsonEncoding.UTF8);
			String[] array = new String[]{"a,b,c"};
			String msg = "Hello jackson";
		       //Object
	        jsonGenerator.writeStartObject();//{
	        jsonGenerator.writeObjectFieldStart("user");//user:{
	        jsonGenerator.writeStringField("name", "jackson");//name:jackson
	        jsonGenerator.writeBooleanField("sex", true);//sex:true
	        jsonGenerator.writeNumberField("age", 22);//age:22
	        jsonGenerator.writeObjectFieldStart("subAge");//subAge:{
	        jsonGenerator.writeStringField("ageName", "jackson");//name:jackson
	        jsonGenerator.writeEndObject();//}
	        jsonGenerator.writeEndObject();//}
	        
	        jsonGenerator.writeArrayFieldStart("infos");//infos:[
	        jsonGenerator.writeNumber(22);//22
	        jsonGenerator.writeString("this is array");//this is array
	        jsonGenerator.writeEndArray();//]
	        
			jsonGenerator.flush();
			jsonGenerator.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

class AccountBean {
	private int id;
	private String name;
	private String email;
	private String address;
	private Birthday birthday;

	// getter、setter

	@Override
	public String toString() {
		return this.name + "#" + this.id + "#" + this.address + "#" + this.birthday + "#" + this.email;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Birthday getBirthday() {
		return birthday;
	}

	public void setBirthday(Birthday birthday) {
		this.birthday = birthday;
	}
}

class Birthday {
	private String birthday;

	public Birthday(String birthday) {
		super();
		this.birthday = birthday;
	}

	// getter、setter

	public Birthday() {
	}

	@Override
	public String toString() {
		return this.birthday;
	}
}
