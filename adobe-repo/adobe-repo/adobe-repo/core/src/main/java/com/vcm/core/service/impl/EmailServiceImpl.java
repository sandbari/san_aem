package com.vcm.core.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import javax.jcr.Session;
import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;

import org.apache.commons.lang.text.StrLookup;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;
import org.apache.sling.api.resource.LoginException;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.day.cq.commons.mail.MailTemplate;
import com.day.cq.mailer.MessageGateway;
import com.day.cq.mailer.MessageGatewayService;
import com.vcm.core.service.EmailService;

@Component(service = EmailService.class, immediate = true)
public class EmailServiceImpl implements EmailService {

    private static final Logger LOG = LoggerFactory.getLogger(EmailServiceImpl.class);

    @Reference
    private MessageGatewayService messageGatewayService;

    @Reference
    private ResourceResolverFactory resourceResolverFactory;

    public void setMessageGatewayService(MessageGatewayService messageGatewayService) {
		this.messageGatewayService = messageGatewayService;
	}

	public void setResourceResolverFactory(ResourceResolverFactory resourceResolverFactory) {
		this.resourceResolverFactory = resourceResolverFactory;
	}

	@Override
    public void sendMail(HtmlEmail email) {

	LOG.debug("Into Send Mail");

	MessageGateway<HtmlEmail> messageGateway = messageGatewayService.getGateway(HtmlEmail.class);

	if (Objects.nonNull(email) && Objects.nonNull(messageGateway)) {

	    messageGateway.send(email);

	}
    }

    public HtmlEmail creatEmail(String templatePath, Map<String, String> emailProperties, String[] recepients) {

	LOG.debug("Send Mail Service TemplatePath {}",templatePath);
	
	LOG.debug("Send Mail Service emailProperties {}",emailProperties);
	
	MailTemplate mailTemplate = this.getEmailTemplate(templatePath);

	ArrayList<InternetAddress> emailRecipients = new ArrayList<InternetAddress>();

	HtmlEmail email = null;
	
	try {

	    if (Objects.nonNull(mailTemplate) && Objects.nonNull(recepients) && recepients.length > 0) {
	
		email = mailTemplate.getEmail(StrLookup.mapLookup(emailProperties), HtmlEmail.class);

		for (String toMailId : recepients) {

		    emailRecipients.add(new InternetAddress(toMailId));
		    
		    LOG.debug("Send Mail Service email recipients {}",emailRecipients);

		}
		
		email.setTo(emailRecipients);
	    }

	} catch (IOException | MessagingException | EmailException e) {
	    
	    LOG.error("Email Exception occured {}",e);
	
	}

	return email;
    }

    public MailTemplate getEmailTemplate(String templatePath) {

	MailTemplate mailTemplate = null;

	Map<String, Object> map = new HashMap<>();

	map.put(ResourceResolverFactory.SUBSERVICE, "vcmservice");
	
	Session session = null;

	if (resourceResolverFactory != null && templatePath != null) {

	    try (final ResourceResolver resourceResolver = resourceResolverFactory.getServiceResourceResolver(map)) {

		LOG.debug("Send Mail Service template path {}",templatePath);
		
		Resource templateRsrc = resourceResolver.getResource(templatePath);

		if (Objects.nonNull(templateRsrc)) {

		    session = resourceResolver.adaptTo(Session.class);

		    mailTemplate = MailTemplate.create(templateRsrc.getPath(), session);
		    
		    LOG.debug("Send Mail Service mailTemplate {}",mailTemplate);
		}
	    } catch (LoginException e) {

		LOG.error("LoginException Occured {}", e);
	    }
	    finally {
		if(Objects.nonNull(session) && session.isLive()) {
		    session.logout();
		}
	    }
	}
	return mailTemplate;
    }

}
