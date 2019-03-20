package com.example.taxify.ui;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.example.taxify.R;
import com.example.taxify.data.api.RateCallback;
import com.example.taxify.data.api.VatClient;
import com.example.taxify.data.model.Rate;
import com.hbb20.CountryCodePicker;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import hugo.weaving.DebugLog;

public class MainActivity extends AppCompatActivity implements RateCallback {

    private CountryCodePicker countryPicker;
    private VatClient client;
    private Map<String, Rate> rates = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        initView();
        client = new VatClient();
        client.loadRates(this);
    }

/*    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }*/

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
        for (Rate rate : rates) {
            this.rates.put(rate.getCountryCode(), rate);
        }
    }

    @DebugLog
    @Override
    public void onError(String message) {

    }

    private void initView() {
        countryPicker = findViewById(R.id.picker_country);

    }
}
