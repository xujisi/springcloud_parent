package util;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.collections.map.ListOrderedMap;

import javax.servlet.http.HttpServletResponse;
import java.util.*;

/**
 * @version V1.0
 * @Title:处理json的工具类.
 * @ClassName: com.spyair.blog.util.JsonUtil.java
 * @Description:
 * @Copyright 许集思的Springboot
 * @author: 许集思
 * @date: 2020/3/16 22:03
 */

public class JsonUtil {

    /**
     * @Title:Json转为List
     * @MethodName: parseJSON2List
     * @Param: * @param jsonStr
     * @Return java.util.List<java.util.Map>                                                                                                                                                                                                                                                                                                                                                                                                                                                                            java.lang.String                                                                                                                                                                                                                                                               ,                                                                                                                                                                                                                                                               java.lang.Object>>
     * @Exception
     * @author: 许集思
     * @date: 2020/3/16 22:15
     */
    public static List<Map<String, Object>> parseJSON2List(String jsonStr) {
        JSONArray jsonArr = JSONArray.fromObject(jsonStr);
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        Iterator<JSONObject> it = jsonArr.iterator();
        while (it.hasNext()) {
            JSONObject json2 = it.next();
            list.add(parseJSON2Map(json2.toString()));
        }
        return list;
    }

    /**
     * @Title:json转为map.
     * @MethodName: parseJSON2List
     * @Param: * @param jsonStr
     * @Return java.util.List<java.util.Map>                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                            java.lang.String                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                               ,                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                               java.lang.Object>>
     * @Exception
     * @author: 许集思
     * @date: 2020/3/16 22:15
     */
    public static Map<String, Object> parseJSON2Map(String jsonStr) {
        ListOrderedMap map = new ListOrderedMap();
        //最外层解析
        JSONObject json = JSONObject.fromObject(jsonStr);
        for (Object k : json.keySet()) {
            Object v = json.get(k);
            //如果内层还是数组的话，继续解析
            if (v instanceof JSONArray) {
                List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
                Iterator<JSONObject> it = ((JSONArray) v).iterator();
                while (it.hasNext()) {
                    JSONObject json2 = it.next();
                    list.add(parseJSON2Map(json2.toString()));
                }
                map.put(k.toString(), list);
            } else {
                map.put(k.toString(), v);
            }
        }
        return map;
    }

    /**
     * @Title:map转为json.
     * @MethodName: mapToJson
     * @Param: * @param map
     * @Return java.lang.String
     * @Exception
     * @author: 许集思
     * @date: 2020/3/16 22:16
     */
    public static String mapToJson(Map<String, String> map) {
        Set<String> keys = map.keySet();
        String key = "";
        String value = "";
        StringBuffer jsonBuffer = new StringBuffer();
        jsonBuffer.append("{");
        for (Iterator<String> it = keys.iterator(); it.hasNext(); ) {
            key = (String) it.next();
            value = map.get(key);
            jsonBuffer.append(key + ":" + "\"" + value + "\"");
            if (it.hasNext()) {
                jsonBuffer.append(",");
            }
        }
        jsonBuffer.append("}");
        return jsonBuffer.toString();
    }

    /**
     * @Title:将对象序列转化为JSON实体
     * @MethodName: toJSONObjectString
     * @Param: * @param object
     * @Return java.lang.String
     * @Exception
     * @author: 许集思
     * @date: 2020/3/16 22:36
     */
    public static JSONObject toJSONObject(Object object) throws Exception {
        return JSONObject.fromObject(object);
    }

    /**
     * @Title:将对象序列转化为JSON文本
     * @MethodName: toJSONObjectString
     * @Param: * @param object
     * @Return java.lang.String
     * @Exception
     * @author: 许集思
     * @date: 2020/3/16 22:36
     */
    public static String toJSONString(JSONObject jsonObject) throws Exception {
        return jsonObject.toString();
    }

    /**
     * @Title:将对象序列转化为JSONobject文本
     * @MethodName: toJSONObjectString
     * @Param: * @param object
     * @Return java.lang.String
     * @Exception
     * @author: 许集思
     * @date: 2020/3/16 22:36
     */
    public static String toJSONObjectString(Object object) throws Exception {
        return toJSONString(toJSONObject(object));
    }

    public static void getAjaxResult(HttpServletResponse response, Object object) throws Exception {
        try {
            response.setContentType("text/html;charset=UTF-8");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().print(toJSONObjectString(object));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}



