package com.reliance.gstn.external.wizard.dao;

import org.hibernate.exception.ConstraintViolationException;

import com.reliance.gstn.model.EwayBillWIItem;
import com.reliance.gstn.model.EwayBillWITransaction;
import com.reliance.gstn.model.LoginMaster;

public interface WithoutInvoiceEwayBillDao {

	String saveWIEWayBillItem(EwayBillWITransaction ewayBillWITransaction, LoginMaster loginMaster) throws ConstraintViolationException, Exception;

}
