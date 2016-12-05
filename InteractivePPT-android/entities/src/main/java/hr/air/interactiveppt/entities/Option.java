package hr.air.interactiveppt.entities;

import com.google.gson.annotations.SerializedName;

/**
 * Created by marin on 9.11.2016..
 */

public class Option {

    @SerializedName("name")
    public String optionText;

    public Option() {
    }

    public String getOptionText() {
        return optionText;
    }

    public void setOptionText(String optionText) {
        this.optionText = optionText;
    }

}
