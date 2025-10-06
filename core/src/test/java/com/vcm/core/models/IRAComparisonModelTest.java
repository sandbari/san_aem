package com.vcm.core.models;

import java.util.ArrayList;
import java.util.List;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.testing.mock.sling.ResourceResolverType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import io.wcm.testing.mock.aem.junit5.AemContext;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;
import junitx.framework.Assert;

@ExtendWith(AemContextExtension.class)
public class IRAComparisonModelTest {
	@InjectMocks
    private IRAComparisonModel iraComparisonModel;
    private ResourceResolver resourceResolver;
   
    public final AemContext context = new AemContext(ResourceResolverType.JCR_OAK);

    @BeforeEach
    public void setup() throws Exception {
        iraComparisonModel=new IRAComparisonModel();
        resourceResolver=context.resourceResolver();
        iraComparisonModel.setResourceResolver(resourceResolver);
        iraComparisonModel.setButtonDescription("Do you know what it takes to work towards a secure retirement?");
        iraComparisonModel.setIraComparisonText("To determine which type of IRA is right for you");
        iraComparisonModel.setLinkUrl("/content/vcm/en");
        iraComparisonModel.setLinkText("Calculate Now");
        iraComparisonModel.setLinkTarget("same tab");
        iraComparisonModel.setIraTitleOne("Traditional IRAs");
        iraComparisonModel.setIraTitleTwo("Roth IRAs");
        iraComparisonModel.setSectionHeading("What is an IRA?");
        iraComparisonModel.setSectionDescription("An individual retirement account (IRA) helps you save for retirement");
        iraComparisonModel.setLinkAriaLabel("Calculate Now");
        iraComparisonModel.setLinkDescription("Link Description");
        iraComparisonModel.init();
    }
    
    @Test
    void testIRACompareTable() throws Exception {
        Assert.assertEquals("Do you know what it takes to work towards a secure retirement?", iraComparisonModel.getButtonDescription());
        Assert.assertEquals("To determine which type of IRA is right for you", iraComparisonModel.getIraComparisonText());
        Assert.assertEquals("/content/vcm/en", iraComparisonModel.getLinkUrl());
        Assert.assertEquals("Calculate Now", iraComparisonModel.getLinkText());
        Assert.assertEquals("same tab", iraComparisonModel.getLinkTarget());
        Assert.assertEquals("Traditional IRAs", iraComparisonModel.getIraTitleOne());
        Assert.assertEquals("Roth IRAs", iraComparisonModel.getIraTitleTwo());
        Assert.assertEquals("What is an IRA?", iraComparisonModel.getSectionHeading());
        Assert.assertEquals("An individual retirement account (IRA) helps you save for retirement", iraComparisonModel.getSectionDescription());
        Assert.assertNotNull("Calculate Now", iraComparisonModel.getLinkAriaLabel());
        Assert.assertNotNull(iraComparisonModel.getLinkDescription());
        Assert.assertNotNull(iraComparisonModel.getResourceResolver());
    }
    @Test
    public void testGetIraTable() {
    	 List<IraCompareText> copyIraTable = new ArrayList<IraCompareText>();
    	 iraComparisonModel.setIraTable(copyIraTable);
    	 Assert.assertNotNull(iraComparisonModel.getIraTable());
    }
    
    @Test
    public void testInitWithElse() {
    	iraComparisonModel.setResourceResolver(context.resourceResolver());
    	iraComparisonModel.setLinkDescription(null); 
    	iraComparisonModel.setLinkText("Link Text");
    	iraComparisonModel.setLinkUrl("Link Url");
    	iraComparisonModel.init();
    }
}