package com.jaf.jcore;


//import org.codehaus.jackson.JsonParseException;
//import org.codehaus.jackson.map.JsonMappingException;
//import org.codehaus.jackson.map.ObjectMapper;


import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;



public class JacksonWrapper {
	
	/**
	 * 读取jsonobject 数据 然后转换为对象处理
	 * @param obj json对象
	 * @param valueType 保存的对象类型
	 * @return
	 */
	public static <T> T json2Bean(JSONObject obj, Class<T> valueType) {
		ObjectMapper objectMapper = new ObjectMapper();
		T ret = null;
		try {
			ret = objectMapper.readValue(obj.toString(), valueType);
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return ret;
	}

    public static JSONObject bean2Json(Object o) {
        ObjectMapper objectMapper = new ObjectMapper();
        JSONObject ret = null;
        try {
            ret = new JSONObject(objectMapper.writeValueAsString(o));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return ret;
    }
	
	/**
	 * 读取json 文件 然后转换为对象处理
	 * @param file json文件
	 * @param valueType 保存的对象类型
	 * @return
	 */
	public static <T> T file2Bean(File file, Class<T> valueType) {
		ObjectMapper objectMapper = new ObjectMapper();
		T ret = null;
		try {
			ret = objectMapper.readValue(file, valueType);
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return ret;
	}
	
	
	/**
//	 *  bean到json时。默认是转换成timestamp类型的，即相对1970年1月1日的毫秒数。 
//    	 *	可以进行设置，设置成你想要的格式。 
// 	 *
//	 *	objectMapper.configure(SerializationConfig.Feature.WRITE_DATES_AS_TIMESTAMPS, false);//关闭时间戳输出，此时是ISO格式  
//    	 *	objectMapper.setDateFormat(myDateFormat);//设置自己的格式  
//	 * 
//	 */
//	public static JSONObject bean2JSONObject(Object obj) {
//		ObjectMapper mapper = new ObjectMapper();
//		String json = "";
//		try {
//			json = mapper.writeValueAsString(obj);
//		} catch (JsonGenerationException e) {
//			e.printStackTrace();
//		} catch (JsonMappingException e) {
//			e.printStackTrace();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//		return JSONUtil.createJSONObject(json);
//	}
	
}
