package com.reliance.gstn.dao;

public interface GenericDao {

	boolean checkUserMappingValidation(String idsValuesToFetch,String getIdListQuery, Integer primId,String targetTable,String refColumn) throws Exception;

	boolean validateGstin(String gstinId, Integer uId) throws Exception;

}
