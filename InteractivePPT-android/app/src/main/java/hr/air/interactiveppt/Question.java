package hr.air.interactiveppt;

import java.util.ArrayList;

/**
 * Created by marin on 9.11.2016..
 */
public class Question {
    public int questionId;
    public String questionText;
    public int questionType;
    public ArrayList<Answer> answers = new ArrayList<>();

    public Question() {
    }

    public Question(int id, String questionText, int questionType) {
        this.questionId = id;
        this.questionText = questionText;
        this.questionType = questionType;
    }


    public Question(Question question) {
        this.questionText = question.getQuestionText();
        this.questionType = question.getQuestionType();
        this.answers = question.getAnswers();
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

    public ArrayList<Answer> getAnswers() {
        return answers;
    }

    public void setAnswers(Answer a) {
        answers.add(a);
    }


}
