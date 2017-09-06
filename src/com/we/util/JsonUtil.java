package com.we.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.util.JSONStringer;


public class JsonUtil {  
	private static final Logger logger = Logger.getLogger(JsonUtil.class);
    /** 
     * 从一个JSON 对象字符格式中得到一个java对象 说明：Bean的无参构造函数一定要写, 否则会报: 
     * net.sf.json.JSONException: java.lang.NoSuchMethodException 
     *  
     * @param jsonString 
     * @param pojoCalss 
     * @return 
     */  
    public static Object getObjectFromJsonString(String jsonString,  
            Class pojoCalss) {  
        Object pojo;  
        JSONObject jsonObject = JSONObject.fromObject(jsonString);  
        pojo = JSONObject.toBean(jsonObject, pojoCalss);  
        return pojo;  
    }  
  
    /** 
     * 将java对象转换成json字符串 
     *  
     * @param javaObj 
     * @return 
     */  
    public static String getJsonStringFromObject(Object javaObj) {  
        JSONObject json;  
        json = JSONObject.fromObject(javaObj);  
        return json.toString();  
    }  
  
    /** 
     * 从json HASH表达式中获取一个map 
     *  
     * @param jsonString 
     * @return 
     */  
    @SuppressWarnings("unchecked")  
    public static Map getMapFromJsonString(String jsonString) {  
        JSONObject jsonObject = JSONObject.fromObject(jsonString);  
        Iterator keyIter = jsonObject.keys();  
        String key;  
        Object value;  
        Map valueMap = new HashMap();  
        while (keyIter.hasNext()) {  
            key = (String) keyIter.next();  
            value = jsonObject.get(key);  
            valueMap.put(key, value);  
        }  
        return valueMap;  
    }  
  
    /** 
     * 从Map对象得到Json字串 
     *  
     * @param map 
     * @return 
     */  
    public static String getJsonStringFromMap(Map map) {  
    	return JSONObject.fromObject(map).toString();  
    }  
  
    /** 
     * 从json字串中得到相应java数组 
     *  
     * @param jsonString 
     *            like "[\"李斯\",100]" 
     * @return 
     */  
    public static Object[] getObjectArrayFromJsonString(String jsonString) {  
        JSONArray jsonArray = JSONArray.fromObject(jsonString);  
        return jsonArray.toArray();  
    }  
  
    /** 
     * 将list转换成Array 
     *  
     * @param list 
     * @return 
     */  
    public static Object[] getObjectArrayFromList(List list) {  
        JSONArray jsonArray = JSONArray.fromObject(list);  
        return jsonArray.toArray();  
    }  
  
    /** 
     * 用JSONStringer构造一个JsonString 
     *  
     * @param m 
     * @return 
     */  
    public static String buildJsonString(Map m) {  
        JSONStringer stringer = new JSONStringer();  
        stringer.object();  
        for (Object key : m.keySet()) {  
            stringer.key((String) key)  
                .value((String)m.get(key));  
        }  
        stringer.key("phone");  
        //begin nesting a array  
        stringer.array();  
        stringer.value("13998098000");  
        stringer.value("8765432");  
        //nestring object in array  
        stringer.object();  
        stringer.key("ppcall");  
        stringer.value(53881);   
        stringer.endObject();  
        stringer.value("13990980980");  
        //end nesting a array  
        stringer.endArray();  
          
        stringer.endObject();  
        return stringer.toString();  
    }  
  
    public static void printMap(Map map) {  
        for (Object key : map.keySet()) {  
            System.out.println(key + ":" + map.get(key));  
        }  
    }  
  
    public static void main(String[] args) {  
        Map m = new HashMap() {  
            {  
                put("JSon", "HelloWorld");  
                put("Flex", "Ok");  
            }  
        };  
        System.out.println(buildJsonString(m));  
        System.out  
                .println(new JSONStringer().object().key("JSON").value(  
                        "Hello, World!").key("Flex").value("OK").endObject()  
                        .toString());  
    } 
    /**
     * 将json数组转化为list类型
     * 例子：[{"ZDMC":"申请部门"},{"ZDMC":"广西分公司-南宁办共同1","ZDSZ":"空调1"}]转化为list-Map集合
     * @param object
     * @return
     */
    public static List<Map<String, Object>> toList(Object object)  
    {  
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();  
        JSONArray jsonArray = JSONArray.fromObject(object);  
        for (Object obj : jsonArray)  
        {  
            JSONObject jsonObject = (JSONObject) obj;  
            Map<String, Object> map = new HashMap<String, Object>();  
            Iterator<?> it = jsonObject.keys();  
            while (it.hasNext())  
            {  
                String key = (String) it.next();  
                Object value = jsonObject.get(key);  
                map.put((String) key, value);  
            }  
            list.add(map);  
        }  
        return list;  
    }  
}  