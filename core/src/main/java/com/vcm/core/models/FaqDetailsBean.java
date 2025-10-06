package com.vcm.core.models;

import java.util.*;
import java.util.List;
import java.util.Collections;

public class FaqDetailsBean {

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	private String groupName;
	
	private List<FaqDetailsCFBean> faqList;

	public List<FaqDetailsCFBean> getFaqList() {
		List<FaqDetailsCFBean> copyFaqList = faqList;
		Collections.copy(copyFaqList, faqList);
		return copyFaqList;
	}

	public void setFaqList(List<FaqDetailsCFBean> faqList) {
		List<FaqDetailsCFBean> copyFaqList = new ArrayList<FaqDetailsCFBean>();
		copyFaqList.addAll(faqList);
		this.faqList = copyFaqList;
	}
	
}
