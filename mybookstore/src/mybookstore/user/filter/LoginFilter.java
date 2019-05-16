package mybookstore.user.filter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import mybookstore.user.User;

/**
 * Servlet Filter implementation class LoginFilter
 */
public class LoginFilter implements Filter {


	public void destroy() {

	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) 
			throws IOException, ServletException {
		HttpServletRequest httprequest = (HttpServletRequest)request;
		User user = (User)httprequest.getSession().getAttribute("user");
		if(user != null){
			chain.doFilter(request, response);
		}else{
			httprequest.setAttribute("msg", "您还没有登陆！");
			httprequest.getRequestDispatcher("/jsps/user/login.jsp")
				.forward(request, response);
		}
	}


	public void init(FilterConfig fConfig) throws ServletException {

	}

}
