package com.vcm.core.pojo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class StrategyBean {

	private List<Map<String, String>> strategyMapList;
	private List<FilterBean> filters;

	public List<Map<String, String>> getStrategyMapList() {
		List<Map<String, String>> copyMapList = strategyMapList;
		Collections.copy(copyMapList, strategyMapList);
		return copyMapList;
	}

	public void setStrategyMapList(List<Map<String, String>> strategyMapList) {
		List<Map<String, String>> copyMapList = new ArrayList<>();
		copyMapList.addAll(strategyMapList);
		this.strategyMapList = copyMapList;
	}

	public List<FilterBean> getFilters() {
		List<FilterBean> copyFilters = filters;
		Collections.copy(copyFilters, filters);
		return copyFilters;
	}

	public void setFilters(List<FilterBean> filters) {
		List<FilterBean> copyFilters = new ArrayList<>();
		copyFilters.addAll(filters);
		this.filters = copyFilters;
	}

}
