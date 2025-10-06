package com.vcm.core.models;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;

@ExtendWith(AemContextExtension.class)
class FaqListContentModelTest {
	
	private FaqListContentModel faq;

	@BeforeEach
	void setUp() throws Exception {
		faq= new FaqListContentModel();
		faq.setAnswer("Test answer");
		faq.setQuestion("Test question");
	}

	@Test
	void test() {
		assertNotNull(faq.getAnswer());
		assertNotNull(faq.getQuestion());
	}

}
