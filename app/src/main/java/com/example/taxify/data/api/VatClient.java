package com.example.taxify.data.api;

import com.example.taxify.data.model.Rate;
import com.example.taxify.data.model.Vat;

import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Roman-372 on 3/20/2019
 * Copyright (c) 2019 bjit. All rights reserved.
 * hawladar.roman@bjitgroup.com
 * Last modified $file.lastModified
 */
public class VatClient implements Callback<Vat> {

    private static final String url = "http://jsonvat.com";

    private VatApi api;
    private RateCallback callback;

    public VatClient() {

        HttpLoggingInterceptor logger = new HttpLoggingInterceptor();
        logger.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient http = new OkHttpClient.Builder()
                .addInterceptor(logger)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .client(http)
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        api = retrofit.create(VatApi.class);
    }

    public void loadRates(RateCallback callback) {
        this.callback = callback;
        Call<Vat> call = api.getVat();
        call.enqueue(this);
    }

    @Override
    public void onResponse(Call<Vat> call, Response<Vat> response) {
        if (response.isSuccessful()) {
            Vat vat = response.body();
            callback.onRates(vat.getRates());
        } else {
            callback.onError(response.message());
        }
    }

    @Override
    public void onFailure(Call<Vat> call, Throwable t) {
        callback.onError(t.getMessage());
    }
}
