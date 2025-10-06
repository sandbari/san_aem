package com.vcm.core.pojo;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class PhoneListBeanTest {
	private PhoneListBean phoneListBean;
	
	@BeforeEach
    public void setup() throws Exception {
		phoneListBean = new PhoneListBean();
	}
	
	@Test
    public void testGetterSetters() throws Exception {
		
		phoneListBean.setStaticOpenText("Open Text");
		phoneListBean.setStaticPhoneNumber("12345");
		
		assertNotNull(phoneListBean.getStaticOpenText());
		assertNotNull(phoneListBean.getStaticPhoneNumber());
		
	}
	
}
