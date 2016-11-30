package hr.air.interactiveppt.entities;

import com.google.gson.annotations.SerializedName;

/**
 * Created by marin on 9.11.2016..
 */

public class Option {

    @SerializedName("text")
    public String answerText;

    public int questionId;

    public Option() {
    }

    public String getOptionText() {
        return answerText;
    }

    public void setOptionText(String answerText) {
        this.answerText = answerText;
    }

    public int getQuestionId() {
        return questionId;
    }

    public void setQuestionId(int questionId) {
        this.questionId = questionId;
    }

}
