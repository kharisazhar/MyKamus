package com.dicoding.kharisazhar.mykamus;

import android.content.Intent;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ProgressBar;

import com.dicoding.kharisazhar.mykamus.db.AppPreference;
import com.dicoding.kharisazhar.mykamus.db.KamusEnglishIndonesiaHelper;
import com.dicoding.kharisazhar.mykamus.db.KamusIndonesiaEnglishHelper;
import com.dicoding.kharisazhar.mykamus.model.KamusModel;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class PreloadActivity extends AppCompatActivity {

    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preload);

        progressBar = findViewById(R.id.progress_bar);

        new LoadData().execute();
    }

    private class LoadData extends AsyncTask<Void, Integer, Void> {
        final String TAG = LoadData.class.getSimpleName();

        KamusEnglishIndonesiaHelper kamusEnglishIndonesiaHelper;
        KamusIndonesiaEnglishHelper kamusIndonesiaEnglishHelper;

        AppPreference appPreference;
        double progress;
        double maxprogress = 100;

        @Override
        protected void onPreExecute() {

            kamusEnglishIndonesiaHelper = new KamusEnglishIndonesiaHelper(PreloadActivity.this);
            kamusIndonesiaEnglishHelper = new KamusIndonesiaEnglishHelper(PreloadActivity.this);
            appPreference = new AppPreference(PreloadActivity.this);
        }

        @Override
        protected Void doInBackground(Void... params) {

            Boolean firstRun = appPreference.getFirstRun();

            if (firstRun) {

                ArrayList<KamusModel> kamusModels = preLoadRaw();
                ArrayList<KamusModel> kamusIndonesiaModels = preLoadIndonesiaRaw();

                kamusEnglishIndonesiaHelper.open();

                progress = 30;
                publishProgress((int) progress);
                Double progressMaxInsert = 80.0;
                Double progressDiff = (progressMaxInsert - progress) / kamusModels.size();

                kamusEnglishIndonesiaHelper.beginTransaction();

                try {
                    for (KamusModel model : kamusModels) {
                        kamusEnglishIndonesiaHelper.insertTransaction(model);
                        progress += progressDiff;
                        publishProgress((int) progress);
                    }
                    // Jika semua proses telah di set success maka akan di commit ke database
                    kamusEnglishIndonesiaHelper.setTransactionSuccess();
                } catch (Exception e) {
                    // Jika gagal maka do nothing
                    Log.e(TAG, "doInBackground: Exception");
                }

                kamusEnglishIndonesiaHelper.endTransaction();

                kamusEnglishIndonesiaHelper.close();

                kamusIndonesiaEnglishHelper.open();

                kamusIndonesiaEnglishHelper.beginTransaction();

                try {
                    for (KamusModel model : kamusIndonesiaModels) {
                        kamusIndonesiaEnglishHelper.insertTransaction(model);
                        progress += progressDiff;
                        publishProgress((int) progress);
                    }
                    // Jika semua proses telah di set success maka akan di commit ke database
                    kamusIndonesiaEnglishHelper.setTransactionSuccess();
                } catch (Exception e) {
                    // Jika gagal maka do nothing
                    Log.e(TAG, "doInBackground: Exception");
                }

                kamusIndonesiaEnglishHelper.endTransaction();

                kamusIndonesiaEnglishHelper.close();


                appPreference.setFirstRun(false);

                publishProgress((int) maxprogress);

            } else {
                try {
                    synchronized (this) {
                        this.wait(2000);

                        publishProgress(50);

                        this.wait(2000);
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
            Intent i = new Intent(PreloadActivity.this, MainActivity.class);
            startActivity(i);
            finish();
        }
    }

    public ArrayList<KamusModel> preLoadRaw() {
        ArrayList<KamusModel> kamusModels = new ArrayList<>();
        String line = null;
        BufferedReader reader;
        try {
            Resources res = getResources();
            InputStream raw_dict = res.openRawResource(R.raw.english_indonesia);

            reader = new BufferedReader(new InputStreamReader(raw_dict));
            int count = 0;
            do {
                line = reader.readLine();
                String[] splitstr = line.split("\t");

                KamusModel kamusModel;

                kamusModel = new KamusModel(splitstr[0], splitstr[1]);
                kamusModels.add(kamusModel);
                count++;
            } while (line != null);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return kamusModels;
    }

    public ArrayList<KamusModel> preLoadIndonesiaRaw() {
        ArrayList<KamusModel> kamusModels = new ArrayList<>();
        String line = null;
        BufferedReader reader;
        try {
            Resources res = getResources();
            InputStream raw_dict = res.openRawResource(R.raw.indonesia_english);

            reader = new BufferedReader(new InputStreamReader(raw_dict));
            int count = 0;
            do {
                line = reader.readLine();
                String[] splitstr = line.split("\t");

                KamusModel kamusModel;

                kamusModel = new KamusModel(splitstr[0], splitstr[1]);
                kamusModels.add(kamusModel);
                count++;
            } while (line != null);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return kamusModels;
    }
}
