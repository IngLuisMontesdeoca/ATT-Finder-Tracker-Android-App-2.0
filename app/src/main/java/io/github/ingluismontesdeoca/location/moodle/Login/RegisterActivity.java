package io.github.ingluismontesdeoca.location.moodle.Login;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;

import io.github.ingluismontesdeoca.location.R;

public class RegisterActivity extends AppCompatActivity {

    private ImageView btnCancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        showToolbar();
        /*
        btnCancel = (ImageView) findViewById(R.id.btnClose);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //onBackPressed();
                onSupportNavigateUp();
            }
        });*/

    }

    /*Habilitar toolBar*/
    private void showToolbar(){
        Toolbar tb = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(tb);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
}
