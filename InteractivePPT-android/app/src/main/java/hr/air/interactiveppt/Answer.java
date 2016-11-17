package hr.air.interactiveppt;

import com.google.gson.annotations.SerializedName;

/**
 * Created by marin on 9.11.2016..
 */

public class Answer {

    @SerializedName("text")
    public String answerText;

    public int questionId;

    public Answer() {
    }

    public String getAnswerText() {
        return answerText;
    }

    public void setAnswerText(String answerText) {
        this.answerText = answerText;
    }

    public int getQuestionId() {
        return questionId;
    }

    public void setQuestionId(int questionId) {
        this.questionId = questionId;
    }

}
