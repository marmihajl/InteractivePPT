package hr.foi.air.interactiveppt.entities;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by marin on 9.11.2016..
 */
public class Question {
    @SerializedName("id")
    public int questionId;

    @SerializedName("name")
    public String questionText;

    @SerializedName("type")
    public int questionType;

    @SerializedName("required_answer")
    public int requiredAnswer;

    @SerializedName("options")
    public ArrayList<Option> options = new ArrayList<>();

    public Question() {
    }

    public Question(Question question) {
        this.questionText = question.getQuestionText();
        this.questionType = question.getQuestionType();
        this.options = question.getOptions();
        this.requiredAnswer = question.getRequiredAnswer();
    }

    public Question(int id, String questionText, int questionType, int requiredAnswer) {
        this.questionId = id;
        this.questionText = questionText;
        this.questionType = questionType;
        this.requiredAnswer = requiredAnswer;
    }

    public int getQuestionId() {
        return questionId;
    }

    public void setQuestionId(int questionId) {
        this.questionId = questionId;
    }

    public String getQuestionText() {
        return questionText;
    }

    public void setQuestionText(String questionText) {
        this.questionText = questionText;
    }

    public int getQuestionType() {
        return questionType;
    }

    public void setQuestionType(int questionType) {
        this.questionType = questionType;
    }

    public ArrayList<Option> getOptions() {
        return options;
    }

    public void setOptions(Option o) {
        options.add(o);
    }

    public void setRequiredAnswer(int requiredAnswer){this.requiredAnswer = requiredAnswer;}

    public int getRequiredAnswer(){return requiredAnswer;}

}
