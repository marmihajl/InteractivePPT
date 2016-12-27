package hr.air.interactiveppt.entities;

import com.google.gson.annotations.SerializedName;


/**
 * Created by marin on 27.12.2016..
 */

public class Presentations {
    @SerializedName("id")
    public int id;

    @SerializedName("path")
    public String path;

    @SerializedName("access_code")
    public String accessCode;
}
