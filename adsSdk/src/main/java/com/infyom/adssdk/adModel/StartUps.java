package com.infyom.adssdk.adModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class StartUps {
    @SerializedName("app_id")
    @Expose
    private String appId;

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }
}
