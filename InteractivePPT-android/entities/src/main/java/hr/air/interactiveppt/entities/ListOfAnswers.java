package hr.air.interactiveppt.entities;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by Smrad on 6.12.2016..
 */

public class ListOfAnswers {

    @SerializedName("data")
    public ArrayList<Answer> answers;

    public ListOfAnswers(ArrayList<Answer> answers){
        this.answers = answers;
    }
}
