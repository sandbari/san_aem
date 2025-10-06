package com.vcm.core.models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import io.wcm.testing.mock.aem.junit5.AemContextExtension;
import junitx.framework.Assert;

@ExtendWith(AemContextExtension.class)
class MultiFooterLinkTest {

    private MultiFooterLink info;

    @BeforeEach
    public void setup() throws Exception {
    	info=new MultiFooterLink();
    	info.setFooterLinkText("footerLinkText");
    	info.setFooterLinkUrl("footerLinkUrl");
    	info.setTabSelect("tabSelect");
    }

    @Test
    void testLevelNavigation() throws Exception {
    	Assert.assertEquals("footerLinkText", info.getFooterLinkText());
    	Assert.assertEquals("footerLinkUrl", info.getFooterLinkUrl());
		Assert.assertEquals("tabSelect", info.getTabSelect());
    }
}
