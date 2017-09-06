/*
 *
 * 2012-3-30
 * 
 */
package com.we.util;

import java.security.NoSuchAlgorithmException;

import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.security.authentication.encoding.ShaPasswordEncoder;

/**
 * MD5加密
 * @author cht
 */
public class MD5 {
	/**
	 * false 表示：生成32位的Hex版,
	 *  这也是encodeHashAsBase64的,
	 *  Acegi 默认配置; true  
	 *  表示：生成24位的Base64版    
	 */
	 public static String md5(String passwd) {     
	        Md5PasswordEncoder md5 = new Md5PasswordEncoder();     
	        // false 表示：生成32位的Hex版, 这也是encodeHashAsBase64的, Acegi 默认配置; true  表示：生成24位的Base64版     
	        md5.setEncodeHashAsBase64(false);     
	        String pwd = md5.encodePassword(passwd, null);     
	        //System.out.println("MD5: " + pwd + " len=" + pwd.length());   
	        return pwd;
	    }
	 /**
	  * 哈希算法 
	  * @throws NoSuchAlgorithmException
	  */
	    public static String sha_256(String passwd) throws NoSuchAlgorithmException {       
	        ShaPasswordEncoder sha = new ShaPasswordEncoder(256);     
	        sha.setEncodeHashAsBase64(true);     
	        String pwd = sha.encodePassword(passwd, null);     
	        //System.out.println("哈希算法 256: " + pwd + " len=" + pwd.length());     
	        return pwd;
	    }     
	    
	    /**
	     * 哈希算法 SHA-256
	     */	        
	    public static String sha_SHA_256(String passwd) {     
	        ShaPasswordEncoder sha = new ShaPasswordEncoder();     
	        sha.setEncodeHashAsBase64(false);     
	        String pwd = sha.encodePassword(passwd, null);      
	       // System.out.println("哈希算法 SHA-256: " + pwd + " len=" + pwd.length());   
	        return pwd;
	    }     
	         
	   /**
	    * 使用动态加密盐的只需要在注册用户的时候将第二个参数换成用户名即可     
	    */
	    public static String md5_SystemWideSaltSource (String passwd) {     
	        Md5PasswordEncoder md5 = new Md5PasswordEncoder();     
	        md5.setEncodeHashAsBase64(false);     
	             
	        // 使用动态加密盐的只需要在注册用户的时候将第二个参数换成用户名即可     
	        String pwd = md5.encodePassword(passwd, "acegisalt");     
	       // System.out.println("MD5 SystemWideSaltSource: " + pwd + " len=" + pwd.length());
	        return pwd;
	    }     
	    public static void main(String[] args) throws NoSuchAlgorithmException {  
	        md5("123456"); // 使用简单的MD5加密方式     
	          
	        sha_256("2"); // 使用256的哈希算法(SHA)加密     
	             
	        sha_SHA_256("ds"); // 使用SHA-256的哈希算法(SHA)加密     
	             
	        md5_SystemWideSaltSource("2"); // 使用MD5再加全局加密盐加密的方式加密      
	    }  
}
