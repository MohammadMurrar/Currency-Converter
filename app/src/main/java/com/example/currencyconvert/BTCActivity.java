package com.example.currencyconvert;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
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

public class BTCActivity extends AppCompatActivity {
    //https://www.alphavantage.co/query?function=CURRENCY_EXCHANGE_RATE&from_currency=BTC&to_currency=CNY&apikey=AVPCKO3WG4QVFRRS
    private final String url = "https://www.alphavantage.co/query?function=CURRENCY_EXCHANGE_RATE&from_currency=BTC&to_currency=";
    private final String code = "AVPCKO3WG4QVFRRS";
    private RequestQueue queue;
    private Spinner spnBTC_Converter;
    private Button btnBTC_Convert;
    private TextView txtBTC_Result;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_btcactivity);
        setUpViews();
        queue = Volley.newRequestQueue(this);

        btnBTC_Convert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnBTC_ConvertOnClick(v);
            }
        });
    }
    private void setUpViews(){
        spnBTC_Converter = findViewById(R.id.spnBTC_Converter);
        btnBTC_Convert = findViewById(R.id.btnBTC_Convert);
        txtBTC_Result = findViewById(R.id.txtBTC_Result);
    }

    private void btnBTC_ConvertOnClick(View view){
        String currency = "";
        currency = spnBTC_Converter.getSelectedItem().toString();
        if(currency.equals("SELECT CURRENCY")){
            txtBTC_Result.setText("Choose CURRENCY");
        }
        else {
            String str = url+currency+"&apikey="+code;
            StringRequest request = new StringRequest(Request.Method.GET, str,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            String result = "";
                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                JSONObject mainObject = jsonObject.getJSONObject("Realtime Currency Exchange Rate");
                                String currencyCode = mainObject.getString("3. To_Currency Code");
                                String currencyName = mainObject.getString("4. To_Currency Name");
                                String exchangeRate = mainObject.getString("5. Exchange Rate");
                                result = "Currency Code \n" + currencyCode + "\n" + "---------------------"+
                                        "\nCurrency Name: \n" + currencyName + "\n" + "---------------------"+
                                        "\nExchange Rate: \n" + exchangeRate;
                                txtBTC_Result.setText(result);
                            } catch (JSONException e) {
                                throw new RuntimeException(e);
                            }

                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    txtBTC_Result.setText("Error fetching data. Please try again later.");
                }
            });
            queue.add(request);
        }
    }
}