package hr.air.interactiveppt.entities;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by zeko868 on 15.11.2016..
 */

public class SurveyWithQuestions {
    @SerializedName("name")
    public String name;

    @SerializedName("description")
    public String description;

    @SerializedName("questions")
    public List<Question> questions;

    @SerializedName("author")
    public String authorId;

    @SerializedName("access_code")
    public String accessCode;

    public SurveyWithQuestions(String name, String description, List<Question> questions, String authorId, String accessCodeIfExistingPpt) {
        this.name = name;
        this.description = description;
        this.questions = questions;
        this.authorId = authorId;
        this.accessCode = accessCodeIfExistingPpt;
    }
}
