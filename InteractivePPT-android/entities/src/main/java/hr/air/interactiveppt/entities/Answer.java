package hr.air.interactiveppt.entities;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Smrad on 5.12.2016..
 */

public class Answer {

    @SerializedName("id")
    public int idUser;

    @SerializedName("id_option")
    public int idOption;

    public Answer(){}

    public Answer(int idUser, int idOption){
        this.idUser=idUser;
        this.idOption=idOption;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public int getIdOption() {
        return idOption;
    }

    public void setIdOption(int idOption) {
        this.idOption = idOption;
    }
}
