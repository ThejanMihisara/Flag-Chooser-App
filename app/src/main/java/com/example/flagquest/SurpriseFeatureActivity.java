package com.example.flagquest;
//SA24610121 - Kahingalage T M
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

import java.util.Iterator;

public class SurpriseFeatureActivity extends AppCompatActivity {
    private EditText etCountryName;
    private Button btnGetDetails;
    private TextView tvCountryDetails;

    private RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_surprise_feature);

        etCountryName = findViewById(R.id.etCountryName);
        btnGetDetails = findViewById(R.id.btnGetDetails);
        tvCountryDetails = findViewById(R.id.tvCountryDetails);

        requestQueue = Volley.newRequestQueue(this);

        btnGetDetails.setOnClickListener(v -> getCountryDetails());
    }
// Refered By Deepseek
    private void getCountryDetails() {
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
                            StringBuilder details = new StringBuilder();

                            String officialName = countryObj.getJSONObject("name").getString("official");
                            details.append("Official Name: ").append(officialName).append("\n\n");

                            String capital = countryObj.has("capital") ?
                                    countryObj.getJSONArray("capital").getString(0) : "No capital";
                            details.append("Capital: ").append(capital).append("\n\n");

                            int population = countryObj.getInt("population");
                            details.append("Population: ").append(String.format("%,d", population)).append("\n\n");

                            String region = countryObj.getString("region");
                            details.append("Region: ").append(region).append("\n\n");

                            if (countryObj.has("languages")) {
                                JSONObject languages = countryObj.getJSONObject("languages");
                                details.append("Languages: ");
                                Iterator<String> keys = languages.keys();
                                while (keys.hasNext()) {
                                    String key = keys.next();
                                    details.append(languages.getString(key));
                                    if (keys.hasNext()) details.append(", ");
                                }
                                details.append("\n\n");
                            }

                            if (countryObj.has("currencies")) {
                                JSONObject currencies = countryObj.getJSONObject("currencies");
                                details.append("Currencies: ");
                                Iterator<String> keys = currencies.keys();
                                while (keys.hasNext()) {
                                    String key = keys.next();
                                    JSONObject currency = currencies.getJSONObject(key);
                                    details.append(currency.getString("name"))
                                            .append(" (")
                                            .append(currency.getString("symbol"))
                                            .append(")");
                                    if (keys.hasNext()) details.append(", ");
                                }
                            }

                            tvCountryDetails.setText(details.toString());
                        } else {
                            tvCountryDetails.setText("Country not found");
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        tvCountryDetails.setText("Error parsing data");
                    }
                },
                error -> tvCountryDetails.setText("Error fetching data"));

        requestQueue.add(request);
    }
}