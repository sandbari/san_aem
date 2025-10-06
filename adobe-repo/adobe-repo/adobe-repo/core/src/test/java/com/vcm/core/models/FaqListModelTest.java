package com.vcm.core.models;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import com.vcm.core.models.mock.contentfragment.ContentFragmentMockAdapter;
import com.vcm.core.models.mock.contentfragment.CoreComponentTestContext;

import io.wcm.testing.mock.aem.junit5.AemContext;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;

@ExtendWith(AemContextExtension.class)
public class FaqListModelTest {

	private FaqListModel faq;
	private ResourceResolver resourceResolver;
	protected final AemContext context = CoreComponentTestContext.aemContext();
	private FaqListContentModel faqContent;
	private List<FaqListContentModel> faqList;
	private final String PATH_FAQ1 = "/content/dam/vcm/faqlist/faq1";
	private final String PATH_FAQ2 = "/content/dam/vcm/faqlist/faq2";
	private final String PATH_FAQ3 = "/content/dam/vcm/faqlist/faq3";
	private final String PATH_FAQ4 = "/content/dam/vcm/faqlist/faq4";
	private final String PATH_FAQ5 = "/content/dam/vcm/faqlist/faq5";

	@BeforeEach
	public void setup() throws Exception {
		resourceResolver = context.resourceResolver();
		faqContent = new FaqListContentModel();
		faq = new FaqListModel();
		
		String[] pages = new String[] {PATH_FAQ1, PATH_FAQ2, PATH_FAQ3, PATH_FAQ4, PATH_FAQ5};
		faq.setPages(pages);
		faq.setHeadText("Frequently asked questions");
		faq.setSeeAllUrl("#");
		faq.setTabSelect("_self");
		faq.setSeeAllText("view all");
		faq.setLinkAriaLabel("Link Aria Lable");
		faq.setLinkDescription("Link Description");

		faq.setResourceResolver(resourceResolver);
		context.load().json("/faqModel.json", "/conf/vcm/settings/dam/cfm/models/faq");
		context.load().json("/faq1cf.json", PATH_FAQ1);
		context.load().json("/faq2cf.json", PATH_FAQ2);
		context.load().json("/faq3cf.json", PATH_FAQ3);
		context.load().json("/faq4cf.json", PATH_FAQ4);
		context.load().json("/faq5cf.json", PATH_FAQ5);
		context.registerAdapter(Resource.class, com.adobe.cq.dam.cfm.ContentFragment.class, ADAPTER);

	}

	@Test
	public void testFaqListModel_Init() {
		faq.init();

		assertNotNull(faq.getHeadText());
		assertNotNull(faq.getSeeAllUrl());
		assertNotNull(faq.getTabSelect());
		assertNotNull(faq.getSeeAllText());
		assertNotNull(faq.getPages());
		assertNotNull(faq.getLinkAriaLabel());
		assertNotNull(faq.getLinkDescription());
		assertNotNull(faq.getFAQs());
	}
    
    @Test
    public void testInitWithElse() {
    	faq.setResourceResolver(context.resourceResolver());
    	faq.setLinkDescription(null); 
    	faq.setSeeAllUrl("Link Text");
    	faq.setSeeAllText("Link Url");
    	faq.init();
    }

	private static final java.util.function.Function<Resource, com.adobe.cq.dam.cfm.ContentFragment> CONTENT_FRAGMENT_ADAPTER = new ContentFragmentMockAdapter();

	public static final com.google.common.base.Function<Resource, com.adobe.cq.dam.cfm.ContentFragment> ADAPTER = new com.google.common.base.Function<Resource, com.adobe.cq.dam.cfm.ContentFragment>() {
		@Nullable
		@Override
		public com.adobe.cq.dam.cfm.ContentFragment apply(@Nullable Resource resource) {
			return CONTENT_FRAGMENT_ADAPTER.apply(resource);
		}
	};
}
