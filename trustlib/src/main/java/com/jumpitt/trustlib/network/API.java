package com.jumpitt.trustlib.network;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by felipe on 15-03-18.
 * <p>
 * This class contains the api methods definition
 */

public interface API {
    //Insert methods here

    @POST("trifle")
    Call<TrifleResponse> trifle(@Body TrifleBody body);
}
