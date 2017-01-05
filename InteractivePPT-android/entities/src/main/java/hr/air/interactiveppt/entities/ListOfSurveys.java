package hr.air.interactiveppt.entities;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by zeko868 on 5.1.2017..
 */

public class ListOfSurveys {

    @SerializedName("surveys")
    public ArrayList<Survey> surveys;
}
