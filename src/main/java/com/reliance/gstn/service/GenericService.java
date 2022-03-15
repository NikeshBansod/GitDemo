package com.reliance.gstn.service;

public interface GenericService {

	boolean checkUserMappingValidation(String idsValuesToFetch,String getPrimIdsListQuery, Integer primId,String targetTable,String refColumn) throws Exception;

	boolean validateGstin(String gstinId, Integer orgUId) throws Exception;

}
