package com.example.taxify.ui;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.taxify.R;
import com.example.taxify.data.api.RateCallback;
import com.example.taxify.data.api.VatClient;
import com.example.taxify.data.model.Period;
import com.example.taxify.data.model.Rate;
import com.hbb20.CountryCodePicker;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatRadioButton;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;
import hugo.weaving.DebugLog;

public class MainActivity
        extends AppCompatActivity
        implements RateCallback, CountryCodePicker.OnCountryChangeListener, TextWatcher, RadioGroup.OnCheckedChangeListener {

    private CountryCodePicker countryPicker;
    private RadioGroup rateGroup;
    private AppCompatEditText editCurrency;
    private AppCompatTextView textVat;
    private VatClient client;
    private Map<String, Rate> rates = new HashMap<>();
    private String countryCodes;
    private boolean currencyEntered;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        loadView();
        calculateAndShowVat();
        client = new VatClient();
        client.loadRates(this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item_country:
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @DebugLog
    @Override
    public void onRates(List<Rate> rates) {
        if (rates == null || rates.isEmpty()) {
            return;
        }

        this.rates.clear();
        StringBuilder codeBuilder = new StringBuilder();
        for (Rate rate : rates) {
            this.rates.put(rate.getCountryCode(), rate);
            if (codeBuilder.length() > 0) {
                codeBuilder.append(",");
            }
            codeBuilder.append(rate.getCountryCode());
        }
        countryCodes = codeBuilder.toString();
        loadView();
        calculateAndShowVat();
    }

    @DebugLog
    @Override
    public void onError(String message) {

    }

    @Override
    public void onCountrySelected() {
        loadView();
        calculateAndShowVat();
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        currencyEntered = true;
        calculateAndShowVat();
    }


    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        calculateAndShowVat();
    }


    private void initView() {
        setContentView(R.layout.activity_main);
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        countryPicker = findViewById(R.id.picker_country);
        rateGroup = findViewById(R.id.group_rate_types);
        editCurrency = findViewById(R.id.edit_currency);
        textVat = findViewById(R.id.text_vat_amount);

        editCurrency.addTextChangedListener(this);
        countryPicker.setOnCountryChangeListener(this);
        rateGroup.setOnCheckedChangeListener(this);
    }

    private void loadView() {
        if (rates.isEmpty()) {
            return;
        }
        countryPicker.setCustomMasterCountries(countryCodes);
        rateGroup.removeAllViews();
        String code = countryPicker.getSelectedCountryNameCode();
        Rate rate = rates.get(code);
        List<Period> periods = rate.getPeriods();
        Period period = periods.get(0);
        Collection<Double> periodRates = period.getRates().values();
        for (Double periodRate : periodRates) {
            AppCompatRadioButton radio = new AppCompatRadioButton(this);
            radio.setId(View.generateViewId());
            radio.setText(String.valueOf(periodRate));
            radio.setTag(periodRate);
            rateGroup.addView(radio);
        }
        rateGroup.check(rateGroup.getChildAt(0).getId());
    }

    private void calculateAndShowVat() {
        textVat.setText(null);
        if (!currencyEntered) {
            return;
        }
        if (rates.isEmpty()) {
            return;
        }
        String currencyText = editCurrency.getText().toString();
        if (TextUtils.isEmpty(currencyText)) {
            editCurrency.setError("Currency shouldn't be zero.");
            return;
        }
        double currency = Double.parseDouble(currencyText);
        RadioButton selectedButton = findViewById(rateGroup.getCheckedRadioButtonId());
        double rate = Double.parseDouble(selectedButton.getText().toString());

        double amount = (rate/100.0)*currency;

        textVat.setText(String.valueOf(amount));
    }
}
