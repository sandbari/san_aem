package com.vcm.core.models;

import io.wcm.testing.mock.aem.junit5.AemContext;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.testing.mock.sling.ResourceResolverType;

@ExtendWith(AemContextExtension.class)
public class TeamsLeaderBioTest {
	
	public final AemContext context = new AemContext(ResourceResolverType.JCR_OAK);
	
	@Mock
	ResourceResolver resourceResolver;
	
    TeamsLeaderBio teamsLeaderBio;

    @BeforeEach
    public void setup() throws Exception {
        teamsLeaderBio = new TeamsLeaderBio();
        teamsLeaderBio.setAltText("Leader Image");
        teamsLeaderBio.setBioPicture("/content/dam/leader.png");
        teamsLeaderBio.setDesignation("CFO");
        teamsLeaderBio.setFullname("John Spear");
        teamsLeaderBio.setLinkTarget("Same Tab");
        teamsLeaderBio.setLinkText("Back to Detail Page");
        teamsLeaderBio.setTeamName("Victory Capital Solutions");
        teamsLeaderBio.setLinkUrl("/content/vcm/us/en/investment-franchises/usaa-mutual-funds/usaa-investments/john-spear");
        teamsLeaderBio.setLeaderUrl("/content/vcm/us/en/investment-franchises/usaa-mutual-funds/usaa-investments/john-spear");
        teamsLeaderBio.setProfileDescription("Victory Capital Chief Investment Officer - USAA Investments, a Victory Capital Investment Franchise");
        teamsLeaderBio.setLinkAriaLabel("View Page");
        teamsLeaderBio.setLinkDescription("Link Description");
        
        resourceResolver = context.resourceResolver();
        teamsLeaderBio.setResourceResolver(resourceResolver);
    }

    @Test
    void testTeamLeader() throws Exception {
        assertNotNull(teamsLeaderBio.getAltText());
        assertNotNull(teamsLeaderBio.getBioPicture());
        assertNotNull(teamsLeaderBio.getDesignation());
        assertNotNull(teamsLeaderBio.getFullname());
        assertNotNull(teamsLeaderBio.getLinkTarget());
        assertNotNull(teamsLeaderBio.getLinkText());
        assertNotNull(teamsLeaderBio.getTeamName());
        assertNotNull(teamsLeaderBio.getProfileDescription());
        assertNotNull(teamsLeaderBio.getLinkUrl());
        assertNotNull(teamsLeaderBio.getLeaderUrl());
        teamsLeaderBio.init();
        assertNotNull(teamsLeaderBio.getLinkAriaLabel());
    }
    
    @Test
    public void testInitWithElse() {
    	teamsLeaderBio.setResourceResolver(context.resourceResolver());
    	teamsLeaderBio.setLinkDescription(null); 
    	teamsLeaderBio.setLinkText("Link Text");
    	teamsLeaderBio.setLinkUrl("Link Url");
    	teamsLeaderBio.init();
    }
}
