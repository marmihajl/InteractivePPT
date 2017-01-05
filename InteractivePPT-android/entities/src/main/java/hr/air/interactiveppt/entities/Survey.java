package hr.air.interactiveppt.entities;

import com.google.gson.annotations.SerializedName;

/**
 * Created by zeko868 on 31.12.2016..
 */

public class Survey {

    @SerializedName("id")
    public int id;

    @SerializedName("name")
    public String name;

    @SerializedName("description")
    public String description;

    @SerializedName("num_of_questions")
    public int numOfQuestions;
}
