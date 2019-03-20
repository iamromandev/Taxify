package com.example.taxify.data.api;

import com.example.taxify.data.model.Rate;

import java.util.List;

/**
 * Created by Roman-372 on 3/20/2019
 * Copyright (c) 2019 bjit. All rights reserved.
 * hawladar.roman@bjitgroup.com
 * Last modified $file.lastModified
 */
public interface RateCallback {
    void onRates(List<Rate> rates);
}
