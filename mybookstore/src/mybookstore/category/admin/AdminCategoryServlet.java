package mybookstore.category.admin;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.itcast.commons.CommonUtils;
import cn.itcast.servlet.BaseServlet;
import mybookstore.category.Category;
import mybookstore.category.CategoryException;
import mybookstore.category.CategoryService;

public class AdminCategoryServlet extends BaseServlet {
	 CategoryService categoryservice = new CategoryService();
	 
	 public String edit(HttpServletRequest request, HttpServletResponse response) 
			 throws ServletException, IOException {
		 Category category = CommonUtils.toBean(request.getParameterMap(), Category.class);
		 categoryservice.edit(category);
		 return findAll(request, response);
	 }
	 
	 public String editPre(HttpServletRequest request, HttpServletResponse response) 
			 throws ServletException, IOException {
		 String cid = request.getParameter("cid");
		 Category category = categoryservice.load(cid);
		 request.setAttribute("category", category);
		 return "f:/adminjsps/admin/category/mod.jsp";
	 }
	 
	 public String delete(HttpServletRequest request, HttpServletResponse response) 
			 throws ServletException, IOException {
		 String cid = request.getParameter("cid");
		 try{
			 categoryservice.delete(cid);
			 request.setAttribute("msg", "恭喜，删除成功！");
		 }catch (CategoryException e){
			 request.setAttribute("msg", e.getMessage());
		 }
		 return "f:/adminjsps/admin/msg.jsp";
	 }
	 
	 public String findAll(HttpServletRequest request, HttpServletResponse response) 
			 throws ServletException, IOException {
		 List<Category> categorylist = categoryservice.findAll();
		 request.setAttribute("categoryList", categorylist);
		 return "f:/adminjsps/admin/category/list.jsp";
	}
	 public String add(HttpServletRequest request, HttpServletResponse response) 
			 throws ServletException, IOException {
		 Category category = CommonUtils.toBean(request.getParameterMap(), Category.class);
		 category.setCid(CommonUtils.uuid());
		 
		 categoryservice.add(category);
		 return findAll(request, response);
	 }
}
