package com.we.service.imp;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.we.dao.SwitchDao;
import com.we.service.SwitchService;
import com.we.util.CommonUtil;
import com.we.util.Consts;

@Service
public class SwitchServiceImpl implements SwitchService {
	@Autowired
	private SwitchDao switchDao;
	
	@Override
	public String getSwitchStatus(Map<String, Object> map) {
		
		Map<String, Object> switchMap = switchDao.getSwitchStatus(map);
		String result = CommonUtil.setResultStringCn(switchMap, Consts.SUCCESS_CODE,
				Consts.SUCCESS_DESCRIBE, "");
		return result;
	}
	
}
