package com.vcm.core.pojo;

/**
 * 
 * @author CTS FundDetail Bean class
 *
 */
public class FundDetailPojo {
	
	private String ticker;
	private String fundId;
	private String fundName;
	private String fundDescription;
	private String factsheeturl;
	private String detailpageurl;
	private String isActiveFundPage;
	private String franchise;
	private String assetClass;
	private String iraEligible;
	private String memberFactsheetUrl;
	private String advisorFactsheetUrl;
	private String solutionType;
	private String investMinFinancial;

	/**
	 * @return investMinFinancial
	 */
	public String getInvestMinFinancial() {
		return investMinFinancial;
	}

	/**
	 * @param investMinFinancial
	 */
	public void setInvestMinFinancial(String investMinFinancial) {
		this.investMinFinancial = investMinFinancial;
	}

	/**
	 * @return solutionType
	 */
	public String getSolutionType() {
		return solutionType;
	}
		
	/**
	 * @param solutionType
	 */
	public void setSolutionType(String solutionType) {
		this.solutionType = solutionType;
	}
	/**
	 * @return memberFactsheetUrl
	 */
	public String getMemberFactsheetUrl() {
		return memberFactsheetUrl;
	}
	/**
	 * @param memberFactsheetUrl
	 */
	public void setMemberFactsheetUrl(String memberFactsheetUrl) {
		this.memberFactsheetUrl = memberFactsheetUrl;
	}
	/**
	 * @return advisorFactsheetUrl
	 */
	public String getAdvisorFactsheetUrl() {
		return advisorFactsheetUrl;
	}
	/**
	 * @param advisorFactsheetUrl
	 */
	public void setAdvisorFactsheetUrl(String advisorFactsheetUrl) {
		this.advisorFactsheetUrl = advisorFactsheetUrl;
	}
	/**
	 * @return the ticker
	 */
	public String getTicker() {
		return ticker;
	}
	/**
	 * @param ticker the ticker to set
	 */
	public void setTicker(String ticker) {
		this.ticker = ticker;
	}
	/**
	 * @return the fundName
	 */
	public String getFundName() {
		return fundName;
	}
	/**
	 * @param fundName the fundName to set
	 */
	public void setFundName(String fundName) {
		this.fundName = fundName;
	}
	/**
	 * @return the fundDescription
	 */
	public String getFundDescription() {
		return fundDescription;
	}
	/**
	 * @param fundDescription the fundDescription to set
	 */
	public void setFundDescription(String fundDescription) {
		this.fundDescription = fundDescription;
	}
	/**
	 * @return the factsheeturl
	 */
	public String getFactsheeturl() {
		return factsheeturl;
	}
	/**
	 * @param factsheeturl the factsheeturl to set
	 */
	public void setFactsheeturl(String factsheeturl) {
		this.factsheeturl = factsheeturl;
	}
	/**
	 * @return the detailpageurl
	 */
	public String getDetailpageurl() {
		return detailpageurl;
	}
	/**
	 * @param detailpageurl the detailpageurl to set
	 */
	public void setDetailpageurl(String detailpageurl) {
		this.detailpageurl = detailpageurl;
	}
	/**
	 * @return the isActiveFundPage
	 */
	public String isActiveFundPage() {
		return isActiveFundPage;
	}
	/**
	 * @param isActiveFundPage the isActiveFundPage to set
	 */
	public void setActiveFundPage(String isActiveFundPage) {
		this.isActiveFundPage = isActiveFundPage;
	}

	public String getFundId() {
		return fundId;
	}

	public void setFundId(String fundId) {
		this.fundId = fundId;
	}

	public String getFranchise() { return franchise; }

	public void setFranchise(String franchise) { this.franchise = franchise; }

	public String getIraEligible() { return iraEligible; }

	public void setIraEligible(String iraEligible) { this.iraEligible = iraEligible; }

	public String getAssetClass() {	return assetClass; }

	public void setAssetClass(String assetClass) { this.assetClass = assetClass; }
	
}
