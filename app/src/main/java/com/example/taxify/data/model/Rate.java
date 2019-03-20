package com.example.taxify.data.model;

import java.util.List;

/**
 * Created by Roman-372 on 3/20/2019
 * Copyright (c) 2019 bjit. All rights reserved.
 * hawladar.roman@bjitgroup.com
 * Last modified $file.lastModified
 */
public class Rate {
    private String name;
    private String code;
    private String countryCode;
    private List<Period> periods;

    public String getName() {
        return name;
    }

    public String getCode() {
        return code;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public List<Period> getPeriods() {
        return periods;
    }
}
