package com.we.actions;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.we.util.CommonUtil;
import com.we.util.DES3Util;
import com.we.util.HttpRequestParam;
import com.we.util.JsonUtil;
import com.we.util.LoginRedirectUtil;
import com.we.util.StringConst;

public class BaseAction {
	
	private static final Log logger = LogFactory.getLog(BaseAction.class);
	
	/**
	 * 下面方法是取得项目路径的方法具体如下
	 * request.getServerName() 通过这个方法可以得到主机的地址 例如：http://127.0.0.1
	 * request.getContextPath() 通过这个方法可以得到项目名称 例如：javaservice
	 * @param request
	 * @return
	 */
	public String getProjetUrl(HttpServletRequest request){
		String strBackUrl = "http://" + request.getServerName()+request.getContextPath();    
		
		return strBackUrl;
	}
	
	/**
	 * 解析请求参数 经过解码decode的
	 **/
	@SuppressWarnings("unchecked")
    public static Map<String, String> decryptParams(HttpServletRequest request) {
		Map<String, String> rtnMap = new HashMap<String, String>();
		logger.info(getParamMap(request));
		
		try {
			// 获取加密后的报文
			String params = request.getParameter("argEncPara");
			logger.debug(">>>接收到的加密参数串：" + params);
			if (StringUtils.isNotBlank(params)) {
				// 解密并解码3DES+Base64得到json串
				params = DES3Util.decodeApp(params);
				logger.debug(">>>解密后参数：" + params);

				// 解析json得到Map
				rtnMap = JsonUtil.getMapFromJsonString(params);
				logger.debug(">>>得到的map：" + rtnMap);
			}
		} catch (Exception e) {
			logger.debug("请求参数解析异常：" + e.getMessage());
		}
		
		return rtnMap;
	}
	
	/**
	 * 获取post参数request.getParameterMap();(返回值时Map) 把request的Map参数转换成普通的Map
	 * */
	public static Map<String, String> getParamMap(HttpServletRequest request) {
		Map<String, String[]> properties = request.getParameterMap();// 参数Map
		Map<String, String> returnMap = new HashMap<String, String>();// 返回值Map

		Iterator<Entry<String, String[]>> entries = properties.entrySet().iterator();
		Map.Entry<String, String[]> entry;
		String name = "";
		String value = "";
		
		while (entries.hasNext()) {
			entry = (Map.Entry<String, String[]>) entries.next();
			name = (String) entry.getKey();

			Object valueObj = entry.getValue();
			if (null == valueObj) {
				value = "";
			} else if (valueObj instanceof String[]) {
				String[] values = (String[]) valueObj;
				for (int i = 0; i < values.length; i++) {
					value = values[i] + ",";
				}
				value = value.substring(0, value.length() - 1);
			} else {
				value = valueObj.toString();
			}
			returnMap.put(name, value);
		}
		
		return returnMap;
	}
	
	public static Map<String, Object> setResCode(String rescode, String resmsg, String resmsg_cn) {
		Map<String, Object> outMap = new HashMap<String, Object>();
		outMap.put(StringConst.RESCODE, rescode);
		outMap.put(StringConst.RESMSG, resmsg);
		outMap.put(StringConst.RESMSG_CN, resmsg_cn);
		
		return outMap;
	}
	
	/**
	 * 返回加密后结果
	 * @param obj
	 */
	public void setResponseEncrypt(Object obj, HttpServletResponse response) {
		// 转换成JSONObject
		JSONObject jsonObj = JSONObject.fromObject(obj);
		String result = jsonObj.toString();
		/*try {
			logger.debug(">>>加密前："+result);
			// 加密返回json串
			result = DES3Util.encodeApp(result);
			logger.debug(">>>加密后的result："+result);
		} catch (Exception e) {
			logger.debug(">>>>>>>>>>>>>>>返回结果加密异常：" + e.getMessage());
		}*/
		
		response(result, response);
	}
	
	
	/**
	 * json返回客户端
	 **/
	private void response(String result, HttpServletResponse response){
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html; charset=utf-8");
		
		try{
			PrintWriter out = response.getWriter();
			out.print(result);
			out.flush();
			out.close();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	/**
	 * 请求javaservice 
	 **/
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public JSONObject sendRequest(String url, Map map){
		String param =	CommonUtil.getParam(map);
		
		try {
			param =DES3Util.encode(param);
		} catch (Exception e) {
			logger.info("加密失败:"+e.getMessage());
			e.printStackTrace();
		}
		
//		String  resultMsg =	HttpRequestParam.sendGet(LoginRedirectUtil.interfacePath+"/loan/getLoanList", param);	
		String  resultMsg =	HttpRequestParam.sendGet(LoginRedirectUtil.interfacePath + url, param);
	    try {
		   resultMsg = java.net.URLDecoder.decode(DES3Util.decode(resultMsg),"UTF-8");
		} catch (Exception e) {
			logger.info("解密失败:"+e.getMessage());
			e.printStackTrace();
		}
	    
	    return JSONObject.fromObject(resultMsg);
	}
	
	/**
	 * 校验参数是否为空
	 * @param param
	 * @return
	 */
	public boolean checkParamNull(String param) {
		if (null == param || "".equals(param)) {
			return true;
		} else {
			return false;
		}
	}
	
	public static Map<String, Object> setResCodeOk(String rescode, String resmsg, String resmsg_cn,String result) {
		Map<String, Object> outMap = new HashMap<String, Object>();
		outMap.put(StringConst.RESCODE, rescode);
		outMap.put(StringConst.RESMSG, resmsg);
		outMap.put(StringConst.RESMSG_CN, resmsg_cn);
		JSONObject resultMsg = JSONObject.fromObject(result);
		outMap.put("data", resultMsg);
		return outMap;
	}
}
