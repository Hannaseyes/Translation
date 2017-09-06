package com.we.dao;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.we.persistence.SwitchMapper;

@Repository
public class SwitchDao {
	
	@Autowired
	private SwitchMapper switchMapper;
	
	public Map<String,Object> getSwitchStatus(Map<String, Object> map){
		return switchMapper.getSwitchStatus(map);
		
	}

}
