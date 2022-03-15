/**
 * 
 */
package com.reliance.gstn.admin.service.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.reliance.gstn.admin.dao.CnDnReasonDao;
import com.reliance.gstn.admin.model.CnDnReason;
import com.reliance.gstn.admin.service.CnDnReasonService;

/**
 * @author Nikesh.Bansod
 *
 */
@Service
public class CnDnReasonServiceImpl implements CnDnReasonService {
	
	@Autowired
	CnDnReasonDao cnDnReasonDao;

	@Override
	@Transactional
	public List<CnDnReason> listCnDnReason() {
		return cnDnReasonDao.listCnDnReason();
	}

}
