package hr.air.interactiveppt.webservice;

import java.util.ArrayList;

import hr.air.interactiveppt.entities.Answer;
import hr.air.interactiveppt.entities.ListOfAnswers;
import hr.air.interactiveppt.entities.RetrieveSurvey;
import hr.air.interactiveppt.entities.responses.Survey;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

/**
 * Created by zeko868 on 26.11.2016..
 */

public interface WebService {
    @Multipart
    @POST("interactivePPT-server.php")
    Call<Boolean> createSurvey(
            @Part MultipartBody.Part file,
            @Part("json") RequestBody json
    );

    @FormUrlEncoded
    @POST("interactivePPT-server.php")
    Call<Survey> getDetails(
            @Field("access_code") String accessCode,
            @Field("request_type") String requestType
    );

    @FormUrlEncoded
    @POST("interactivePPT-server.php")
    Call<Boolean> registerUser(
            @Field("request_type") String requestType,
            @Field("app_uid") String appUid,
            @Field("name") String name
    );

    @FormUrlEncoded
    @POST("interactivePPT-server.php")
    Call<RetrieveSurvey> getSurvey(
            @Field("access_code") String accessCode,
            @Field("request_type") String requestType
    );

    @FormUrlEncoded
    @POST("interactivePPT-server.php")
    Call<Boolean> sendAnswersToServer(
            @Field("answers") ListOfAnswers answers,
            @Field("request_type") String requestType
    );
}