package com.vcm.core.models;

import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.testing.mock.sling.ResourceResolverType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import io.wcm.testing.mock.aem.junit5.AemContext;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;
import junit.framework.Assert;

@ExtendWith(AemContextExtension.class)
class AudienceSelectionModelTest {

	private AudienceSelectionModel audienceSelectionModel;

	public final AemContext context = new AemContext(ResourceResolverType.JCR_OAK);

	private ResourceResolver resourceResolver;
	String tabselectFinprof = "tabselectFinprof";
	String desc = "desc";
	String victoryFundLinkText = "victoryFundLinkText";
	String finProfPageLinkUrl = "finProfPageLinkUrl";
	String finProfPageLinkText = "finProfPageLinkText";
	String headingText = "headingText";
	String instInvPageLinkText = "instInvPageLinkText";
	String instInvPageLinkUrl = "instInvPageLinkUrl";
	String memPageLinkText = "memPageLinkText";
	String memPageLinkUrl = "memPageLinkUrl";
	String tabSelect = "tabSelect";
	String tabselectInstinv = "tabselectInstinv";
	String individualInvestorLinkText = "IndividualInvestor";
	String usaafundsInvestorLinkText = "usaafundsInvestorLinkText";
	String usaafundsInvestorLinkUrl = "usaafundsInvestorLinkUrl";
	String victoryfundsInvestorLinkText = "victoryfundsInvestorLinkText";
	String finadvLinkText = "finadvLinkText";
	String victoryFinadvLinkUrl = "victoryFinadvLinkUrl";
	String overlayText = "overlayText";
	String overlayContentPath = "overlayContentPath";
	String overlayId = "overlayId";
	String individualInvestordesc = "individualInvestordesc";

	@BeforeEach
	public void setup() throws Exception {
		resourceResolver = context.resourceResolver();
		audienceSelectionModel = new AudienceSelectionModel();

		audienceSelectionModel.setDesc(desc);

		audienceSelectionModel.setFinProfPageLinkUrl(finProfPageLinkUrl);

		audienceSelectionModel.setFinProfPageLinkText(finProfPageLinkText);

		audienceSelectionModel.setVictoryFundLinkText(victoryFundLinkText);

		audienceSelectionModel.setHeadingText(headingText);

		audienceSelectionModel.setInstInvPageLinkText(instInvPageLinkText);

		audienceSelectionModel.setInstInvPageLinkUrl(instInvPageLinkUrl);

		audienceSelectionModel.setMemPageLinkText(memPageLinkText);

		audienceSelectionModel.setMemPageLinkUrl(memPageLinkUrl);
		audienceSelectionModel.setResourceResolver(resourceResolver);

		audienceSelectionModel.setTabSelect(tabSelect);

		audienceSelectionModel.setTabselectInstinv(tabselectInstinv);

		audienceSelectionModel.setTabselectFinprof(tabselectFinprof);
		
		audienceSelectionModel.setTabselectInstinv(tabselectInstinv);

		audienceSelectionModel.setIndividualInvestorLinkText(individualInvestorLinkText);

		audienceSelectionModel.setUsaafundsInvestorLinkText(usaafundsInvestorLinkText);

		audienceSelectionModel.setUsaafundsInvestorLinkUrl(usaafundsInvestorLinkUrl);

		audienceSelectionModel.setVictoryfundsInvestorLinkText(victoryfundsInvestorLinkText);

		audienceSelectionModel.setFinadvLinkText(finadvLinkText);

		audienceSelectionModel.setVictoryFinadvLinkUrl(victoryFinadvLinkUrl);

		audienceSelectionModel.setOverlayContentPath(overlayContentPath);

		audienceSelectionModel.setOverlayId(overlayId);

		audienceSelectionModel.setOverlayText(overlayText);

		audienceSelectionModel.setIndividualInvestordesc(individualInvestordesc);
	}

	@Test
	void testInit() throws Exception {
		Assert.assertEquals(audienceSelectionModel.getDesc(), desc);
		Assert.assertEquals(audienceSelectionModel.getFinProfPageLinkText(),this.finProfPageLinkText);
		Assert.assertEquals(audienceSelectionModel.getFinProfPageLinkUrl(), this.finProfPageLinkUrl);
		Assert.assertEquals(audienceSelectionModel.getVictoryFundLinkText(), this.victoryFundLinkText);
		Assert.assertEquals(audienceSelectionModel.getHeadingText(), headingText);
		Assert.assertEquals(audienceSelectionModel.getInstInvPageLinkText(), this.instInvPageLinkText);
		Assert.assertEquals(audienceSelectionModel.getInstInvPageLinkUrl(),this.instInvPageLinkUrl);
		Assert.assertEquals(audienceSelectionModel.getMemPageLinkText(),this.memPageLinkText);
		Assert.assertEquals(audienceSelectionModel.getMemPageLinkUrl(),this.memPageLinkUrl);
		Assert.assertEquals(audienceSelectionModel.getTabSelect(),this.tabSelect);
		Assert.assertEquals(audienceSelectionModel.getTabselectFinprof(),this.tabselectFinprof);
		Assert.assertEquals(audienceSelectionModel.getTabselectInstinv(),this.tabselectInstinv);
		Assert.assertNotNull(audienceSelectionModel.getResourceResolver());
		Assert.assertEquals(audienceSelectionModel.getIndividualInvestorLinkText(),this.individualInvestorLinkText);
		Assert.assertEquals(audienceSelectionModel.getUsaafundsInvestorLinkText(), this.usaafundsInvestorLinkText);
		Assert.assertEquals(audienceSelectionModel.getUsaafundsInvestorLinkUrl(), this.usaafundsInvestorLinkUrl);
		Assert.assertEquals(audienceSelectionModel.getVictoryfundsInvestorLinkText(), this.victoryfundsInvestorLinkText);
		Assert.assertEquals(audienceSelectionModel.getFinadvLinkText(),this.finadvLinkText);
		Assert.assertEquals(audienceSelectionModel.getVictoryFinadvLinkUrl(),this.victoryFinadvLinkUrl);
		Assert.assertEquals(audienceSelectionModel.getOverlayContentPath(),this.overlayContentPath);
		Assert.assertEquals(audienceSelectionModel.getOverlayId(),this.overlayId);
		Assert.assertEquals(audienceSelectionModel.getOverlayText(),this.overlayText);
		Assert.assertEquals(audienceSelectionModel.getIndividualInvestordesc(),this.individualInvestordesc);
	}

}
