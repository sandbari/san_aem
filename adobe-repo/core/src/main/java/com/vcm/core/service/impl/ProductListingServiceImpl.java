package com.vcm.core.service.impl;

import java.util.*;

import org.apache.commons.lang.StringUtils;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ValueMap;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.metatype.annotations.AttributeDefinition;
import org.osgi.service.metatype.annotations.Designate;
import org.osgi.service.metatype.annotations.ObjectClassDefinition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.day.cq.tagging.Tag;
import com.day.cq.tagging.TagManager;
import com.day.cq.wcm.api.NameConstants;
import com.day.cq.wcm.api.Page;
import com.google.gson.Gson;
import com.vcm.core.constants.Constant;
import com.vcm.core.pojo.FilterBean;
import com.vcm.core.pojo.FundDetailPojo;
import com.vcm.core.pojo.StrategyBean;
import com.vcm.core.service.ConstantsConfigService;
import com.vcm.core.service.ProductListingService;
import com.vcm.core.utils.UtilityService;

@Component(service = ProductListingService.class, immediate = true)
@Designate(ocd = ProductListingServiceImpl.Config.class)
public class ProductListingServiceImpl implements ProductListingService {
	private static final Logger LOG = LoggerFactory.getLogger(ProductListingServiceImpl.class);
	private String membersPageRootPath;
	private String fundListEndpoint=StringUtils.EMPTY;
	private String fundListApiKey=StringUtils.EMPTY;
	private String etfPageRootPath;
	private String etfListEndpoint=StringUtils.EMPTY;
	private String strategyPageRootPath;
	private String strategyListEndPoint = StringUtils.EMPTY;
	private String productApiKey = StringUtils.EMPTY;
	private String highIndexEndPoint = StringUtils.EMPTY;
	
	@Reference
	private ConstantsConfigService constantsConfigService;

	@Activate
	@Modified
	protected void activate(final Config config) {
		membersPageRootPath = String.valueOf(config.getMembersPageRootPath());
		fundListEndpoint=String.valueOf(config.getFundListEndPoint());
		fundListApiKey=String.valueOf(config.getFundApiKey());
		etfPageRootPath = String.valueOf(config.getETFPageRootPath());
		etfListEndpoint = String.valueOf(config.getETFListEndPoint());
		LOG.debug("configure: membersPageRootPath ='{}'", membersPageRootPath);
		strategyPageRootPath = String.valueOf(config.getStrategyPageRootPath());
		strategyListEndPoint = String.valueOf(config.getStrategyListEndPoint());
		highIndexEndPoint = String.valueOf(config.getHighestIndexEndPoint());
		productApiKey=String.valueOf(config.getProductApiKey());
		LOG.debug("configure: strategyPageRootPath ='{}'", strategyPageRootPath);
	}

	@Override
	public String getFundList(ResourceResolver resourceResolver) {
		List<FundDetailPojo> fundList=new ArrayList<>();
		String jsonString=StringUtils.EMPTY;
		LOG.debug("Inside Members Get Fund Listing Service");
		if(Objects.nonNull(membersPageRootPath)) {
			Resource rootPage=resourceResolver.getResource(membersPageRootPath);
			TagManager tagManager = resourceResolver.adaptTo(TagManager.class);
			if(Objects.nonNull(rootPage)) {	
				Iterator<Page> productList = rootPage.adaptTo(Page.class).listChildren();
				while(productList.hasNext()) {
					String investMinFinancial = "false";
					FundDetailPojo fundDetail=new FundDetailPojo();
					Page childPage = productList.next();
					ValueMap pageProps = childPage.getProperties();
					String ticker=pageProps.get("ticker",String.class);
					String fundId=pageProps.get("fundID",String.class);
					String franchise = "";
					String advisorFactsheetUrl = StringUtils.EMPTY;
					String memFactsheetUrl = StringUtils.EMPTY;
					if(null != pageProps.get("factsheeturl",String.class)) {
						memFactsheetUrl=UtilityService.getPDFLinkUrl(pageProps.get("factsheeturl",String.class));
					}
					if(null != pageProps.get("advisorFactsheetUrl",String.class)) {
						advisorFactsheetUrl=UtilityService.getPDFLinkUrl(pageProps.get("advisorFactsheetUrl",String.class));
					}
					if(null != pageProps.get("franchise", String.class)) {
						franchise = pageProps.get("franchise",String.class);
						Tag franchiseTag = tagManager.resolve(franchise);
						if(null != franchiseTag) {
							franchise = franchiseTag.getTitle();
						}
					}
					if(null != pageProps.get("investMinFinancial", String.class)) {
						investMinFinancial = pageProps.get("investMinFinancial",String.class);
					}					
					if(Objects.nonNull(ticker) && !ticker.equals("") && !ticker.equals(StringUtils.EMPTY)) {
						String title=childPage.getPageTitle() == null ? pageProps.get(NameConstants.PN_TITLE,String.class) : childPage.getPageTitle();
						fundDetail.setFundName(title);
						fundDetail.setFundDescription(childPage.getDescription());
						fundDetail.setDetailpageurl(UtilityService.identifyLinkUrl(childPage.getPath(), resourceResolver));
						fundDetail.setTicker(ticker);
						fundDetail.setFundId(fundId); 
						fundDetail.setFranchise(franchise); 
						fundDetail.setInvestMinFinancial(investMinFinancial);
						fundDetail.setMemberFactsheetUrl(memFactsheetUrl);
						fundDetail.setAdvisorFactsheetUrl(advisorFactsheetUrl);
						fundDetail.setIraEligible(pageProps.get("iraEligible",String.class));
						fundDetail.setActiveFundPage(pageProps.get("isActiveFundPage",String.class));
						fundList.add(fundDetail);
					}
				}
				Gson gson = new Gson();
				jsonString = gson.toJson(fundList);
			}
		}
		return jsonString;
	}

	@Override
	public String getETFList(ResourceResolver resourceResolver) {
		List<FundDetailPojo> fundList=new ArrayList<>();
		String jsonString=StringUtils.EMPTY;
		LOG.debug("Inside ETF Get Fund Listing Service");
		if(Objects.nonNull(etfPageRootPath)) {
			Resource rootPage=resourceResolver.getResource(etfPageRootPath);
			TagManager tagManager = resourceResolver.adaptTo(TagManager.class);
			if(Objects.nonNull(rootPage)) {
				Iterator<Page> productList = rootPage.adaptTo(Page.class).listChildren();
				while(productList.hasNext()) {
					FundDetailPojo fundDetail=new FundDetailPojo();
					Page childPage = productList.next();
					ValueMap pageProps = childPage.getProperties();
					String ticker=pageProps.get("ticker",String.class);
					String fundId=pageProps.get("fundID",String.class);
					String franchise = "";
					String assetClass = "";
					String solutionType = "";
					
					if(null != pageProps.get("franchise", String.class)) {
						Tag topicTag = tagManager.resolve(pageProps.get("franchise",String.class));
						franchise = topicTag.getTitle();
					}
					if(null != pageProps.get("fundType", String.class)) {
						Tag topicTag = tagManager.resolve(pageProps.get("fundType",String.class));
						assetClass = topicTag.getTitle();
					}

					if(null != pageProps.get("solutionType", String.class)) {
						Tag topicTag = tagManager.resolve(pageProps.get("solutionType", String.class));
						Resource tagResource = resourceResolver.getResource(topicTag.getPath());
						if(null != tagResource) {
							solutionType = tagResource.getValueMap().get(NameConstants.PN_TITLE).toString();
						}
					}
					if(Objects.nonNull(ticker) && !ticker.equals("") && !ticker.equals(StringUtils.EMPTY)) {
						String title=childPage.getPageTitle() == null ? pageProps.get(NameConstants.PN_TITLE,String.class) : childPage.getPageTitle();
						fundDetail.setFundName(title);
						fundDetail.setFundDescription(childPage.getDescription());
						fundDetail.setDetailpageurl(UtilityService.identifyLinkUrl(childPage.getPath(), resourceResolver));
						fundDetail.setTicker(ticker);
						fundDetail.setFundId(fundId);
						fundDetail.setFranchise(franchise);
						fundDetail.setAssetClass(assetClass);
						fundDetail.setSolutionType(solutionType);
						String factsheeturl=UtilityService.getPDFLinkUrl(pageProps.get("factsheeturl",String.class));
						fundDetail.setFactsheeturl(factsheeturl);
						fundDetail.setIraEligible(pageProps.get("iraEligible",String.class));
						fundDetail.setActiveFundPage(pageProps.get("isActiveFundPage",String.class));
						fundList.add(fundDetail);
					}
				}
				Gson gson = new Gson();
				jsonString = gson.toJson(fundList);
			}
		}

		return jsonString;
	}


	@Override
	public String getFundListEndPoint() {
		return fundListEndpoint;
	}

	@Override
	public String getEtfListEndpoint() {
		return etfListEndpoint;
	}

	/**
	 * @return the fundListApiKey
	 */
	@Override
	public String getFundListApiKey() {
		return fundListApiKey;
	}

	@Override
	public String getStrategyList(ResourceResolver resourceResolver) {
		StrategyBean strategyBean = new StrategyBean();
		List<Map<String,String>> mapList = new ArrayList<>();
		List<FilterBean> filters = new ArrayList<>();
		LOG.debug("Inside Strategy Listing Listing Service");
		if(Objects.nonNull(strategyPageRootPath)) {
			Resource rootPage = resourceResolver.getResource(strategyPageRootPath);
			TagManager tagManager = resourceResolver.adaptTo(TagManager.class);
			if(Objects.nonNull(rootPage)) {
				FilterBean franchiseBean = new FilterBean();
				franchiseBean.setName(Constant.INVESTMENT_FRANCHISE);
				Tag franchiseTag = tagManager.resolve(Constant.FRANCHISE_FILTER_ROOTPATH);
				franchiseBean.setValues(getChildTagList(franchiseTag));
				filters.add(franchiseBean);
				FilterBean assetBean = new FilterBean();
				assetBean.setName(Constant.ASSET_CLASS);
				Tag assetTag = tagManager.resolve(Constant.ASSET_FILTER_ROOT_PATH);
				assetBean.setValues(getChildTagList(assetTag));
				filters.add(assetBean);
				Iterator<Page> StrategyPageList = rootPage.adaptTo(Page.class).listChildren();
				while(StrategyPageList.hasNext()) {
					Page childPage = StrategyPageList.next();
					ValueMap pageProps = childPage.getProperties();
					String fundId = pageProps.get("fundID",String.class);
					String assetClass = "";
					String franchise = "";
					if(Objects.nonNull(fundId) && !fundId.equals("")) {
						Map<String,String> strategyMap=new HashMap<>();
						if(null != pageProps.get("franchise", String.class)) {
							Tag topicTag = tagManager.resolve(pageProps.get("franchise",String.class));
							if(topicTag.getTitle() !=null ) {
								franchise = topicTag.getTitle();
							}
						}
						if(null != pageProps.get("fundType", String.class)) {
							Tag topicTag = tagManager.resolve(pageProps.get("fundType",String.class));
							if (topicTag.getTitle() !=null ) {
								assetClass = topicTag.getTitle();
							}
						}
						String title = childPage.getPageTitle() == null ? pageProps.get(NameConstants.PN_TITLE,String.class) : childPage.getPageTitle();
						strategyMap.put("strategyCode", fundId);
						strategyMap.put("strategyPageTitle", title);
						strategyMap.put("strategyUrl", UtilityService.identifyLinkUrl(childPage.getPath(), resourceResolver));
						strategyMap.put("assetClass", assetClass);
						strategyMap.put("franchise", franchise);
						mapList.add(strategyMap);
					}
				}
				strategyBean.setStrategyMapList(mapList);
				strategyBean.setFilters(filters);
				LOG.debug("size of strategy map list {}", mapList.size());
			}
		}
		Gson gson = new Gson();
		String jsonString = gson.toJson(strategyBean);
		return jsonString;
	}
	
	public List<String> getChildTagList(Tag tag){
		List<String> childTagList = new ArrayList<>();
		if(null != tag) {
			Iterator<Tag> tagIterator = tag.listChildren();
			while(tagIterator.hasNext()) {
				
				Tag childTag = tagIterator.next();
				childTagList.add(childTag.getTitle());
			}
		}
		return childTagList;
	}

	@Override
	public String getStrategyListEndPoint() {
		return strategyListEndPoint;
	}
	

	@Override
	public String getProductApiKey() {
		return productApiKey;
	}

	@Override
	public String getHighIndexEndPoint() {
		return highIndexEndPoint;
	}

	/**
	 * @param constantsConfigService the constantsConfigService to set
	 */
	public void setConstantsConfigService(ConstantsConfigService constantsConfigService) {
		this.constantsConfigService = constantsConfigService;
	}

	@ObjectClassDefinition(name = "VCM Product Listing Service Configuration", description = "This service contains "
			+ "all VCM Product listing OSGI Configuration")
	public @interface Config {
		@AttributeDefinition(name = "Members Page Root Path", description = "Please enter members page root path to get fund listing")
		String getMembersPageRootPath() default StringUtils.EMPTY;
		@AttributeDefinition(name = "Members Fund List Api Endpoint", description = "Please enter members fund listing api endpoint")
		String getFundListEndPoint() default StringUtils.EMPTY;
		@AttributeDefinition(name = "ETF Page Root Path", description = "Please enter ETF page root path to get fund listing")
		String getETFPageRootPath() default StringUtils.EMPTY;
		@AttributeDefinition(name = "ETF Fund List Api Endpoint", description = "Please enter ETF fund listing api endpoint")
		String getETFListEndPoint() default StringUtils.EMPTY;
		@AttributeDefinition(name = "Members Fund List Api Key", description = "Please enter members fund listing api key")
		String getFundApiKey() default StringUtils.EMPTY;
		@AttributeDefinition(name = "Our Strategies Page Root Path", description = "Please enter Strategy page root path to get fund listing")
		String getStrategyPageRootPath() default StringUtils.EMPTY;
		@AttributeDefinition(name = "Strategy Api Endpoint", description = "Please enter Strategy listing api endpoint")
		String getStrategyListEndPoint() default StringUtils.EMPTY;
		@AttributeDefinition(name = "Product Listing Api Key", description = "Please enter Strategy listing api key")
		String getProductApiKey() default StringUtils.EMPTY;
		@AttributeDefinition(name = "Daily Highest Index EndPoint", description = "Please enter daily highest index endPoint")
		String getHighestIndexEndPoint() default StringUtils.EMPTY;
	}



}
