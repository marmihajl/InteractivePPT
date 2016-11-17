package hr.air.interactiveppt;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by zeko868 on 15.11.2016..
 */

public class SurveyWithQuestions {
    @SerializedName("request_type")
    public String requestType;

    @SerializedName("title")
    public String name;

    @SerializedName("description")
    public String description;

    @SerializedName("questions")
    public List<Question> questions;

    @SerializedName("author")
    public String authorId;

    public SurveyWithQuestions(String name, String description, List<Question> questions, String authorId) {
        this.requestType = "create_survey";
        this.name = name;
        this.description = description;
        this.questions = questions;
        this.authorId = authorId;
    }
}
