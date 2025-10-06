package com.vcm.core.pojo;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TabBeanTest {
	private TabBean tabBean;
	
	@BeforeEach
    public void setup() throws Exception {
		tabBean = new TabBean();
	}
	
	@Test
    public void testGetterSetters() throws Exception {
		
		tabBean.setTabLink("Tab Link");
		tabBean.setTabTitle("Tab Title");
		
		assertNotNull(tabBean.getTabLink());
		assertNotNull(tabBean.getTabTitle());
		
	}
	
}
