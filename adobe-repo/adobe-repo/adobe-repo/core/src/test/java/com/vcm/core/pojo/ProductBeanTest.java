package com.vcm.core.pojo;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import com.vcm.core.pojo.ProductsBean;

public class ProductBeanTest {
	private ProductsBean productBean;

	@BeforeEach
	public void setup() throws Exception {
	productBean = new ProductsBean();
	}

	@Test
	public void testGetterSetters() throws Exception {
		
	productBean.setProductTitle("Product Title");
	productBean.setProductLink("product Link");
	productBean.setTabSelect("tab Select");
	productBean.setDescTabSelect("description TabSelect");
	productBean.setProductHeading("product Heading");
	productBean.setProductDescription("product Description");
	productBean.setDesktopButtonText("desktop Button Text");
	productBean.setTabletButtonText("tablet Button Text");
	productBean.setProductButtonLink("Product Button Link");
	productBean.setProductTextArea("Product Text Area");
	productBean.setProductIconTitle("ProductIcon Title");
	productBean.setProductText("Product Text");
	productBean.setIsmultiTargetLink(true);
	productBean.setMemberProductLink("member Product Link");
	productBean.setFinancialProductlink("financial Product link");
	productBean.setIiProductlink("iiProductlink");
	productBean.setProductLinkUrl("product LinkUrl");
	productBean.setProductLinkText("product LinkText");
	productBean.setProductIcon("product Icon");
	productBean.setProductGreyText("product Grey Text");
	productBean.setProductBlueText("product Blue Text");
	productBean.setLinkDescriptionType("link Description Type");
	productBean.setButtonLinkDesc("button Link Description");
	productBean.setOverlayLinkDesc("overlay Link Desc");
	productBean.setLinkDescription("link Description");
	productBean.setOverlayAttribute("overlay Attribute");
	productBean.setSectionDescription("section Description");
	productBean.setRightColumnButtonText("right ColumnButtonText");
	productBean.setRightColumnLink("rightColumn Link");
	productBean.setRightColumnTitle("rightColumn Title");
	productBean.setRightColumnDescription("right Column Description");
	productBean.setLeftColumnTitle("leftColumn Title");
	productBean.setLeftColumnDescription("leftColumn Description");
	String[] audienceType = {"audienceType1", "audienceType2", "audienceType3"};
	productBean.setAudienceType(audienceType);
	
	assertEquals("leftColumn Description", productBean.getLeftColumnDescription());
	assertEquals("leftColumn Title", productBean.getLeftColumnTitle());
	assertEquals("right Column Description", productBean.getRightColumnDescription());
	assertEquals("rightColumn Title", productBean.getRightColumnTitle());
	assertEquals("rightColumn Link", productBean.getRightColumnLink());
	assertEquals("right ColumnButtonText", productBean.getRightColumnButtonText());
	assertEquals("section Description", productBean.getSectionDescription());
	assertEquals("overlay Attribute", productBean.getOverlayAttribute());
	assertEquals("link Description", productBean.getLinkDescription());
	assertEquals("button Link Description", productBean.getButtonLinkDesc());
	assertEquals("link Description Type", productBean.getLinkDescriptionType());
	assertEquals("product Blue Text", productBean.getProductBlueText());
	assertEquals("product Grey Text", productBean.getProductGreyText());
	assertEquals("product Icon", productBean.getProductIcon());
	assertEquals("product LinkText", productBean.getProductLinkText());
	assertEquals("product LinkUrl", productBean.getProductLinkUrl());
	assertEquals("iiProductlink", productBean.getIiProductlink());
	assertEquals("financial Product link", productBean.getFinancialProductlink());
	assertEquals("member Product Link", productBean.getMemberProductLink());
	assertEquals("Product Text", productBean.getProductText());
	assertEquals("ProductIcon Title", productBean.getProductIconTitle());
	assertEquals("Product Text Area", productBean.getProductTextArea());
	assertEquals("Product Button Link", productBean.getProductButtonLink());
	assertEquals("tablet Button Text", productBean.getTabletButtonText());
	assertEquals("desktop Button Text", productBean.getDesktopButtonText());
	assertEquals("product Description", productBean.getProductDescription());
	assertEquals("product Heading", productBean.getProductHeading());
	assertEquals("description TabSelect", productBean.getDescTabSelect());
	assertEquals("tab Select", productBean.getTabSelect());
	assertEquals("product Link", productBean.getProductLink());
	assertEquals("Product Title", productBean.getProductTitle());
	assertEquals("overlay Link Desc", productBean.getOverlayLinkDesc());
	assertEquals(true, productBean.isIsmultiTargetLink());
	}
}
