package hr.air.interactiveppt.entities;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Smrad on 5.12.2016..
 */

public class Answer {

    @SerializedName("id_question")
    public int idQuest;

    @SerializedName("id")
    public String idUser;

    @SerializedName("id_option")
    public int idOption;

    public Answer(){}

    public Answer(int idQuest, String idUser, int idOption){
        this.idQuest=idQuest;
        this.idUser=idUser;
        this.idOption=idOption;
    }

    public int getIdQuest() {
        return idQuest;
    }

    public void setIdQuest(int idQuest) {
        this.idQuest = idQuest;
    }

    public String getIdUser() {
        return idUser;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }

    public int getIdOption() {
        return idOption;
    }

    public void setIdOption(int idOption) {
        this.idOption = idOption;
    }

}
