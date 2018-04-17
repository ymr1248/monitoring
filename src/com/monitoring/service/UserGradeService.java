package com.monitoring.service;

import java.util.List;

import com.monitoring.entity.UserGrade;

/**
 * @author mry
 *2018年1月2日 上午11:25:41
 */
public interface UserGradeService {
	public UserGrade getUserGradeById(int id);
	public UserGrade getUserGradeByType(int type);
	public int addUserGrade(UserGrade userGrade);
	public void deleteUserGradeById(int id);
	public List<UserGrade> getUserGrades();
	public boolean update(UserGrade userGrade);
}
