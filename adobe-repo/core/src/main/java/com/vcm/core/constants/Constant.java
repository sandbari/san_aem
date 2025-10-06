package com.vcm.core.constants;

import java.time.ZoneId;

public class Constant {

	public static final String HTML_EXTENSION=".html";
	
	public static final String MODEL_JSON_EXTENSION=".model.json";
	
	public static final String VCM_CONTENT_PATH="/content/vcm";
	
    public static final String CONTENT_TYPE = "Content-Type";
    public static final String APPLICATION_JSON = "application/json";
    public static final String CONTENTTYPE_FORMURLENCODED = "application/x-www-form-urlencoded";
    public static final String RESULTS_PN = "results";
    public static final String WAIT_TIME_SEC_PN = "estimatedWaitTimeSeconds";

	public static final String VCM_DAM_SEARCH_PATH = "/content/dam/vcm/forms";
	
	public static final String IGNORE_SEARCH_PAGE_PROP="ignoreSearchTag";

	public static final String VCM_DAM_ROOT_PATH = "/content/dam/vcm";
	public static final String PDF_EXTENSION = ".pdf";

	public static final String VCM_STATIC_TEMPLATE = "/conf/vcm/settings/wcm/templates/vcm-static-page";

	public static final String VCM_ETF_TEMPLATE = "/conf/vcm/settings/wcm/templates/etf-product-template";
	
	public static final String VCM_MF_TEMPLATE ="/conf/vcm/settings/wcm/templates/mutual-fund-product-template";
		
	public static final String VCM_SP_TEMPLATE = "/conf/vcm/settings/wcm/templates/strategy-product-template";

	public static final String ETC_CLIENT_LIBS = "/etc.clientlibs";

	public static final String SITEMAP_JCR_DATA = "/jcr:content/renditions/original/jcr:content";
	public static final String SITEMAP_DOT_XML = ".sitemap.xml";
	
	/*Start site config page names */
	public static final String ETF_PAGE_PATTERN_CONFIGURATION_KEY = "etfLinks";
	
	public static final String ETF_THEME_COLOR_CONFIGURATION_KEY = "etfThemeColors";

	public static final String CHART_COLOR_CONFIGURATION_KEY = "chartColors";
	
	public static final String API_CALL_FAILURE_MESSAGE_KEY = "apiCallFailureMessage";
	/*End Site config page names */
	public static final String AEMGRIDCLASS = "aem-GridColumn";
	
	public static final String ASSET_FILTER_ROOT_PATH = "/content/cq:tags/vcm/product-menu-funds/mutual-funds";
	
	public static final String FRANCHISE_FILTER_ROOTPATH = "/content/cq:tags/vcm/investment-franchise";
	
	public static final String VIEW = "View ";
	
	public static final String INVESTMENT_FRANCHISE = "Investment Franchise";
	
	public static final String ASSET_CLASS = "Asset Class";
	
	public static final ZoneId CENTRAL_ZONE_ID = ZoneId.of("America/Chicago");
	
	//Author Instance
	public static final String AUTHOR_INSTANCE = "author";

	public static final String VCMSERVICEUSER = "vcmservice";

	public static final String JSON_EXTENSION = ".json";

	public static final String META_DESCRIPTION = "metaDescription";
	
	public static final String ETF_LABELS = "etflistingLabels";

	public static final String MF_LABELS = "MFlisting";

	public static final String ST_LABELS = "strategyListing";

	public static final String HIGH_INDEX_LABELS = "dataHighIndex";

	public static final String COLLEGE_DETAIL_LABELS = "529collegeplan";
	
    private Constant() {
    }

}
