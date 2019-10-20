package com.ygs.testing.util;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/*pojo for sending data to serv*/
public class Energy implements java.io.Serializable  {
    @SerializedName("status")
    @Expose
    private Integer status;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Energy(){}
    public Energy(Integer status){
        this.status = status;
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        if(obj instanceof Energy){
            Energy energy = (Energy) obj;
            return energy.getStatus().equals(status);
        }
        return false;
    }

    @NonNull
    @Override
    public String toString() {
        return "status: "+status;
    }
}
