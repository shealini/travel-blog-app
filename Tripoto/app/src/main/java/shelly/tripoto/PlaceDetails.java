package shelly.tripoto;

import com.google.gson.annotations.SerializedName;

/**
 * Created by shelly on 11/12/15.
 */
public class PlaceDetails {

    @SerializedName("user_name")
    protected String userName;
    @SerializedName("trip_name")
    protected String title;
    @SerializedName("trip_image_url")
    protected String tripImageurl;
    @SerializedName("user_image_url")
    protected String userImageurl;


    public PlaceDetails(String userName, String title, String tripImageurl, String userImageurl) {
        this.userName = userName;
        this.title = title;
        this.tripImageurl = tripImageurl;
        this.userImageurl = userImageurl;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTripImageurl() {
        return tripImageurl;
    }

    public void setTripImageurl(String tripImageurl) {
        this.tripImageurl = tripImageurl;
    }

    public String getUserImageurl() {
        return userImageurl;
    }

    public void setUserImageurl(String userImageurl) {
        this.userImageurl = userImageurl;
    }
}
