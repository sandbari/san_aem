package com.vcm.core.service.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.jcr.Node;
import javax.jcr.NodeIterator;
import javax.jcr.RepositoryException;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ValueMap;
import org.json.JSONException;
import org.json.JSONWriter;
import org.osgi.service.component.annotations.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.day.cq.tagging.Tag;
import com.day.cq.tagging.TagManager;
import com.day.cq.wcm.api.Page;
import com.day.cq.wcm.api.PageManager;
import com.vcm.core.constants.ComponentConstants;
import com.vcm.core.pojo.ProductMenuItems;
import com.vcm.core.service.ProductMenuService;
import com.vcm.core.utils.PropertyUtil;
import com.vcm.core.utils.UtilityService;

@Component(service = ProductMenuService.class, immediate = true)
public class ProductMenuServiceImpl implements ProductMenuService {

	private static final Logger LOG = LoggerFactory.getLogger(ProductMenuServiceImpl.class);

	@Override
	public void writeJsonResponse(SlingHttpServletRequest request, JSONWriter writer)
			throws JSONException, RepositoryException {

		LOG.debug(">>>>>>Product Menu - inside write json response");

		ResourceResolver resourceResolver = request.getResourceResolver();

		PageManager pageManager = resourceResolver.adaptTo(PageManager.class);

		TagManager tagManager = resourceResolver.adaptTo(TagManager.class);

		Page rootPage = null;
		List<ProductMenuItems> productMenuList = getTabTagDetails(request, tagManager);

		if (productMenuList.isEmpty()) {
			writer.object();
			writer.key("info").value("Please add tags to display product menu with pages");
			writer.endObject();
		} else {
			writer.array();

			for (ProductMenuItems productMenuItems : productMenuList) {

				rootPage = pageManager
						.getContainingPage(resourceResolver.getResource(productMenuItems.getParentPagePath()));
				if (null != rootPage) {

					LOG.debug("<<<< Root Page Path - {}", rootPage.getPath());
					writer.object();

					writer.key(ComponentConstants.PROPERTY_TAB_TITLE).value(productMenuItems.getTabTitle());

					writer.key(ComponentConstants.PROPERTY_TAB_LINK)
							.value(UtilityService.identifyLinkUrl(productMenuItems.getTabLink(), resourceResolver));

					writer.key(ComponentConstants.PROPERTY_BUTTON_TEXT).value(productMenuItems.getButtonText());

					writer.key(ComponentConstants.PROPERTY_BUTTON_LINK)
							.value(UtilityService.identifyLinkUrl(productMenuItems.getButtonLink(), resourceResolver));

					populateTagListItems(resourceResolver, productMenuItems.getParentPagePath(),
							productMenuItems.getTagList(), writer);

					writer.endObject();
				}
			}
			writer.endArray();
		}
	}

	private void populateTagListItems(ResourceResolver resourceResolver, String parentPagePath, List<Tag> tagList,
			JSONWriter writer) throws JSONException, RepositoryException {

		writer.key(ComponentConstants.CHILD_TAG_LIST).array();
		Resource resource = resourceResolver.getResource(parentPagePath);
		LOG.debug("resource {} rootPath{}", resource.getPath(), parentPagePath);
		for (Tag tagItems : tagList) {
			if (null != tagItems) {
				writer.object();
				writer.key(ComponentConstants.TAG_TITLE).value(tagItems.getTitle());
				LOG.debug("TAG title written in json {}", tagItems.getTitle());
				writer.key(ComponentConstants.TAGGED_PAGES).array();
				Iterator<Page> pageIterator = resource.adaptTo(Page.class).listChildren();
				while (pageIterator.hasNext()) {
					String fundType = "";
					Page containingPage = pageIterator.next();
					ValueMap pageProp = containingPage.getProperties();
					if (null != pageProp.get(ComponentConstants.PRODUCT_CATEGORY_TEXT, String.class)) {
						if (pageProp.get(ComponentConstants.PRODUCT_CATEGORY_TEXT, String.class).equals("MF")) {
							fundType = pageProp.get(ComponentConstants.FUND_TYPE, String.class);
						}
						if (pageProp.get(ComponentConstants.PRODUCT_CATEGORY_TEXT, String.class).equals("ETF")) {
							fundType = pageProp.get(ComponentConstants.SOLUTION_TYPE, String.class);
						}
					}
					if (null != fundType && fundType.equals(tagItems.getTagID())) {
						writer.object();
						writer.key(ComponentConstants.PAGE_TITLE).value(containingPage.getTitle());
						writer.key(ComponentConstants.PAGE_LINK)
								.value(UtilityService.identifyLinkUrl(containingPage.getPath(), resourceResolver));
						writer.endObject();
					}
				}
				writer.endArray();
				writer.endObject();
			}
		}
		writer.endArray();

	}

	public List<ProductMenuItems> getTabTagDetails(SlingHttpServletRequest request, TagManager tagManager)
			throws RepositoryException {

		List<ProductMenuItems> productMenuList = new ArrayList<>();

		Node currentNode = request.getResource().adaptTo(Node.class);

		NodeIterator currentNodeIterator = currentNode.getNodes();

		LOG.debug("Current Node Path {}", currentNode.getPath());
		// get the grandchild node of the currentNode - which represents where the
		// MultiField values are stored

		while (currentNodeIterator.hasNext()) {

			Node child = currentNodeIterator.nextNode();
			if (("productMenuItems").equalsIgnoreCase(child.getName())) {

				NodeIterator childNodeIterator = child.getNodes();

				while (childNodeIterator.hasNext()) {

					Node childNode = childNodeIterator.nextNode();
					ProductMenuItems productMenuItems = new ProductMenuItems();
					if (null != childNode.getProperty(ComponentConstants.PROPERTY_BUTTON_TEXT)) {
						productMenuItems.setButtonText(
								childNode.getProperty(ComponentConstants.PROPERTY_BUTTON_TEXT).getString());
					}
					if (null != childNode.getProperty(ComponentConstants.PROPERTY_BUTTON_LINK)) {
						productMenuItems.setButtonLink(
								childNode.getProperty(ComponentConstants.PROPERTY_BUTTON_LINK).getString());
					}
					if (null != childNode.getProperty(ComponentConstants.ROOT_PAGE_PATH)) {
						productMenuItems.setParentPagePath(
								childNode.getProperty(ComponentConstants.ROOT_PAGE_PATH).getString());
					}
					if (null != childNode.getProperty(ComponentConstants.PROPERTY_TAB_TITLE)) {
						productMenuItems
								.setTabTitle(childNode.getProperty(ComponentConstants.PROPERTY_TAB_TITLE).getString());
					}
					if (null != childNode.getProperty(ComponentConstants.PROPERTY_TAB_LINK)) {
						productMenuItems
								.setTabLink(childNode.getProperty(ComponentConstants.PROPERTY_TAB_LINK).getString());
					}
					if (null != childNode.getProperty(ComponentConstants.TAG_PATH_LIST)) {

						productMenuItems.setTagList(PropertyUtil.getTagListFromProperty(
								childNode.getProperty(ComponentConstants.TAG_PATH_LIST).getValues(), tagManager));

					}

					productMenuList.add(productMenuItems);
				}
			}

		}
		return productMenuList;

	}

}
