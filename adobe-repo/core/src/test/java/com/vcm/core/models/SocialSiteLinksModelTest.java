package com.vcm.core.models;

import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.testing.mock.sling.ResourceResolverType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import io.wcm.testing.mock.aem.junit5.AemContext;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;
import junitx.framework.Assert;

import java.util.ArrayList;
import java.util.List;

@ExtendWith(AemContextExtension.class)
class SocialSiteLinksModelTest {

    private SocialSiteLinksModel social;
    private ResourceResolver resourceResolver;
    public final AemContext context = new AemContext(ResourceResolverType.JCR_OAK);

    @BeforeEach
    public void setup() throws Exception {
        resourceResolver=context.resourceResolver();
        social=new SocialSiteLinksModel();
        SocialLinkIcons socialShareIcons = new SocialLinkIcons();
        socialShareIcons.setLinkUrl("https://www.facebook.com");
        List<SocialLinkIcons> copySocialShare = new ArrayList<SocialLinkIcons>();
        copySocialShare.add(socialShareIcons);
        social.setUpdSocialShare(copySocialShare);
        context.load().json("/socialshare.json", "/content/vcm/language-masters/en/socialshare");
        social.setHeading("headText");
        social.setResourceResolver(resourceResolver);
        social.setSocialLinks(resourceResolver.getResource("/content/vcm/language-masters/en/socialshare"));
       
    }

    @Test
    void testLevelNavigation() throws Exception {
    	
    	Assert.assertEquals("headText", social.getHeading());
		Assert.assertNotNull(social.getUpdSocialShare());
        Assert.assertNotNull(social.getSocialLinks());
        Assert.assertNotNull(social.getResourceResolver());
		social.init();
    }
}
