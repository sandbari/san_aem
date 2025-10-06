package com.vcm.core.models;

import com.day.cq.wcm.api.Page;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.testing.mock.sling.ResourceResolverType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import io.wcm.testing.mock.aem.junit5.AemContext;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;
import junitx.framework.Assert;

@ExtendWith(AemContextExtension.class)
class FooterModelTest {

    private FooterModel footer;
    private Page currentPage;
    private ResourceResolver resourceResolver;
    private static final String PAGE_PATH = "/content/vcm/language-masters/en/footer";
    public final AemContext context = new AemContext(ResourceResolverType.JCR_OAK);

    @BeforeEach
    public void setup() throws Exception {
        context.load().json("/investmentTeams.json", "/content/vcm/language-masters/en/footer");
        resourceResolver=context.resourceResolver();
        currentPage = context.currentPage(PAGE_PATH);
        footer=new FooterModel();
        footer.setResourceResolver(resourceResolver);
        footer.setCurrentPage(currentPage);
        footer.setCopyRightText("copyRightText");
        footer.setIncSocShare(false);
        footer.setLogoImagePath("logoImagePath");
        footer.setLogoMobImagePath("logoMobImagePath");
        footer.setLogoAltText("logoAltText");
        footer.setLogoLinkUrl("logoLinkUrl");
        footer.setMappedCurrentPage("https://www.vcm.com");
        footer.setMultiFooterLinks(resourceResolver.getResource("/content/vcm/language-masters/en/footer"));
        footer.setPostLoginMultiFooterLinks(resourceResolver.getResource("/content/vcm/language-masters/en/footer"));
        footer.setUpdFooterLinks(footer.getUpdFooterLinks());
        footer.setUpdPstLoginFooterLinks(footer.getUpdPstLoginFooterLinks());
    }

    @Test
    void testLevelNavigation() throws Exception {

		Assert.assertEquals("copyRightText", footer.getCopyRightText());
		Assert.assertEquals("logoImagePath", footer.getLogoImagePath());
		Assert.assertEquals("logoMobImagePath", footer.getLogoMobImagePath());
		Assert.assertEquals("logoAltText", footer.getLogoAltText());
		Assert.assertEquals("logoLinkUrl", footer.getLogoLinkUrl());
		Assert.assertEquals("logoAltText", footer.getLogoAltText());
        Assert.assertEquals("https://www.vcm.com", footer.getMappedCurrentPage());
		Assert.assertEquals(false, footer.getIncSocShare().booleanValue());
		Assert.assertNotNull(footer.getUpdFooterLinks());
        Assert.assertNotNull(footer.getMultiFooterLinks());
        Assert.assertNotNull(footer.getResourceResolver());
        Assert.assertNotNull(footer.getCurrentPage());
        Assert.assertNotNull(footer.getUpdPstLoginFooterLinks());
        Assert.assertNotNull(footer.getPostLoginMultiFooterLinks());
    	footer.init();
    }
}
