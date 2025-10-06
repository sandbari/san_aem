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
class InvestmentTeamsTest {

    private InvestmentTeams investmentTeams;
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
        investmentTeams = new InvestmentTeams();
        leaderBio = new LeaderBio();

        ArrayList<LeaderBio> copyList = new ArrayList<LeaderBio>();

        investmentTeams.setResourceResolver(resourceResolver);
        investmentTeams.setCurrentPage(currentPage);
        investmentTeams.setRootPath("/content/vcm/us/en/investment-franchises/usaa-mutual-funds/usaa-investments");
        investmentTeams.setHeading("Investment Teams");
        investmentTeams.setLinkText("Learn More");
        investmentTeams.setTeamDisplayStyle("3 column short info display");
        investmentTeams.setMappedCurrentPage("/investment-franchises/usaa-mutual-funds/usaa-investments");
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
        leaderBio.setBackLinkAriaLabel("Aria Label");
        leaderBio.setPageLinkAriaLabel("Page Aria Label");
        Page pageDetails = currentPage;
        leaderBio.setPageDetails(pageDetails);
        copyList.add(leaderBio);
        investmentTeams.setList(copyList);
    }

    @Test
    void testInvestmentTeams() throws Exception {
        investmentTeams.init();
        assertNotNull(investmentTeams.getHeading());
        assertNotNull(investmentTeams.getLinkText());
        assertNotNull(investmentTeams.getRootPath());
        assertNotNull(investmentTeams.getTeamDisplayStyle());
        assertNotNull(investmentTeams.getMappedCurrentPage());
        assertNotNull(investmentTeams.getCurrentPage());
        assertNotNull(investmentTeams.getResourceResolver());
        assertNotNull(investmentTeams.getList());
        assertTrue(StringUtils.equalsAnyIgnoreCase("Investment Teams", investmentTeams.getHeading()));
        assertTrue(StringUtils.equalsAnyIgnoreCase("Learn More", investmentTeams.getLinkText()));
        assertTrue(StringUtils.equalsAnyIgnoreCase("/content/vcm/us/en/investment-franchises/usaa-mutual-funds/usaa-investments", investmentTeams.getRootPath()));
        assertTrue(StringUtils.equalsAnyIgnoreCase("3 column short info display", investmentTeams.getTeamDisplayStyle()));

    }

    @Test
    void testLeaderBio() throws Exception {
    	leaderBio.setResourceResolver(resourceResolver);
        assertNotNull(leaderBio.getLinkText());
        assertNotNull(leaderBio.getAltText());
        assertNotNull(leaderBio.getBioPicture());
        assertNotNull(leaderBio.getFullname());
        assertNotNull(leaderBio.getTitleAbbreviation());
        assertNotNull(leaderBio.getDesignation());
        assertNotNull(leaderBio.getProfileDescription());
        assertNotNull(leaderBio.getShortDescription());
        assertNotNull(leaderBio.getBioDisplayStyle());
        assertNotNull(leaderBio.getTitleDescription());
        assertNotNull(leaderBio.getManagementPicture());
        assertNotNull(leaderBio.getPagePath());
        leaderBio.getBackPage();
        leaderBio.getPageDetails();
        assertNotNull(leaderBio.getBackLinkAriaLabel());
        assertNotNull(leaderBio.getPageLinkAriaLabel());
        assertTrue(StringUtils.equalsAnyIgnoreCase("Back", leaderBio.getLinkText()));
        assertTrue(StringUtils.equalsAnyIgnoreCase("Leader image", leaderBio.getAltText()));
        assertTrue(StringUtils.equalsAnyIgnoreCase("/content/dam/vcm/basic/call-logo.svg", leaderBio.getBioPicture()));
        assertTrue(StringUtils.equalsAnyIgnoreCase("Dan Banaszak", leaderBio.getFullname()));
        assertTrue(StringUtils.equalsAnyIgnoreCase("CFA", leaderBio.getTitleAbbreviation()));
        assertTrue(StringUtils.equalsAnyIgnoreCase("President, VictoryShares and Solutions", leaderBio.getDesignation()));
        assertTrue(StringUtils.equalsAnyIgnoreCase("Mannik S. Dhillon serves as president of VictoryShares and Solutions for Victory Capital", leaderBio.getProfileDescription()));
        assertTrue(StringUtils.equalsAnyIgnoreCase("Mannik S. Dhillon serves as president of VictoryShares", leaderBio.getShortDescription()));
        assertTrue(StringUtils.equalsAnyIgnoreCase("Leader Bio Style", leaderBio.getBioDisplayStyle()));
        assertTrue(StringUtils.equalsAnyIgnoreCase("true", leaderBio.getManagementPicture()));
    }
}
