package co.getchannel.channel.api;

import java.util.Map;

import co.getchannel.channel.CHConfiguration;
import co.getchannel.channel.Channel;
import co.getchannel.channel.helpers.CHConstants;
import co.getchannel.channel.models.Client;
import co.getchannel.channel.responses.CHApplicationInfoResponse;
import co.getchannel.channel.responses.CHClientResponse;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.Headers;

/**
 * Created by Admin on 8/16/2017.
 */

public interface CHAPIInterface {

    @GET("app/info")
    Call<CHApplicationInfoResponse> getApplicationInfo();

    @POST("client")
    Call<CHClientResponse> connectClient(@Body Client client);
}