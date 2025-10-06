package com.vcm.core.models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import io.wcm.testing.mock.aem.junit5.AemContextExtension;
import junitx.framework.Assert;

@ExtendWith(AemContextExtension.class)
class SocialLinkIconsTest {

    private SocialLinkIcons info;

    @BeforeEach
    public void setup() throws Exception {

    	info=new SocialLinkIcons();
    	info.setSocialIcons("socialIcons");
    	info.setAltText("AltText");
    	info.setLinkUrl("LinkUrl");
    	info.setTabSelect("tabSelect");
       
    }

    @Test
    void testLevelNavigation() throws Exception {
    	Assert.assertEquals("socialIcons", info.getSocialIcons());
    	Assert.assertEquals("AltText", info.getAltText());
    	Assert.assertEquals("LinkUrl", info.getLinkUrl());
		Assert.assertEquals("tabSelect", info.getTabSelect());
    }
}
