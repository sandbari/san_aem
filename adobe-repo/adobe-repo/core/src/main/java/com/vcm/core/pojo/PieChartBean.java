package com.vcm.core.pojo;

import java.util.List;
import com.google.gson.annotations.Expose;

public class PieChartBean {	
	
    @Expose
    List<DataBean> data;

	@Expose
    String title;
	
	@Expose
    String age;

    @Expose
    String description;
    
    @Expose
    String viewData;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAge() {
		return age;
	}

	public void setAge(String age) {
		this.age = age;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	public List<DataBean> getData() {
		return data;
	}

	public void setData(List<DataBean> data) {
		this.data = data;
	}
	
	public String getViewData() {
		return viewData;
	}

	public void setViewData(String viewData) {
		this.viewData = viewData;
	}
}
