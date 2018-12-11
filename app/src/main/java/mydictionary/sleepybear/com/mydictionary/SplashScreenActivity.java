/*
 * Copyright (c) 2018. Rosdyana Kusuma.
 */

package mydictionary.sleepybear.com.mydictionary;

import android.content.Intent;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ProgressBar;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import mydictionary.sleepybear.com.mydictionary.db.DictionaryHelper;
import mydictionary.sleepybear.com.mydictionary.model.DictionaryModel;
import mydictionary.sleepybear.com.mydictionary.utils.AppPreference;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class SplashScreenActivity extends AppCompatActivity {
    @BindView(R.id.progress_bar)
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_splash_screen);
        ButterKnife.bind(this);

        new LoadData().execute();
    }

    private class LoadData extends AsyncTask<Void, Integer, Void> {
        final String TAG = LoadData.class.getSimpleName();
        DictionaryHelper dictionaryHelper;
        AppPreference appPreference;
        double progress;
        double maxprogress = 100;

        @Override
        protected void onPreExecute() {

            dictionaryHelper = new DictionaryHelper(SplashScreenActivity.this);
            appPreference = new AppPreference(SplashScreenActivity.this);
        }

        @Override
        protected Void doInBackground(Void... voids) {
            boolean isFirstRun = appPreference.isFirstRun();

            if(isFirstRun){
                ArrayList<DictionaryModel> dictionaryENtoID = preLoadRaw(R.raw.english_indonesia);
                ArrayList<DictionaryModel> dictionaryIDtoEN = preLoadRaw(R.raw.indonesia_english);

                dictionaryHelper.open();
                progress = 50;
                publishProgress((int) progress);
                Double progressMaxInsert = 50.0;
                Double progressDiff = (progressMaxInsert - progress) / (dictionaryENtoID.size()+dictionaryIDtoEN.size());

                dictionaryHelper.insertTransaction(dictionaryENtoID, true);
                progress += progressDiff;
                publishProgress((int) progress);

                dictionaryHelper.insertTransaction(dictionaryIDtoEN, false);
                progress += progressDiff;
                publishProgress((int) progress);

                dictionaryHelper.close();

                appPreference.setFirstRun(false);

                publishProgress((int) maxprogress);

            } else {
                try {
                    synchronized (this) {
                        this.wait(666);
                        publishProgress(50);
                        publishProgress((int) maxprogress);
                    }
                } catch (Exception e) {
                }
            }
            return null;
        }
        @Override
        protected void onProgressUpdate(Integer... values) {
            progressBar.setProgress(values[0]);
        }

        @Override
        protected void onPostExecute(Void result) {
            Intent i = new Intent(SplashScreenActivity.this, HomeActivity.class);
            startActivity(i);
            finish();
        }
    }

    private ArrayList<DictionaryModel> preLoadRaw(int rawdata) {
        ArrayList<DictionaryModel> dictionaryModels = new ArrayList<>();
        String line = null;
        BufferedReader reader;
        try {
            Resources res = getResources();
            InputStream raw_dict = res.openRawResource(rawdata);

            reader = new BufferedReader(new InputStreamReader(raw_dict));
            int count = 0;
            do {
                line = reader.readLine();
                String[] splitstr = line.split("\t");

                DictionaryModel dictionaryModel;

                dictionaryModel = new DictionaryModel(splitstr[0], splitstr[1]);
                dictionaryModels.add(dictionaryModel);
                count++;
            } while (line != null);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dictionaryModels;
    }
}
