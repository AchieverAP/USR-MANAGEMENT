package in.ashokit.service;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import in.ashokit.bindings.AcivateAccount;
import in.ashokit.bindings.Login;
import in.ashokit.bindings.User;
import in.ashokit.entity.UserMaster;
import in.ashokit.repo.UserMasterRepository;
import in.ashokit.utils.EmailUtils;

@Service
public class UserMgmtServiceImpl implements UserMgmtService {

	@Autowired
	UserMasterRepository usrmgntrepo;
	
	@Autowired
	EmailUtils emailUtils;
	
	@Override
	public boolean saveUser(User user) {
		// TODO Auto-generated method stub
		UserMaster entity = new UserMaster();
		BeanUtils.copyProperties(user, entity);
		entity.setPassword(genrateRandomPassword());
		entity.setAccStatus("In-Active");
		UserMaster saved = usrmgntrepo.save(entity);
		emailUtils.sendEmail(user.getEmailId(), readEmailBody(user.getFullName(), saved.getPassword()));
		return saved.getUid()!=null;
	}

	@Override
	public boolean activateUserAcc(AcivateAccount account) {
		// TODO Auto-generated method stub
		
		UserMaster entity = new UserMaster();
		entity.setEmailId(account.getEmail());
		entity.setPassword(account.getTempPwd());
		Example<UserMaster> of=Example.of(entity);
		List<UserMaster> findAll = usrmgntrepo.findAll(of);
		if(findAll.isEmpty()) {
			return false;
		}else {
			
			UserMaster userMaster = findAll.get(0);
			userMaster.setPassword(account.getNewPwd());
			userMaster.setAccStatus("Active");
			UserMaster save = usrmgntrepo.save(userMaster);
			return save.getUid()!=null;
		}
		
	}

	@Override
	public List<User> getAllUser() {
		// TODO Auto-generated method stub
		
		List<UserMaster> findAll = usrmgntrepo.findAll();
		
		List<User> userList = new ArrayList<User>();
		for(UserMaster enMaster :findAll) {
			User user = new User();
			BeanUtils.copyProperties(enMaster, user);
			userList.add(user);
		}
		return userList;
	}

	@Override
	public User getUserById(Integer userId) {
		// TODO Auto-generated method stub
		
		Optional<UserMaster> findById = usrmgntrepo.findById(userId);
		if(findById.isPresent()) {
			
			UserMaster userMaster = findById.get();
			User user = new User();
			BeanUtils.copyProperties(userMaster, user);
			return user;		
	}
		return null;
	}

	@Override
	public boolean deleteUserById(Integer userId) {
		// TODO Auto-generated method stub
		try {
			usrmgntrepo.deleteById(userId);
			return true;
		}catch (Exception e) {
		  	// TODO: handle exception
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean changeAccountStatus(Integer userId, String accntStatus) {
		// TODO Auto-generated method stub
		
		Optional<UserMaster> findById = usrmgntrepo.findById(userId);
		if(findById.isPresent()) {
			UserMaster userMaster = findById.get();
			userMaster.setAccStatus(accntStatus);
			usrmgntrepo.save(userMaster);
			return true;
		}
		return false;
	}

	@Override
	public String login(Login login) {
		// TODO Auto-generated method stub
		List<UserMaster> list = usrmgntrepo.findByEmailIdAndPassword(login.getEmail(), login.getPass());
		if(list.isEmpty()) {
			return "Invalid Credentials";
		}else {
			UserMaster userMaster = list.get(0);
			if(userMaster.getAccStatus().equals("Active")) {
				return "Login Success";
			}else {
				return "Login failed";
			}
		}		
	}

	@Override
	public String forgetPwd(String email) {
		// TODO Auto-generated method stub
		UserMaster findByEmailId = usrmgntrepo.findByEmailId(email);
		if(findByEmailId != null) {
			
			return findByEmailId.getPassword();
		}else {
			
			return "Invalid Email";
		}
		
		
	}
	
	
	
	private String readEmailBody(String name, String tempPassword)  {
		
		String fileName = "REG_EMAIL_BODY.txt";
		String mailBody = null;
		
		try {
		FileReader fileReader = new FileReader(fileName);
		BufferedReader br = new BufferedReader(fileReader);
		StringBuilder sb = new StringBuilder();
		String line = br.readLine();
		while(line != null) {
			sb.append(line);
			line = br.readLine();
		}
		mailBody = sb.toString();
		mailBody = mailBody.replace("{name}",name);
		mailBody = mailBody.replace("{temp}",tempPassword);
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
    	return mailBody;
	}
	
	private String genrateRandomPassword() {
		
		    String upperAlphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		    String lowerAlphabet = "abcdefghijklmnopqrstuvwxyz";
		    String numbers = "0123456789";
		    String alphaNumeric = upperAlphabet + lowerAlphabet + numbers;
		    StringBuilder sb = new StringBuilder();
		    Random random = new Random();
		    int length = 6;

		    for(int i = 0; i < length; i++) {

		      int index = random.nextInt(alphaNumeric.length());
		      char randomChar = alphaNumeric.charAt(index);
		      sb.append(randomChar);
		    }
		   return sb.toString();

		}
		
	}


