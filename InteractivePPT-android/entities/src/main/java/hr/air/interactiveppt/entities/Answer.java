package hr.air.interactiveppt.entities;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Smrad on 5.12.2016..
 */

public class Answer {

    @SerializedName("id_question")
    public int idQuest;

    @SerializedName("option_name")
    public String optionName;

    public Answer(){}

    public Answer(int idQuest, String optionName){
        this.idQuest=idQuest;
        this.optionName=optionName;
    }

    public int getIdQuest() {
        return idQuest;
    }

    public void setIdQuest(int idQuest) {
        this.idQuest = idQuest;
    }

    public String getOptionName() {
        return optionName;
    }

    public void getOptionName(String optionName) {
        this.optionName = optionName;
    }

}
