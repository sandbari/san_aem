package com.vcm.core.models;

import static org.junit.jupiter.api.Assertions.*;

import org.apache.sling.testing.mock.sling.ResourceResolverType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import io.wcm.testing.mock.aem.junit5.AemContext;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;

@ExtendWith(AemContextExtension.class)

public class VideoModelTest {
    private VideoModel videoTest;
    public final AemContext context = new AemContext(ResourceResolverType.JCR_OAK);

    @BeforeEach
    public void setup() throws Exception {
        videoTest = new VideoModel();
        context.load().json("/videoModel.json", "/content/vcm/en/video");
    }

    @Test
    public void testGetterSetters() throws Exception {
        videoTest.setVideoUrl("Leader Image");
        videoTest.setVideoDuration("20");
        videoTest.setYoutubeVideoId("70bqoVZQdcY");
        videoTest.setYoutubeWidth("789");
        videoTest.setYoutubeHeight("446");
        videoTest.setHeading("A message from CEO");
        videoTest.setDescription("A video test measures this adaptive bitrate to tell you the maximum resolution, load time and buffer you should be able to expect given current network conditions. ");
        videoTest.setVideoTitle("A message from CEO");
        videoTest.setTranscriptText("watch now");
        videoTest.setTranscriptDescription("A video test measures this adaptive bitrate to tell you the maximum resolution, load time and buffer you should be able to expect given current network conditions. ");

        assertNotNull(videoTest.getVideoUrl());
        assertNotNull(videoTest.getVideoDuration());
        assertNotNull(videoTest.getYoutubeVideoId());
        assertNotNull(videoTest.getYoutubeWidth());
        assertNotNull(videoTest.getYoutubeHeight());
        assertNotNull(videoTest.getHeading());
        assertNotNull(videoTest.getDescription());
        assertNotNull(videoTest.getVideoTitle());
        assertNotNull(videoTest.getTranscriptText());
        assertNotNull(videoTest.getTranscriptDescription());

    }

}