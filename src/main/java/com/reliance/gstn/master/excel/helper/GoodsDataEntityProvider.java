package com.reliance.gstn.master.excel.helper;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.reliance.gstn.model.LoginMaster;
import com.reliance.gstn.model.Product;
import com.reliance.gstn.util.GSTNUtil;

public class GoodsDataEntityProvider implements ExcelDataEntityProvider<Product> {

	@Override
	public List<Product> getEntityList(List<Object[]> data, LoginMaster loginMaster) {
		List<Product> productList = new ArrayList<>();

		for (Object[] objects : data) {
			Product product = new Product();

			if (!GSTNUtil.isNullOrEmpty(objects[0])) {
				product.setHsnDescription(String.valueOf(objects[0]));
			}
			if (!GSTNUtil.isNullOrEmpty(objects[1])) {
				product.setHsnCode(String.valueOf(objects[1]));
			}
			if (!GSTNUtil.isNullOrEmpty(objects[2])) {
				product.setName(String.valueOf(objects[2]));
			}
			if (!GSTNUtil.isNullOrEmpty(objects[3])) {
				product.setUnitOfMeasurement(String.valueOf(objects[3]).toUpperCase());
				product.setPurchaseUOM(String.valueOf(objects[3]).toUpperCase());
			}
			if (String.valueOf(objects[3]).equalsIgnoreCase("others")
					|| String.valueOf(objects[3]).equalsIgnoreCase("other") && !GSTNUtil.isNullOrEmpty(objects[4])) {
				product.setPurchaseOtherUOM(String.valueOf(objects[4]).toUpperCase());
				product.setOtherUOM(String.valueOf(objects[4]).toUpperCase());
			}
			product.setProductRate(Double.valueOf(String.valueOf(objects[5])));
			product.setProductIgst(Double.valueOf(String.valueOf(objects[6])));
			if (!GSTNUtil.isNullOrEmpty(objects[7]))
				product.setPurchaseRate(Double.valueOf(String.valueOf(objects[7])));
			product.setAdvolCess(0d);
			product.setNonAdvolCess(0d);
			if (!GSTNUtil.isNullOrEmpty(objects[2])) {
				product.setCurrentStock(Double.valueOf(String.valueOf(objects[8])));
				product.setOpeningStock(Double.valueOf(String.valueOf(objects[8])));
			}

			if (!GSTNUtil.isNullOrEmpty(objects[2])) {
				product.setCurrentStockValue(Double.valueOf(String.valueOf(objects[9])));
				product.setOpeningStockValue(Double.valueOf(String.valueOf(objects[9])));
			}

			product.setInventoryUpdateFlag("N");
			if (!GSTNUtil.isNullOrEmpty(objects[10]))
				product.setStoreName(String.valueOf(objects[10]));
			if (!GSTNUtil.isNullOrEmpty(objects[11]))
				product.setGstin(String.valueOf(objects[11]));

			product.setReferenceId(loginMaster.getuId());
			product.setRefOrgId(loginMaster.getOrgUId());
			product.setCreatedBy(loginMaster.getuId().toString());
			product.setUpdatedBy(loginMaster.getuId().toString());
			product.setCreatedOn(new java.sql.Timestamp(new Date().getTime()));
			product.setUpdatedOn(new java.sql.Timestamp(new Date().getTime()));
			product.setStatus("1");
			productList.add(product);
		}
		return productList;
	}

}
