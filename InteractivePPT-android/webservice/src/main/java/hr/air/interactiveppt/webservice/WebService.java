package hr.air.interactiveppt.webservice;

import hr.air.interactiveppt.entities.PresentationWithSurveys;
import hr.air.interactiveppt.entities.SurveyWithQuestions;
import hr.air.interactiveppt.entities.ListOfPresentations;
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
    Call<ListOfPresentations> getPresentationList(
            @Field("uid") String uid,
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
    Call<SurveyWithQuestions> getSurvey(
            @Field("access_code") String accessCode,
            @Field("request_type") String requestType
    );

    @FormUrlEncoded
    @POST("interactivePPT-server.php")
    Call<Boolean> sendAnswers(
            @Field("answers") String serializedAnswers,
            @Field("request_type") String requestType
    );

    @FormUrlEncoded
    @POST("interactivePPT-server.php")
    Call<PresentationWithSurveys> getPresentation(
            @Field("access_code") String accessCode,
            @Field("request_type") String requestType
    );

    @FormUrlEncoded
    @POST("interactivePPT-server.php")
    Call<Boolean> saveToken(
            @Field("request_type") String requestType,
            @Field("token") String token,
            @Field("id") String id
    );

    @FormUrlEncoded
    @POST("interactivePPT-server.php")
    Call<Boolean> saveSubscription(
            @Field("request_type") String requestType,
            @Field("path") String path,
            @Field("id") String id
    );

    @FormUrlEncoded
    @POST("interactivePPT-server.php")
    Call<Boolean> checkStatus(
            @Field("request_type") String requestType,
            @Field("id") String id,
            @Field("path") String path
    );
}