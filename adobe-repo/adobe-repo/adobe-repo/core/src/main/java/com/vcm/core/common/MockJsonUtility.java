package com.vcm.core.common;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MockJsonUtility {

	private transient static Logger LOG = LoggerFactory.getLogger(MockJsonUtility.class);

	public static String getJsonData(String filename) {
		String content = "";
		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		URL resource = classLoader.getResource(filename);
		if (resource != null) {
			File file = new File(resource.getFile());

			if (file != null && file.exists()) {
				try {
					//content = new String(Files.readAllBytes(file.toPath()));
					content = new String(Files.readAllBytes(Paths.get(resource.toURI())), StandardCharsets.UTF_8);
				} catch (IOException | URISyntaxException e) {
					LOG.error("error while getting json data {}", e);
				}
			}
		}
		return content;

	}

	public static String getRemoveScriptTagStr(String text) {
		LOG.info("Remove script tag method(): ");
	  	return text.replaceAll("(?s)&lt;script.*?(/&gt;|&lt;/script&gt;)", "");
	}

}
