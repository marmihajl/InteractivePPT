package hr.air.interactiveppt.webservice;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Toast;

import java.lang.reflect.Type;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.function.BiConsumer;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by zeko868 on 26.11.2016..
 */
public class CommunicationHandler {

    public static <S> void SendDataAndProcessResponse(Call<S> consumerSend, final BiConsumer<Call<S>, Response<S>> consumerSuccess, final BiConsumer<Call<S>, Throwable> consumerError, final boolean forcePerformTaskOnMainThread, final Context baseContext) {

        if (consumerSend != null) {
            Callback<S> callback = new Callback<S>() {

                @Override
                public void onResponse(final Call<S> call, final Response<S> response) {
                    if (response.isSuccessful()) {
                        if (forcePerformTaskOnMainThread == true) {
                            Handler mainHandler = new Handler(baseContext.getMainLooper());
                            Runnable myRunnable = new Runnable() {
                                @Override
                                public void run() {
                                    consumerSuccess.accept(call, response);
                                }
                            };
                            mainHandler.post(myRunnable);
                        } else {
                            consumerSuccess.accept(call, response);
                        }
                    }

                }

                @Override
                public void onFailure(final Call<S> call, final Throwable t) {
                    if (forcePerformTaskOnMainThread == true) {
                        Handler mainHandler = new Handler(baseContext.getMainLooper());
                        Runnable myRunnable = new Runnable() {
                            @Override
                            public void run() {
                                consumerError.accept(call, t);
                            }
                        };
                        mainHandler.post(myRunnable);
                    } else {
                        consumerError.accept(call, t);
                    }
                }
            };
            new RetrieveFeedTask().execute(consumerSend, callback);
        }

    }

}

class RetrieveFeedTask extends AsyncTask {

    private Exception exception;

    @Override
    protected Object doInBackground(Object[] params) {
        Call<?> consumerSend = (Call<?>)params[0];
        Callback<?> callback = (Callback<?>)params[1];
        ((Call)consumerSend).enqueue((Callback)callback);
        return null;
    }
}

