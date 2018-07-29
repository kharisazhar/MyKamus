package com.dicoding.kharisazhar.mykamus;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class DetailActivity extends AppCompatActivity {

    TextView tvDetailWord, tvDetailTranslate;
    String detailWord, detailTransalte;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        tvDetailWord = findViewById(R.id.tv_detail_word);
        tvDetailTranslate = findViewById(R.id.tv_detail_translate);

        Intent intent = getIntent();
        detailWord = intent.getStringExtra("EXTRA_WORD");
        detailTransalte = intent.getStringExtra("EXTRA_TRANSLATE");

        tvDetailWord.setText(detailWord);
        tvDetailTranslate.setText(detailTransalte);
    }
}
