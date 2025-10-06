package com.vcm.core.service.impl;

import java.text.SimpleDateFormat;
import java.util.*;
import javax.jcr.Node;
import javax.jcr.RepositoryException;

import com.google.gson.Gson;
import com.vcm.core.pojo.PostloginBannerBean;
import com.vcm.core.service.PostloginBannerService;
import com.vcm.core.service.VCMSiteConfiguationService;
import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@Component(service = PostloginBannerService.class, immediate = true)
public class PostloginBannerServiceImpl implements PostloginBannerService {

    private static final Logger LOGGER = LoggerFactory.getLogger(PostloginBannerServiceImpl.class);

	private String RESPONSIVEGRID = "root/responsivegrid/";

	private String COMPONENT_NAME = "vcm/components/content/postloginbanner";

	private String bannerRootpath;

	private String bannerPath;

	private int bannerCount;

	@Reference
	private VCMSiteConfiguationService vcmSiteConfigService;

	@Override
	public String getPostloginBannerDetails(SlingHttpServletRequest request) throws RepositoryException {
		getBannerConfig();
		String jsonString = StringUtils.EMPTY;
		List<PostloginBannerBean> bannerBeanList = new ArrayList<>();
		int count = 1;
		Resource root = request.getResourceResolver().getResource(bannerPath);
		if(root.hasChildren()) {
			Iterable<Resource> resources = root.getChildren();
			for (Resource resource : resources) {
				Iterable<Resource> componentResources = resource.getChild(RESPONSIVEGRID).getChildren();
				for (Resource componentResource : componentResources) {
					if (componentResource.isResourceType(COMPONENT_NAME)) {
						PostloginBannerBean postloginBannerBean = new PostloginBannerBean();
						Node node = componentResource.adaptTo(Node.class);
						if(node.hasProperty("banners") && node.getProperty("banners").getString().equals("Popup Banner")){
							if(node.hasProperty("enabledBanner") && node.getProperty("enabledBanner").getString().equals("true")){
								String id = "Banner" + count++;
								postloginBannerBean.setBannerId(id);
								if(node.hasProperty("caption")) {
									postloginBannerBean.setBannerCaption(node.getProperty("caption").getString());
								}
								if(node.hasProperty("message")) {
									postloginBannerBean.setBannerMessage(node.getProperty("message").getString());
								}
								if(node.hasProperty("startDate")) {
									//Calendar cal = node.getProperty("startDate").getDate();
									//Date startDate = cal.getTime();
									//SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm a");
									//String formatStartDate = formatter.format(startDate);
									//postloginBannerBean.setStartDate(formatStartDate);
									postloginBannerBean.setStartDate(node.getProperty("startDate").getString());
								}
								if(node.hasProperty("endDate")) {
								/*Calendar cal1 = node.getProperty("endDate").getDate();
								Date endDate = cal1.getTime();
								SimpleDateFormat formatter1 = new SimpleDateFormat("yyyy-MM-dd hh:mm a");
								String formatEndDate = formatter1.format(endDate);
								postloginBannerBean.setEndDate(formatEndDate);*/
									postloginBannerBean.setEndDate(node.getProperty("endDate").getString());
								}
								if(node.hasProperty("type")) {
									postloginBannerBean.setType(node.getProperty("type").getString());
								}
								if(node.hasProperty("priority")) {
									postloginBannerBean.setPriority(node.getProperty("priority").getString());
								}
								if(node.hasProperty("usertype")) {
									postloginBannerBean.setUserType(node.getProperty("usertype").getString());
								}
								bannerBeanList.add(postloginBannerBean);
							}
						}
					}
				}
			}
		}
		Gson gson = new Gson();
		jsonString = gson.toJson(bannerBeanList);
		return jsonString;
	}

	@Override
	public String getActivePostloginBanner(SlingHttpServletRequest request) throws RepositoryException{
		getBannerConfig();
		int count = 0;
		Resource root = request.getResourceResolver().getResource(bannerRootpath);
		if(root.hasChildren()) {
			Iterable<Resource> resources = root.getChildren();
			for (Resource resource : resources) {
				Iterable<Resource> componentResources = resource.getChild(RESPONSIVEGRID).getChildren();
				for (Resource componentResource : componentResources) {
					if (componentResource.isResourceType(COMPONENT_NAME)) {
						Node node = componentResource.adaptTo(Node.class);
						if(node.hasProperty("enabledBanner") && node.getProperty("enabledBanner").getString().equals("true")){
							count++;
						}

					}
				}
			}
		}
		if(count >= bannerCount){
			return "true";
		} else {
			return "false";
		}
	}

	public void getBannerConfig(){
		Map<String, Object> bannerConfig = vcmSiteConfigService.getLabelConfigAsMap();
		if(null != bannerConfig && !bannerConfig.isEmpty()) {
			bannerRootpath = "/content/vcm/language-masters" + bannerConfig.get("rootpath").toString();
			bannerPath = "/content/vcm/us" + bannerConfig.get("rootpath").toString();
			bannerCount = Integer.parseInt(bannerConfig.get("count").toString());
		}
	}
}
