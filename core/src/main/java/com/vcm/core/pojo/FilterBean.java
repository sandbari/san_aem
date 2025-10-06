package com.vcm.core.pojo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class FilterBean {
	private String name;
	private List<String> values;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<String> getValues() {
		List<String> copyValues = values;
		Collections.copy(copyValues, values);
		return copyValues;
	}

	public void setValues(List<String> values) {
		List<String> copyValues = new ArrayList<>();
		copyValues.addAll(values);
		this.values = copyValues;
	}

}
