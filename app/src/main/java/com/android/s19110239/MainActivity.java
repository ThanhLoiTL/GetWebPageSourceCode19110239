package com.android.s19110239;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class MainActivity extends AppCompatActivity {
    EditText txtUrlWeb;
    Button btnGetPageSource;
    TextView pageSource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();

        btnGetPageSource.setOnClickListener(view -> {
            String urlWeb = "https://" + txtUrlWeb.getText().toString();
            new GetPageWeb().execute(urlWeb);
        });

    }
    private void init() {
        txtUrlWeb = findViewById(R.id.txt_url_web);
        btnGetPageSource = findViewById(R.id.btn_get_page_source);
        pageSource = findViewById(R.id.page_source);
    }

    private class GetPageWeb extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... strings) {
            StringBuilder stringBuilder = new StringBuilder();
            URLConnection urlConnection = null;
            try {
                URL url = new URL(strings[0]);
                urlConnection = url.openConnection();
                InputStream inputStream = urlConnection.getInputStream();
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String line = "";
                while ((line = bufferedReader.readLine()) != null) {
                    stringBuilder.append(line + "\n");
                }
                bufferedReader.close();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return stringBuilder.toString();
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            pageSource.setText(s+"");
        }
    }
}