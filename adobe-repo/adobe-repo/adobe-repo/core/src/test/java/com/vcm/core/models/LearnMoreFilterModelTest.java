package com.vcm.core.models;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.osgi.framework.InvalidSyntaxException;
import com.day.cq.wcm.api.Page;
import com.day.cq.wcm.api.PageManager;
import com.vcm.core.service.LearnMoreService;

@ExtendWith(MockitoExtension.class)
public class LearnMoreFilterModelTest {

	@InjectMocks
	public LearnMoreFilterModel learnMoreFilterModel;
	@Mock
	private LearnMoreService learnMoreService;

	@Mock
	private ResourceResolver resourceResolver;
	@Mock
	PageManager pageManager;
	@Mock
	Page currentPage;
	@Mock
	private Resource resource;
	private String defaultContent = "default content";

	@BeforeEach
	public void initMocks() {
		MockitoAnnotations.initMocks(this);
	}

	@BeforeEach
	public void setup() throws InvalidSyntaxException, NoSuchFieldException {
		when(resourceResolver.adaptTo(PageManager.class)).thenReturn(pageManager);
		when(pageManager.getContainingPage(resource)).thenReturn(currentPage);
		when(currentPage.getPath()).thenReturn("/content/path");
		when(learnMoreService.getDefaultContent("/content/path", resourceResolver)).thenReturn(defaultContent);
	}

	@Test
	void testInit() throws Exception {
		learnMoreFilterModel.init();
		assertEquals(defaultContent, learnMoreFilterModel.getDefaultContent());
	}
}