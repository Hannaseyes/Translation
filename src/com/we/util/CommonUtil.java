package com.we.util;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Random;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.Logger;

/**
 * 常用公共方法
 * */
public class CommonUtil {
	private static final Logger logger = Logger.getLogger(CommonUtil.class);
	
		/**
		 * 本方法是将页面传递过来的参数quest.getParameter("")封装在MAP中，最好为LinkedHashMap的进行封装
		 * hashmap是没有排序的，而LinkedHashMap是按着放入参数的顺序组合出来的字符串
		 * @param map
		 * @return 返回组合好的参数拼串
		 */
		public static String getParam(Map<String,Object> map){
			
			LogUtil.getStart("CommonUtil.getParam", "开始拼装参数方法", "CommonUtil", "");
			StringBuffer sb = new StringBuffer();
			String param="";
		
				try {
					for (String key : map.keySet()) {
					   System.out.println("key= "+ key + " and value= " + map.get(key));
					   
					   sb.append(key);
					   sb.append("=");
					   sb.append( map.get(key));
					   sb.append("&");
					   
					 }
					if(!"".equals(sb.toString().trim()) && sb.toString().trim()!=null){
						 param = sb.toString().substring(0, sb.length()-1);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
				LogUtil.getEnd("CommonUtil.getParam", "结束拼装参数方法", "CommonUtil");
			return param;
			
		}
		
	
	
	/**
	 * @return (00000000+str)返回包括报文长度的字符串
	 * @param str action中返回的字符串
	 * @param len 表示JSON前面的8位的"报文长度"
	 * */
	public static String countRtnByte(String str, int len) {
		logger.debug(">>>>>>>>不包含00000000:" + str);
		byte[] byteStr = null;
		try {
			byteStr = str.getBytes("UTF-8");
		} catch (UnsupportedEncodingException e) {
			logger.debug("字符串不是UTF-8编码格式");
		}
		
		int length = byteStr.length;//报文体长度
		int lengthTotal = length + len;//报文总长度
		
		String add0Str = addStr(lengthTotal + "", len);
		return add0Str + str;
	}
	
	/**
	 * 读取Properties属性文件:
	 * fileName是以src为根目录的文件名称,例:src/config/deal/dealConfig.properties
	 * */
	public static Properties parseProperties(Class<?> inputClass, String fileName) {
		logger.debug("文件名称是:" + fileName);
		Properties props = new Properties();
		
		//method one
//		String path = inputClass.getClass().getResource("/").getPath().substring(1);
//		String filePath = path.substring(0, (path.length() - 4)) + fileName;
		
		//method two
		String filePath = System.getProperty("user.dir").toString() + fileName;
		
		logger.debug("文件路径是:" + filePath);
		
		try {
			props.load(new FileInputStream(filePath));
		} catch (FileNotFoundException e) {
			logger.error("/CommonUtil/parseProperties 异常:" + e.getMessage());
		} catch (IOException e) {
			logger.error("/CommonUtil/parseProperties 异常:" + e.getMessage());
		}
		return props;
	}
	
	/**
	 * 补齐len位,不足左补0
	 * */
	public static String addStr(String str, int len) {
		String strRtn = "";
		for(int i = 0; i < (len - str.length()); i++) {
			strRtn += "0";
		}
		strRtn = strRtn + str;
		return strRtn;
	}
	
	/**
	 * 构建json字符串
	 * @param transCode 交易码
	 * @param sysuserid 操作人ID
	 * @param pageModel 报文体Model
	 * @return
	 */
	public static String constructJsonStr(String transCode, String sysuserid, 
			Map<String, Object> pageModel) throws Exception{
		// 系统时间
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddhhmmss");
		String sysdate = sdf.format(new Date());
		
		pageModel.put("iface", transCode);
		pageModel.put("sysuserid", sysuserid);
		pageModel.put("sysdate", sysdate);
		
		return JsonUtil.getJsonStringFromMap(pageModel);
	}
	
	/**
	 * 调用核心接口
	 * @param jsonStr
	 * @return
	 */
/*	public static String getCoreValue(String jsonStr) throws Exception{
		TcpMinaClient tcpMinaClient = new TcpMinaClient();
		String rtnStr = tcpMinaClient.sendMessage(jsonStr);
		logger.debug(">>>>>>>responsemessage:" + rtnStr);
		
		rtnStr = rtnStr.substring(8, rtnStr.length());
		
		return rtnStr;
	}*/
	

//	/**
//	 * 把传入的对象转成json
//	 * */
//	public static String createJson(Object obj) throws Exception {
//		ObjectMapper objectMapper = new ObjectMapper();
//		return objectMapper.writeValueAsString(obj);
//	}
	
	/*
	/**
	 * 获取随机验证码:随机产生的6位数字
	 */
	public static String getRandomCode() {
		// 获取验证码
		String randomCode = "";
		// 验证码字符个数
		int codeCount = 6;
		// 创建一个随机数生成器类
		Random random = new Random();
		// 随机产生codeCount数字的验证码
		for (int i = 0; i < codeCount; i++) {
			// 得到随机产生的验证码数字
			String strRand = String.valueOf(random.nextInt(10));
			randomCode += strRand;
		}
		return randomCode;
	}

	/**
	 * 生成订单号
	 * 
	 * 订单号格式:20140510(日期) + 1399540001193(当时系统的毫秒数)
	 * 
	 * */
	public static String createTime() {
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		String formatStr = sdf.format(date);//20140508
		String dateStr = date.getTime() + "";//1399540001193
		String now = formatStr + dateStr;
		
		return now;
	}
	
	/**
	 * 设置返回rescode、resmsg
	 * 
	 * */
	public static String setResultInfo(Map<String, Object> map, 
			String rescode, String resmsg) {
		map.put("rescode", rescode);
		map.put("resmsg", resmsg);
		// 处理返回结果
		return JsonUtil.getJsonStringFromMap(map);
	}
	
	/**
	 * 设置返回rescode、resmsg、resmsg_cn
	 * 
	 * */
	public static String setResultStringCn(Map<String, Object> map, 
			String rescode, String resmsg,String resmsgCn) {
		map.put("rescode", rescode);
		map.put("resmsg", resmsg);
		map.put("resmsg_cn", resmsgCn);
		// 处理返回结果
		return JsonUtil.getJsonStringFromMap(map);
	}
	
	/**
	 * 金额判断大小
	 * 
	 * */
	public static int checkAmount(BigDecimal amount1, BigDecimal amount2){
		//-1表示小于，0是等于，1是大于
		return amount1.compareTo(amount2);
	}
	
	/**
	 * 年龄判断
	 * 
	 * */
	public static int checkAge(int compareObj1,int compareObj2){
		Calendar calendar = Calendar.getInstance();
	    calendar.add(Calendar.YEAR, -compareObj2);
	    SimpleDateFormat sf = new SimpleDateFormat("yyyyMMdd");
        String str = sf.format(calendar.getTime());
        if(Integer.parseInt(str) == compareObj1){
        	return 0;//等于
        }else if(Integer.parseInt(str) > compareObj1){
        	return 1;//大于
        }else{
        	return -1;//小于
        }
	}
	/**
	 * 获取系统时间
	 * 
	 * */
	public static String getNow(String formatStr){
		Calendar calendar = Calendar.getInstance();
	    SimpleDateFormat sf = new SimpleDateFormat(formatStr);
        String str = sf.format(calendar.getTime());
        return str;
	}

	/**
	 * 获取uuid
	 * 
	 * */
	public static String getUuid(){
		UUID uuid = UUID.randomUUID();
		String str = uuid.toString(); 
        // 去掉"-"符号   
        String temp = str.substring(0, 8) + str.substring(9, 13) + str.substring(14, 18) + str.substring(19, 23) + str.substring(24); 
        return temp; 
	}
	
	/**
	 * 获取客户端真实IP
	 * @param args
	 * @throws Exception
	 */
	public static String getClientIP(HttpServletRequest request) {
		String ip = request.getHeader("X-Real-IP");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		return ip;
	}
	
	/**
	 * 向指定 URL 发送POST方法的请求
	 * 
	 * @param url
	 *            发送请求的 URL
	 * @param param
	 *            请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
	 * @return 所代表远程资源的响应结果
	 */
	public static String sendPostByHttp(String url, String param) {
		PrintWriter out = null;
		BufferedReader in = null;
		String result = "";
		try {
			URL realUrl = new URL(url);
			// 打开和URL之间的连接
			URLConnection conn = realUrl.openConnection();
			// 设置通用的请求属性
			conn.setRequestProperty("accept", "*/*");
			conn.setRequestProperty("connection", "Keep-Alive");
			conn.setRequestProperty("user-agent",
					"Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
			conn.setRequestProperty("Accept-Charset", "UTF-8");
			conn.setRequestProperty("contentType", "UTF-8");
			// 发送POST请求必须设置如下两行
			conn.setDoOutput(true);
			conn.setDoInput(true);
			// 获取URLConnection对象对应的输出流
			out = new PrintWriter(conn.getOutputStream());
			// 发送请求参数
			out.print(param);
			// flush输出流的缓冲
			out.flush();
			// 定义BufferedReader输入流来读取URL的响应
			in = new BufferedReader(
					new InputStreamReader(conn.getInputStream(),"UTF-8"));
			String line;
			while ((line = in.readLine()) != null) {
				result += line;
			}
		} catch (Exception e) {
			logger.error("发送Http的POST 请求出现异常！" + e);
		}
		// 使用finally块来关闭输出流、输入流
		finally {
			try {
				if (out != null) {
					out.close();
				}
				if (in != null) {
					in.close();
				}
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		return result;
	}
	
	/**
	 * Map的数据转换成POST请求参数的格式。 name1=value1&name2=value2 的形式。
	 */
	public static String getHttpParameterFromMap(Map<String, String> map) {
		StringBuffer stringBuffer = new StringBuffer();

		for (Map.Entry<String, String> entry : map.entrySet()) {
			try {
				stringBuffer.append(entry.getKey()).append("=")
						.append(entry.getValue())
						.append("&");

			} catch (Exception e) {
				logger.error("转换POST请求参数格式出现异常！" + e);
			}
		}
		stringBuffer.deleteCharAt(stringBuffer.length() - 1);
		
		logger.debug("转换后的POST请求参数串：" + stringBuffer.toString());
		return stringBuffer.toString();
	}
	 /**
     * 
     * 功能描述：获取真实的IP地址
     * @param request
     * @return
     * @author guoyx
     */
    public static String getIpAddr(HttpServletRequest request)
    {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip))
        {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip))
        {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip))
        {
            ip = request.getRemoteAddr();
        }
        if (!isnull(ip) && ip.contains(","))
        {
            String[] ips = ip.split(",");
            ip = ips[ips.length - 1];
        }
        
        return ip;
    }
    public static boolean isnull(String str)
    {
        if (null == str || str.equalsIgnoreCase("null") || str.equals(""))
        {
            return true;
        } else
            return false;
    }
	public static void main(String[] args) throws Exception{
//		List<String> ids = new ArrayList<String>();
//		ids.add("1");
//		ids.add("2");
//		ids.add("3");
//		ids.add("4");
//		ids.add("5");
//		
//		String sql = getOracleSQLIn(ids,ids.size(),"CREC.CREDIT_ACCT_CID");
//		System.out.print(sql);
		//getUuid();
		Map<String,Object> map =new LinkedHashMap<String,Object>();
		map.put("userName", "cht");
		map.put("password", "123456");
		map.put("channel", "1");
		map.put("setupFlag", "1");
		map.put("invitationCode", "dfs456");
		map.put("checkCode", "dsdfs3");
		System.out.println(getParam(map));
	}

}
