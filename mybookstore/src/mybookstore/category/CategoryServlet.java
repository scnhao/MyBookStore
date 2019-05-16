package mybookstore.category;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.itcast.servlet.BaseServlet;

public class CategoryServlet extends BaseServlet {
	private CategoryService categoryservice = new CategoryService();
	
	public String findAll(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		request.setAttribute("categoryList", categoryservice.findAll());
		return "f:/jsps/left.jsp";
	}


}
