/**
 * 
 */
package com.reliance.gstn.admin.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.reliance.gstn.admin.dao.SACCodeDao;
import com.reliance.gstn.admin.service.SACCodeService;
import com.reliance.gstn.model.SACCode;

/**
 * @author Pradeep.N.Reddy
 *
 */
@Service
public class SACCodeServiceImpl implements SACCodeService {
	
	@Autowired
	private SACCodeDao sacDao;

	@Override
	@Transactional
	public List<SACCode> getSACCodeList() {
		return sacDao.getSACCodeList();
	}

	@Override
	@Transactional
	public String addSACCode(SACCode sacCode) {
		// TODO Auto-generated method stub
		return sacDao.addSACCode(sacCode);
	}

	@Override
	@Transactional
	public SACCode getSACDetailsById(Integer id) {
		// TODO Auto-generated method stub
		return sacDao.getSACDetailsById(id);
	}

	@Override
	@Transactional
	public String updateSACDetails(SACCode sacCode) throws Exception {
		// TODO Auto-generated method stub
		return sacDao.updateSACDetails(sacCode);
	}

	@Override
	@Transactional
	public String removeSACDetails(Integer id) {
		// TODO Auto-generated method stub
		return sacDao.removeSACDetails(id);
	}

}
