package hr.foi.air.interactiveppt.webservice;

import hr.foi.air.interactiveppt.entities.ListOfSurveys;
import hr.foi.air.interactiveppt.entities.PresentationWithSurveys;
import hr.foi.air.interactiveppt.entities.SurveyWithQuestions;
import hr.foi.air.interactiveppt.entities.ListOfPresentations;
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
            @Field("name") String name,
            @Field("token") String token
    );

    @FormUrlEncoded
    @POST("interactivePPT-server.php")
    Call<SurveyWithQuestions> getSurvey(
            @Field("survey_id") String surveyId,
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

    @FormUrlEncoded
    @POST("interactivePPT-server.php")
    Call<Boolean> saveReplyRequest(
            @Field("request_type") String requestType,
            @Field("path") String path,
            @Field("id") String id
    );

    @FormUrlEncoded
    @POST("interactivePPT-server.php")
    Call<ListOfSurveys> getSurveyList(
            @Field("request_type") String requestType,
            @Field("ppt_path") String path
    );

    @FormUrlEncoded
    @POST("interactivePPT-server.php")
    Call<Boolean> sendChatMessage(
            @Field("request_type") String requestType,
            @Field("pptid") int pptId,
            @Field("userid") String uid,
            @Field("content") String messageContent
    );

    @FormUrlEncoded
    @POST("interactivePPT-server.php")
    Call<Boolean> killConnection(
            @Field("request_type") String requestType,
            @Field("pptid") int pptId,
            @Field("userid") String uid
    );
}