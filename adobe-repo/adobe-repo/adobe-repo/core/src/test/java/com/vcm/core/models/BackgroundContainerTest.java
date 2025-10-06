package com.vcm.core.models;

import io.wcm.testing.mock.aem.junit5.AemContext;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;
import junitx.framework.Assert;

import java.util.ArrayList;
import java.util.List;

import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.testing.mock.sling.ResourceResolverType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.vcm.core.service.AudienceSelectorService;

@ExtendWith(AemContextExtension.class)
@ExtendWith(MockitoExtension.class)
public class BackgroundContainerTest {
    BackgroundContainer backgroundContainer;
    private ResourceResolver resourceResolver;
    public final AemContext context = new AemContext(ResourceResolverType.JCR_OAK);

    @Mock
	AudienceSelectorService audienceSelectorService;
	
    @BeforeEach
    public void setup() throws Exception {
    	List<String> allowedaudience=new ArrayList<String>();
		allowedaudience.add("ALL");
		Mockito.when(audienceSelectorService.getAllowedAudienceForComponent(Mockito.any())).thenReturn(allowedaudience);
        resourceResolver=context.resourceResolver();
        backgroundContainer = new BackgroundContainer();
        backgroundContainer.setResourceResolver(resourceResolver);
        backgroundContainer.setBackgroundStyle("grey");
        backgroundContainer.setCornerTriangle("triangle");
        backgroundContainer.setHeadingAlignment("left");
        backgroundContainer.setHeadingText("Welcome");
        backgroundContainer.setLeftLinkText("Previous");
        backgroundContainer.setLeftLinkUrl("/content/vcm/language-masters/en");
        backgroundContainer.setLeftPageTitle("Home");
        backgroundContainer.setLoadMoreButtonLabel("LoadMore");
        backgroundContainer.setRightLinkText("Next Page");
        backgroundContainer.setRightLinkUrl("/content/vcm/language-masters/en");
        backgroundContainer.setRightPageTitle("Members");
        backgroundContainer.setAudienceSelectorService(audienceSelectorService);
        backgroundContainer.setFilterViewType("Test Filter View Type");
        backgroundContainer.setSeeAllLabel("Test See All Label");
        backgroundContainer.setSeeAllLink("Test See All Link");
        backgroundContainer.setIlcAlignment("Test ILC Element");
        backgroundContainer.setSlingRequest(context.request());
        context.load().json("/rootPage.json", "/content/vcm/language-masters/en");
    }

    @Test
    void testBackgroundContainerComponent() throws Exception {

        Assert.assertNotNull(backgroundContainer.getResourceResolver());
        Assert.assertNotNull(backgroundContainer.getBackgroundStyle());
        Assert.assertNotNull(backgroundContainer.getCornerTriangle());
        Assert.assertNotNull(backgroundContainer.getHeadingAlignment());
        Assert.assertNotNull(backgroundContainer.getHeadingText());
        Assert.assertNotNull(backgroundContainer.getLeftLinkText());
        Assert.assertNotNull(backgroundContainer.getLeftLinkUrl());
        Assert.assertNotNull(backgroundContainer.getLeftPageTitle());
        Assert.assertNotNull(backgroundContainer.getLoadMoreButtonLabel());
        Assert.assertNotNull(backgroundContainer.getRightLinkText());
        Assert.assertNotNull(backgroundContainer.getRightLinkUrl());
        Assert.assertNotNull(backgroundContainer.getRightPageTitle());
        Assert.assertNotNull(backgroundContainer.getAllowedAudience());
        Assert.assertNotNull(backgroundContainer.getFilterViewType());
        Assert.assertNotNull(backgroundContainer.getSeeAllLabel());
        Assert.assertNotNull(backgroundContainer.getSeeAllLink());
        Assert.assertNotNull(backgroundContainer.getIlcAlignment());
        backgroundContainer.init();

    }
}
