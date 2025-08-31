package com.example.flagquest;
//SA24610049 - A W A S D Abeysekara
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class CapitalFinderActivity extends AppCompatActivity {
    private EditText etCountryName;
    private Button btnFindCapital;
    private TextView tvCapitalResult;
    private RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_capital_finder);

        etCountryName = findViewById(R.id.etCountryName);
        btnFindCapital = findViewById(R.id.btnFindCapital);
        tvCapitalResult = findViewById(R.id.tvCapitalResult);

        requestQueue = Volley.newRequestQueue(this);

        btnFindCapital.setOnClickListener(v -> findCapital());
    }

    private void findCapital() {
        String countryName = etCountryName.getText().toString().trim();
        if (countryName.isEmpty()) {
            Toast.makeText(this, "Please enter a country name", Toast.LENGTH_SHORT).show();
            return;
        }

        String url = "https://restcountries.com/v3.1/name/" + countryName;

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null,
                response -> {
                    try {
                        if (response.length() > 0) {
                            JSONObject countryObj = response.getJSONObject(0);
                            String capital = countryObj.has("capital") ?
                                    countryObj.getJSONArray("capital").getString(0) : "No capital";

                            tvCapitalResult.setText("Capital: " + capital);
                        } else {
                            tvCapitalResult.setText("Country not found");
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        tvCapitalResult.setText("Error parsing data");
                    }
                },
                error -> tvCapitalResult.setText("Error fetching data"));

        requestQueue.add(request);
    }
}