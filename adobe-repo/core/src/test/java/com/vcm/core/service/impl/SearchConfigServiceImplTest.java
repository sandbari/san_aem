package com.vcm.core.service.impl;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;

import org.apache.sling.testing.mock.sling.ResourceResolverType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import io.wcm.testing.mock.aem.junit5.AemContext;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;

@ExtendWith(MockitoExtension.class)
@ExtendWith(AemContextExtension.class)
class SearchConfigServiceImplTest {
	
    public final AemContext context = new AemContext(ResourceResolverType.JCR_OAK);
    
    private SearchConfigServiceImpl searchConfigServiceImpl;

    @BeforeEach
    void setup() throws Exception {
    	searchConfigServiceImpl = new SearchConfigServiceImpl();
    	
    	SearchConfigServiceImpl.Config config = mock(SearchConfigServiceImpl.Config.class);
    	searchConfigServiceImpl.activate(config);
    }

    @Test
    public void testSearchConfigServiceImpl() {
    	
    	searchConfigServiceImpl.setSearchRootPath("/content/vcm/us/en");
    	searchConfigServiceImpl.setSuggestFileIndexPath("/content/dam/vcm/configs/suggestionIndexFiles/");
    	
    	assertNotNull(searchConfigServiceImpl.getSearchRootPath());
    	assertNotNull(searchConfigServiceImpl.getSuggestFileIndexPath());
    }
}
