package mybookstore.user;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.mail.MessagingException;
import javax.mail.Session;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.itcast.commons.CommonUtils;
import cn.itcast.mail.Mail;
import cn.itcast.mail.MailUtils;
import cn.itcast.servlet.BaseServlet;
import mybookstore.book.UserException;
import mybookstore.cart.Cart;

public class UserServlet extends BaseServlet {
	public UserService userservice = new UserService();
	
	public String active(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String code = request.getParameter("code");
		try {
			userservice.active(code);
			request.setAttribute("msg", "恭喜激活成功！");
		}catch (UserException e){
			request.setAttribute("msg", e.getMessage());
		}
		return "f:/jsps/msg.jsp";
	}
	
	public String regist(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		User user = CommonUtils.toBean(request.getParameterMap(), User.class);
		user.setUid(CommonUtils.uuid());
		user.setCode(CommonUtils.uuid() + CommonUtils.uuid());
		
		Map<String, String> errors = new HashMap<String, String>();
		
		String username = request.getParameter("username");
		if(username == null || username.trim().isEmpty()){
			errors.put("username", "用户名不能为空！");}
		else if (username.length() <= 3 || username.length() >= 10){
			errors.put("username", "用户名长度必须在3到10之间");};
		
		String email = request.getParameter("email");
		if(email == null || email.trim().isEmpty()){
			errors.put("email", "邮箱不能为空！");}
		else if (!email.matches("\\w+@\\w+\\.\\w+")){
			errors.put("email", "邮箱格式错误！");
		}
		
		if (errors.size() >0){
			request.setAttribute("errors", errors);
			request.setAttribute("user", user);
			return "f:/jsps/user/regist.jsp";}
		
		try {
			userservice.regist(user);
		}catch(UserException e){
			request.setAttribute("msg", e.getMessage());
			request.setAttribute("user", user);
			return "f:/jsps/user/regist.jsp";
		}	
		
		Properties props = new Properties();
		props.load(this.getClass().getClassLoader()
				.getResourceAsStream("email_template.properties"));
		String host = props.getProperty("host");
		String uname = props.getProperty("uname");
		String pwd = props.getProperty("pwd");
		String from = props.getProperty("from");
		String to = user.getEmail();
		String subject = props.getProperty("subject");
		String content = props.getProperty("content");
		content = MessageFormat.format(content, user.getCode());
		Session session = MailUtils.createSession(host, uname, pwd);
		Mail mail = new Mail(from, to, subject, content);
		try {
			MailUtils.send(session, mail);
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		request.setAttribute("msg", "恭喜，注册成功！请到邮箱激活！");
		return "f:/jsps/msg.jsp";
	}
	public String login(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		User user = CommonUtils.toBean(request.getParameterMap(), User.class);
		try{
			User u =userservice.login(user);
			request.getSession().setAttribute("session_user", u);
			request.getSession().setAttribute("cart", new Cart());
			return "f:/index.jsp";
		}catch (UserException e){
			request.setAttribute("msg", e.getMessage());
			request.setAttribute("user", user);
			return "f:/jsps/user/login.jsp";
		}
	}
	public String quit(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.getSession().invalidate();
		return "r:/index.jsp";
	}
}
