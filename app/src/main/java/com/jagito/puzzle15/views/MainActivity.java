package com.jagito.puzzle15.views;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import com.jagito.puzzle15.R;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.main_play).setOnClickListener(v-> startActivity(new Intent(this,GameActivity.class)));
        findViewById(R.id.main_about).setOnClickListener(v-> startActivity(new Intent(this,AboutActivity.class)));
        findViewById(R.id.main_exit).setOnClickListener(v-> finishAffinity());
    }
}
