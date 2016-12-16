package hr.air.interactiveppt.entities;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by Smrad on 6.12.2016..
 */

public class ListOfAnswers {

    @SerializedName("data")
    public ArrayList<Answer> answers;

    @SerializedName("app_uid")
    public String idUser;

    public ListOfAnswers(ArrayList<Answer> answers, String idUser){
        this.answers = answers;
        this.idUser = idUser;
    }
}
