package com.vcm.core.pojo;

import java.util.Arrays;
import java.util.Objects;

public class AccountApplicationBean {

    private String pdfTitle;
    private String pdfDescription;
    private String[] tags;
    private String subCategory;
    private String category;

    private String fund;
    private String formID;
    private String pdfPath;

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getPdfTitle() {
        return pdfTitle;
    }

    public void setPdfTitle(String pdfTitle) {
        this.pdfTitle = pdfTitle;
    }

    public String getPdfDescription() {
        return pdfDescription;
    }

    public void setPdfDescription(String pdfDescription) {
        this.pdfDescription = pdfDescription;
    }

    public String getSubCategory() {
        return subCategory;
    }

    public void setSubCategory(String subCategory) {
        this.subCategory = subCategory;
    }

    public String getFormID() {
        return formID;
    }

    public void setFormID(String formID) {
        this.formID = formID;
    }

    public String getPdfPath() {
        return pdfPath;
    }

    public void setPdfPath(String pdfPath) {
        this.pdfPath = pdfPath;
    }

    public String[] getTags() {
        return tags.clone();
    }
    public void setTags(String[] tags) {
        String[] copyStringArr =  tags.clone();
        this.tags = copyStringArr;
    }

    public String getFund() {
        return fund;
    }

    public void setFund(String fund) {
        this.fund = fund;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AccountApplicationBean that = (AccountApplicationBean) o;
        return Objects.equals(pdfTitle, that.pdfTitle) && Objects.equals(pdfDescription, that.pdfDescription) && Arrays.equals(tags, that.tags) && Objects.equals(subCategory, that.subCategory) && Objects.equals(category, that.category) && Objects.equals(fund, that.fund) && Objects.equals(formID, that.formID) && Objects.equals(pdfPath, that.pdfPath);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(pdfTitle, pdfDescription, subCategory, category, fund, formID, pdfPath);
        result = 31 * result + Arrays.hashCode(tags);
        return result;
    }
}
