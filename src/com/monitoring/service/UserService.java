package com.monitoring.service;

import java.util.List;

import com.monitoring.entity.User;
import com.monitoring.util.Pager;

/**
 * @author mry
 *2018年1月2日 上午8:44:02
 */
public interface UserService {
	public int loginUser(String account,String password);
	public User getUserById(int id);
	public User getUserByAccount(String account);
	public User getUserByName(String name);
	
	public int addUser(User user);

	public boolean deleteUserById(int id);

	public boolean updateUserPermission(int id, int permission);

	public boolean update(User user);
	
	public boolean checkPhone(String phone);
	
	public List<User> getUsers();
	
	public List<User> getUsers(int num);

	Pager<User> getUserByUserGradeId(int pageNum,int userGradeId);

	public int getPageNum();

	public List<User> getUserListByPermission(int permission, int num);

	public int getPageNumByPermission(int grade);

	public boolean isOrNotAgreeAdd(int id);
	
	public boolean checkUserPsw(int id, String oldPassword);

	public boolean updateUserPsw(int id, String newPassword);
	public boolean updateUserInfo(int id, String name);
	public boolean updateUserGrade(int id, int userGradeId);
	public int isOrNotAdded(String avatar,String userName,String userAccount,String password,
								String userAddress,String userPhone,String userEmail,int userGradeId );

	public boolean updateUserAppId(int userId,String appId);
}
