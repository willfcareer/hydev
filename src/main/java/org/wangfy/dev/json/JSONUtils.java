package org.wangfy.dev.json;

import java.io.IOException;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.type.TypeReference;

public class JSONUtils {

	private static final ObjectMapper objectMapper = new ObjectMapper();

	static {
		objectMapper.setSerializationInclusion(JsonSerialize.Inclusion.NON_NULL);
	}

	public static ObjectMapper getObjectMapper(){
		return objectMapper;
	}
	@SuppressWarnings("unchecked")
	public static <T> T parse(String data, TypeReference<T> typeRef) throws IOException {
		return (T) objectMapper.readValue(data, typeRef);
	}

	public static <T> T parse(String data, Class<T> clazz) throws IOException {
		return (T) objectMapper.readValue(data, clazz);
	}

	public static JsonNode parse(String data) throws IOException {
		return objectMapper.readTree(data);
	}

	public static String writeValue(Object value) throws IOException {
		return objectMapper.writeValueAsString(value);
	}
}
