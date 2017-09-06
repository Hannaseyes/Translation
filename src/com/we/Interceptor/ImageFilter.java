package com.we.Interceptor;

import java.io.IOException;  
 
import javax.servlet.Filter;  
import javax.servlet.FilterChain;  
import javax.servlet.FilterConfig;  
import javax.servlet.ServletException;  
import javax.servlet.ServletRequest;  
import javax.servlet.ServletResponse;  
import javax.servlet.http.HttpServletRequest;  
import javax.servlet.http.HttpServletResponse;  
 
public class ImageFilter implements Filter{  
 
    @Override  
    public void destroy() {  
        // TODO Auto-generated method stub  
          
    }  
  
    @Override  
    public void doFilter(ServletRequest arg0, ServletResponse arg1,  
            FilterChain arg2) throws IOException, ServletException {  
        // TODO Auto-generated method stub  
        HttpServletRequest request = (HttpServletRequest) arg0;    
        HttpServletResponse response = (HttpServletResponse) arg1;   
          
        String yanzhengm = request.getParameter("j_captcha");  
        String sessionyanz = (String)request.getSession(true).getAttribute("yzkeyword");  
        if(yanzhengm.equals(sessionyanz)){  
            arg2.doFilter(request, response);   
        }else{  
            response.sendRedirect("/login.do");  
        }  
    }  
  
    @Override  
    public void init(FilterConfig arg0) throws ServletException {  
        // TODO Auto-generated method stub  
          
    }  
  
      
}  

