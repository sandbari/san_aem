package com.vcm.core.pojo;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

public class BreadCrumbNavItemTest {

	@Test
    public void testGetterSetters() throws Exception {
		
		String title = "testTitle";
		String link = "testLink";
		String memberUrl = "memberUrl";
		String guestUrl = "guestUrl";
		
		BreadCrumbNavItem breadCrumbNavItem = new BreadCrumbNavItem(title, link);
		breadCrumbNavItem.setMemberUrl(memberUrl);
		breadCrumbNavItem.setGuestUrl(guestUrl);
		assertNotNull(breadCrumbNavItem.getLink());
		assertNotNull(breadCrumbNavItem.getTitle());
		assertNotNull(breadCrumbNavItem.getGuestUrl());
		assertNotNull(breadCrumbNavItem.getMemberUrl());
		
	}
	
}
