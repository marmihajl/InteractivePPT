package hr.foi.air.interactiveppt.webservice;

import android.os.AsyncTask;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by zeko868 on 12.1.2017..
 */

public class SendDataAndProcessResponseTask extends AsyncTask {

    @Override
    protected Object doInBackground(Object[] params) {
        Call<?> consumerSend = (Call<?>) params[0];
        Callback<Object> callback;
        if (params.length == 2) {
            final PostActions postActions = (PostActions) params[1];
            callback = new Callback<Object>() {
                @Override
                public void onResponse(Call<Object> call, Response<Object> response) {
                    postActions.onSuccess(response.body());
                }

                @Override
                public void onFailure(Call<Object> call, Throwable t) {
                    postActions.onFailure();
                }
            };
        }
        else {
            callback = new Callback<Object>() {
                @Override
                public void onResponse(Call<Object> call, Response<Object> response) {
                }

                @Override
                public void onFailure(Call<Object> call, Throwable t) {
                }
            };
        }
        ((Call) consumerSend).enqueue((Callback) callback);
        return null;
    }

    public interface PostActions {
        void onSuccess(Object response);
        void onFailure();
    }

    public SendDataAndProcessResponseTask(Call<?> consumerSend, PostActions postActions) {
        this.execute(consumerSend, postActions);
    }

    public SendDataAndProcessResponseTask(Call<?> consumerSend) {
        this.execute(consumerSend);
    }
}