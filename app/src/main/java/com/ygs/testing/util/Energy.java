package com.ygs.testing.util;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
/*pojo for sending data to serv*/
public class Energy {
    @SerializedName("Status")
    @Expose
    private Integer status;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
