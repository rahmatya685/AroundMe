
package cloudgis.mihanblog.com.aroundme.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BeenHere {

    @SerializedName("count")
    @Expose
    private Integer count;
    @SerializedName("lastCheckinExpiredAt")
    @Expose
    private Integer lastCheckinExpiredAt;
    @SerializedName("marked")
    @Expose
    private Boolean marked;
    @SerializedName("unconfirmedCount")
    @Expose
    private Integer unconfirmedCount;

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Integer getLastCheckinExpiredAt() {
        return lastCheckinExpiredAt;
    }

    public void setLastCheckinExpiredAt(Integer lastCheckinExpiredAt) {
        this.lastCheckinExpiredAt = lastCheckinExpiredAt;
    }

    public Boolean getMarked() {
        return marked;
    }

    public void setMarked(Boolean marked) {
        this.marked = marked;
    }

    public Integer getUnconfirmedCount() {
        return unconfirmedCount;
    }

    public void setUnconfirmedCount(Integer unconfirmedCount) {
        this.unconfirmedCount = unconfirmedCount;
    }

}
