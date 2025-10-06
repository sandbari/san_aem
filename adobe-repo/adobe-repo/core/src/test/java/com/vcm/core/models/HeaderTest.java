package com.vcm.core.models;

import com.vcm.core.pojo.HeaderBean;
import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.testing.mock.sling.ResourceResolverType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import io.wcm.testing.mock.aem.junit5.AemContext;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(AemContextExtension.class)
class HeaderTest {

    private Header header;
    private HeaderBean headerBean;
    private ResourceResolver resourceResolver;
    public final AemContext context = new AemContext(ResourceResolverType.JCR_OAK);

    @BeforeEach
    public void setup() throws Exception {
        resourceResolver=context.resourceResolver();
        header=new Header();
        headerBean = new HeaderBean();
        headerBean.setImageAlt("ImageAlt");
        headerBean.setLinkText("Link Text");
        headerBean.setNavText("Nav Text");
        headerBean.setEntityType("Members");
        headerBean.setLinkUrl("/content/vcm/en");
        headerBean.setImagePath("/content/dam/vcm/basic/call-logo.svg");
        header.setNavRootPath("/content/vcm/language-masters/en");
        header.setContactImage("/content/dam/vcm/basic/call-logo.svg");
        header.setLogoImage("/content/dam/vcm/basic/VCM-logo.svg");
        header.setLogoLinkUrl("/content/vcm/testheader/investment-franchies");
        header.setLogoAltText("Logo");
        header.setFinancialLinkText("Financial Professionals");
        header.setFinancialLinkUrl("/content/vcm/testheader/investment-franchies");
        header.setInstitutionalLinkText("Investor relations");
        header.setInstitutionalLinkUrl("/content/vcm/testheader/investment-franchies");
        header.setInvestorLinkText("Investor Type");
        header.setInvestorLinkUrl("/content/vcm/testheader");
        header.setMembersLinkText("USSA Member");
        header.setMembersLinkUrl("/content/vcm/en");
        header.setRegisterLinkText("Signin/Register");
        header.setRegisterLinkUrl("/content/vcm/testheader/investment-franchies");
        header.setContactLinkText("Contact Us");
        header.setContactLinkUrl("/contact/vcm/contactus");
        header.setMobileInvestorText("What type of Investor are you?");
        header.setInvestorLinkTarget("Same Tab");
        header.setContactLinkTarget("Same Tab");
        header.setSigninLinkTarget("Same Tab");
        header.setSearchPlaceHolder("How can we help you?");
        header.setBackText("Back");
        header.setMobileSigninLinkText("Sign In");
        header.setResourceResolver(resourceResolver);
        header.setMemberLinkDescription("Member Link Desc");
        header.setFinancialLinkDescription("Financial Link Desc");
        header.setInstitutionalLinkDescription("Institutional Link Desc");
        header.setInvestorLinkDescription("Investor Link Desc");
        header.setContactLinkDescription("Contact Link Desc");
        header.setRegisterLinkDescription("Register Link Desc");
        header.setMemberAriaLabel("Member Aria Label");
        header.setFinancialAriaLabel("Financial Aria Label");
        header.setInstitutionalAriaLabel("Insitutional Aria Label");
        header.setContactAriaLabel("Contact Aria Label");
        header.setContactLinkUrlII("Contact Link URL II");
        header.setContactLinkUrlFinancial("Contact Link URL for Financial");
        header.setInvestorAriaLabel("Investor Aria Label");
        header.setRegisterAriaLabel("Register Aria Label");
        context.load().json("/rootPage.json", "/content/vcm/language-masters/en");
    }

    @Test
    void testLevelNavigation() throws Exception {
        header.init();
        assertNotNull(header.getBeanList());
        assertNotNull(headerBean.getChildCount());
    }

    @Test
    void testContactImage() throws Exception {
        assertNotNull(header.getContactImage());
        assertTrue(StringUtils.equalsAnyIgnoreCase("/content/dam/vcm/basic/call-logo.svg", header.getContactImage()));
        assertNotNull(header.getLogoImage());
        assertTrue(StringUtils.equalsAnyIgnoreCase("/content/dam/vcm/basic/VCM-logo.svg", header.getLogoImage()));
        assertNotNull(header.getLogoLinkUrl());
        assertTrue(StringUtils.equalsAnyIgnoreCase("/content/vcm/testheader/investment-franchies", header.getLogoLinkUrl()));
        assertNotNull(header.getFinancialLinkText());
        assertTrue(StringUtils.equalsAnyIgnoreCase("Financial Professionals", header.getFinancialLinkText()));
        assertNotNull(header.getFinancialLinkUrl());
        assertTrue(StringUtils.equalsAnyIgnoreCase("/content/vcm/testheader/investment-franchies", header.getFinancialLinkUrl()));
        assertNotNull(header.getInstitutionalLinkText());
        assertTrue(StringUtils.equalsAnyIgnoreCase("Investor relations", header.getInstitutionalLinkText()));
        assertNotNull(header.getInstitutionalLinkUrl());
        assertTrue(StringUtils.equalsAnyIgnoreCase("/content/vcm/testheader/investment-franchies", header.getInstitutionalLinkUrl()));
        assertNotNull(header.getInvestorLinkUrl());
        assertTrue(StringUtils.equalsAnyIgnoreCase("/content/vcm/testheader", header.getInvestorLinkUrl()));
        assertNotNull(header.getInvestorLinkText());
        assertTrue(StringUtils.equalsAnyIgnoreCase("Investor Type", header.getInvestorLinkText()));
        assertNotNull(header.getMembersLinkText());
        assertTrue(StringUtils.equalsAnyIgnoreCase("USSA Member", header.getMembersLinkText()));
        assertNotNull(header.getRegisterLinkText());
        assertTrue(StringUtils.equalsAnyIgnoreCase("Signin/Register", header.getRegisterLinkText()));
        assertNotNull(header.getRegisterLinkUrl());
        assertTrue(StringUtils.equalsAnyIgnoreCase("/content/vcm/testheader/investment-franchies", header.getRegisterLinkUrl()));
        assertNotNull(header.getMobileInvestorText());
        assertTrue(StringUtils.equalsAnyIgnoreCase("What type of Investor are you?", header.getMobileInvestorText()));
        assertNotNull(header.getContactLinkUrl());
        assertTrue(StringUtils.equalsAnyIgnoreCase("/contact/vcm/contactus", header.getContactLinkUrl()));
        assertNotNull(header.getContactLinkText());
        assertTrue(StringUtils.equalsAnyIgnoreCase("Contact Us", header.getContactLinkText()));
        assertNotNull(header.getLogoAltText());
        assertTrue(StringUtils.equalsAnyIgnoreCase("Logo", header.getLogoAltText()));
        assertNotNull(header.getInvestorLinkTarget());
        assertTrue(StringUtils.equalsAnyIgnoreCase("Same Tab", header.getInvestorLinkTarget()));
        assertNotNull(header.getContactLinkTarget());
        assertTrue(StringUtils.equalsAnyIgnoreCase("Same Tab", header.getContactLinkTarget()));
        assertNotNull(header.getSigninLinkTarget());
        assertTrue(StringUtils.equalsAnyIgnoreCase("Same Tab", header.getSigninLinkTarget()));
        assertNotNull(header.getSearchPlaceHolder());
        assertTrue(StringUtils.equalsAnyIgnoreCase("How can we help you?", header.getSearchPlaceHolder()));
        assertNotNull(header.getBackText());
        assertTrue(StringUtils.equalsAnyIgnoreCase("Back", header.getBackText()));
        assertNotNull(header.getMobileSigninLinkText());
        assertTrue(StringUtils.equalsAnyIgnoreCase("Sign In", header.getMobileSigninLinkText()));
        assertNotNull(header.getMemberLinkDescription());
        assertNotNull(header.getFinancialLinkDescription());
        assertNotNull(header.getInstitutionalLinkDescription());
        assertNotNull(header.getInvestorLinkDescription());
        assertNotNull(header.getContactLinkDescription());
        assertNotNull(header.getRegisterLinkDescription());
        assertNotNull(header.getMemberAriaLabel());
        assertNotNull(header.getFinancialAriaLabel());
        assertNotNull(header.getInvestorAriaLabel());
        assertNotNull(header.getInstitutionalAriaLabel());
        assertNotNull(header.getContactAriaLabel());
        assertNotNull(header.getRegisterAriaLabel());
        assertNotNull(header.getContactLinkUrlFinancial());
        assertNotNull(header.getContactLinkUrlII());
        assertNotNull(header.getMembersLinkUrl());
        assertNotNull(header.getNavRootPath());
    }

    @Test
    void testHeaderBean() throws Exception {
        assertNotNull(headerBean.getNavText());
        assertNotNull(headerBean.getLinkText());
        assertNotNull(headerBean.getLinkUrl());
        assertNotNull(headerBean.getImagePath());
        assertNotNull(headerBean.getEntityType());
        assertNotNull(headerBean.getImageAlt());
    }
    
    @Test
    void testInitWithElse() throws Exception {
        header.setMemberLinkDescription(null);
        header.setMembersLinkText("USSA Member");
        header.setMembersLinkUrl("/content/vcm/en");
        header.setFinancialLinkDescription(null);
        header.setFinancialLinkText("Financial Professionals");
        header.setFinancialLinkUrl("/content/vcm/testheader/investment-franchies");
        header.setInstitutionalLinkDescription(null);
        header.setInstitutionalLinkText("Investor relations");
        header.setInstitutionalLinkUrl("/content/vcm/testheader/investment-franchies");
        header.setInvestorLinkDescription(null);
        header.setContactLinkDescription(null);
        header.setContactLinkText("Contact Us");
        header.setContactLinkUrl("/contact/vcm/contactus");
        header.setRegisterLinkDescription(null);
        header.init();
    }

}