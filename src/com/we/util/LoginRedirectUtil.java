package com.we.util;

import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.core.io.support.PropertiesLoaderUtils;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * session失效时设置重定向URL
 * @author wangenlai
 */
public class LoginRedirectUtil {
	private static final Log logger = LogFactory.getLog(LoginRedirectUtil.class);
	/**
	 * 非SSL本地使用
	 */
	public static String URL = "";//本地无证书切换使用
	public static String URLS = "";//java后台使用
	public static String interfacePath = "";//java后台使用
	static{
		try {
			Properties  props=PropertiesLoaderUtils.loadAllProperties("config.properties");
			URL = props.getProperty("URL");
			URLS = props.getProperty("URLS");
			interfacePath = props.getProperty("interfacePath");
			if(URL==null){
				URL = "";
			}
			if(URLS==null){
				URLS = "";
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("读取配置文件config.properties失败");
		}
	}
	/**
	 * session失效时设置重定向URL
	 */
	public static void getRedirectUrl(HttpServletRequest request, RedirectAttributes attr){
//		StringBuffer url = request.getRequestURL();
		String uri = request.getRequestURI();
		if(uri != null && uri.length() > 8){
			String url = uri.substring(8);
			String paramters = request.getQueryString();
			String edaifrom = url;
			if(paramters != null && !"".equals(paramters) && !"null".equals(edaifrom))
				edaifrom = edaifrom + "?" +paramters;
			attr.addFlashAttribute("edaifrom", edaifrom);
		}
	}
	
	/**
	 * 未登录状态下，返回rescode:00002
	 */
	public static void notLoggedIn(PrintWriter out){
		try {
			JSONObject jsonObject =new JSONObject();
			jsonObject.put("rescode", "00002");
			String data = new String(jsonObject.toString().getBytes("utf-8"),"ISO8859-1");
			out.println(data);
			out.flush();
			out.close();
		} catch (UnsupportedEncodingException e) {
			logger.debug(e);
			e.printStackTrace();
		}
	}
}
