package com.vcm.core.models;

import com.day.cq.wcm.api.Page;
import com.day.cq.wcm.api.PageManager;
import com.vcm.core.pojo.HeaderBean;
import com.vcm.core.utils.UtilityService;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ValueMap;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.Via;
import org.apache.sling.models.annotations.injectorspecific.SlingObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

@Model(adaptables = {SlingHttpServletRequest.class}, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class Header {

    private static final Logger LOGGER = LoggerFactory.getLogger(Header.class);

    @SlingObject
    private ResourceResolver resourceResolver;

    @Inject
    @Via("resource")
    private String logoImage;

    @Inject
    @Via("resource")
    private String logoAltText;

    @Inject
    @Via("resource")
    private String logoLinkUrl;

    @Inject
    @Via("resource")
    private String logoImageInvestor;

    @Inject
    @Via("resource")
    private String logoAltTextInvestor;

    @Inject
    @Via("resource")
    private String logoLinkUrlInvestor;
    @Inject
    @Via("resource")
    private String membersLinkText;

    @Inject
    @Via("resource")
    private String memberLinkDescription;

    @Inject
    @Via("resource")
    private String membersLinkUrl;

    @Inject
    @Via("resource")
    private String financialLinkText;

    @Inject
    @Via("resource")
    private String financialLinkDescription;

    @Inject
    @Via("resource")
    private String financialLinkUrl;

    @Inject
    @Via("resource")
    private String institutionalLinkText;

    @Inject
    @Via("resource")
    private String institutionalLinkDescription;

    @Inject
    @Via("resource")
    private String institutionalLinkUrl;

    @Inject
    @Via("resource")
    private String investorLinkText;

    @Inject
    @Via("resource")
    private String investorLinkDescription;

    @Inject
    @Via("resource")
    private String investorLinkUrl;

    @Inject
    @Via("resource")
    private String corporateHomeLinkText;

    @Inject
    @Via("resource")
    private String corporateHomeLinkDescription;

    @Inject
    @Via("resource")
    private String corporateHomeLinkUrl;

    @Inject
    @Via("resource")
    private String contactImage;

    @Inject
    @Via("resource")
    private String contactLinkText;

    @Inject
    @Via("resource")
    private String contactLinkDescription;

    @Inject
    @Via("resource")
    private String contactLinkUrl;

    @Inject
    @Via("resource")
    private String contactLinkUrlFinancial;

    @Inject
    @Via("resource")
    private String contactLinkUrlII;

    @Inject
    @Via("resource")
    private String registerLinkText;


    @Inject
    @Via("resource")
    private String registerLinkDescription;

    @Inject
    @Via("resource")
    private String registerLinkUrl;

    @Inject
    @Via("resource")
    private String searchPlaceHolder;

    @Inject
    @Via("resource")
    private String backText;

    @Inject
    @Via("resource")
    private String mobileSigninLinkText;

    @Inject
    @Via("resource")
    private String mobileInvestorText;

    @Inject
    @Via("resource")
    private String navRootPath;

    @Inject
    @Via("resource")
    private String investorLinkTarget;

    @Inject
    @Via("resource")
    private String corporateHomeLinkTarget;

    @Inject
    @Via("resource")
    private String isCorpHomepage;

    @Inject
    @Via("resource")
    private String contactLinkTarget;

    @Inject
    @Via("resource")
    private String signinLinkTarget;

    private List<HeaderBean> beanList = new ArrayList<HeaderBean>();

    private String FALSE = "false";

    String hideInNav = FALSE;


    String memberAriaLabel;
    String financialAriaLabel;
    String investorAriaLabel;
    String institutionalAriaLabel;
    String corporateHomeAriaLabel;
    String contactAriaLabel;
    String registerAriaLabel;


    @PostConstruct
    protected void init() {
        if (navRootPath != null) {
            PageManager pageManager = resourceResolver.adaptTo(PageManager.class);
            Page rootPage = pageManager.getPage(navRootPath);
            LOGGER.debug("Root Page Path : {} ", rootPage.getPath());
            vcmLevelNavigation(rootPage, 3, beanList);
        }

        if (Objects.nonNull(memberLinkDescription)) {
            memberAriaLabel = memberLinkDescription;
        } else if (Objects.nonNull(membersLinkUrl) && Objects.nonNull(membersLinkText)) {
            memberAriaLabel = UtilityService.getLinkDescription(membersLinkUrl, membersLinkText, resourceResolver);
        }

        if (Objects.nonNull(financialLinkDescription)) {
            financialAriaLabel = financialLinkDescription;
        } else if (Objects.nonNull(financialLinkUrl) && Objects.nonNull(financialLinkText)) {
            financialAriaLabel = UtilityService.getLinkDescription(financialLinkUrl, financialLinkText, resourceResolver);
        }

        if (Objects.nonNull(institutionalLinkDescription)) {
            institutionalAriaLabel = institutionalLinkDescription;
        } else if (Objects.nonNull(institutionalLinkUrl) && Objects.nonNull(institutionalLinkText)) {
            institutionalAriaLabel = UtilityService.getLinkDescription(institutionalLinkUrl, institutionalLinkText, resourceResolver);
        }

        if (Objects.nonNull(investorLinkDescription)) {
            investorAriaLabel = investorLinkDescription;
        } else if (Objects.nonNull(financialLinkUrl) && Objects.nonNull(investorLinkText)) {
            investorAriaLabel = UtilityService.getLinkDescription(investorLinkUrl, investorLinkText, resourceResolver);
        }

        if (Objects.nonNull(contactLinkDescription)) {
            contactAriaLabel = contactLinkDescription;
        } else if (Objects.nonNull(contactLinkUrl) && Objects.nonNull(contactLinkText)) {
            contactAriaLabel = UtilityService.getLinkDescription(contactLinkUrl, contactLinkText, resourceResolver);
        }

        if (Objects.nonNull(registerLinkDescription)) {
            registerAriaLabel = registerLinkDescription;
        } else if (Objects.nonNull(contactLinkUrl) && Objects.nonNull(registerLinkText)) {
            registerAriaLabel = UtilityService.getLinkDescription(registerLinkUrl, registerLinkText, resourceResolver);
        }

        if (Objects.nonNull(corporateHomeLinkDescription)) {
            corporateHomeAriaLabel = corporateHomeLinkDescription;
        } else if (Objects.nonNull(corporateHomeLinkUrl) && Objects.nonNull(corporateHomeLinkText)) {
            corporateHomeAriaLabel = UtilityService.getLinkDescription(corporateHomeLinkUrl, corporateHomeLinkText, resourceResolver);
        }
    }

    public void vcmLevelNavigation(Page rootPage, int level, List<HeaderBean> beanList) {

        try {
            if (level > 1) {
                rootPage.listChildren().forEachRemaining(currentPage -> {
                    LOGGER.debug("currentPage level > 1 ---> {}", currentPage.getPath());
                    checkHideInNav(currentPage);
                    boolean navImage = false;
                    if (hideInNav.equalsIgnoreCase(FALSE)) {
                        if (level == 2 && null != currentPage.getParent().getProperties().get("useImageInHeaderNav", String.class) && currentPage.getParent().getProperties().get("useImageInHeaderNav", String.class).equalsIgnoreCase("true")) {
                            LOGGER.debug("inside second level image is set to true");
                            navImage = true;
                        }
                        HeaderBean tempsite = generateBeanAddPages(currentPage, beanList, Boolean.FALSE, navImage, level);
                        vcmLevelNavigation(currentPage, level - 1, tempsite.getListBean());
                    }
                });
            } else {
                rootPage.listChildren().forEachRemaining(currentPage -> {
                    boolean navImage = false;
                    LOGGER.debug("currentPage level < 1 ---> {}", currentPage.getPath());
                    checkHideInNav(currentPage);
                    if (hideInNav.equalsIgnoreCase(FALSE)) {
                        if (null != currentPage.getParent().getProperties().get("useImageInHeaderNav", String.class) && currentPage.getParent().getProperties().get("useImageInHeaderNav", String.class).equalsIgnoreCase("true")) {
                            navImage = true;
                        }
                        generateBeanAddPages(currentPage, beanList, Boolean.TRUE, navImage, level);
                    }
                });
            }
        } catch (UnsupportedOperationException | ClassCastException | IllegalArgumentException ex) {
            LOGGER.error("Exception Occured while fetching the child pages :: {}", ex);
        }
    }

    public int getChildNumbers(Page rootPage) {
        int count = 0;
        Iterator<Page> secondLevelPagesItr = rootPage.listChildren();
        while (secondLevelPagesItr.hasNext()) {
            Page secondLevelPage = secondLevelPagesItr.next();
            checkHideInNav(secondLevelPage);
            if (hideInNav.equalsIgnoreCase(FALSE)) {
                count = count + 1;
                Iterator<Page> thirdLevelPagesItr = secondLevelPage.listChildren();
                while (thirdLevelPagesItr.hasNext()) {
                    checkHideInNav(thirdLevelPagesItr.next());
                    if (hideInNav.equalsIgnoreCase(FALSE)) {
                        count = count + 1;
                    }
                }
            }
        }
        LOGGER.debug("count of the child ppages : {}", count);
        if (count > 2) {
            count = (count + 1) / 2;
        }
        return count;
    }

    public HeaderBean generateBeanAddPages(Page levelPage, List<HeaderBean> beanList, Boolean leaf, Boolean navImage, int level)
            throws UnsupportedOperationException, ClassCastException, IllegalArgumentException {
        HeaderBean tempsite = new HeaderBean();
        beanList.add(tempsite);
        List<String> dataValue = new ArrayList<String>();
        if (null != levelPage.getProperties().get("membersType", String.class) && levelPage.getProperties().get("membersType", String.class).equalsIgnoreCase("true")) {
            dataValue.add("usaa_member");
        }
        if (null != levelPage.getProperties().get("financialAdvisorsType", String.class) && levelPage.getProperties().get("financialAdvisorsType", String.class).equalsIgnoreCase("true")) {
            dataValue.add("financial_professional");
        }
        if (null != levelPage.getProperties().get("institutionalinvestorsType", String.class) && levelPage.getProperties().get("institutionalinvestorsType", String.class).equalsIgnoreCase("true")) {
            dataValue.add("institutional_investor");
        }
        if (dataValue.isEmpty()) {
            dataValue.add("ALL");
        }
        LOGGER.debug("Nav Image Flag ---> {}", navImage);
        String entityValue = String.join(",", dataValue);
        Resource imgRes = levelPage.getContentResource("image");
        if (navImage == true && null != imgRes) {
            LOGGER.debug("imgRes entry logic --> {}", imgRes);
            ValueMap properties = imgRes.adaptTo(ValueMap.class);
            String navImagePath = properties.get("fileReference", String.class);
            LOGGER.debug("navImagePath ---> {}", navImagePath);
            if (Objects.nonNull(navImagePath)) {
                String[] words = navImagePath.split("/");
                String imageName = words[words.length - 1];
                String[] image = imageName.split("\\.");
                LOGGER.debug("imageName ---> {}", imageName);
                String imageAlt = image[0];
                tempsite.setImageAlt(imageAlt);
                tempsite.setImagePath(navImagePath);
                tempsite.setLinkText(levelPage.getTitle());
            }

        } else if (null != levelPage.getProperties().get("navTitle", String.class)) {
            tempsite.setLinkText(levelPage.getProperties().get("navTitle", String.class));
        } else {
            tempsite.setLinkText(levelPage.getTitle());
        }
        if (null != levelPage.getProperties().get("subtitle", String.class)) {
            tempsite.setNavText(levelPage.getProperties().get("subtitle", String.class));
        } else {
            tempsite.setNavText(levelPage.getTitle());
        }
        if (level == 3) {
            int childCount = getChildNumbers(levelPage);
            tempsite.setChildCount(childCount);
        }
        if (null != levelPage.getProperties().get("secondLevelNavOnly", String.class) && levelPage.getProperties().get("secondLevelNavOnly", String.class).equalsIgnoreCase("true")) {
            tempsite.setSecondLevelOnly(true);
        }
        tempsite.setLinkUrl(UtilityService.identifyLinkUrl(levelPage.getPath(), resourceResolver));
        tempsite.setEntityType(entityValue);
        if (leaf == Boolean.FALSE) {
            List<HeaderBean> temp = new ArrayList<>();
            tempsite.setListBean(temp);
        }
        return tempsite;
    }

    public void checkHideInNav(Page levelPage) {
        hideInNav = FALSE;
        if (null != levelPage.getProperties().get("hideInNav", String.class) && levelPage.getProperties().get("hideInNav", String.class).equalsIgnoreCase("true")) {
            hideInNav = "true";
        }
    }

    public String getLogoImage() {
        return logoImage;
    }

    public void setLogoImage(String logoImage) {
        this.logoImage = logoImage;
    }

    public String getLogoAltText() {
        return logoAltText;
    }

    public void setLogoAltText(String logoAltText) {
        this.logoAltText = logoAltText;
    }

    public String getLogoLinkUrl() {
        return UtilityService.identifyLinkUrl(logoLinkUrl, resourceResolver);
    }

    public void setLogoLinkUrl(String logoLinkUrl) {
        this.logoLinkUrl = logoLinkUrl;
    }

    public String getMembersLinkText() {
        return membersLinkText;
    }

    public void setMembersLinkText(String membersLinkText) {
        this.membersLinkText = membersLinkText;
    }

    public String getMembersLinkUrl() {
        return UtilityService.identifyLinkUrl(membersLinkUrl, resourceResolver);
    }

    public void setMembersLinkUrl(String membersLinkUrl) {
        this.membersLinkUrl = membersLinkUrl;
    }

    public String getFinancialLinkText() {
        return financialLinkText;
    }

    public void setFinancialLinkText(String financialLinkText) {
        this.financialLinkText = financialLinkText;
    }

    public String getFinancialLinkUrl() {
        return UtilityService.identifyLinkUrl(financialLinkUrl, resourceResolver);
    }

    public void setFinancialLinkUrl(String financialLinkUrl) {
        this.financialLinkUrl = financialLinkUrl;
    }

    public String getInstitutionalLinkText() {
        return institutionalLinkText;
    }

    public void setInstitutionalLinkText(String institutionalLinkText) {
        this.institutionalLinkText = institutionalLinkText;
    }

    public String getInstitutionalLinkUrl() {
        return UtilityService.identifyLinkUrl(institutionalLinkUrl, resourceResolver);
    }

    public void setInstitutionalLinkUrl(String institutionalLinkUrl) {
        this.institutionalLinkUrl = institutionalLinkUrl;
    }

    public String getInvestorLinkText() {
        return investorLinkText;
    }

    public void setInvestorLinkText(String investorLinkText) {
        this.investorLinkText = investorLinkText;
    }

    public String getInvestorLinkUrl() {
        return UtilityService.identifyLinkUrl(investorLinkUrl, resourceResolver);
    }

    public void setInvestorLinkUrl(String investorLinkUrl) {
        this.investorLinkUrl = investorLinkUrl;
    }

    public String getContactImage() {
        return contactImage;
    }

    public void setContactImage(String contactImage) {
        this.contactImage = contactImage;
    }

    public String getContactLinkText() {
        return contactLinkText;
    }

    public void setContactLinkText(String contactLinkText) {
        this.contactLinkText = contactLinkText;
    }

    public String getContactLinkUrl() {
        return UtilityService.identifyLinkUrl(contactLinkUrl, resourceResolver);
    }

    public void setContactLinkUrlFinancial(String contactLinkUrlFinancial) {
        this.contactLinkUrlFinancial = contactLinkUrlFinancial;
    }

    public String getContactLinkUrlFinancial() {
        return UtilityService.identifyLinkUrl(contactLinkUrlFinancial, resourceResolver);
    }

    public void setContactLinkUrlII(String contactLinkUrlII) {
        this.contactLinkUrlII = contactLinkUrlII;
    }

    public String getContactLinkUrlII() {
        return UtilityService.identifyLinkUrl(contactLinkUrlII, resourceResolver);
    }


    public void setContactLinkUrl(String contactLinkUrl) {
        this.contactLinkUrl = contactLinkUrl;
    }

    public String getRegisterLinkText() {
        return registerLinkText;
    }

    public void setRegisterLinkText(String registerLinkText) {
        this.registerLinkText = registerLinkText;
    }

    public String getRegisterLinkUrl() {
        return UtilityService.identifyLinkUrl(registerLinkUrl, resourceResolver);
    }

    public void setRegisterLinkUrl(String registerLinkUrl) {
        this.registerLinkUrl = registerLinkUrl;
    }

    public List<HeaderBean> getBeanList() {
        return new ArrayList<HeaderBean>(beanList);
    }

    public String getSearchPlaceHolder() {
        return searchPlaceHolder;
    }

    public String getBackText() {
        return backText;
    }

    public String getMobileSigninLinkText() {
        return mobileSigninLinkText;
    }

    public void setSearchPlaceHolder(String searchPlaceHolder) {
        this.searchPlaceHolder = searchPlaceHolder;
    }

    public void setBackText(String backText) {
        this.backText = backText;
    }

    public void setMobileSigninLinkText(String mobileSigninLinkText) {
        this.mobileSigninLinkText = mobileSigninLinkText;
    }

    public String getNavRootPath() {
        return navRootPath;
    }

    public void setNavRootPath(String navRootPath) {
        this.navRootPath = navRootPath;
    }

    public void setResourceResolver(ResourceResolver resourceResolver) {
        this.resourceResolver = resourceResolver;
    }

    public String getMobileInvestorText() {
        return mobileInvestorText;
    }

    public void setMobileInvestorText(String mobileInvestorText) {
        this.mobileInvestorText = mobileInvestorText;
    }

    public String getInvestorLinkTarget() {
        return investorLinkTarget;
    }

    public void setInvestorLinkTarget(String investorLinkTarget) {
        this.investorLinkTarget = investorLinkTarget;
    }

    public String getContactLinkTarget() {
        return contactLinkTarget;
    }

    public void setContactLinkTarget(String contactLinkTarget) {
        this.contactLinkTarget = contactLinkTarget;
    }

    public String getSigninLinkTarget() {
        return signinLinkTarget;
    }

    public void setSigninLinkTarget(String signinLinkTarget) {
        this.signinLinkTarget = signinLinkTarget;
    }

    public String getMemberLinkDescription() {
        return memberLinkDescription;
    }

    public void setMemberLinkDescription(String memberLinkDescription) {
        this.memberLinkDescription = memberLinkDescription;
    }

    public String getFinancialLinkDescription() {
        return financialLinkDescription;
    }

    public void setFinancialLinkDescription(String financialLinkDescription) {
        this.financialLinkDescription = financialLinkDescription;
    }

    public String getInstitutionalLinkDescription() {
        return institutionalLinkDescription;
    }

    public void setInstitutionalLinkDescription(String institutionalLinkDescription) {
        this.institutionalLinkDescription = institutionalLinkDescription;
    }

    public String getInvestorLinkDescription() {
        return investorLinkDescription;
    }

    public void setInvestorLinkDescription(String investorLinkDescription) {
        this.investorLinkDescription = investorLinkDescription;
    }

    public String getContactLinkDescription() {
        return contactLinkDescription;
    }

    public void setContactLinkDescription(String contactLinkDescription) {
        this.contactLinkDescription = contactLinkDescription;
    }

    public String getRegisterLinkDescription() {
        return registerLinkDescription;
    }

    public void setRegisterLinkDescription(String registerLinkDescription) {
        this.registerLinkDescription = registerLinkDescription;
    }

    public String getMemberAriaLabel() {
        return memberAriaLabel;
    }

    public void setMemberAriaLabel(String memberAriaLabel) {
        this.memberAriaLabel = memberAriaLabel;
    }

    public String getFinancialAriaLabel() {
        return financialAriaLabel;
    }

    public void setFinancialAriaLabel(String financialAriaLabel) {
        this.financialAriaLabel = financialAriaLabel;
    }

    public String getInvestorAriaLabel() {
        return investorAriaLabel;
    }

    public void setInvestorAriaLabel(String investorAriaLabel) {
        this.investorAriaLabel = investorAriaLabel;
    }

    public String getInstitutionalAriaLabel() {
        return institutionalAriaLabel;
    }

    public void setInstitutionalAriaLabel(String institutionalAriaLabel) {
        this.institutionalAriaLabel = institutionalAriaLabel;
    }

    public String getContactAriaLabel() {
        return contactAriaLabel;
    }

    public void setContactAriaLabel(String contactAriaLabel) {
        this.contactAriaLabel = contactAriaLabel;
    }

    public String getRegisterAriaLabel() {
        return registerAriaLabel;
    }

    public void setRegisterAriaLabel(String registerAriaLabel) {
        this.registerAriaLabel = registerAriaLabel;
    }

    public String getCorporateHomeLinkText() {
        return corporateHomeLinkText;
    }

    public void setCorporateHomeLinkText(String corporateHomeLinkText) {
        this.corporateHomeLinkText = corporateHomeLinkText;
    }

    public String getCorporateHomeLinkDescription() {
        return corporateHomeLinkDescription;
    }

    public void setCorporateHomeLinkDescription(String corporateHomeLinkDescription) {
        this.corporateHomeLinkDescription = corporateHomeLinkDescription;
    }

    public String getCorporateHomeLinkUrl() {
        return corporateHomeLinkUrl;
    }

    public void setCorporateHomeLinkUrl(String corporateHomeLinkUrl) {
        this.corporateHomeLinkUrl = corporateHomeLinkUrl;
    }

    public String getCorporateHomeAriaLabel() {
        return corporateHomeAriaLabel;
    }

    public void setCorporateHomeAriaLabel(String corporateHomeAriaLabel) {
        this.corporateHomeAriaLabel = corporateHomeAriaLabel;
    }

    public String getCorporateHomeLinkTarget() {
        return corporateHomeLinkTarget;
    }

    public void setCorporateHomeLinkTarget(String corporateHomeLinkTarget) {
        this.corporateHomeLinkTarget = corporateHomeLinkTarget;
    }

    public String getIsCorpHomepage() {
        return isCorpHomepage;
    }

    public void setIsCorpHomepage(String isCorpHomepage) {
        this.isCorpHomepage = isCorpHomepage;
    }

    public String getLogoImageInvestor() {
        return logoImageInvestor;
    }

    public void setLogoImageInvestor(String logoImageInvestor) {
        this.logoImageInvestor = logoImageInvestor;
    }

    public String getLogoAltTextInvestor() {
        return logoAltTextInvestor;
    }

    public void setLogoAltTextInvestor(String logoAltTextInvestor) {
        this.logoAltTextInvestor = logoAltTextInvestor;
    }

    public String getLogoLinkUrlInvestor() {
        return logoLinkUrlInvestor;
    }

    public void setLogoLinkUrlInvestor(String logoLinkUrlInvestor) {
        this.logoLinkUrlInvestor = logoLinkUrlInvestor;
    }
}
