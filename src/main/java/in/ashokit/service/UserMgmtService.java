package in.ashokit.service;

import java.util.List;

import in.ashokit.bindings.AcivateAccount;
import in.ashokit.bindings.Login;
import in.ashokit.bindings.User;

public interface UserMgmtService {

	public boolean saveUser(User user);
	
	public boolean activateUserAcc(AcivateAccount account);
	
	public List<User> getAllUser();
	
	public User getUserById(Integer userId);
	
	public boolean deleteUserById(Integer userId);
	
	public boolean changeAccountStatus(Integer userId, String accntStatus);
	
	public String login(Login login);
	
	public String forgetPwd(String email);
	
}
