package com.nyc.cmm.service;

import java.util.List;
import java.util.Map;

public interface MintService {
	Object insert(String queryId, Map param) throws Exception;
	
	int update(String queryId, Map param) throws Exception;
	
	int delete(String queryId, Map param) throws Exception;
	
	Map<String, String> selectByPk(String queryId, Map param) throws Exception;
	
	List list_map(String queryId, Map<String, Object> param) throws Exception; // list 형으로 return
	
	Map<String, Object> detail_map(String queryId, Map<String, Object> param) throws Exception; // map 사용시(상세조회용)
	
	/*그리드 사용
	Map<String, Object> list_map(String queryId, String queryId2, Map<String, Object> param) throws Exception; // map 사용시
	 
	String list_map_json(String queryId, Map<String, Object> param) throws Exception; // 리스트를 json 형태로 return

	Map<String, Object> callProcedure_map(String queryId, Map<String, Object> param) throws Exception; //procedure 용
	
	int mult_save(List<String> queryID, List<String> queryIUD, jqgridJason param) throws Exception;
	
	jqgridJason list(String queryId, Map<String, Object> param) throws Exception;  // jqgrid 사용시

	jqgridJason list(String queryId, String queryId2, Map<String, Object> param) throws Exception; // jqgrid 사용시
	
	JSONObject list_json(String queryId, Map<String, Object> param) throws Exception; // winiGrid 사용시
	
	JSONObject list_json(String queryId, String queryId2, Map<String, Object> param) throws Exception; // winiGrid 사용시
	
	easyuigridJason list_easyui_noParam(String queryId, Map<String, Object> param) throws Exception; // easyui 사용시
	
	easyuigridJason list_easyui(String queryId, Map<String, Object> param) throws Exception; // easyui 사용시
    
	easyuigridJason list_easyui(String queryId, String queryId2, Map<String, Object> param) throws Exception; // easyui 사용시
    
	axGridJason list_axgrid(String queryId, Map<String, Object> param) throws Exception;  // axgrid 사용시
	
	axGridJason list_axgrid(String queryId, String queryId2, Map<String, Object> param) throws Exception;  // axgrid 사용시
	*/
}
