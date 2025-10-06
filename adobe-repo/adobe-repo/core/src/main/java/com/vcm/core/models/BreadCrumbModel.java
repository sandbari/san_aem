package com.vcm.core.models;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ValueMap;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.Source;
import org.apache.sling.models.annotations.injectorspecific.ChildResource;
import org.apache.sling.models.annotations.injectorspecific.ScriptVariable;
import org.apache.sling.models.annotations.injectorspecific.SlingObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.day.cq.wcm.api.Page;
import com.day.cq.wcm.api.designer.Style;
import com.vcm.core.pojo.BreadCrumbNavItem;
import com.vcm.core.service.AudienceSelectorService;
import com.vcm.core.utils.UtilityService;

/**
 * @author CTS. A Model class for ProductsAccordionModel
 */

@Model(adaptables = { SlingHttpServletRequest.class,
	Resource.class }, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class BreadCrumbModel {

    private static final String STATIC_TYPE_KEY = "static";

    private static final String NAV_ITEM = "navItem";

    private static final String IS_HIDEIN_BREAD_CRUMB = "isHideinBreadCrumb";

    private static final Logger LOGGER = LoggerFactory.getLogger(BreadCrumbModel.class);

    private static final boolean PROP_SHOW_HIDDEN_DEFAULT = false;

    private static final boolean PROP_HIDE_CURRENT_DEFAULT = false;

    private static final int PROP_START_LEVEL_DEFAULT = 2;

    @Inject
    @Source("sling-object")
    private ResourceResolver resourceResolver;

    @SlingObject
    SlingHttpServletRequest slingRequest;

    @ScriptVariable
    private Style currentStyle;

    @ScriptVariable
    private ValueMap properties;

    private List<BreadCrumbNavItem> navItems;

    @ScriptVariable
    private Page currentPage;

    private boolean showHidden;

    private boolean hideCurrent;

    private int startLevel;

    private String PN_START_LEVEL = "startLevel";

    private String PN_SHOW_HIDDEN = "showHidden";

    private String PN_HIDE_CURRENT = "hideCurrent";

    private String BREADCRUMB_TYPE_KEY = "breadCrumbType";

    private String BREADCRUMBTYPE_DEFAULT = "dynamic";

    private String BREADCRUMBTYPE = "dynamic";

    private String POSTLOGIN_MEMBERURL = "postloginMemberUrl";

	private String POSTLOGIN_GUESTURL = "postloginGuestUrl";

    @ChildResource
    private Resource staticItems;

    private List<String> allowedAudience = new ArrayList<String>();

    @Inject
    private AudienceSelectorService audienceSelectorService;

    /**
     * Post Construct method
     */
    @PostConstruct
    protected void init() {

	LOGGER.debug("Into BreadCrumbModel Model");

	startLevel = properties.get(PN_START_LEVEL, currentStyle.get(PN_START_LEVEL, PROP_START_LEVEL_DEFAULT));

	showHidden = properties.get(PN_SHOW_HIDDEN, currentStyle.get(PN_SHOW_HIDDEN, PROP_SHOW_HIDDEN_DEFAULT));

	hideCurrent = properties.get(PN_HIDE_CURRENT, currentStyle.get(PN_HIDE_CURRENT, PROP_HIDE_CURRENT_DEFAULT));

	BREADCRUMBTYPE = properties.get(BREADCRUMB_TYPE_KEY,
		currentStyle.get(BREADCRUMB_TYPE_KEY, BREADCRUMBTYPE_DEFAULT));

	allowedAudience = audienceSelectorService.getAllowedAudienceForComponent(slingRequest);

	LOGGER.debug("End BreadCrumbModel Model");
    }

    public Collection<BreadCrumbNavItem> getNavItems() {

	if (navItems == null) {

	    if (BREADCRUMBTYPE.equals(STATIC_TYPE_KEY)) {

		navItems = createStaticItems();

	    } else {

		navItems = createItems();
	    }
	}

	return Collections.unmodifiableList(navItems);
    }

    private List<BreadCrumbNavItem> createStaticItems() {

	List<BreadCrumbNavItem> items = new ArrayList<>();

	if (Objects.nonNull(staticItems)) {

	    Iterator<Resource> multiFieldItems = staticItems.listChildren();

	    while (multiFieldItems.hasNext()) {

		Resource multiFieldResource = multiFieldItems.next();

		if (Objects.nonNull(multiFieldResource)) {

		    ValueMap valueMap = multiFieldResource.getValueMap();

		    if (valueMap.containsKey(NAV_ITEM)) {

			String pagePath = valueMap.get(NAV_ITEM, String.class);

			Resource pageObject = resourceResolver.getResource(pagePath);

			if (Objects.nonNull(pageObject)) {

			    Page page = pageObject.adaptTo(Page.class);

			    if (Objects.nonNull(page)) {

				String link = this.getURL(slingRequest, page);

				String title = this.getTitle(page);

				String postloginMemberUrl = page.getProperties().get(POSTLOGIN_MEMBERURL,String.class);

				String postloginGuestUrl = page.getProperties().get(POSTLOGIN_GUESTURL,String.class);

				BreadCrumbNavItem navigationItem = new BreadCrumbNavItem(title, link);

				if (Objects.nonNull(postloginMemberUrl)) {

					navigationItem.setMemberUrl(postloginMemberUrl);
				}

				if (Objects.nonNull(postloginGuestUrl)) {

					navigationItem.setGuestUrl(postloginGuestUrl);
				}

				items.add(navigationItem);

			    }
			}
		    }
		}
	    }
	}

	
	String currentPageLink = this.getURL(slingRequest, currentPage);

	String currentPageTitle = this.getTitle(currentPage);

	BreadCrumbNavItem navigationItem = new BreadCrumbNavItem(currentPageTitle, currentPageLink);

	items.add(navigationItem);

	return items;

    }

    private List<BreadCrumbNavItem> createItems() {

	List<BreadCrumbNavItem> items = new ArrayList<>();

	int currentLevel = currentPage.getDepth();

	while (startLevel < currentLevel) {

	    Page page = currentPage.getAbsoluteParent(startLevel);

	    if (page != null) {

		boolean isActivePage = page.equals(currentPage);

		if (isActivePage && hideCurrent) {

		    break;
		}
		if (checkIfNotHidden(page)) {

		    String link = this.getURL(slingRequest, page);

		    String title = this.getTitle(page);

			String postloginMemberUrl = page.getProperties().get(POSTLOGIN_MEMBERURL,String.class);

			String postloginGuestUrl = page.getProperties().get(POSTLOGIN_GUESTURL,String.class);

		    BreadCrumbNavItem navigationItem = new BreadCrumbNavItem(title, link);

		    if (Objects.nonNull(postloginMemberUrl)) {

				navigationItem.setMemberUrl(postloginMemberUrl);
			}

			if (Objects.nonNull(postloginGuestUrl)) {

				navigationItem.setGuestUrl(postloginGuestUrl);
			}

		    items.add(navigationItem);
		}
	    }

	    startLevel++;
	}

	return items;
    }

    public String getTitle(Page page) {

	String title = page.getNavigationTitle();

	if (title == null) {

	    title = page.getPageTitle();
	}

	if (title == null) {

	    title = page.getTitle();
	}
	if (title == null) {

	    title = page.getName();
	}

	return title;
    }

    public String getURL(SlingHttpServletRequest request, Page page) {

	String vanityURL = page.getVanityUrl();

	return StringUtils.isEmpty(vanityURL) ? (UtilityService.identifyLinkUrl(page.getPath(), resourceResolver))
		: (request.getContextPath() + vanityURL);

    }

    private boolean checkIfNotHidden(Page page) {

	boolean hideInBreadCrumb = false;

	boolean isValidPage = true;

	Resource resource = page.getContentResource();

	if (Objects.nonNull(resource)) {

	    ValueMap resourceMap = resource.getValueMap();

	    if (resourceMap.containsKey(IS_HIDEIN_BREAD_CRUMB)) {

		hideInBreadCrumb = resourceMap.get(IS_HIDEIN_BREAD_CRUMB, Boolean.class);
	    }

	}

	isValidPage = (hideInBreadCrumb == true ? false : (!page.isHideInNav() || showHidden));

	return isValidPage;
    }

    public List<String> getAllowedAudience() {

	List<String> copyOfAllowedAudience = allowedAudience;

	Collections.copy(copyOfAllowedAudience, allowedAudience);

	return copyOfAllowedAudience;
    }

    /**
     * @param audienceSelectorService the audienceSelectorService to set
     */
    public void setAudienceSelectorService(AudienceSelectorService audienceSelectorService) {
        this.audienceSelectorService = audienceSelectorService;
    }

}
