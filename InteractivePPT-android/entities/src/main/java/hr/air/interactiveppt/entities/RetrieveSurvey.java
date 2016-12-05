package hr.air.interactiveppt.entities;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Smrad on 2.12.2016..
 */

public class RetrieveSurvey {

        @SerializedName("name")
        public String name;

        @SerializedName("description")
        public String description;

        @SerializedName("link_to_presentation")
        public String link;

        @SerializedName("questions")
        public List<Question> questions;

        public RetrieveSurvey(String name, String description, String link, List<Question> questions) {
            this.name = name;
            this.description = description;
            this.link = link;
            this.questions = questions;
        }
}
