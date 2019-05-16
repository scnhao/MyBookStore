package mybookstore.category;

import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import cn.itcast.jdbc.TxQueryRunner;

public class CategoryDao {
	QueryRunner qr = new TxQueryRunner();
	public List<Category> findAll(){
		try{
			String sql = "select * from category";
			return qr.query(sql, new BeanListHandler<Category>(Category.class));
		}catch(SQLException e){
			throw new RuntimeException();
		}
	}
	public void add(Category category){
		try{
			String sql = "insert into category values(?,?)";
			qr.update(sql, category.getCid(), category.getCname());
		}catch(SQLException e){
			throw new RuntimeException();
		}
	}
	public void delete(String cid){
		try{
			String sql = "delete from category where cid = ?";
			qr.update(sql,cid);
		}catch(SQLException e){
			throw new RuntimeException();
		}
	}
	public Category load(String cid){
		try{
			String sql = "select * from category where cid = ?";
			return qr.query(sql, new BeanHandler<Category>(Category.class), cid);
		}catch(SQLException e){
			throw new RuntimeException();
		}
	}
	public void edit(Category category){
		try{
			String sql = "update category set cname = ? where cid = ?";
			qr.update(sql,category.getCname(),category.getCid());
		}catch(SQLException e){
			throw new RuntimeException();
		}
	}
}
