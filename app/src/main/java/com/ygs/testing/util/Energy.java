package com.ygs.testing.util;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
@author Ihor Yutsyk
pojo-class for sending data to  nasladdin server
 contain status of energy loses of user

 */
public class Energy implements java.io.Serializable  {
    @SerializedName("status")
    @Expose
    private Integer status;

    public Integer getStatus() {
        return status;
    }


    /**
     * @param status gets params from 0 to 1 for status
     * <p>
     * status =1 mean that energy losing was succeeded
     * </p>
     *  <p>
     *   status = 0 mean that energy losing was failed;
     * </p>
     *
     * */


    public void setStatus(@Nullable Integer status) throws NumberFormatException,NullPointerException {
        if(status>1||status<0) {
            throw new NumberFormatException("status is`nt in rage [0;1]");
        }
        this.status = status;

    }

    public Energy(){}


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
