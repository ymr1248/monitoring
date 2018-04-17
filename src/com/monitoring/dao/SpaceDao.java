package com.monitoring.dao;

import java.util.List;

import com.monitoring.entity.Space;

/**
 * @author mry
 *2018年1月2日 上午11:17:03
 */
public interface SpaceDao {
	public Space getSpaceById(int id);

	public Space getSpaceByUserId(int userId);

	public Space getSpaceByName(String name);
	
	List<Space> getSpaceByNameLike(String name);

	public void addSpace(Space space);

	public int deleteSpaceById(int id);
	
	public int deleteSpaceByUserId(int userId);

	public void saveOrupdate(Space space);

	public List<Space> getSpaces();

	public List<Space> getSpacesPageList(int pageNum);

	public int pageS();

	public Integer isOrNadd(int id);

	public Integer updateSpace(Space space);

	public Integer updateSpaceExtraSize(String extraSize, int id);
}
