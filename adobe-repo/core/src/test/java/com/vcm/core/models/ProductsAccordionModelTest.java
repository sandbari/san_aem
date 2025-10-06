package com.vcm.core.models;

import java.util.ArrayList;
import java.util.List;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.testing.mock.sling.ResourceResolverType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.day.cq.wcm.api.Page;
import com.vcm.core.pojo.ProductAccordionBean;
import com.vcm.core.service.AudienceSelectorService;

import io.wcm.testing.mock.aem.junit5.AemContext;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;
import junit.framework.Assert;

@ExtendWith(AemContextExtension.class)
@ExtendWith(MockitoExtension.class)
class ProductsAccordionModelTest {

	private ProductsAccordionModel productsAccordionModel;
	private ProductAccordionBean productsAccordionBean;
	private GenericContainerModel genericContainerModel;
	private Resource productResource;
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
		resourceResolver = context.resourceResolver();
		productsAccordionModel = new ProductsAccordionModel();
		productsAccordionBean = new ProductAccordionBean();
		genericContainerModel = new GenericContainerModel();
		productsAccordionModel.setAudienceSelectorService(audienceSelectorService);
		productsAccordionModel.setSlingRequest(context.request());
		context.load().json("/productAccordionPage.json", "/content/vcm/language-masters/en");
		context.load().json("/hrefUtilLinkChecker.json", "/content/vcm/us/en/about-victory");
		productResource = resourceResolver.getResource("/content/vcm/language-masters/en");
		productsAccordionModel.setResourceResolver(resourceResolver);
		productsAccordionModel.setProductPages(productResource);
		productsAccordionModel.setHeading("heading");
		productsAccordionModel.setUserAuthorized(true);
		productsAccordionModel.setSlingRequest(context.request());
		productsAccordionModel.getId();
		productsAccordionBean
				.setPage(resourceResolver.getResource("/content/vcm/us/en/about-victory").adaptTo(Page.class));
		productsAccordionBean.setTarget("_self");
		productsAccordionBean.setIsExternalUrl(true);
		productsAccordionBean.setLinkTitle("linkTitle");
		productsAccordionBean.setProductPageUrl("productPageUrl");
	}

	@Test
	void testProductAccorionModel() throws Exception {
		productsAccordionModel.init();
		genericContainerModel.setContainerType("productAccordion");
		List<ProductAccordionBean> pageList = productsAccordionModel.getProductPagesList();
		Assert.assertNotNull(productsAccordionBean.getPage());
		Assert.assertEquals("_self", productsAccordionBean.getTarget());
		Assert.assertEquals("productAccordion", genericContainerModel.getContainerType());
		Assert.assertEquals("heading", productsAccordionModel.getHeading());
		Assert.assertNotNull(productsAccordionModel.getProductPages());
		Assert.assertEquals("linkTitle", productsAccordionBean.getLinkTitle());
		Assert.assertEquals("productPageUrl", productsAccordionBean.getProductPageUrl());
		Assert.assertEquals(true, productsAccordionBean.getIsExternalUrl());
		Assert.assertEquals(true, productsAccordionModel.getIsUserAuthorized());
		Assert.assertNotNull(productsAccordionModel.getAllowedAudience());
		Assert.assertNotNull(pageList);
		
		genericContainerModel.setAudienceSelectorService(audienceSelectorService);
		genericContainerModel.getId();
		genericContainerModel.setResource(productResource);
		List<String> allowedAudience = new ArrayList<String>();
		allowedAudience.add("ALL");
		Mockito.when(audienceSelectorService.getAllowedAudienceListFromResource(productResource)).thenReturn(allowedAudience);
		genericContainerModel.init();
		genericContainerModel.getAllowedAudience();
	}

}