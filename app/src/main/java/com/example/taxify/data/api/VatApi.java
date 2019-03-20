package com.example.taxify.data.api;

import com.example.taxify.data.model.Vat;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by Roman-372 on 3/20/2019
 * Copyright (c) 2019 bjit. All rights reserved.
 * hawladar.roman@bjitgroup.com
 * Last modified $file.lastModified
 */
public interface VatApi {

    @GET("/")
    Call<Vat> getVat();
}
