package com.vcm.core.models;

import javax.inject.Inject;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;

@Model(adaptables = Resource.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)

public class Signin {
	
	@Inject
	private String userName;
	
	@Inject
	private String password;
	
	@Inject
	private String signInButton;
	
	
	@Inject
	private String signInUrl;
	
	@Inject
	private String forgotUserName;
	
	@Inject
	private String linkUrl;
	
	@Inject 
	private String tabSelect;
	
	@Inject
	private String registerAccount;
	
	@Inject
	private String registerUrl;
	
	@Inject 
	private String tabSelectRegister;
	
	@Inject
	private String errorMessage;
	
	@Inject 
	private String redirectUrl;
	
	@Inject 
	private String tabSelectRedirect;

	public String getTabSelectRedirect() {
		return tabSelectRedirect;
	}

	public void setTabSelectRedirect(String tabSelectRedirect) {
		this.tabSelectRedirect = tabSelectRedirect;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getSignInButton() {
		return signInButton;
	}

	public void setSignInButton(String signInButton) {
		this.signInButton = signInButton;
	}

	public String getSignInUrl() {
		return signInUrl;
	}

	public void setSignInUrl(String signInUrl) {
		this.signInUrl = signInUrl;
	}

	public String getForgotUserName() {
		return forgotUserName;
	}

	public void setForgotUserName(String forgotUserName) {
		this.forgotUserName = forgotUserName;
	}

	public String getLinkUrl() {
		return linkUrl;
	}

	public void setLinkUrl(String linkUrl) {
		this.linkUrl = linkUrl;
	}
	
	public String getTabSelect() {
		return tabSelect;
	}

	public void setTabSelect(String tabSelect) {
		this.tabSelect = tabSelect;
	}

	public String getRegisterAccount() {
		return registerAccount;
	}

	public void setRegisterAccount(String registerAccount) {
		this.registerAccount = registerAccount;
	}

	public String getRegisterUrl() {
		return registerUrl;
	}

	public void setRegisterUrl(String registerUrl) {
		this.registerUrl = registerUrl;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public String getRedirectUrl() {
		return redirectUrl;
	}

	public void setRedirectUrl(String redirectUrl) {
		this.redirectUrl = redirectUrl;
	}

	public String getTabSelectRegister() {
		return tabSelectRegister;
	}

	public void setTabSelectRegister(String tabSelectRegister) {
		this.tabSelectRegister = tabSelectRegister;
	}


}