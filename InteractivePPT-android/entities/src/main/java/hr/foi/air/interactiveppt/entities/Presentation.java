package hr.foi.air.interactiveppt.entities;

import com.google.gson.annotations.SerializedName;

/**
 * Created by zeko868 on 15.11.2016..
 */

public class Presentation {

    @SerializedName("num_of_surveys")
    public int numOfSurveys;

    @SerializedName("access_code")
    public String accessCode;

    @SerializedName("path")
    public String path;

    @SerializedName("author_name")
    public String authorName;

    public String getPresentationName() {
        return path.substring(path.indexOf('-') + 1, path.lastIndexOf('.'));
    }
}
