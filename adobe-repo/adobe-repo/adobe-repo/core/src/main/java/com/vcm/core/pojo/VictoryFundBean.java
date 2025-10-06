package com.vcm.core.pojo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class VictoryFundBean {
    private List<ServiceBean> service;
    private List<AccountApplicationBean> accountApplication;

    public VictoryFundBean(){
        this.service = new ArrayList<>();
        this.accountApplication = new ArrayList<>();
    }

    public List<ServiceBean> getService() {
        List<ServiceBean> copyServiceList = service;
        Collections.copy(copyServiceList, service);
        return copyServiceList;
    }

    public void setService(List<ServiceBean> service) {
        List<ServiceBean> copyServiceList = new ArrayList<>();
        copyServiceList.addAll(service);
        this.service = copyServiceList;
    }

    public List<AccountApplicationBean> getAccountApplication() {
        List<AccountApplicationBean> copyAccountApplicationBeanList = accountApplication;
        Collections.copy(copyAccountApplicationBeanList, accountApplication);
        return copyAccountApplicationBeanList;
    }

    public void setAccountApplication(List<AccountApplicationBean> accountApplication) {
        List<AccountApplicationBean> copyAccountApplicationBeanList = new ArrayList<>();
        copyAccountApplicationBeanList.addAll(accountApplication);
        this.accountApplication = copyAccountApplicationBeanList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        VictoryFundBean that = (VictoryFundBean) o;
        return Objects.equals(service, that.service) && Objects.equals(accountApplication, that.accountApplication);
    }

    @Override
    public int hashCode() {
        return Objects.hash(service, accountApplication);
    }
}
