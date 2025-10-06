package com.vcm.core.models;

import com.day.cq.wcm.api.Page;
import io.wcm.testing.mock.aem.junit5.AemContext;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;
import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.testing.mock.sling.ResourceResolverType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(AemContextExtension.class)
public class ProductManagementTeamTest {

    private ProductManagementTeam productManagementTeam;
    private ManagementTeam managementTeam;
    private LeaderBio leaderBio;
    private ResourceResolver resourceResolver;
    private Page currentPage;
    private static final String PAGE_PATH = "/content/vcm/us/en/investment-franchises/usaa-mutual-funds/usaa-investments";
    public final AemContext context = new AemContext(ResourceResolverType.JCR_OAK);

    @BeforeEach
    public void setup() throws Exception {
        context.load().json("/investmentTeams.json", "/content/vcm/us/en/investment-franchises/usaa-mutual-funds/usaa-investments");
        resourceResolver = context.resourceResolver();
        currentPage = context.currentPage(PAGE_PATH);
        ArrayList<ManagementTeam> copyList = new ArrayList<ManagementTeam>();
        ArrayList<LeaderBio> leaderList = new ArrayList<LeaderBio>();
        productManagementTeam = new ProductManagementTeam();
        productManagementTeam.setDescription("typography");
        productManagementTeam.setHeading("Management Team");
        productManagementTeam.setLinkText("See Full Bio");
        productManagementTeam.setResourceResolver(resourceResolver);
        productManagementTeam.setTeamLinkText("Show Team");
        productManagementTeam.setTriangleColor("fff");
        productManagementTeam.setTeamLinkUrl("/content/vcm/us/en/investment-franchises/usaa-mutual-funds/usaa-investments");
        managementTeam = new ManagementTeam();
        managementTeam.setLeaderUrl("/content/vcm/us/en/investment-franchises/usaa-mutual-funds/usaa-investments");
        managementTeam.setPictureview("true");
        copyList.add(managementTeam);
        productManagementTeam.setTeamsLeaderBio(copyList);
        leaderBio = new LeaderBio();
        leaderBio.setLinkText("Back");
        leaderBio.setFullname("Dan Banaszak");
        leaderBio.setAltText("Leader image");
        leaderBio.setBioPicture("/content/dam/vcm/basic/call-logo.svg");
        leaderBio.setTitleAbbreviation("CFA");
        leaderBio.setDesignation("President, VictoryShares and Solutions");
        leaderBio.setProfileDescription("Mannik S. Dhillon serves as president of VictoryShares and Solutions for Victory Capital");
        leaderBio.setShortDescription("Mannik S. Dhillon serves as president of VictoryShares");
        leaderBio.setBioDisplayStyle("Leader Bio Style");
        leaderBio.setTitleDescription("Victory Capital investor");
        leaderBio.setPagePath("/content/vcm/us/en/investment-franchises/usaa-mutual-funds/usaa-investments");
        leaderBio.setManagementPicture("true");
        Page pageDetails = currentPage;
        leaderBio.setPageDetails(pageDetails);
        leaderList.add(leaderBio);
        productManagementTeam.setList(leaderList);
    }

    @Test
    void testProductManagementTeam() throws Exception {
        productManagementTeam.init();
        assertNotNull(productManagementTeam.getDescription());
        assertNotNull(productManagementTeam.getHeading());
        assertNotNull(productManagementTeam.getLinkText());
        assertNotNull(productManagementTeam.getResourceResolver());
        assertNotNull(productManagementTeam.getTeamLinkText());
        assertNotNull(productManagementTeam.getTeamLinkUrl());
        assertNotNull(productManagementTeam.getTeamsLeaderBio());
        assertNotNull(productManagementTeam.getList());
    }

    @Test
    void testManagementTeam() throws Exception {
        assertNotNull(managementTeam.getLeaderUrl());
        assertNotNull(managementTeam.getPictureview());
    }
}
