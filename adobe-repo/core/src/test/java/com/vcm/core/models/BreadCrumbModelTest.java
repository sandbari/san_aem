package com.vcm.core.models;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import io.wcm.testing.mock.aem.junit5.AemContext;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ValueMap;
import org.apache.sling.testing.mock.sling.ResourceResolverType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import com.day.cq.wcm.api.Page;
import com.day.cq.wcm.api.designer.Style;
import com.vcm.core.pojo.BreadCrumbNavItem;
import com.vcm.core.service.AudienceSelectorService;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class BreadCrumbModelTest {
    @InjectMocks
    private BreadCrumbModel breadCrumbModel;
    @Mock
    private ResourceResolver mockResourceResolver;
    @Mock
    private Resource mockResource;
    @Mock
    SlingHttpServletRequest mockSlingRequest;
    @Mock
    private ValueMap mockValueMap;
    @Mock
    private Style mockCurrentStyle;
    @Mock
    private ValueMap properties;
    @Mock
    private Page currentPage;
    @Mock
    private Page mockPage;
    private int startLevel;
    private String PN_START_LEVEL = "startLevel";
    private String PN_SHOW_HIDDEN = "showHidden";
    private String PN_HIDE_CURRENT = "hideCurrent";
    private String POSTLOGIN_MEMBERURL = "postloginMemberUrl";
    private String POSTLOGIN_GUESTURL = "postloginGuestUrl";
    private static final boolean PROP_SHOW_HIDDEN_DEFAULT = false;
    private static final boolean PROP_HIDE_CURRENT_DEFAULT = false;
    private static final int PROP_START_LEVEL_DEFAULT = 2;
    private int style;
    private String title = "title";
    private String vanityURL = "/content/title";
    private int currentLevel = 2;
    private String postlogin = "https://www.vcm.com";
    List<BreadCrumbNavItem> navItems;
    public final AemContext context = new AemContext(ResourceResolverType.JCR_OAK);
    @Mock
    AudienceSelectorService audienceSelectorService;

    @BeforeEach
    public void initMocks() {
	MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testInitTitle() {
	List<String> allowedaudience = new ArrayList<String>();
	allowedaudience.add("ALL");
	when(audienceSelectorService.getAllowedAudienceForComponent(Mockito.any())).thenReturn(allowedaudience);
	when(mockCurrentStyle.get(PN_START_LEVEL, PROP_START_LEVEL_DEFAULT)).thenReturn(style);
	when(properties.get(PN_START_LEVEL, style)).thenReturn(startLevel);
	when(mockCurrentStyle.get(PN_SHOW_HIDDEN, PROP_SHOW_HIDDEN_DEFAULT)).thenReturn(false);
	when(properties.get(PN_SHOW_HIDDEN, false)).thenReturn(false);
	when(mockCurrentStyle.get(PN_HIDE_CURRENT, PROP_HIDE_CURRENT_DEFAULT)).thenReturn(false);
	when(properties.get(PN_HIDE_CURRENT, false)).thenReturn(false);
	breadCrumbModel.init();
	String title = "page title";
	when(currentPage.getNavigationTitle()).thenReturn(title);
	breadCrumbModel.getTitle(currentPage);
	assertNotNull(breadCrumbModel.getTitle(currentPage));
    }

    @Test
    public void testTitleIsNull() {
	String currentPageTitle = "current page title";
	when(currentPage.getNavigationTitle()).thenReturn(null);
	when(currentPage.getPageTitle()).thenReturn(currentPageTitle);
	breadCrumbModel.getTitle(currentPage);
	assertNotNull(breadCrumbModel.getTitle(currentPage));
    }

    @Test
    public void testGetTitle() {
	String currentTitle = "current title";
	when(currentPage.getNavigationTitle()).thenReturn(null);
	when(currentPage.getTitle()).thenReturn(currentTitle);
	breadCrumbModel.getTitle(currentPage);
	assertNotNull(breadCrumbModel.getTitle(currentPage));
    }

    @Test
    public void testName() {
	when(currentPage.getNavigationTitle()).thenReturn(null);
	when(currentPage.getTitle()).thenReturn(null);
	when(currentPage.getName()).thenReturn("Page");
	breadCrumbModel.getTitle(currentPage);
	assertNotNull(breadCrumbModel.getTitle(currentPage));
    }

    @Test
    public void testGetNavItemsIsNotNull() {
	breadCrumbModel.getNavItems();
	assertNotNull(breadCrumbModel.getNavItems());
    }

    @Test
    public void testGetNavItemsIsNull() {
    when(mockPage.getProperties()).thenReturn(mockValueMap);
    when(mockValueMap.get(POSTLOGIN_MEMBERURL)).thenReturn(vanityURL);
    when(mockValueMap.get(POSTLOGIN_GUESTURL)).thenReturn(vanityURL);
	when(currentPage.getDepth()).thenReturn(currentLevel);
	when(currentPage.getAbsoluteParent(startLevel)).thenReturn(mockPage);
	when(mockPage.getVanityUrl()).thenReturn(vanityURL);
	when(mockPage.getNavigationTitle()).thenReturn(title);
	breadCrumbModel.getNavItems();
	assertNotNull(breadCrumbModel.getNavItems());

    }
}