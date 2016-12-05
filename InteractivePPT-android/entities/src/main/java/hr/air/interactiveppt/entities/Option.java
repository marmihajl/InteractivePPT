package hr.air.interactiveppt.entities;

import com.google.gson.annotations.SerializedName;

/**
 * Created by marin on 9.11.2016..
 */

public class Option {

    @SerializedName("name")
    public String optionText;

    @SerializedName("id_option")
    public int id;

    public Option() {
    }

    public Option(int id, String name){
        this.id = id;
        this.optionText = name;
    }

    public int getId(){return id;}

    public void setId(int id){this.id = id;}

    public String getOptionText() {
        return optionText;
    }

    public void setOptionText(String optionText) {
        this.optionText = optionText;
    }

}
