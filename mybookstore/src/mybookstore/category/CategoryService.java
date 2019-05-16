package mybookstore.category;

import java.util.List;

import mybookstore.book.Book;
import mybookstore.book.BookDao;

public class CategoryService {
	private CategoryDao categorydao = new CategoryDao();
	private BookDao bookdao = new BookDao();
	public List<Category> findAll(){
		return categorydao.findAll();
	}
	public void add(Category category){
		categorydao.add(category);
	}
	public void delete(String cid) throws CategoryException{
		List<Book> booklist = bookdao.findByCategory(cid);
		if(!(booklist.size()==0)) throw new CategoryException("该分类下还存在图书，无法删除！");
		categorydao.delete(cid);
	}
	public Category load(String cid){
		return categorydao.load(cid);
	}
	public void edit(Category category){
		categorydao.edit(category);
	}
}
