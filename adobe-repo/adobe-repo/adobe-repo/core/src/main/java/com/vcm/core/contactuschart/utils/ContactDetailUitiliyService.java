package com.vcm.core.contactuschart.utils;

import java.time.DayOfWeek;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import javax.jcr.Session;

import org.apache.sling.api.resource.LoginException;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.day.cq.replication.ReplicationActionType;
import com.day.cq.replication.ReplicationException;
import com.day.cq.replication.Replicator;
import com.vcm.core.constants.Constant;
import com.vcm.core.contactuschart.exceptions.ServiceException;

@Component(service = ContactDetailUitiliyService.class, immediate = true)
public class ContactDetailUitiliyService {

    @Reference
    private Replicator replicator;
  
    @Reference
    private ResourceResolverFactory resourceResolverFactory;
   
    public void setResourceResolverFactory(ResourceResolverFactory resourceResolverFactory) {
		this.resourceResolverFactory = resourceResolverFactory;
	}
   
    public void setReplicator(Replicator replicator) {
		this.replicator = replicator;
	}
   
    private static Logger LOG = LoggerFactory.getLogger(ContactDetailUitiliyService.class);

    private static final String DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSSXXXXX";

    public boolean isError(int status) {
	if (status == 200 || status == 201 || status == 204 || status == 400 || status == 401) {
	    return false;
	} else {
	    return true;
	}
    }

    public ZonedDateTime getCentralTime(String datestring_from_service) {
	ZonedDateTime cstZoned = null;
	DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern(DATE_FORMAT);
	ZonedDateTime zdtInstanceAtOffset = ZonedDateTime.parse(datestring_from_service, DATE_TIME_FORMATTER);
	cstZoned = zdtInstanceAtOffset.withZoneSameInstant(Constant.CENTRAL_ZONE_ID);
	return cstZoned;
    }

    public String getTimeIntervalString(String dateInString) {
	String timeInterval = null;
	String minutesString = "00";
	if (Objects.nonNull(dateInString)) {
	    ZonedDateTime centralZonedDateTime = this.getCentralTime(dateInString);
	    if (Objects.nonNull(centralZonedDateTime)) {
		int hours = centralZonedDateTime.getHour();
		int minutes = centralZonedDateTime.getMinute();
		if (minutes == 0) {
		    minutesString = "00";
		} else {
		    minutesString = "" + minutes;
		}
		timeInterval = hours + ":" + minutesString;
	    }
	}
	LOG.debug("Minutes String is {} ",minutesString);
	return timeInterval;
    }

    public String getDayOfWeekFromCSTString(String dateInString) {
	String dow = null;
	if (Objects.nonNull(dateInString)) {
	    ZonedDateTime centralZonedDateTime = this.getCentralTime(dateInString);
	    if (Objects.nonNull(centralZonedDateTime)) {
		DayOfWeek dowObj = centralZonedDateTime.getDayOfWeek();
		dow = dowObj.toString();

	    }
	}
	return dow;

    }

    public void generalExceptionHandling(int status) throws ServiceException {
	throw new ServiceException(Integer.toString(status), "Generic Error Ocuured");
    }

    public void generalExceptionHandling(int status, String message) throws ServiceException {
	throw new ServiceException(Integer.toString(status), message);

    }
    
    public void replicateAsset(String assetPath) {
	
	 Map<String, Object> map = new HashMap<>();
	
	 map.put(ResourceResolverFactory.SUBSERVICE, "vcmservice");
	 
	 Session  session = null;
	 
	 if (resourceResolverFactory != null) {
	 
	     try (ResourceResolver resourceResolver = resourceResolverFactory.getServiceResourceResolver(map)) {
		
		 session = resourceResolver.adaptTo(Session.class);
	         
		 if(Objects.nonNull(session) && session.isLive()) {
	         
		     replicator.replicate(session, ReplicationActionType.ACTIVATE, assetPath);
	          }
	    }
	    
	     catch (LoginException | ReplicationException e) {
		
		 LOG.error("Exception Occured during replication {}", e);
	    }
	     finally {
		 
		 if(Objects.nonNull(session) && session.isLive()) {
		 
		     session.logout();
		 }
	     }
	 }
    }

}
