package hr.foi.air.interactiveppt.questiontype;

import java.util.ArrayList;

/**
 * Created by zeko868 on 29.1.2017..
 */

public interface QuestionTypeControl {
    ArrayList<String> getSelected();
    void leaveBlank();
    boolean isBlank();
}
