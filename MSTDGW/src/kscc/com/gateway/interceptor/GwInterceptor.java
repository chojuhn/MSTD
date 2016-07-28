package kscc.com.gateway.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

public class GwInterceptor implements HandlerInterceptor{
	
	private static final Logger LOG = LoggerFactory.getLogger(GwInterceptor.class);
	
	@Override
	public void afterCompletion (HttpServletRequest req, HttpServletResponse res, Object argObj, Exception excObj) throws Exception {
		
	}
	
	@Override 
	public boolean preHandle(HttpServletRequest req, HttpServletResponse res, Object argObj) throws Exception {
		LOG.debug("GW Interceptor : PreHandle");
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest req, HttpServletResponse res, Object argObj, ModelAndView model) throws Exception {
		LOG.debug("GW Interceptor : PostHandle");
	}
}
