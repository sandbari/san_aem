package com.vcm.core.models;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.testing.mock.sling.ResourceResolverType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.osgi.resource.Resource;

import com.day.cq.tagging.Tag;
import com.day.cq.tagging.TagManager;

import io.wcm.testing.mock.aem.junit5.AemContext;

@ExtendWith(MockitoExtension.class)
public class MoreTopicsModelTest {
	@Mock
	private TagManager tagManager;
	@Mock
	private Tag mockTag;
	@Mock
	private ResourceResolver mockResourceResolver;
	@Mock
	private Resource resource;
	@InjectMocks
	private MoreTopicsModel moreTopicTest;
	public final AemContext context = new AemContext(ResourceResolverType.JCR_OAK);
	private String[] topics = { "topic1" };
	private String topic = "topic1";

	@BeforeEach
	public void initMocks() {
		MockitoAnnotations.initMocks(this);
	}

	@BeforeEach
	public void setup() throws Exception {
		when(mockResourceResolver.adaptTo(TagManager.class)).thenReturn(tagManager);
		when(tagManager.resolve(topic)).thenReturn(mockTag);
		moreTopicTest.setBlogListPath("/blog/bloglist");
		moreTopicTest.setTopics(topics);
		moreTopicTest.setHeading("Heading");
		moreTopicTest.setResourceResolver(mockResourceResolver);
	}

	@Test
	public void testGetterSetters() throws Exception {
		moreTopicTest.init();
		assertNotNull(moreTopicTest.getBlogListPath());
		assertNotNull(moreTopicTest.getHeading());
		assertNotNull(moreTopicTest.getResourceResolver());
		assertNotNull(moreTopicTest.getTopicList());
		assertNotNull(moreTopicTest.getTopics());
	}
}
