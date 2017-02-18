package hr.foi.air.interactiveppt.questiontype;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;

import java.lang.reflect.Constructor;
import java.util.ArrayList;

import hr.foi.air.interactiveppt.questiontype.R;

/**
 * Created by zeko868 on 31.1.2017..
 */
public class ReflectionQtHelper {
    private static ReflectionQtHelper ourInstance = new ReflectionQtHelper();

    public static ReflectionQtHelper getInstance() {
        return ourInstance;
    }

    private ReflectionQtHelper() {
    }

    public int getModeOfQuestionType(int questionTypeCode, Activity activity) {
        int questionTypeMode = 0;
        try {
            questionTypeMode = Class.forName("hr.foi.air.interactiveppt.questiontype." + activity.getResources().getStringArray(R.array.questionTypesClassNames)[questionTypeCode-1]).getField("mode").getInt(null);
        }
        catch (Exception e) {
            LogErrorMessageAndKillApp(activity);
        }
        return questionTypeMode;
    }

    public QuestionTypeControl instatiateControl(int questionTypeCode, Activity activity, Object ... objects) {
        QuestionTypeControl qtInstance = null;
        try {
            Class<?> cl = Class.forName("hr.foi.air.interactiveppt.questiontype." + activity.getResources().getStringArray(R.array.questionTypesClassNames)[questionTypeCode-1]);
            Constructor<?> cons;
            switch (objects.length) {
                case 1:
                    cons = cl.getConstructor(Context.class, ArrayList.class);
                    qtInstance = (QuestionTypeControl) cons.newInstance(activity, objects[0]);
                    break;
                case 2:
                    cons = cl.getConstructor(Context.class, AttributeSet.class, ArrayList.class);
                    qtInstance = (QuestionTypeControl) cons.newInstance(activity, objects[0], objects[1]);
                    break;
                case 3:
                    cons = cl.getConstructor(Context.class, AttributeSet.class, int.class, ArrayList.class);
                    qtInstance = (QuestionTypeControl) cons.newInstance(activity, objects[0], objects[1], objects[2]);
                    break;
                case 4:
                    cons = cl.getConstructor(Context.class, AttributeSet.class, int.class, int.class, ArrayList.class);
                    qtInstance = (QuestionTypeControl) cons.newInstance(activity, objects[0], objects[1], objects[2], objects[3]);
                    break;
            }
        }
        catch (Exception e) {
            LogErrorMessageAndKillApp(activity);
        }
        return qtInstance;
    }

    private void LogErrorMessageAndKillApp(Activity activity) {
        Log.d("INTERACTIVEPPT", "Ako se ovo ispiše, developer aplikacije ili održavatelj BP-a je zeznul kod rada s tipovima pitanja");
        activity.finish();
        System.exit(0);
    }
}
