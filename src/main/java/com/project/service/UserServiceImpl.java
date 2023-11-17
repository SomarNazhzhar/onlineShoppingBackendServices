package com.project.service;



import java.util.List;
import java.util.Optional;

import com.project.exception.AddressNotFound;
import com.project.model.Address;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.project.exception.UserAlreadyExists;
import com.project.model.CurrentUserSession;
import com.project.model.User;
import com.project.repository.CurrentUserSessionDao;
import com.project.repository.UserDao;

@Service
@Log4j2
public class UserServiceImpl implements UserService{
	
	@Autowired
	private UserDao uDao;
	
	@Autowired
	private CurrentUserSessionDao cuserDao;

	@Override
	public User saveUer(User user) {		
		User u = uDao.findByMobile(user.getMobile());
		if(u==null) return uDao.save(user);
		else throw new UserAlreadyExists("User Already Exists");
	}

	@Override
	public User updateUserCredential(User user, String key) {
		CurrentUserSession cuser =  cuserDao.findByUniqueId(key);
		if(cuser==null) throw new UserAlreadyExists("user not loged in");
		Optional<User> opt = uDao.findById(cuser.getUserId());
		if(opt.isPresent()) throw new UserAlreadyExists("user not found");
		User userWithSameNumber = uDao.findByMobile(user.getMobile());
		if(userWithSameNumber!=null) throw new UserAlreadyExists("one user find with the same mobile number");
		User saveUser = uDao.save(user);
		cuserDao.delete(cuser);
		uDao.delete(opt.get());
		return saveUser;
	}

	@Override
	public String userLogout(String key) {
		CurrentUserSession cuser =  cuserDao.findByUniqueId(key);
		if(cuser==null) throw new UserAlreadyExists("user not loged in");
		cuserDao.delete(cuser);
		return "Logged Out successfully";
	}

	@Override
	public List<User> getAllUsers(){
		List<User> alist=uDao.findAll();
		if(alist!=null) {
			ExcelWriter excelWriter = new ExcelWriter();
			excelWriter.writeUsersToExcel(alist, "C:\\springBoot_Practice\\Online-Shopping-App-SpringBoot--main\\Online-Shopping-App-SpringBoot--main\\OnlineShopingApp\\file.xlsx");
			log.info("the users added to the Excel File");
			return alist;
		}
		else
		{
			throw new AddressNotFound("No address exists");
		}

	}


	
}
