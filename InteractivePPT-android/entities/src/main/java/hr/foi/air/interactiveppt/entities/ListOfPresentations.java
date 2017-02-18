package hr.foi.air.interactiveppt.entities;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by zeko868 on 28.12.2016..
 */

public class ListOfPresentations {

    @SerializedName("my_ppts")
    public ArrayList<Presentation> myPresentations;

    @SerializedName("subbed_ppts")
    public ArrayList<Presentation> subbedPresentations;
}
