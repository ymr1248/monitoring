package com.monitoring.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.monitoring.dao.UserGradeDao;
import com.monitoring.entity.UserGrade;
import com.monitoring.service.UserGradeService;
/**
 * @author mry
 *2018年1月2日 上午11:28:32
 */
@Service
public class UserGradeServiceImpl implements UserGradeService{
@Autowired
UserGradeDao userGradeDao;

@Override
public UserGrade getUserGradeById(int id) {
	return userGradeDao.getUserGradeById(id);
}
@Override
public UserGrade getUserGradeByType(int type) {
	return userGradeDao.getUserGradeByType(type);
}

@Override
public int addUserGrade(UserGrade userGrade) {
	return userGradeDao.addUserGrade(userGrade);
}

@Override
public void deleteUserGradeById(int id) {
	userGradeDao.deleteUserGradeById(id);
}

@Override
public List<UserGrade> getUserGrades() {
	return userGradeDao.getUserGrades();
}

@Override
public boolean update(UserGrade userGrade) {
	if(userGradeDao.update(userGrade) == 1){
		return true;
	}
	return false;
}

}
