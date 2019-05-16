package mybookstore.book;

import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import cn.itcast.jdbc.TxQueryRunner;

public class BookDao {
	private QueryRunner qr = new TxQueryRunner();
	public List<Book> findAll(){
		try{
			String sql = "select * from book where del = false";
			return qr.query(sql, new BeanListHandler<Book>(Book.class));
		}catch (SQLException e){
			throw new RuntimeException();
		}
	}
	public List<Book> findByCategory(String cid){
		try{
			String sql = "select * from book where cid = ? and del = false";
			return qr.query(sql, new BeanListHandler<Book>(Book.class),cid);
		}catch (SQLException e){
			throw new RuntimeException();
		}
	}
	public Book load(String bid){
		try{
			String sql = "select * from book where bid = ?";
			return qr.query(sql, new BeanHandler<Book>(Book.class),bid);
		}catch (SQLException e){
			throw new RuntimeException();
		}
	}	
	public void add(Book book){
		try{
			String sql = "insert into book values(?,?,?,?,?,?)";
			Object[] params = {book.getBid(),book.getBname(),book.getPrice(),
					book.getAuthor(),book.getImage(),book.getCid()};
			qr.update(sql,params);
		}catch (SQLException e){
			throw new RuntimeException();
		}
	}
	public void delete(String bid){
		try{
			String sql = "update book set del = true where bid = ?";
			qr.update(sql,bid);
		}catch (SQLException e){
			throw new RuntimeException();
		}
	}
	public void edit(Book book){
		try{
			String sql = "update book set bname=?,price=?,author=?,image=?,cid=? where bid=?";
			Object[] params = {book.getBname(),book.getPrice(),
					book.getAuthor(),book.getImage(),
					book.getCid(),book.getBid()};
			qr.update(sql,params);
		}catch (SQLException e){
			throw new RuntimeException();
		}
	}
}