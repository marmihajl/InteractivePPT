package hr.air.interactiveppt.entities;

import com.google.gson.annotations.SerializedName;

/**
 * Created by zeko868 on 15.11.2016..
 */

public class Presentation {

    @SerializedName("num_of_surveys")
    public int numOfSurveys;

    @SerializedName("access_code")
    public String accessCode;

    @SerializedName("presentation_name")
    public String presentationName;

    @SerializedName("author_name")
    public String authorName;
}
