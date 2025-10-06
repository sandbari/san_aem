package com.vcm.core.service;

import java.util.Map;

import org.apache.commons.mail.HtmlEmail;

public interface EmailService{
    
    public void sendMail(HtmlEmail email);
    
    public HtmlEmail creatEmail(String templatePath, Map<String, String> emailProperties, String[] recepients);
    
}
