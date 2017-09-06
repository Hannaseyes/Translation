package com.we.util;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class TokenHandler {
	private static Log logger = LogFactory.getLog(TokenHandler.class);
	 
    static Map<String, String> springmvc_token = null;
     
        //生成一个唯一值的token
    @SuppressWarnings("unchecked")
    public synchronized static String generateGUID(HttpSession session) {
        String token = "";
        try {
            Object obj =  session.getAttribute("SPRINGMVC.TOKEN");
            if(obj!=null)
                springmvc_token = (Map<String,String>)session.getAttribute("SPRINGMVC.TOKEN");
           else
               springmvc_token = new HashMap<String, String>();
            token = new BigInteger(165, new Random()).toString(36)
                    .toUpperCase();
            springmvc_token.put(Constants.DEFAULT_TOKEN_NAME + "." + token,token);
            session.setAttribute("SPRINGMVC.TOKEN", springmvc_token);
            Constants.TOKEN_VALUE = token;
 
        } catch (IllegalStateException e) {
            logger.error("generateGUID() mothod find bug,by token session...");
        }
        return token;
    }
 
       //验证表单token值和session中的token值是否一致
    @SuppressWarnings("unchecked")
    public static boolean validToken(HttpServletRequest request) {
        String inputToken = getInputToken(request);
 
        if (inputToken == null) {
            logger.warn("token is not valid!inputToken is NULL");
            return false;
        }
 
        HttpSession session = request.getSession();
        Map<String, String> tokenMap = (Map<String, String>)session.getAttribute("SPRINGMVC.TOKEN");
        if (tokenMap == null || tokenMap.size() < 1) {
            logger.warn("token is not valid!sessionToken is NULL");
            return false;
        }
        String sessionToken = tokenMap.get(Constants.DEFAULT_TOKEN_NAME + "."
                + inputToken);
        System.out.println("%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%="+sessionToken);
        if (!inputToken.equals(sessionToken)) {
            logger.warn("token is not valid!inputToken='" + inputToken
                    + "',sessionToken = '" + sessionToken + "'");
            return false;
        }
        tokenMap.remove(Constants.DEFAULT_TOKEN_NAME + "." + inputToken);
        session.setAttribute("SPRINGMVC.TOKEN", tokenMap);
 
        return true;
    }
 
        //获取表单中token值
    @SuppressWarnings("unchecked")
    public static String getInputToken(HttpServletRequest request) {
        Map params = request.getParameterMap();
 
        if (!params.containsKey(Constants.DEFAULT_TOKEN_NAME)) {
            logger.warn("Could not find token name in params.");
            return null;
        }
 
        String[] tokens = (String[]) (String[]) params
                .get(Constants.DEFAULT_TOKEN_NAME);
 
        if ((tokens == null) || (tokens.length < 1)) {
            logger.warn("Got a null or empty token name.");
            return null;
        }
 
        return tokens[0];
    }
}
