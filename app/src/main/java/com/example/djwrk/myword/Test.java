package com.example.djwrk.myword;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public class Test extends Activity {
    TextView m0, m1, m2, m3, m4, m5, m6, m7, m8, m9;
    TextView w0, w1, w2, w3, w4, w5, w6, w7, w8, w9;
    static String[] words = new String[200];
    static String[] meaning = new String[200];
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wordlist);
        final TextView w0 = (TextView)findViewById(R.id.w0);
        final TextView w1 = (TextView)findViewById(R.id.w1);
        final TextView w2 = (TextView)findViewById(R.id.w2);
        final TextView w3 = (TextView)findViewById(R.id.w3);
        final TextView w4 = (TextView)findViewById(R.id.w4);
        final TextView w5 = (TextView)findViewById(R.id.w5);
        final TextView w6 = (TextView)findViewById(R.id.w6);
        final TextView w7 = (TextView)findViewById(R.id.w7);
        final TextView w8 = (TextView)findViewById(R.id.w8);
        final TextView w9 = (TextView)findViewById(R.id.w9);

        final TextView m0 = (TextView)findViewById(R.id.m0);
        final TextView m1 = (TextView)findViewById(R.id.m1);
        final TextView m2 = (TextView)findViewById(R.id.m2);
        final TextView m3 = (TextView)findViewById(R.id.m3);
        final TextView m4 = (TextView)findViewById(R.id.m4);
        final TextView m5 = (TextView)findViewById(R.id.m5);
        final TextView m6 = (TextView)findViewById(R.id.m6);
        final TextView m7 = (TextView)findViewById(R.id.m7);
        final TextView m8 = (TextView)findViewById(R.id.m8);
        final TextView m9 = (TextView)findViewById(R.id.m9);
        int i =  1;
        w0.setText(words[i*10 + 0]);
        m0.setText(meaning[i*10 + 0]);
        w1.setText(words[i*10 + 1]);
        m1.setText(meaning[i*10 + 1]);
        w2.setText(words[i*10 + 2]);
        m2.setText(meaning[i*10 + 2]);
        w3.setText(words[i*10 + 3]);
        m3.setText(meaning[i*10 + 3]);
        w4.setText(words[i*10 + 4]);
        m4.setText(meaning[i*10 + 4]);
        w5.setText(words[i*10 + 5]);
        m5.setText(meaning[i*10 + 5]);
        w6.setText(words[i*10 +6]);
        m6.setText(meaning[i*10 + 6]);
        w7.setText(words[i*10 + 7]);
        m7.setText(meaning[i*10 + 7]);
        w8.setText(words[i*10 + 8]);
        m8.setText(meaning[i*10 + 8]);
        w9.setText(words[i*10 + 9]);
        m9.setText(meaning[i*10 + 9]);

    }
    private List<WordSample> wordSamples = new ArrayList<>();

    private void readWordData() {
        InputStream inS = getResources().openRawResource(R.raw.word);
        BufferedReader reader = new BufferedReader(
                new InputStreamReader(inS, Charset.forName("UTF-8"))
        );

        String line = "";
        try {
            // Step over headers
            //reader.readLine();
            int count = 0;
            while ((line = reader.readLine()) != null) {
                Log.d("MyAcitivity", "Line: " + line);

                // Split by ','
                String[] tokens = line.split(",");

                // read the data
                WordSample sample = new WordSample();
                words[count] = tokens[0];
                meaning[count] = tokens[1];
                sample.setWord(tokens[0]);
                sample.setMeaning(tokens[1]);

                wordSamples.add(sample);

                Log.d("MyAcitivity", "Just create: " + sample);
                count++;

            }
        } catch (IOException e) {
            Log.wtf("MyActivity", "Error reading data file on line" + line, e);
            e.printStackTrace();
        }


    }
}
