package mybookstore.book;

import java.util.List;

public class BookService {
	private BookDao	bookdao = new BookDao();
	public List<Book> findAll(){
		return bookdao.findAll();
	}
	public List<Book> findByCategory(String cid) {
		return bookdao.findByCategory(cid);
	}
	public Book load(String bid){
		return bookdao.load(bid);
	}
	public void add(Book book){
		bookdao.add(book);
	}
	public void delete(String bid){
		bookdao.delete(bid);
	}
	public void edit(Book book){
		bookdao.edit(book);
	}
}

