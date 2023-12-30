package com.example.currencyconvert;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class EXActivity extends AppCompatActivity {

    private final String url = "https://v6.exchangerate-api.com/v6/";
    private final String code = "c55b5fb324cb4ad6b0655506";
    private final String afterCode = "/latest/USD";
    private RequestQueue queue;
    private Spinner spnFrom;
    private Spinner spnTo;
    private Button btnConvertCurrency;
    private TextView txtCurrencyExchangeResult;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exactivity);
        setUpViews();
        queue = Volley.newRequestQueue(this);
        btnConvertCurrency.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnConvertCurrencyOnClick(v);
            }
        });
    }

    private void setUpViews(){
        spnFrom = findViewById(R.id.spnFrom);
        spnTo = findViewById(R.id.spnTo);
        btnConvertCurrency = findViewById(R.id.btnConvertCurrency);
        txtCurrencyExchangeResult = findViewById(R.id.txtCurrencyExchangeResult);
    }

    private void btnConvertCurrencyOnClick(View view) {
        StringRequest request = new StringRequest(Request.Method.GET, url + code + afterCode,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            if ("success".equals(jsonResponse.optString("result"))) {
                                JSONObject conversionRates = jsonResponse.getJSONObject("conversion_rates");

                                String fromCurrencyCode = spnFrom.getSelectedItem().toString();
                                String toCurrencyCode = spnTo.getSelectedItem().toString();

                                double fromCurrencyRate = conversionRates.optDouble(fromCurrencyCode, -1);
                                double toCurrencyRate = conversionRates.optDouble(toCurrencyCode, -1);

                                if (fromCurrencyRate != -1 && toCurrencyRate != -1) {
                                    double amountInFromCurrency = 100.0;
                                    double amountInToCurrency = amountInFromCurrency * (toCurrencyRate / fromCurrencyRate);

                                    // Display the result
                                    txtCurrencyExchangeResult.setText(String.format("%.2f %s = %.2f %s", amountInFromCurrency, fromCurrencyCode, amountInToCurrency, toCurrencyCode));
                                } else {
                                    txtCurrencyExchangeResult.setText("Invalid currency code");
                                }
                            } else {
                                txtCurrencyExchangeResult.setText("Error in response");
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            txtCurrencyExchangeResult.setText("Error parsing JSON");
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                txtCurrencyExchangeResult.setText("Error fetching data. Please try again later.");
            }
        });
        queue.add(request);
    }

}