package com.vcm.core.models;

import org.apache.sling.testing.mock.sling.ResourceResolverType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import io.wcm.testing.mock.aem.junit5.AemContext;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;
import junitx.framework.Assert;

@ExtendWith(AemContextExtension.class)
class SearchOverlayModelTest {

    private SearchOverlayModel searchOverlayModel;
    public final AemContext context = new AemContext(ResourceResolverType.JCR_OAK);

    @BeforeEach
    public void setup() throws Exception {
        searchOverlayModel=new SearchOverlayModel();
        searchOverlayModel.setClearsearchlabel("clearsearchlabel");
        searchOverlayModel.setClosebottontext("closebottontext");
        searchOverlayModel.setDisplaySearchBar(false);
        searchOverlayModel.setDisplaySearchOverlay(false);
        searchOverlayModel.setSearchLabel("helptext");
        searchOverlayModel.setIndexUrl("index");
        searchOverlayModel.setRecentsearchlabel("recentsearchlabel");
        searchOverlayModel.setSearchResultsPage("searchResultsPage");
        searchOverlayModel.setSearchText("searchText");
        searchOverlayModel.setSuggestionsLimit("suggestionsLimit");
        searchOverlayModel.setSuggestionsPath("test");
        searchOverlayModel.setResourceResolver(context.resourceResolver());
        searchOverlayModel.setVcmLogoImage("/content/dam/vcm");
    }
    

    @Test
    void testSearchModel() throws Exception {
    	Assert.assertEquals("clearsearchlabel",searchOverlayModel.getClearsearchlabel() );
    	Assert.assertEquals("closebottontext", searchOverlayModel.getClosebottontext());
    	Assert.assertEquals(false,searchOverlayModel.isDisplaySearchBar() );
    	Assert.assertEquals(false, searchOverlayModel.isDisplaySearchOverlay());
    	Assert.assertEquals("helptext", searchOverlayModel.getSearchLabel());
    	Assert.assertEquals("recentsearchlabel", searchOverlayModel.getRecentsearchlabel() );
    	Assert.assertEquals("searchResultsPage",searchOverlayModel.getSearchResultsPage() );
    	Assert.assertEquals("searchText", searchOverlayModel.getSearchText());
    	Assert.assertEquals("suggestionsLimit",searchOverlayModel.getSuggestionsLimit() );
    	Assert.assertEquals("test", searchOverlayModel.getSuggestionsPath());
    	Assert.assertEquals("/content/dam/vcm", searchOverlayModel.getVcmLogoImage());
    	Assert.assertNotNull(searchOverlayModel.getIndexUrl());
    }

}