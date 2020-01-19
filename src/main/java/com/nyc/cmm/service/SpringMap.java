package com.nyc.cmm.service;

import org.apache.commons.collections.map.ListOrderedMap;

import com.nyc.cmm.util.CamelUtil;

public class SpringMap extends ListOrderedMap{

	@Override
	public Object put(Object key, Object value) {
		// TODO Auto-generated method stub
		return super.put(CamelUtil.convert2CamelCase((String) key), value);
	}
}
