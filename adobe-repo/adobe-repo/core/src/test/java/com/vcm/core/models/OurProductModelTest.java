package com.vcm.core.models;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.testing.mock.sling.ResourceResolverType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import io.wcm.testing.mock.aem.junit5.AemContext;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;
import junit.framework.Assert;

@ExtendWith(AemContextExtension.class)
public class OurProductModelTest {

	private OurProductsModel ourProductModel;
	ResourceResolver resourceResolver;
	public final AemContext context = new AemContext(ResourceResolverType.JCR_OAK);

	@BeforeEach
	public void setup() throws Exception {
		ourProductModel = new OurProductsModel();
		ourProductModel.setResourceResolver(context.resourceResolver());
		context.load().json("/ourProduct1.json", "/content/vcm/language-masters/titleProduct");
		context.load().json("/ourProduct2.json", "/content/vcm/language-masters/titleDescProduct");
		context.load().json("/ourproduct3.json", "/content/vcm/language-masters/en/products/details");
		context.load().json("/ourproduct4.json", "/content/vcm/language-masters/en/products/variant");

	}

	@Test
	void testInit() throws Exception {
		Resource resource = context.currentResource("/content/vcm/language-masters/titleProduct");
		ourProductModel.setResource(resource);
		context.load().json("/rootPage.json", "/content/vcm/en/investment-franchise");
		ourProductModel.init();
		assertEquals("Retirement", ourProductModel.getProductList().get(2).getProductTitle());
		assertEquals("/content/vcm/en/investment-franchise.html",
				ourProductModel.getProductList().get(1).getProductLink());
		
		ourProductModel.setTitle("Set Title");
		ourProductModel.setDesktopImage("Set Desktop Image"); //(0) ?
		ourProductModel.setContentType("Set content type");
		ourProductModel.setLinkVariant("Set Variant");
		ourProductModel.setTitleVariant("Title Variant");
		ourProductModel.setColumnVariant("Column Variant");
		ourProductModel.setListingPageLink("/content/vcm/en/investment-franchise");
		ourProductModel.setListingLinkDesc("Description");
		
		Assert.assertNotNull(ourProductModel.getTitle());
		Assert.assertNotNull(ourProductModel.getDesktopImage());
		Assert.assertNotNull(ourProductModel.getContentType());
		Assert.assertNotNull(ourProductModel.getLinkVariant());
		Assert.assertNotNull(ourProductModel.getTitleVariant());
		Assert.assertNotNull(ourProductModel.getColumnVariant());
		Assert.assertNotNull(ourProductModel.getListingPageLink());
		Assert.assertNotNull(ourProductModel.getListingLinkDesc());
		ourProductModel.getAltText();
		
	}

	@Test
	void testInitPathResourceNull() throws Exception {
		Resource resource = context.currentResource("/content/vcm/language-masters/titleProduct");
		ourProductModel.setResource(resource);
		ourProductModel.init();
		assertEquals("Retirement", ourProductModel.getProductList().get(2).getProductTitle());
		assertEquals("/content/vcm/en/investment-franchise", ourProductModel.getProductList().get(1).getProductLink());
	}

	@Test
	void testInitPathNull() throws Exception {
		Resource resource = context.currentResource("/content/vcm/language-masters/titleProduct");
		ourProductModel.setResource(resource);
		ourProductModel.init();
		assertEquals("Retirement", ourProductModel.getProductList().get(2).getProductTitle());
		assertEquals("", ourProductModel.getProductList().get(2).getProductLink());
	}
	
	@Test
	void testInitDescription() throws Exception {
		Resource resource = context.currentResource("/content/vcm/language-masters/titleDescProduct");
		ourProductModel.setResource(resource);
		context.load().json("/rootPage.json", "/content/vcm/language-masters/en/products/retirement");
		ourProductModel.init();
		assertEquals("Retirement<br>Products", ourProductModel.getProductList().get(2).getProductHeading());
		assertEquals("See More", ourProductModel.getProductList().get(0).getDesktopButtonText());
		assertEquals("Overview", ourProductModel.getProductList().get(0).getTabletButtonText());
		assertEquals("/content/vcm/language-masters/en/products/retirement.html", ourProductModel.getProductList().get(2).getProductButtonLink());
	}
	@Test
	void testInitDescPathResourceNull() throws Exception {
		Resource resource = context.currentResource("/content/vcm/language-masters/titleDescProduct");
		ourProductModel.setResource(resource);
		ourProductModel.init();
		assertEquals("/content/vcm/language-masters/en/products/usaa-529-education-savings-plan", ourProductModel.getProductList().get(3).getProductButtonLink());
	}
	@Test
	void testInitDescPathNull() throws Exception {
		Resource resource = context.currentResource("/content/vcm/language-masters/titleDescProduct");
		ourProductModel.setResource(resource);
		ourProductModel.init();
		assertEquals("", ourProductModel.getProductList().get(2).getProductLink());
	}
	

	@Test
	public void testProductsBeanRetirement() {
		
		Resource resource = context.currentResource("/content/vcm/language-masters/en/products/details");
		ourProductModel.setResource(resource);
		ourProductModel.init();
		Assert.assertNotNull(ourProductModel.getProductList().get(1).getProductTextArea());
		Assert.assertNotNull(ourProductModel.getProductList().get(1).getProductIcon());
		Assert.assertNotNull(ourProductModel.getProductList().get(1).getProductIconTitle());
		
	}
	@Test
	public void testProductOverlay() {
		
		Resource resource = context.currentResource("/content/vcm/language-masters/en/products/variant");
		ourProductModel.setResource(resource);
		ourProductModel.init();
		ourProductModel.setSectionDescription("This is section description");
		Assert.assertNotNull(ourProductModel.getSectionDescription());
		Assert.assertNotNull(ourProductModel.getProductList().get(0).getProductGreyText());
		Assert.assertNotNull(ourProductModel.getProductList().get(0).getProductBlueText());
		Assert.assertNotNull(ourProductModel.getProductList().get(0).getLeftColumnTitle());
		Assert.assertNotNull(ourProductModel.getProductList().get(0).getLeftColumnDescription());

		Assert.assertNotNull(ourProductModel.getProductList().get(0).getRightColumnButtonText());
		Assert.assertNotNull(ourProductModel.getProductList().get(0).getRightColumnDescription());
		Assert.assertNotNull(ourProductModel.getProductList().get(0).getRightColumnLink());
		Assert.assertNotNull(ourProductModel.getProductList().get(0).getRightColumnTitle());
		
	}
	
}