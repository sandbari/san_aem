package com.vcm.core.models;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.osgi.framework.InvalidSyntaxException;
import junitx.util.PrivateAccessor;

@ExtendWith(MockitoExtension.class)
public class FaqDetailsBeanTest {
	@InjectMocks
	private FaqDetailsBean faqDetailsBean;
	@Mock
	private FaqDetailsCFBean faqDetailsCFBean;
	private String groupName = "Group Name";

	@BeforeEach
	public void initMocks() throws NoSuchFieldException, InvalidSyntaxException {
		MockitoAnnotations.initMocks(this);
		wireMocks();
	}

	private void wireMocks() throws InvalidSyntaxException, NoSuchFieldException {
		PrivateAccessor.setField(faqDetailsBean, "groupName", groupName);

	}

	@Test
	public void testGroupName() {
		faqDetailsBean.setGroupName(groupName);
		assertEquals("Group Name", faqDetailsBean.getGroupName());
	}

	@Test
	public void testGetFaqList() {
		List<FaqDetailsCFBean> copyFaqList = new ArrayList<FaqDetailsCFBean>();
		faqDetailsBean.setFaqList(copyFaqList);
		assertNotNull(faqDetailsBean.getFaqList());
	}
}