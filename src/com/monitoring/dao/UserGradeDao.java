package com.monitoring.dao;

import java.util.List;

import com.monitoring.entity.UserGrade;

/**
 * @author mry
 *2018年1月2日 上午11:19:04
 */
public interface UserGradeDao {
	
	public UserGrade getUserGradeById(int id);
	
	public UserGrade getUserGradeByType(int type);
	
	public int addUserGrade(UserGrade userGrade);
	
	public void deleteUserGradeById(int id);
	
	public List<UserGrade> getUserGrades();
	
	public int update(UserGrade userGrade);
}
