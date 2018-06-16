
package ir.mihanblog.cloudgis.aroundme.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Stats {

    @SerializedName("tipCount")
    @Expose
    private Integer tipCount;
    @SerializedName("usersCount")
    @Expose
    private Integer usersCount;
    @SerializedName("checkinsCount")
    @Expose
    private Integer checkinsCount;
    @SerializedName("visitsCount")
    @Expose
    private Integer visitsCount;

    public Integer getTipCount() {
        return tipCount;
    }

    public void setTipCount(Integer tipCount) {
        this.tipCount = tipCount;
    }

    public Integer getUsersCount() {
        return usersCount;
    }

    public void setUsersCount(Integer usersCount) {
        this.usersCount = usersCount;
    }

    public Integer getCheckinsCount() {
        return checkinsCount;
    }

    public void setCheckinsCount(Integer checkinsCount) {
        this.checkinsCount = checkinsCount;
    }

    public Integer getVisitsCount() {
        return visitsCount;
    }

    public void setVisitsCount(Integer visitsCount) {
        this.visitsCount = visitsCount;
    }

}
