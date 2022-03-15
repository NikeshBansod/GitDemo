package com.reliance.gstn.master.excel.helper;

import java.util.List;

import com.reliance.gstn.model.LoginMaster;

public interface ExcelDataEntityProvider<T> {

	public List<T> getEntityList(List<Object[]> data,LoginMaster loginMaster);
}
