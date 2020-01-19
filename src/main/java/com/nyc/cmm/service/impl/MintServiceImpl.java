package com.nyc.cmm.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.nyc.cmm.service.MintService;

@Service("defaultService")
public class MintServiceImpl implements MintService{
	
	/** defaultDAO */
    @Resource(name="defaultDAO")
    private MintDefaultDAO defaultDAO;

    @Override
	public Object insert(String queryId, Map param)
			throws Exception {
		// TODO Auto-generated method stub
		return defaultDAO.insert(queryId, param);
	}

	@Override
	public int update(String queryId, Map param) throws Exception {
		// TODO Auto-generated method stub
		return defaultDAO.update(queryId, param);
	}

	@Override
	public int delete(String queryId, Map param) throws Exception {
		// TODO Auto-generated method stub
		return defaultDAO.delete(queryId, param);
	}
	
	@Override
	public Map<String, String> selectByPk(String queryId, Map param)
			throws Exception {
		// TODO Auto-generated method stub
		return (Map<String, String>) defaultDAO.selectByPk(queryId, param);
	}

    
    /**
     * 리스트 조회 (List 형으로 리턴);
     * @param queryId, queryId2(cnt 쿼리ID), param Map     
     * @return Map
     * @exception Exception
     */
    @Override
    public List list_map(String queryId, Map<String, Object> param)
            throws Exception {
        
        Map<String, Object> map = new HashMap<String, Object>();
        
        List result = defaultDAO.list(queryId, param);
            
        return result;
        
    }
    
    /**
     * 상세조회 map 을 return;
     * @param queryId, queryId2(cnt 쿼리ID), param Map     
     * @return Map
     * @exception Exception
     */
    @Override
    public Map<String, Object> detail_map(String queryId, Map<String, Object> param)
            throws Exception {
        
        Map<String, Object> map = new HashMap<String, Object>();
        
        List result = defaultDAO.list(queryId, param);
            
        if (result != null && result.size() > 0) {
            
            map =  (Map<String, Object>) result.get(0);
            
        }

        return map;
        
    }
    
    /*

    @Override
    public Map<String, Object> list_map(String queryId, String queryId2, Map<String, Object> param)
            throws Exception {
        
        int page     = 1;
        int rowNum   = 10;
        int pageSize = 10;
        
        if (param.get("page") != null && !param.get("page").equals("")) {
            page = Integer.parseInt(""+param.get("page")); 
        }
        
        if (param.get("rowNum") != null && !param.get("rowNum").equals("")) {
            rowNum = Integer.parseInt(""+param.get("rowNum")); 
        }
        
        if (param.get("pageSize") != null && !param.get("pageSize").equals("")) {
            pageSize = Integer.parseInt(""+param.get("pageSize")); 
        }
        
//        log.debug("page      ======"+page);
//        log.debug("rowNum    ======"+rowNum);
//        log.debug("pageSize  ======"+pageSize);
        
        // Paing
        PaginationInfo paginationInfo = new PaginationInfo();
        paginationInfo.setCurrentPageNo(page); // 페이지 번호
        paginationInfo.setRecordCountPerPage(rowNum); // row건수
        paginationInfo.setPageSize(pageSize);  // 페이지 건수
        
        param.put("page",     page);   
        param.put("rowNum",   rowNum);
        param.put("pageSize", pageSize);
        
        int totCnt = (Integer)defaultDAO.selectByPk(queryId2, param);
        
        List result = defaultDAO.list(queryId, param);
               
        //log.debug("result Size  ======"+result.size());
        
        paginationInfo.setTotalRecordCount(totCnt); // 총건수
        
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("resultList",      result);
        map.put("resultCnt",       totCnt);
        map.put("paginationInfo",  paginationInfo);

        return map;
        
    }
    
    
    @Override
    public jqgridJason list(String queryId, String queryId2, Map<String, Object> param)
            throws Exception {
        
        String gridSortCol = EgovStringUtil.isNullToString(param.get("gridSortCol")); // 정렬 컬럼
        String gridSortType = EgovStringUtil.isNullToString(param.get("gridSortType")); // 정렬 구분 1: ASC 2:DESC  <== SQL 인젝션을 방지하기위해 1또는2를 받아온다
        gridSortType = gridSortType.equals("1") ? "ASC" : "DESC";
        param.put("gridSortType", gridSortType);
       
        jqgridJasonDAO jqdao = new jqgridJasonDAO();
        
        int totCnt = (Integer)defaultDAO.selectByPk(queryId2, param);
        
        param = jqdao.loadJqgridJason(param, totCnt);
        jqgridJason resultData = jqdao.makeJqgridJason(defaultDAO.list(queryId, param), param);
        return resultData;
    }
   
	   
	@Override
	public jqgridJason list(String queryId, Map<String, Object> param)
			throws Exception {
		
		jqgridJasonDAO jqdao = new jqgridJasonDAO();
    	
		jqgridJason resultData = jqdao.makeJqgridJason(defaultDAO.list(queryId, param));
		
        return resultData;
	}

	@Override
    public axGridJason list_axgrid(String queryId, Map<String, Object> param)
            throws Exception {
        
		AxGridJasonDAO griddao = new AxGridJasonDAO();
        
        
        axGridJason resultData = griddao.makeAxGridJason(defaultDAO.list(queryId, param));
        return resultData;
    }
	
	@Override
    public axGridJason list_axgrid(String queryId, String queryId2, Map<String, Object> param)
            throws Exception {
        
		AxGridJasonDAO griddao = new AxGridJasonDAO();
        
        int totCnt = (Integer)defaultDAO.selectByPk(queryId2, param);
        
        param = griddao.loadAxGridJason(param, totCnt);
        axGridJason resultData = griddao.makeAxGridJason(defaultDAO.list(queryId, param), param);
        return resultData;
    }
	
   @Override
    public JSONObject list_json(String queryId, String queryId2, Map<String, Object> param)
            throws Exception {
        
        String gridSortCol = EgovStringUtil.isNullToString(param.get("gridSortCol")); // 정렬 컬럼
        String gridSortType = EgovStringUtil.isNullToString(param.get("gridSortType")); // 정렬 구분 1: ASC 2:DESC  <== SQL 인젝션을 방지하기위해 1또는2를 받아온다
        gridSortType = gridSortType.equals("1") ? "ASC" : "DESC";
        param.put("gridSortType", gridSortType);
        //log.debug("gridSortCol=========="+gridSortCol);
        //log.debug("gridSortType=========="+gridSortType);
        
        int totCnt = (Integer)defaultDAO.selectByPk(queryId2, param);
        
        //param = jqdao.loadJqgridJason(param, totCnt);
        
        winigridJasonDAO winigrid = new winigridJasonDAO();
        
        return winigrid.makeWiniGrid15List(param, defaultDAO.list(queryId, param), totCnt);
        
        //jqgridJason resultData = jqdao.makeJqgridJason(defaultDAO.list(queryId, param), param);
        //return resultData;
    }	
	
    @Override
    public JSONObject list_json(String queryId, Map<String, Object> param)
            throws Exception {
        
        winigridJasonDAO winigrid = new winigridJasonDAO();
                
        return winigrid.makeWiniGrid15List(param, defaultDAO.list(queryId, param), 0);
    }
    
    @Override
    public easyuigridJason list_easyui_noParam(String queryId, Map<String, Object> param)
            throws Exception {
        
        easyuigridJasonDAO easyuidao = new easyuigridJasonDAO();
                
        
        return easyuidao.makeEasyuigridJason(defaultDAO.list(queryId, param));
    }

    @Override
    public easyuigridJason list_easyui(String queryId, Map<String, Object> param)
            throws Exception {
        
        easyuigridJasonDAO easyuidao = new easyuigridJasonDAO();
                
        
        return easyuidao.makeEasyuigridJason(defaultDAO.list(queryId, param), param);
    }
    
    
    @Override
    public easyuigridJason list_easyui(String queryId, String queryId2, Map<String, Object> param)
            throws Exception {
        
        String gridSortCol = EgovStringUtil.isNullToString(param.get("gridSortCol")); // 정렬 컬럼
        String gridSortType = EgovStringUtil.isNullToString(param.get("gridSortType")); // 정렬 구분 1: ASC 2:DESC  <== SQL 인젝션을 방지하기위해 1또는2를 받아온다
        gridSortType = gridSortType.equals("1") ? "ASC" : "DESC";
        param.put("gridSortType", gridSortType);
        //log.debug("gridSortCol=========="+gridSortCol);
        //log.debug("gridSortType=========="+gridSortType);
        
        easyuigridJasonDAO easyuidao = new easyuigridJasonDAO();
        
        int totCnt = (Integer)defaultDAO.selectByPk(queryId2, param);
        
        param = easyuidao.loadEasyuigridJason(param, totCnt);
        easyuigridJason resultData = easyuidao.makeEasyuigridJason(defaultDAO.list(queryId, param), param);
        return resultData;
    }   
    
    
    @Override
    public int mult_save(List<String> queryID, List<String> queryIUD, jqgridJason param)
            throws Exception {
        // TODO Auto-generated method stub
        
        List<Map>    save_data  = param.getRows();
        
        //log.debug("save rows  ======"+save_data.size());
        
        if (queryID.size() > 0) {
            
            for (int i = 0; i < queryID.size(); i++) {
                String qId = queryID.get(i);
                String idu = queryIUD.get(i);
                Map<String, Object> dataMap = save_data.get(i);
               
                egovLogger.debug("queryID  ======"+qId);
                egovLogger.debug("idu      ======"+idu);
                egovLogger.debug("data     ======"+dataMap.toString());
                
                if (idu != null && idu.equals("I")) {
                    defaultDAO.insert(qId, dataMap);
                } else if (idu != null && idu.equals("U")) {
                    defaultDAO.update(qId, dataMap);
                } else if (idu != null && idu.equals("D")) {
                    defaultDAO.delete(qId, dataMap);
                } else {
                    return -1;
                }
            }
            
            return 1;
        } else {
            return 0;           
        }
        
    }
    
    @Override
    public String list_map_json(String queryId, Map<String, Object> param)
            throws Exception {
        
        List<Map<String, Object>> result = defaultDAO.list(queryId, param);

        JSONArray json_arr = JSONArray.fromObject(result);
    
        return json_arr.toString();
    }
    
    @Override
    public Map<String, Object> callProcedure_map(String queryId, Map<String, Object> param)
            throws Exception {
        
        jqgridJasonDAO jqdao = new jqgridJasonDAO();
        
        defaultDAO.selectByPk(queryId, param);
        
        return param;
    }  
    
    */
    
}
