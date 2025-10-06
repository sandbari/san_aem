package com.vcm.core.service.impl;

import com.day.cq.wcm.api.Page;
import com.google.gson.Gson;
import com.vcm.core.service.ProductDetailService;
import org.apache.commons.lang.StringUtils;
import org.apache.sling.api.resource.ValueMap;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.metatype.annotations.AttributeDefinition;
import org.osgi.service.metatype.annotations.AttributeType;
import org.osgi.service.metatype.annotations.Designate;
import org.osgi.service.metatype.annotations.ObjectClassDefinition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Component(service = ProductDetailService.class, immediate = true)
@Designate(ocd = ProductDetailServiceImpl.Config.class)
public class ProductDetailServiceImpl implements ProductDetailService {

	private static final Logger log = LoggerFactory.getLogger(ProductDetailServiceImpl.class);
	private String applicationName;
	private String productDetailEndPointDomain;
	private String productDetailEndPoint;
	private String strategyDetailEndPoint;
	private String indexDetailEndPoint;
	private String productDetailAPIKey;
	private String[] productsServiceUrl;

	private String fundListEndPoint;

	private String fundApiKey;

	@Activate
	@Modified
	protected void activate(final Config config) {
		this.applicationName = config.applicationName();
		this.productDetailEndPointDomain = config.productDetailEndPointDomain();
		this.productDetailEndPoint = config.productDetailEndPoint();
		this.strategyDetailEndPoint = config.strategyDetailEndPoint();
		this.indexDetailEndPoint = config.indexDetailEndPoint();
		this.productDetailAPIKey = config.productDetailAPIKey();
		this.productsServiceUrl = config.productsServiceUrl();
		this.fundListEndPoint = config.fundListEndPoint();
		this.fundApiKey = config.fundApiKey();
	}

	@Override
	public String getApplicationName() {
		return this.applicationName;
	}

	@Override
	public String getProductDetailEndPoint() {
		return this.productDetailEndPoint;
	}

	@Override
	public String getProductDetailAPIKey() {
		return this.productDetailAPIKey;
	}

	@Override
	public String getStrategyDetailEndPoint() {
		String strategyEndPoint = this.productDetailEndPointDomain + this.strategyDetailEndPoint;
		return strategyEndPoint;
	}

	@Override
	public String getIndexDetailEndPoint() {
		String indexEndPoint = this.productDetailEndPointDomain + this.indexDetailEndPoint;
		return indexEndPoint;
	}

	@Override
	public String[] getProductsServiceUrl() {		
		String[] productsServiceUrlLocal= {""};
		if(Objects.nonNull(productsServiceUrl)) {
			productsServiceUrlLocal=productsServiceUrl.clone();
		}
		return productsServiceUrlLocal;
	}

	public String getConfigAsJson(Page currentPage) {
		Map<String, String> map = new HashMap<>();
		String json;
		if (currentPage != null) {
			ValueMap pageProps = currentPage.getProperties();
			if (pageProps != null) {
				for (String productUrl : productsServiceUrl) {
					if (!productUrl.equals("")) {
						String[] valueArr = productUrl.split(":");
						map.put(valueArr[0],
								productDetailEndPointDomain + productDetailEndPoint + pageProps.get("fundID", String.class) + "/" + valueArr[1]);
					}
				}
			}
		}
		Gson gson = new Gson();
		json = gson.toJson(map);
		return json;
	}

	@Override
	public String getFundListEndPoint() {
		return this.fundListEndPoint;
	}

	@Override
	public String getFundListAPIKey() {
		return this.fundApiKey;
	}

	@ObjectClassDefinition(name = "VCM Product Details API Configuration", description = "This service contains "
			+ "all Product Details API OSGI Configuration")
	public @interface Config {
		@AttributeDefinition(name = "Application Name", type = AttributeType.STRING)
		String applicationName() default "VCM";

		@AttributeDefinition(name = "API End Point Domain", description = "Please enter API End Point Domain")
		String productDetailEndPointDomain() default "https://dev.api.vcm.com/";

		@AttributeDefinition(name = "Product Detail API End Point", description = "Please enter Product Detail API End Point")
		String productDetailEndPoint() default "search/product/";

		@AttributeDefinition(name = "Strategy Detail API End Point", description = "Please enter Strategy Detail API End Point")
		String strategyDetailEndPoint() default "search/institutionalStrategy/";

		@AttributeDefinition(name = "Index Detail API End Point", description = "Please enter Index Detail API End Point")
		String indexDetailEndPoint() default "search/index/";

		@AttributeDefinition(name = "Product API Key", description = "Please enter Product API Key")
		String productDetailAPIKey() default "aWNSUczYsa1acwedSJw4eGjjLWVRGtj6CppidZL8";

		@AttributeDefinition(name = "Products Values", description = "Please enter colon separated values Ex-annualreturns:AnnualReturns")
		String[] productsServiceUrl() default { "" };
		@AttributeDefinition(name = "Fund List URL", description = "Please enter Fund List API End Point")
		String fundListEndPoint() default StringUtils.EMPTY;
		@AttributeDefinition(name = "Fund List API Key", description = "Please enter Fund List API Key")
		String fundApiKey() default StringUtils.EMPTY;
	}

}
