package com.vcm.core.utils;

import com.day.cq.commons.jcr.JcrConstants;
import com.google.common.collect.Iterators;
import org.apache.sling.api.resource.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.ArrayList;
import java.util.Iterator;

public class ComponentProperties {

    private static final Logger log = LoggerFactory.getLogger(ComponentProperties.class);

    private String RESPONSIVEGRID = "jcr:content/root/responsivegrid/";

    ArrayList<Resource> component= new ArrayList<Resource>();

    public ArrayList<Resource> getProperties(Resource root, String componentName){
        if(root.hasChildren()) {
            Iterable<Resource> resources = root.getChildren();
            for (Resource resource : resources) {
                if (!resource.getName().equalsIgnoreCase(JcrConstants.JCR_CONTENT) && resource.getChild(RESPONSIVEGRID).hasChildren()) {
                    Iterable<Resource> componentResources = resource.getChild(RESPONSIVEGRID).getChildren();
                    componentAddition(componentResources, componentName);
					Iterator<Resource> resourceIterators = resource.listChildren();
					int sizes = Iterators.size(resourceIterators);
					if(sizes > 1){
						getProperties(resource, componentName);
					}
                }                
            }
        }
        return component;
    }

    public void componentAddition(Iterable<Resource> componentResources, String componentName){
        for (Resource componentResource : componentResources) {
            if (componentResource.isResourceType(componentName)) {
                log.debug("Resource Type is added : {}" , componentResource.getPath());
                component.add(componentResource);
            }
        }
    }
}
