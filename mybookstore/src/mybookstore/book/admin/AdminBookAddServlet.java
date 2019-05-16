package mybookstore.book.admin;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUpload;
import org.apache.commons.fileupload.FileUploadBase;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import cn.itcast.commons.CommonUtils;
import mybookstore.book.Book;
import mybookstore.book.BookService;
import mybookstore.category.CategoryService;

public class AdminBookAddServlet extends HttpServlet {
	
	BookService bookservice = new BookService();
	CategoryService categoryservice = new CategoryService();

	public void doPost(HttpServletRequest req, HttpServletResponse resp) 
			throws ServletException, IOException {
		req.setCharacterEncoding("utf-8");
		resp.setContentType("text/html;charset=utf-8");
		
		DiskFileItemFactory fact = new DiskFileItemFactory(15*1024, new File("F:/f/temp"));
		ServletFileUpload sfu = new ServletFileUpload(fact);
		
		sfu.setFileSizeMax(15 * 1024);
		try {
			List<FileItem> fileItemList = sfu.parseRequest(req);
			Map<String, String> map = new HashMap<String, String>();
			for(FileItem fileitem:fileItemList){
				if(fileitem.isFormField()){
					map.put(fileitem.getFieldName(), fileitem.getString("utf-8"));
				}
			}
			Book book = CommonUtils.toBean(map, Book.class);
			book.setBid(CommonUtils.uuid());
			
			String savepath = this.getServletContext().getRealPath("/book_img");
			String filename = CommonUtils.uuid()  + "_" +  fileItemList.get(1).getName();
			if(!filename.toLowerCase().endsWith("jpg")){
				req.setAttribute("msg", "你上传的图片不是jpg格式！");
				req.setAttribute("categoryList", categoryservice.findAll());
				req.getRequestDispatcher("/adminjsps/admin/book/add.jsp")
					.forward(req, resp);
				return;
			}
			File destFile = new File(savepath, filename);
			fileItemList.get(1).write(destFile);
			
			book.setImage("book_img/" + filename);
			
			bookservice.add(book);
			
			req.getRequestDispatcher("/admin/AdminBookServlet?method=findAll")
				.forward(req, resp);
			
		} catch (Exception e) {
			if(e instanceof FileUploadBase.SizeLimitExceededException){
				req.setAttribute("msg", "文件大小超过限制！");
				req.setAttribute("categoryList", categoryservice.findAll());
				req.getRequestDispatcher("/adminjsps/admin/book/add.jsp")
					.forward(req, resp);
			}
		}
		
}

}
