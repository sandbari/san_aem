package com.vcm.core.pojo;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class FilterBeanTest {
	private FilterBean filterBean;
	
	@BeforeEach
    public void setup() throws Exception {
		filterBean = new FilterBean();
	}
	
	@Test
    public void testGetterSetters() throws Exception {
		
		filterBean.setName("Test Name");
		List<String> listValues = new ArrayList<String>();
		listValues.add("Value 1");
		listValues.add("Value 2");
		listValues.add("Value 3");
		filterBean.setValues(listValues);
		
		assertNotNull(filterBean.getName());
		assertNotNull(filterBean.getValues());
		
	}
	
}
