package com.vcm.core.service.impl;

import java.util.HashMap;
import java.util.Map;

import javax.jcr.Session;

import org.apache.commons.mail.HtmlEmail;
import org.apache.sling.api.resource.LoginException;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.apache.sling.testing.mock.sling.ResourceResolverType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.day.cq.commons.mail.MailTemplate;
import com.day.cq.mailer.MessageGateway;
import com.day.cq.mailer.MessageGatewayService;

import io.wcm.testing.mock.aem.junit5.AemContext;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;

@ExtendWith(MockitoExtension.class)
@ExtendWith(AemContextExtension.class)
class EmailServiceImplTest {
	
    public final AemContext context = new AemContext(ResourceResolverType.JCR_OAK);
    
    @Mock
    private MessageGatewayService messageGatewayService;

    @Mock
    private ResourceResolverFactory resourceResolverFactory;
    
    @Mock
    MessageGateway messageGateway;
    
    @Mock
    MailTemplate mailTemplate;
    
    @Mock
    HtmlEmail email;
    
    @Mock
    ResourceResolver resourceResolver;
    
    @Mock
    Resource resource;
    
    @Mock
    Session session;
    
    private EmailServiceImpl emailServiceImpl;

    @BeforeEach
    void setup() throws Exception {
    	emailServiceImpl = new EmailServiceImpl();
    	emailServiceImpl.setMessageGatewayService(messageGatewayService);
    	emailServiceImpl.setResourceResolverFactory(resourceResolverFactory);
    }

    @Test
    public void testSendEmail() throws Exception {
    	
    	Mockito.when(messageGatewayService.getGateway(Mockito.any())).thenReturn(messageGateway);
    	emailServiceImpl.sendMail(email);
    	
    }
    
    @Test
    public void testCreatEmail() throws Exception {
    	
    	String templatePath = "/content/dam/vcm";
    	String[] recepients = {"abc@cts.com", "xyz@cts.com"};
    	Map<String, String> emailProperties = new HashMap<String, String>();
    	
    	Mockito.when(resourceResolverFactory.getServiceResourceResolver(Mockito.any())).thenReturn(resourceResolver);
    	Mockito.when(resourceResolver.getResource(templatePath)).thenReturn(resource);
    	Mockito.when(resource.getPath()).thenReturn(templatePath);
    	Mockito.when(resourceResolver.adaptTo(Session.class)).thenReturn(session);
    	mailTemplate = MailTemplate.create(templatePath, session);
    	Mockito.when(session.isLive()).thenReturn(true);
    	emailServiceImpl.creatEmail(templatePath, emailProperties, recepients);
    }
    
    @Test
    public void testException() throws Exception {
    	Mockito.when(resourceResolverFactory.getServiceResourceResolver(Mockito.any())).thenThrow(LoginException.class);
    	emailServiceImpl.getEmailTemplate("/content/dam/vcm");
    }
}
