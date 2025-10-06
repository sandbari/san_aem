package com.vcm.core.utils;

import javax.annotation.Nullable;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.testing.mock.sling.ResourceResolverType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import com.vcm.core.models.mock.contentfragment.ContentFragmentMockAdapter;

import io.wcm.testing.mock.aem.junit5.AemContext;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;
import junit.framework.Assert;

import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
@ExtendWith(AemContextExtension.class)
class UtilityServiceTest {
	
    private ResourceResolver resourceResolver;
    public final AemContext context = new AemContext(ResourceResolverType.JCR_OAK);
    
    @BeforeEach
    public void setup() throws Exception {
        resourceResolver=context.resourceResolver();
        context.registerAdapter(Resource.class, com.adobe.cq.dam.cfm.ContentFragment.class, ADAPTER);
    }
    
    @Test
    void testActivate() {
        
//        String[] linkRewriterValues = {"/content/dam/vcm:/assets"};
//        String[] extensionlessDomainValues = {"vcm-dev-aem65.adobecqms.net", "dev.vcm.com", "dev.institutional.vcm.com", "dev.advisor.vcm.com"};
        
        UtilityService.Config config = mock(UtilityService.Config.class);
        UtilityService utilityService = new UtilityService();;
        utilityService.activate(config);
    }

    @Test
    void testInternalUrl() throws Exception {
    	context.load().json("/hrefUtilLinkChecker.json", "/Content/vcm/en");
    	Assert.assertEquals("/Content/vcm/en.html",UtilityService.identifyLinkUrl("/Content/vcm/en", resourceResolver));
    }
    @Test
    void testExternalUrl() throws Exception {
    	Assert.assertEquals("https://www.google.com/test",UtilityService.identifyLinkUrl("https://www.google.com/test", resourceResolver));
    }
    @Test
    void testNullUrl() throws Exception {
    	Assert.assertEquals(null,UtilityService.identifyLinkUrl(null,resourceResolver));
    }
    @Test
    void testDAMUrl() throws Exception {
    	Assert.assertEquals("/content/dam/vcm/basic/VCM-logo.svg",UtilityService.identifyLinkUrl("/content/dam/vcm/basic/VCM-logo.svg",resourceResolver));
    }
    @Test
    void testPDFRewriterwithNull() {
    	Assert.assertEquals("/content/dam/vcm/basic/VCM.pdf",UtilityService.getPDFLinkUrl("/content/dam/vcm/basic/VCM.pdf"));
    }
    @Test
    void testPDFRewriterwithString() {
    	Assert.assertEquals("/content/dam/vcm/basic/VCM.pdf",UtilityService.getPDFLinkUrl("/content/dam/vcm/basic/VCM.pdf"));
    }
    @Test
    void testPopupIdValue() { 
    	context.load().json("/popupNote.json", "/content/dam/vcm/vcm-content-fragments/pop-up/fund-list-tool-tip");
    	context.load().json("/contentFragmentPopupnote.json","/conf/vcm/settings/dam/cfm/models/popup-note");
    	Assert.assertEquals("RiskInfoPopupContent",UtilityService.getPopupId("/content/dam/vcm/vcm-content-fragments/pop-up/fund-list-tool-tip",resourceResolver));
    }
	
    @Test
    void testProductDetailOverlayContent() { 
    	context.load().json("/tooltipNote.json", "/content/dam/vcm/vcm-content-fragments/tool-tip/product-detail-tool-tip");
    	context.load().json("/contentFragmentTooltipNote.json","/conf/vcm/settings/dam/cfm/models/tooltip-note");
    	Assert.assertEquals("This is Tool Tip Content Fragment Sample",UtilityService.getProductDetailOverlayContent("/content/dam/vcm/vcm-content-fragments/tool-tip/product-detail-tool-tip",resourceResolver,"tooltipContent"));
    }
    
    
    @Test
    void testNullPopupIdValue() {
    	Assert.assertEquals(null,UtilityService.getPopupId(null,resourceResolver));
    }
	
    @Test
    void testNullTooltipValue() {
    	Assert.assertEquals(null,UtilityService.getProductDetailOverlayContent(null,resourceResolver,"tooltipContent"));
    }
    
    @Test
    void testGetLinkDescription() {
    	context.load().json("/contactVictory.json", "/content/vcm/language-masters/en/financial-professional/contact-us");
    	Assert.assertNotNull(UtilityService.getLinkDescription("/content/vcm/language-masters/en/financial-professional/contact-us", "Link Text", resourceResolver));
    }
    
    @Test
    void testGetLinkRewriterValues() {
    	UtilityService utilityService = new UtilityService();
    	Assert.assertNotNull(utilityService.getLinkRewriterValues());
    }
    
    private static final java.util.function.Function<Resource, com.adobe.cq.dam.cfm.ContentFragment> CONTENT_FRAGMENT_ADAPTER = new ContentFragmentMockAdapter();
	public static final com.google.common.base.Function<Resource, com.adobe.cq.dam.cfm.ContentFragment> ADAPTER = new com.google.common.base.Function<Resource, com.adobe.cq.dam.cfm.ContentFragment>() {
		@Nullable
		@Override
		public com.adobe.cq.dam.cfm.ContentFragment apply(@Nullable Resource resource) {
			return CONTENT_FRAGMENT_ADAPTER.apply(resource);
		}
	};
}
