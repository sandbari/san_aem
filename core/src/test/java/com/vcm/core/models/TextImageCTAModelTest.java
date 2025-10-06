package com.vcm.core.models;

import static org.junit.jupiter.api.Assertions.assertNotNull;

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

import io.wcm.testing.mock.aem.junit5.AemContext;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;

@ExtendWith(AemContextExtension.class)
@ExtendWith(MockitoExtension.class)
public class TextImageCTAModelTest {
	TextImageCTAModel textImageCTAModel;
	private ResourceResolver resourceResolver;
    public final AemContext context = new AemContext(ResourceResolverType.JCR_OAK);
	@Mock
	AudienceSelectorService audienceSelectorService;
	 @BeforeEach
	    public void setup() throws Exception {
		 List<String> allowedaudience=new ArrayList<String>();
		 allowedaudience.add("ALL");
		 Mockito.when(audienceSelectorService.getAllowedAudienceForComponent(Mockito.any())).thenReturn(allowedaudience);
		 Mockito.when(audienceSelectorService.isUserAuthorized(Mockito.any())).thenReturn(true);	 
		 resourceResolver=context.resourceResolver();
		 textImageCTAModel = new TextImageCTAModel();
		 textImageCTAModel.setAudienceSelectorService(audienceSelectorService);
		 textImageCTAModel.setResourceResolver(resourceResolver);
		 textImageCTAModel.setVariation("cardWithTitle");
		 textImageCTAModel.setBackgroundImage("/content/dam/vcm/textimageCTA/iStock-1061845192%402x.png");
		 textImageCTAModel.setSectionTitle("Market");
		 textImageCTAModel.setHeading("What is Mutual Fund?");
		 textImageCTAModel.setGreenUnderline("true");
		 textImageCTAModel.setDescription("Investing involves risk including loss of principal. Victory Mutual Funds and USAA Mutual Funds are distributed by Victory Capital Advisers, Inc. (VCA). VictoryShares ETFs and VictoryShares USAA ETFs are distributed by Foreside Fund Services, LLC (Foreside). VCA and Foreside are members of FINRA and SIPC. Victory Capital Management Inc. (VCM) is the investment adviser to the Victory Mutual Funds, USAA Mutual Funds, VictoryShares ETFs and VictoryShares USAA ETFs. VCA and VCM are not affiliated with Foreside. USAA is not affiliated with Foreside, VCM, or VCA. USAA and the USAA logos are registered trademarks and the USAA Mutual Funds and USAA Investments logos are trademarks of United Services Automobile Association and are being used by Victory Capital and its affiliates under license. Victory Capital means Victory Capital Management Inc., the investment manager of the USAA 529 College Savings Plan (Plan). The Plan is distributed by Victory Capital Advisers, Inc., a broker dealer registered with FINRA and an affiliate of Victory Capital. Victory Capital and its affiliates are not affiliated with United Services Automobile Association or its affiliates. USAA and the USAA logo are registered trademarks and the USAA 529 College Savings Plan logo is a trademark of United Services Automobile Association and are being used by Victory Capital and its affiliates under license. News Policies Careers User Agreement Events Fund Literature Blog Connect with us ©2020 Victory Capital Management Inc.");
		 textImageCTAModel.setSecondLinkText("Future");
		 textImageCTAModel.setSecondLinkUrl("/content/vcm/language-masters/en/member/products");
		 textImageCTAModel.setSecondLinkDescription("See Future calculator");
		 textImageCTAModel.setNoOfLinks("true");
		 textImageCTAModel.setLinkText("See Our Franchise");
		 textImageCTAModel.setLinkUrl("/content/vcm/language-masters/en/member/investment-franchise");
		 textImageCTAModel.setLinkDescription("See All Our Franchise list");
		 textImageCTAModel.setButtonStyle("true");
		 textImageCTAModel.setSectionStyle("#792C7A");
		 textImageCTAModel.setTabSelect("_self");
		 textImageCTAModel.setNote("2 min read");
		 textImageCTAModel.setLinkStyle("forwardArrow");
		 textImageCTAModel.setCardShadow("true");
		 textImageCTAModel.setBackgroundStyle("bg-white");
		 textImageCTAModel.setHeadingAlignment("true");;
		 textImageCTAModel.setPopupOverlayContentPath("/content/dam/vcm/vcm-content-fragments/pop-up/blog-popup-model");
		 textImageCTAModel.setPopupId("RiskInfoPopupContent");
		 textImageCTAModel.setPopupIdForScndLink("externalPopUp");
		 textImageCTAModel.setLinkStyleForScndLink("forwardArrow");
		 textImageCTAModel.setButtonStyleForScndLink("true");
		 textImageCTAModel.setPopupOverlayContentPathForScndLink("/content/dam/vcm/vcm-content-fragments/pop-up/external-site-popup ");
		 textImageCTAModel.setTabSelectForScndLink("_self");
		 textImageCTAModel.setIsUserAuthorized(true);
		 textImageCTAModel.setAllowedAudience(allowedaudience);
		 textImageCTAModel.setMembersType("true");
		 textImageCTAModel.setFinancialAdvisorsType("true");
		 textImageCTAModel.setInstitutionalinvestorsType("false");
		 textImageCTAModel.setAltText("What is mutual fund");
		 textImageCTAModel.setLinkAriaLabel("See Future calculator");
		 textImageCTAModel.setSecondLinkAriaLabel("See All Our Franchise list");
		 textImageCTAModel.setExperiencePopupPath("/content/experience-fragments/vcm-experience-fragments/video-overlay.html");
		 context.load().json("/popupNote.json", "/content/dam/vcm/vcm-content-fragments/pop-up/fund-list-tool-tip");
		 context.load().json("/contentFragmentPopupnote.json","/conf/vcm/settings/dam/cfm/models/popup-note");

	 }
	    @Test
	    void testTextImageCTA() throws Exception {
	    	assertNotNull(textImageCTAModel.getResourceResolver());
	    	assertNotNull(textImageCTAModel.getPopupId());
	        assertNotNull(textImageCTAModel.getVariation());
	        assertNotNull(textImageCTAModel.getBackgroundImage());
	        assertNotNull(textImageCTAModel.getSectionTitle());
	        assertNotNull(textImageCTAModel.getHeading());
	        assertNotNull(textImageCTAModel.getGreenUnderline());
	        assertNotNull(textImageCTAModel.getDescription());
	        assertNotNull(textImageCTAModel.getLinkUrl());
	        assertNotNull(textImageCTAModel.getLinkText());
	        assertNotNull(textImageCTAModel.getLinkDescription());
	        assertNotNull(textImageCTAModel.getSecondLinkUrl());
	        assertNotNull(textImageCTAModel.getSecondLinkText());
	        assertNotNull(textImageCTAModel.getSecondLinkDescription());
	        assertNotNull(textImageCTAModel.getNoOfLinks());
	        assertNotNull(textImageCTAModel.getButtonStyle());
	        assertNotNull(textImageCTAModel.getSectionStyle());
	        assertNotNull(textImageCTAModel.getTabSelect());
	        assertNotNull(textImageCTAModel.getNote());
	        assertNotNull(textImageCTAModel.getLinkStyle());
	        assertNotNull(textImageCTAModel.getCardShadow());
	        assertNotNull(textImageCTAModel.getBackgroundStyle());
	        assertNotNull(textImageCTAModel.getHeadingAlignment());
	        assertNotNull(textImageCTAModel.getPopupOverlayContentPath());
	        assertNotNull(textImageCTAModel.getPopupIdForScndLink());
	        assertNotNull(textImageCTAModel.getPopupOverlayContentPathForScndLink());
	        assertNotNull(textImageCTAModel.getLinkStyleForScndLink());
	        assertNotNull(textImageCTAModel.getTabSelectForScndLink());
	        assertNotNull(textImageCTAModel.getButtonStyleForScndLink());
	        assertNotNull(textImageCTAModel.getIsUserAuthorized());
	        assertNotNull(textImageCTAModel.getAllowedAudience());
	        assertNotNull(textImageCTAModel.getMembersType());
	        assertNotNull(textImageCTAModel.getFinancialAdvisorsType());
	        assertNotNull(textImageCTAModel.getInstitutionalinvestorsType());
	        assertNotNull(textImageCTAModel.getAltText());
	        assertNotNull(textImageCTAModel.getLinkAriaLabel());
	        assertNotNull(textImageCTAModel.getSecondLinkAriaLabel());
	        assertNotNull(textImageCTAModel.getExperiencePopupPath());
	        textImageCTAModel.init();
	    }
	    
	    @Test
	    public void testInitWithElse() {
	    	textImageCTAModel.setResourceResolver(context.resourceResolver());
	    	textImageCTAModel.setLinkDescription(null); 
	    	textImageCTAModel.setLinkText("Link Text");
	    	textImageCTAModel.setLinkUrl("Link Url");
	    	textImageCTAModel.setSecondLinkDescription(null); 
	    	textImageCTAModel.setSecondLinkText("Link Text");
	    	textImageCTAModel.setSecondLinkUrl("Link Url");
	    	textImageCTAModel.init();
	    }

}
