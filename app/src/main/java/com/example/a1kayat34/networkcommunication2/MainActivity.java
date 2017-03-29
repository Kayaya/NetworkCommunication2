package com.example.a1kayat34.networkcommunication2;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends Activity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_song);
    }


    @Override
    public void onClick(View view) {
        EditText songEditText = (EditText) findViewById(R.id.songEditText);
        String song = songEditText.getText().toString();

        EditText titleEditText = (EditText) findViewById(R.id.artistEditText);
        String title = songEditText.getText().toString();

        EditText yearEditText = (EditText) findViewById(R.id.yearEditText);
        String year = songEditText.getText().toString();

        String postData = "song"+song+"title"+ title + "year"+year;

    }

    class AddHitsAsyncTask extends AsyncTask<String, Void, String> {


        @Override
        protected String doInBackground(String... params) {

            String postData = params[0];
            //Connect to server
            HttpURLConnection conn = null;
            try{
                URL urlobj = new URL("http://www.free-map.org.uk/course/mad/ws/addhit.php");
                conn = (HttpURLConnection) urlobj.openConnection();

                //get ready to write out data
                conn.setDoOutput(true);
                conn.setFixedLengthStreamingMode(postData.length());

                //write out data
                OutputStream out = conn.getOutputStream();
                out.write(postData.getBytes());

                //check if everything is okay
                if(conn.getResponseCode() == 200){
                    //Read response of the server
                    InputStream in = conn.getInputStream();
                    BufferedReader br = new BufferedReader(new InputStreamReader(in));

                    String response = "";
                    String line = br.readLine();
                    while(line !=null) {
                        response += line;
                        line = br.readLine();
                    }
                }

            }
            catch (IOException e){
                return "Error";
            }

            return null;
        }

        @Override
        protected void onPostExecute(String response) {
            TextView results = (TextView) findViewById(R.id.textView);
            results.setText(response);

        }
    }
}
