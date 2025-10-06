package com.vcm.core.service;

import com.day.cq.wcm.api.Page;

public interface ProductDetailService {

	public String getApplicationName();
	public String getProductDetailEndPoint();
	public String getStrategyDetailEndPoint();
	public String getIndexDetailEndPoint();
	public String getProductDetailAPIKey();
	public String[] getProductsServiceUrl();
	public String getConfigAsJson(Page currentPage);

	public String getFundListEndPoint();

	public String getFundListAPIKey();
}
