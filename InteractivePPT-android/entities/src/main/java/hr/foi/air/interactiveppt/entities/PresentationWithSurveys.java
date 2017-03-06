package hr.foi.air.interactiveppt.entities;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by zeko868 on 31.12.2016..
 */

public class PresentationWithSurveys {

    @SerializedName("id")
    public int id;

    @SerializedName("path")
    public String path;

    @SerializedName("surveys")
    public List<Survey> surveys;

    @SerializedName("author_name")
    public String authorName;

    public String getPresentationName() {
        return path.substring(path.indexOf('-') + 1, path.lastIndexOf('.'));
    }
}
