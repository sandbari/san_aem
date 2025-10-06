package com.vcm.core.contactuschart.exceptions;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.apache.sling.testing.mock.sling.ResourceResolverType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.wcm.testing.mock.aem.junit5.AemContext;

public class ServiceExceptionTest {
	private ServiceException serviceException;
    public final AemContext context = new AemContext(ResourceResolverType.JCR_OAK);

    @BeforeEach
    public void setup() throws Exception {
    	Throwable cause = null;
    	serviceException = new ServiceException("errorKey", "errorMessage");
    	serviceException = new ServiceException("errorKey", "errorMessage", cause);
    }
    
    @Test
    public void testGetterSetters() throws Exception {

    	assertNotNull(serviceException.getErrorKey());
    	assertNotNull(serviceException.getErrorMessage());
    }

}
