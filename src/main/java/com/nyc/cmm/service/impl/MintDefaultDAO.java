package com.nyc.cmm.service.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.nyc.cmm.dao.AbstactDAO;

@Repository("defaultDAO")
public class MintDefaultDAO extends AbstactDAO {
	
	@Override
	public int delete(String queryId, Object parameterObject) {
		// TODO Auto-generated method stub
		return super.delete(queryId, parameterObject);
	}

	@Override
	public int insert(String queryId, Object parameterObject) {
		// TODO Auto-generated method stub
		return super.insert(queryId, parameterObject);
	}

	@Override
	public List list(String queryId, Object parameterObject) {
		// TODO Auto-generated method stub
		return super.list(queryId, parameterObject);
	}

	@Override
	public List listWithPaging(String queryId, Object parameterObject,
			int pageIndex, int pageSize) {
		// TODO Auto-generated method stub
		return super.listWithPaging(queryId, parameterObject, pageIndex, pageSize);
	}

	@Override
	public int update(String queryId, Object parameterObject) {
		// TODO Auto-generated method stub
		return super.update(queryId, parameterObject);
	}

	@Override
    public Object selectByPk(String queryId, Object parameterObject) {
        // TODO Auto-generated method stub
        return super.selectOne(queryId, parameterObject);
    }
	
	@Override
    public Object selectOne(String queryId, Object parameterObject) {
        // TODO Auto-generated method stub
        return super.selectOne(queryId, parameterObject);
    }
	
}
