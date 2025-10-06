package com.vcm.core.models;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.apache.sling.testing.mock.sling.ResourceResolverType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.wcm.testing.mock.aem.junit5.AemContext;

public class SignInTest {

	private Signin signin;
	public final AemContext context = new AemContext(ResourceResolverType.JCR_OAK);
	
	@BeforeEach
    public void setup() throws Exception {
		signin = new Signin();
    }
	
	@Test
	public void testGetterSetters() throws Exception {
		signin.setUserName("user name");
		signin.setPassword("password");
		signin.setErrorMessage("error message");
		signin.setForgotUserName("forgot user name");
		signin.setLinkUrl("/link/url");
		signin.setRedirectUrl("/redirect/url");
		signin.setRegisterAccount("register account");
		signin.setRegisterUrl("/register/url");
		signin.setSignInButton("sign in button");
		signin.setSignInUrl("/signin/url");
		signin.setTabSelect("tabselect");
		signin.setTabSelectRedirect("/tabselect/redirect");
		signin.setTabSelectRegister("tab select register");
		
		assertNotNull(signin.getUserName());
		assertNotNull(signin.getPassword());
		assertNotNull(signin.getErrorMessage());
		assertNotNull(signin.getForgotUserName());
		assertNotNull(signin.getLinkUrl());
		assertNotNull(signin.getRedirectUrl());
		assertNotNull(signin.getRegisterAccount());
		assertNotNull(signin.getRegisterUrl());
		assertNotNull(signin.getSignInButton());
		assertNotNull(signin.getSignInUrl());
		assertNotNull(signin.getTabSelect());
		assertNotNull(signin.getTabSelectRedirect());
		assertNotNull(signin.getTabSelectRegister());
	}
	
}
