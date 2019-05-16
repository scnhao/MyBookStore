package mybookstore.user;

import mybookstore.book.UserException;

public class UserService {
	public UserDao userdao = new UserDao();
	public void regist(User user) throws UserException{
		if(userdao.findByUsername(user.getUsername()) != null) 
			throw new UserException("用户名已被注册！");
		if(userdao.findByEmail(user.getEmail()) != null)
			throw new UserException("邮箱已被占用！");
		userdao.add(user);
	}
	public void active(String code) throws UserException{
		User user = userdao.findByCode(code);
		if (user == null) throw new UserException("激活码无效");
		if (user.isState() == true) throw new UserException("用户已经激活了.");
		userdao.updateState(user.getUid(), true);
	}
	public User login(User user) throws UserException{
		User u = userdao.findByUsername(user.getUsername());
		if (u == null) throw new UserException("用户不存在");
		if (!user.getPassword().equals(u.getPassword())) throw new UserException("密码错误");
		if (u.isState() != true) throw new UserException("用户未激活");
		return u;
	}
}
