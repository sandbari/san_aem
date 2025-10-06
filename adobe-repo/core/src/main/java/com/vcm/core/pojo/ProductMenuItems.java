package com.vcm.core.pojo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.day.cq.tagging.Tag;

public class ProductMenuItems {
	String tabTitle;

	String tabLink;

	String parentPagePath;

	String buttonText;

	String buttonLink;

	List<Tag> tagList;

	public void setTabTitle(String tabTitle) {
		this.tabTitle = tabTitle;
	}

	public List<Tag> getTagList() {
        List<Tag> copyTagList = tagList;
        Collections.copy(copyTagList, tagList);
        return copyTagList;
	}

	public void setTagList(List<Tag> tagList) {
		List<Tag> copyTagList = new ArrayList<>();
		copyTagList.addAll(tagList);
		this.tagList = copyTagList;
	}

	public void setTabLink(String tabLink) {
		this.tabLink = tabLink;
	}

	public void setParentPagePath(String parentPagePath) {
		this.parentPagePath = parentPagePath;
	}
	
	public void setButtonText(String buttonText) {
		this.buttonText = buttonText;
	}

	public void setButtonLink(String buttonLink) {
		this.buttonLink = buttonLink;
	}

	public String getTabTitle() {
		return tabTitle;
	}

	public String getTabLink() {
		return tabLink;
	}

	public String getParentPagePath() {
		return parentPagePath;
	}

	public String getButtonText() {
		return buttonText;
	}

	public String getButtonLink() {
		return buttonLink;
	}

}
