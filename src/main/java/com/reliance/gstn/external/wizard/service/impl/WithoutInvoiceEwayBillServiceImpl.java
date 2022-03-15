package com.reliance.gstn.external.wizard.service.impl;

import javax.transaction.Transactional;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.reliance.gstn.external.wizard.dao.WithoutInvoiceEwayBillDao;
import com.reliance.gstn.external.wizard.service.WithoutInvoiceEwayBillService;
import com.reliance.gstn.model.EwayBillWIItem;
import com.reliance.gstn.model.EwayBillWITransaction;
import com.reliance.gstn.model.LoginMaster;

@Service
public class WithoutInvoiceEwayBillServiceImpl implements WithoutInvoiceEwayBillService {

	@Autowired
	private WithoutInvoiceEwayBillDao withoutInvoiceEwayBillDao;
	
	
	
	@Override
	@Transactional
	public String saveWIEWayBillItem(EwayBillWITransaction ewayBillWITransaction, LoginMaster loginMaster) throws ConstraintViolationException, Exception {
		return withoutInvoiceEwayBillDao.saveWIEWayBillItem(ewayBillWITransaction,loginMaster);
	}
}
